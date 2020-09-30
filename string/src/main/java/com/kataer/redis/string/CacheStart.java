package com.kataer.redis.string;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@Component
@Order(1)
public class CacheStart implements CommandLineRunner {
    private String filepath = "C:\\Users\\Administrator\\Desktop\\image";

    @Autowired
    private RedisTemplate<String, String> strRedisTemplate;

    @Override
    public void run(String... args) throws Exception {
        log.info(">>>>>>>>>>>>>>>服务启动执行，加载缓存数据 <<<<<<<<<<<<<");
        File f = new File(filepath);
        List<File> container = new ArrayList<>();
        getImageFiles(f, container);
        if (container != null) {
            for (File image : container) {
                byte[] bytes = imageToByteArry(image);
                String str = Base64.getEncoder().encodeToString(bytes);
                strRedisTemplate.opsForValue().set(image.getName(), str);
            }
        }
        log.info(">>>>>>>>>>>>>>>加载缓存数据完成 <<<<<<<<<<<<<");
    }


    private void getImageFiles(File file, List<File> container) {
        if (container == null) {
            throw new RuntimeException("container cant be null");
        }
        if (file != null && isJpg(file)) {
            container.add(file);
        } else {
            File[] childFiles = file.listFiles();
            for (File childFile : childFiles) {
                getImageFiles(childFile, container);
            }
        }
    }

    private boolean isJpg(File file) {
        if (file != null && file.isFile()) {
            String name = file.getName();
            String[] strs = name.split("\\.");
            if ("jpg".equalsIgnoreCase(strs[strs.length - 1])) {
                return true;
            }
        }

        return false;
    }


    private byte[] imageToByteArry(File file) throws IOException {
        FileInputStream inputStream = null;
        ByteArrayOutputStream byteStream;
        try {
            byteStream = new ByteArrayOutputStream();
            inputStream = new FileInputStream(file);
            byte[] buffer = new byte[4096];
            int len = inputStream.read(buffer);
            while (len > 0) {
                byteStream.write(buffer, 0, len);
                len = inputStream.read(buffer);
            }
            return byteStream.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
