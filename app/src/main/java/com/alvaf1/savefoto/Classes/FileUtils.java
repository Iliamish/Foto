package com.alvaf1.savefoto.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

    public FileUtils() {

    }

    public static String saveImageFile(Bitmap bitmap, String imageName, Context context) {

        System.out.println("dsfsfd");

        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagePath = new File(directory, imageName);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(imagePath);
            if (bitmap != null) {


               ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 5, baos);
                byte[] b = baos.toByteArray();
                String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
               // System.out.println(imageEncoded);

                fileOutputStream.write(b);

                System.out.println(b);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null) fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
            System.gc();
        }

        Log.d("Utils", imagePath.getAbsolutePath());

        return imagePath.getAbsolutePath();
    }

    public Bitmap decodeBitmap(String imagePath) throws IOException {


        System.out.println("decode");

        FileInputStream fin=new FileInputStream(imagePath);

        System.out.printf("File size: %d bytes \n", fin.available());


        String line = "gfhgfj";
        byte[] buffer = new byte[fin.available()];
// считаем файл в буфер
        fin.read(buffer, 0, fin.available());


        //byte[] decodedByte = Base64.decode(buffer,0);
        return BitmapFactory.decodeByteArray(buffer, 0, buffer.length);

        }


}
