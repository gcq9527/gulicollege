package com.atguigu.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * @author gcq
 * @Create 2020-09-13
 */

public interface OssService {
    public String uploadFileAvatar(MultipartFile file) ;
}