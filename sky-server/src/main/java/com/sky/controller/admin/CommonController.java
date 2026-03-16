package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequestMapping("/admin/common")
@RestController
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result upload(MultipartFile file) {
        // 获取原始文件名
        String originalFilename = file.getOriginalFilename();
        log.info("文件上传中，原始文件名： {}", originalFilename);

        // 获取文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 上传文件至 OSS，获取文件路径
        String url = null;
        try {
            // 调用 Ali0ssUtil 工具类的 upload 上传方法
            String objectName = UUID.randomUUID().toString() + suffix;
            url = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(url);
        } catch (IOException e) {
            log.info("文件上传失败：{}", e.getMessage());
            return Result.error("文件上传失败");
        }
    }
}
