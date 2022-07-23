package com.epetnet.epetnet.controller;

import com.alibaba.fastjson.JSON;
import com.epetnet.epetnet.common.R;
import io.minio.*;
import io.minio.messages.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Api("minio")
@Slf4j
@RequestMapping("/minio")
@RestController
public class MinioController {
    @Autowired
    MinioClient minioClient;

    @Value("${minio.bucketName}")
    String bucketName;
    /**
     * 获取bucket
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public R<List<Object>> getList() throws Exception{
        Iterable<Result<Item>> objects = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        Iterator<Result<Item>> iterator = objects.iterator();
        List<Object> retList = new ArrayList<>();
        String format = "{'filename':'%s','filesize':'%s'}";
        while(iterator.hasNext()){
            Item item = iterator.next().get();
            retList.add(JSON.parse(String.format(format, item.objectName(), item.size())));
        }
        return R.success(retList);
    }

    /**
     * 下载图片，我这里直接写回页面
     * @param response
     * @param fileName
     */
    @GetMapping("/download/{fileName}")
    public void download(HttpServletResponse response, @PathVariable("fileName") String fileName){
        InputStream inputStream = null;
        try{
            //获取对象信息
            StatObjectResponse statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(fileName).build());
//            response.setContentType(statObjectResponse.contentType());
//            response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(fileName,"UTF-8"));
            //我这改成直接写回浏览器了
            response.setContentType("image/jpeg");

            //文件下载
            inputStream = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(fileName).build());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.getOutputStream().close();


        }catch (Exception e){
            log.info(e.getMessage());
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    log.info(e.getMessage());
                }
            }
        }
    }


    /**
     * 删除
     * @param fileName
     * @return
     */
    @DeleteMapping("/delete/{fileName}")
    public R<String> deleteFile(@PathVariable("fileName") String fileName){
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
        }catch (Exception e){
            log.info(e.getMessage());
            return R.error("删除失败");
        }
        return R.success("删除成功");
    }



    /**
     * 文件上传
     */
    @SneakyThrows
    @ApiOperation("上传图片")
    @PostMapping("/upload")
    public R<String> uploadFile(@RequestBody MultipartFile file){
        if(file == null){
            return R.error("上传文件不能为空");
        }

            String originalFileName = file.getOriginalFilename();

            InputStream inputStream = file.getInputStream();

            String fileName = bucketName + System.currentTimeMillis() + originalFileName;
            //上传
            try {
                minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(inputStream,file.getSize(),-1).contentType(file.getContentType()).build());
                inputStream.close();
            }catch (Exception e){
                log.info(e.getMessage());
                return R.error("上传失败");
            }
        return R.success(fileName);
    }
}
