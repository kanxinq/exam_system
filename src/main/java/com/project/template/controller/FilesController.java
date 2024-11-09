package com.project.template.controller;

import cn.hutool.core.io.FileUtil;
import com.project.template.common.Result;
import com.project.template.dto.FileInfoDTO;
import com.project.template.exception.CustomException;
import com.project.template.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/file")
@Slf4j
public class FilesController {

    @Resource
    private FileService fileService;
//    定义一个文件服务接口的实例变量，用于调用服务层的方法

    @PostMapping("/upload/{bucket}")
//    请求路径为/file/upload/{bucket}，其中{bucket}是一个路径变量
    public Result uploads(@RequestParam MultipartFile file, @PathVariable String bucket) throws IOException {
        return new Result().success(fileService.upload(file, bucket));
    }
//    方法接收一个文件上传对象和一个桶名称作为请求参数，通过调用文件服务的upload方法上传文件，并返回一个封装了上传结果的统一响应结果对象

    @GetMapping("/{bucket}/**")
//    请求路径为/file/{bucket}/**，其中{bucket}是一个路径变量，可以匹配任意深度的路径
    public ResponseEntity<Object> get(@PathVariable("bucket") String bucket,
                                      @RequestParam(value = "width", required = false) Integer width,
                                      @RequestParam(value = "height", required = false) Integer height,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws IOException {
//        方法接收桶名称、可选的宽度和高度参数、HTTP 请求对象和 HTTP 响应对象作为请求参数，用于处理文件下载请求
        String key = extractKeyFromRequest(request, bucket);
//        从请求路径中提取文件的键（路径）
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
//        创建一个文件信息数据传输对象
        fileInfoDTO.setKey(key);
//        设置文件信息对象的键
        fileInfoDTO.setBucket(bucket);
//        设置文件信息对象的桶名称

        try {
            File file = fileService.getFile(fileInfoDTO, width, height);
            return handleRangeRequest(request, response, file);
        } catch (FileNotFoundException e) {
            handleFileNotFound(response, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        尝试获取文件并处理范围请求，如果文件未找到则处理异常
    }

    private String extractKeyFromRequest(HttpServletRequest request, String bucket) {
        return request.getRequestURI().replaceFirst("/file/" + bucket + "/", "/");
    }
//从请求路径中提取文件的键，通过将请求路径中的特定部分替换为/来实现

    private ResponseEntity<Object> handleRangeRequest(HttpServletRequest request, HttpServletResponse response, File file) throws IOException {
        String range = request.getHeader(HttpHeaders.RANGE);
//        获取请求头中的范围信息
        if (range == null) {
            setResponseHeaders(response, file);
            writeContentToResponse(response, file);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        long fileLength = file.length();
        long start = 0;
        long end = fileLength - 1;

        Pattern pattern = Pattern.compile("bytes=(\\d+)-(\\d*)");
//        定义正则表达式模式
        Matcher matcher = pattern.matcher(range);
//        创建匹配器对象并进行匹配
        if (matcher.find()) {
            start = Long.parseLong(matcher.group(1));
            if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
                end = Long.parseLong(matcher.group(2));
            }
        }
        if (end > fileLength - 1) {
            end = fileLength - 1;
        }
        long contentLength = end - start + 1;
//如果匹配成功，提取起始位置start和结束位置end。如果结束位置未指定或为空，默认为文件长度减 1。如果结束位置超出文件长度，则调整为文件长度减 1
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
//        设置响应状态为部分内容（206）
        response.setHeader(HttpHeaders.ACCEPT_RANGES, "bytes");
//        表示服务器接受字节范围请求
        response.setHeader(HttpHeaders.CONTENT_RANGE, "bytes " + start + "-" + end + "/" + fileLength);
//        设置响应的内容范围信息
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(contentLength));
//设置响应的内容长度为请求的部分内容长度
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
//            随机访问文件，定位到起始位置，读取部分内容并写入响应的输出流
            randomAccessFile.seek(start);
            byte[] buffer = new byte[8192];
            int bytesRead;
            ServletOutputStream outputStream = response.getOutputStream();
            while ((bytesRead = randomAccessFile.read(buffer)) != -1 && contentLength > 0) {
//                循环读取文件内容并写入输出流，直到读取完请求的部分内容或到达文件末尾
                outputStream.write(buffer, 0, (int) Math.min(bytesRead, contentLength));
                contentLength -= bytesRead;
            }
            outputStream.flush();
//            将输出流中的缓冲数据立即发送出去
        }

        return new ResponseEntity<>(HttpStatus.PARTIAL_CONTENT);
//        返回一个状态为部分内容（206）的响应实体
    }
//    处理范围请求，如果请求包含范围头，则进行部分内容响应；否则，进行完整内容响应
//    如果范围信息为空，设置响应头并将文件内容写入响应，返回HttpStatus.OK的响应实体。
//    如果范围信息不为空，解析范围信息，设置响应头，使用随机访问文件读取文件内容并根据范围进行部分内容响应，返回HttpStatus.PARTIAL_CONTENT的响应实体
    private void setResponseHeaders(HttpServletResponse response, File file) {
        // response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName());
        response.setContentType(getMimeType(file));
        response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
    }
//    设置响应头，包括内容处置、内容类型和内容长度

    private String getMimeType(File file) {
        return new MimetypesFileTypeMap().getContentType(file);
    }
//    使用MimetypesFileTypeMap获取文件的 MIME 类型

    private void writeContentToResponse(HttpServletResponse response, File file) throws IOException {
        try (ServletOutputStream os = response.getOutputStream()) {
            os.write(FileUtil.readBytes(file));
            os.flush();
        } catch (IOException e) {
            throw new CustomException("Error writing file to response output stream");
        }
    }
//将文件内容写入响应输出流，如果出现异常则抛出自定义异常
    private void handleFileNotFound(HttpServletResponse response, FileNotFoundException e) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
        log.error("File download failed, file not found：{}", e.getMessage());
    }
//    处理文件未找到的情况，发送错误响应并记录日志
}

