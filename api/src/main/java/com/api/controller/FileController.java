package com.api.controller;

import com.api.config.JsonResult;
import com.api.pojo.FileInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("/file")
public class FileController {
    // 设置固定的日期格式
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // 将 yml 中的自定义配置注入到这里
    @Value("${file.path}")
    private String filePath;
    @Value("${file.url}")
    private String fileUrl;
    // 日志打印
    private Logger log = LoggerFactory.getLogger("FileController");

    // 文件上传 （可以多文件上传）
    @PostMapping("/upload")
    public JsonResult fileUploads(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        // 得到格式化后的日期
        String format = sdf.format(new Date());
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();
        // 时间 和 日期拼接
        String newFileName = format + "_" + fileName;
        // 得到文件保存的位置以及新文件名
        File dest = new File(System.getProperty("user.dir") +filePath + newFileName);
        try {
            // 上传的文件被保存了
            file.transferTo(dest);
            // 打印日志
            log.info("上传成功，当前上传的文件保存在 {}",System.getProperty("user.dir")+filePath + newFileName);
            FileInfo fileInfo = new FileInfo();
            fileInfo.setUrl(fileUrl + newFileName);
            fileInfo.setPath(System.getProperty("user.dir")+filePath + newFileName);
            fileInfo.setFileName(newFileName);
            // 自定义返回的统一的 JSON 格式的数据，可以直接返回这个字符串也是可以的。
            return JsonResult.success("上传成功",fileInfo);
        } catch (IOException e) {
            log.error(e.toString());
        }
        // 待完成 —— 文件类型校验工作
        return JsonResult.fail(210,"上传错误");
    }
    @GetMapping("/show/{filename}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) throws MalformedURLException {
        Path currentDir = Paths.get(System.getProperty("user.dir")+filePath);
        Path filePath = currentDir.resolve(filename).normalize();
        System.out.println(filePath);
        Resource resource = new UrlResource(filePath.toUri());


        if (!resource.exists()) {
            throw new RuntimeException("Image not found: " + filename);
        }

        String contentType = "image/jpeg"; // Set the appropriate content type based on your image type

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}