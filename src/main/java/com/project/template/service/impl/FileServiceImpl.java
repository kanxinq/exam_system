package com.project.template.service.impl;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.net.url.UrlPath;
import com.project.template.dto.FileInfoDTO;
import com.project.template.exception.CustomException;
import com.project.template.service.FileService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {


    @Value("${server.ip}")
//    从配置文件中读取名为server.ip的属性值，并注入到serverIp变量中
    private String serverIp;
//    定义一个私有字符串变量，用于存储服务器的 IP 地址
    @Value("${server.port}")
//    从配置文件中读取名为server.port的属性值，并注入到serverPort变量中
    private int serverPort;
//    定义一个私有整数变量，用于存储服务器的端口号
    @Value("${files.uploads.path}")
//    从配置文件中读取名为files.uploads.path的属性值，并注入到basePath变量中
    private String basePath;
//    定义一个私有字符串变量，用于存储文件上传的基础路径


    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("/yyyy/MM/dd/");
//    定义一个不可变的日期时间格式化器，用于格式化日期为特定的目录结构格式

    public FileInfoDTO upload(MultipartFile file, String bucket) throws IOException {
//        实现了FileService接口中的upload方法，用于文件上传操作
        checkBucket(bucket);
//        调用checkBucket方法检查上传的桶（可能是存储目录或其他逻辑上的分区）的合法性和权限
        //获取上传文件扩展名
        String fix = FilenameUtils.getExtension(file.getOriginalFilename());
        if (StringUtils.isBlank(fix)) {
//            如果扩展名为空，抛出自定义异常
            throw new CustomException("文件扩展名不能为空");
        }
        checkFix(fix);
//        调用checkFix方法检查文件扩展名的合法性和权限
        String newFileName = UUID.randomUUID() + "." + fix;
//        生成一个随机的文件名，包括扩展名
        FileInfoDTO fileInfoDTO = new FileInfoDTO();
//        创建一个文件信息数据传输对象
        fileInfoDTO.setAlt(file.getOriginalFilename());
//        设置文件信息对象的备用名称为上传文件的原始文件名
        File newFile = createFile(bucket, newFileName, fileInfoDTO);
//        调用createFile方法创建一个新的文件对象，并设置文件信息对象的相关属性

        // 直接转移文件到指定路径
        file.transferTo(newFile);
        return fileInfoDTO;
//        返回文件信息对象，其中包含上传文件的相关信息
    }

    public File getFile(FileInfoDTO fileInfoDTO, Integer width, Integer height) throws IOException {
//        实现了FileService接口中的getFile方法，用于获取文件
        File file = new File(fileInfoDTO.buildFilePath(basePath));
        if (width != null && height != null) {
            String fix = FilenameUtils.getExtension(file.getName());
//            获取文件的扩展名
            String resizeFileName = replaceLastOccurrence(file.getName(), "." + fix, "_" + width + "_" + height + "." + fix);
//            生成一个新的文件名，用于存储调整尺寸后的文件
            File resizeFile = new File(file.getAbsolutePath() + resizeFileName);
//            创建一个新的文件对象，表示调整尺寸后的文件
            if (!resizeFile.exists()) {
//                如果调整尺寸后的文件不存在，则调用resizeImage方法调整图片尺寸并保存为新文件
                resizeImage(file, resizeFile, width, height);
            }
            return resizeFile;
//            返回调整尺寸后的文件对象
        }
        return file;
//        直接返回原始文件对象
    }


    /**
     * 根据指定的宽度和高度调整图片尺寸，并保存为新文件。
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param width      目标宽度
     * @param height     目标高度
     * @throws IOException 处理图片时可能发生的I/O异常
     */
    public void resizeImage(File sourceFile, File targetFile, int width, int height) throws IOException {
//        用于根据指定的宽度和高度调整图片尺寸，并保存为新文件

        // 使用ByteArrayOutputStream保存调整后的图片数据到内存
//        创建一个字节数组输出流，用于保存调整后的图片数据到内存
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(sourceFile)
                .size(width, height)
                .keepAspectRatio(true) // 保持原图的宽高比
                .toOutputStream(outputStream);
//        使用 Thumbnailator 库调整图片尺寸，并将结果写入到字节数组输出流中

//        将内存中的字节数组写入到目标文件中，如果文件不存在则创建，覆盖现有文件内容，并设置写入权限
        Files.write(targetFile.toPath(), outputStream.toByteArray(),
                StandardOpenOption.CREATE, // 如果文件不存在则创建
                StandardOpenOption.TRUNCATE_EXISTING, // 覆盖现有文件内容
                StandardOpenOption.WRITE); // 写入权限
    }

    private String getServer() {
//        用于构建服务器的 URL，包括协议、IP 地址、端口号和路径
        String buildUrl = UrlBuilder.create()
                .setScheme("http")
                .setHost(serverIp)
                .setPort(serverPort)
                .addPath("file")
                .build();
//        使用 Hutool 的 URL 构建器构建 URL
        return buildUrl;
//        返回构建好的服务器 URL

    }

    private void checkBucket(String bucket) {
        //TODO 检测权限以及合法性
    }

    private void checkFix(String fix) {
        //TODO 检测权限以及合法性
    }

    private File createFile(String bucket, String fileName, FileInfoDTO fileInfoDTO) throws IOException {
//        用于创建一个新的文件对象，并设置文件信息对象的相关属性
        LocalDate currentDate = LocalDate.now();
//        获取当前日期

        // 格式化日期，用于创建目录结构
        String dateTimeFormatterStr = currentDate.format(dateTimeFormatter);

        fileInfoDTO.setServer(getServer());
//        设置文件信息对象的服务器 URL
        fileInfoDTO.setBucket(bucket);
//        设置文件信息对象的桶名称
        fileInfoDTO.setKey(dateTimeFormatterStr + fileName);
//        设置文件信息对象的键（可能是文件在存储系统中的唯一标识）
        File file = new File(fileInfoDTO.buildFilePath(basePath));
//        创建一个文件对象，根据基础路径和文件信息对象构建文件路径。

        File parentFile = file.getParentFile();
//        获取文件的父目录
        if (!parentFile.exists()) {
//            如果父目录不存在，则创建它
            parentFile.mkdirs();
        }
        return file;
//        返回创建的文件对象
    }

    /**
     * 替换字符串中最后一个匹配到的字符。
     *
     * @param originalStr 原始字符串
     * @param targetChar  要替换的目标字符
     * @param replacement 替换字符或字符串
     * @return 替换后的字符串
     */
    public static String replaceLastOccurrence(String originalStr, String targetChar, String replacement) {
//        静态方法，用于替换字符串中最后一个匹配到的字符
        int index = originalStr.lastIndexOf(targetChar);
//        查找目标字符在原始字符串中的最后一个出现位置
        if (index == -1) {
            return originalStr; // 如果没有找到目标字符，返回原字符串
        }
        return originalStr.substring(0, index) + replacement;
//        返回替换后的字符串，将原始字符串中最后一个目标字符替换为指定的替换字符或字符串
    }
}



