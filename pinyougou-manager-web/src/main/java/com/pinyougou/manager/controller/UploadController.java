package com.pinyougou.manager.controller;

import com.common.pojo.Result;
import com.common.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author mawenlong
 * @date 2018/08/12
 * describe: 文件上传
 */
@RestController
public class UploadController {

    @Value("${FILE_SERVER_URL}")
    private String FILE_SERVER_URL;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
        try {
            FastDFSClient client = new FastDFSClient("classpath:config/fdfs_client.conf");
            String fileId = client.uploadFile(file.getBytes(), extName);
            String url = FILE_SERVER_URL + fileId;
            return new Result(true, url);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "上传失败");
        }
    }
}
