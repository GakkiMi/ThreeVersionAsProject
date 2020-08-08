package com.example.threeversionasproject.retrofit;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Ocean on 2020/6/10.
 */

public class FileStream {
    /**
        * 将文件转成base64 字符串
        *
        * @param path文件路径
        * @return *
        * @throws Exception
        */
    public  String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int) file.length()];
        inputFile.read(buffer);
        inputFile.close();

        return Base64Utils.encode(buffer);
    }

}
