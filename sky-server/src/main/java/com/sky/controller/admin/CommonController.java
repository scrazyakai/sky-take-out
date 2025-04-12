package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/admin/common")
@Api(tags = "文件上传")
public class CommonController {
    @Autowired  AliOssUtil aliOssUtil;
    /**
     * 文件上传
     *
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("上传文件{}", file.getOriginalFilename());
        try {
            String originalFilename = file.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            String extension = originalFilename.substring(index);
            String objectName = UUID.randomUUID().toString() + extension;
            String filePath = aliOssUtil.upload(file.getBytes(),objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败" + e.getMessage());
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}

