package com.mq.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static void persistFile(MultipartFile file, String realName, String fileTypePath) throws IOException {
        String fileRealPath = fileTypePath.concat(realName);
        File dest = new File(fileRealPath);
        file.transferTo(dest);
    }

    public static void removeFile(String fileRealPath) {
        File delete = new File(fileRealPath);
        delete.delete();
    }
}
