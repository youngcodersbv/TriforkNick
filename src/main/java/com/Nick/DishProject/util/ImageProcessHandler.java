package com.Nick.DishProject.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class ImageProcessHandler {

    public static String imageToBase64(File file) {
        try{
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        }catch(IOException e) {
            System.out.println(e);
            return null;
        }
    }

    public static File base64ToImage(String s) {
        //TODO: Convert BASE64 to Image

        return new File(s);
    }

}
