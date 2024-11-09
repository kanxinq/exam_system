package com.project.template.service;

import com.project.template.dto.FileInfoDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface FileService {

    FileInfoDTO upload(MultipartFile file, String bucket) throws IOException;
//方法用于文件上传操作
//    接受一个MultipartFile类型的参数file，表示上传的文件。MultipartFile是 Spring 框架提供的用于处理文件上传的对象，包含了上传文件的信息和内容
//    还接受一个String类型的参数bucket，表示文件存储的桶名称或目录等
//    方法声明抛出IOException异常，表明在文件上传过程中可能出现输入输出异常，需要在调用该方法的地方进行处理
//    返回一个FileInfoDTO类型的对象，包含上传文件的信息，如文件路径、文件名、大小等
    File getFile(FileInfoDTO fileInfoDTO, Integer width, Integer height) throws IOException;
//    方法用于获取文件。
//接受一个FileInfoDTO类型的参数fileInfoDTO，其中包含了要获取的文件的信息，包括文件路径、文件名等。
//还接受两个可选的整数参数width和height
//方法声明抛出IOException异常，因为在获取文件的过程中可能出现输入输出异常。
//返回一个File类型的对象，表示获取到的文件
}
