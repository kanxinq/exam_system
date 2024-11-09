package com.project.template.dto;

import lombok.Data;

import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class FileInfoDTO {
//定义一个名为FileInfoDTO的数据传输对象类
    private String server;
//    定义一个私有字符串类型的字段，表示文件服务器的地址
    private String key;
//    定义一个私有字符串类型的字段，可能表示文件在服务器上的唯一标识或路径
    private String bucket;
//    定义一个私有字符串类型的字段，可能表示文件存储的桶或目录
    private String alt;
//定义一个私有字符串类型的字段，可能表示文件的备用名称或描述
    public String getUrl() {
        return server + "/" + bucket + key;
    }
//    定义一个方法，用于构建文件的完整 URL

    public String buildFilePath(String basePath) {
        return Paths.get(basePath, bucket, key).toString();
    }
//定义一个方法，用于构建文件在本地文件系统中的完整路径

}
