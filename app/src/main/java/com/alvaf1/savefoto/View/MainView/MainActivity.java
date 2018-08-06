package com.alvaf1.savefoto;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.alvaf1.savefoto.Classes.FileUtils;
import com.alvaf1.savefoto.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private ImageView imageView;
    private final int Pick_image = 1;
    FileUtils fileUtils = new FileUtils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);

        //Связываемся с нашей кнопкой Button:
        Button PickImage = (Button) findViewById(R.id.button);
        //Настраиваем для нее обработчик нажатий OnClickListener:
        PickImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case Pick_image:
                if(resultCode == RESULT_OK){
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        String path =fileUtils.saveImageFile(selectedImage,"name", this);
                        System.out.println(path);
                        ImageView imageView2 = findViewById(R.id.imageView2);

                        imageView.setImageBitmap(selectedImage);
                        imageView2.setImageBitmap( fileUtils.decodeBitmap(path));
                       //System.out.println(readFile(path));

                        //imageView2.setImageBitmap(fileUtils.decodeBitmap(readFile( fileUtils.saveImageFile(selectedImage,"name", this))));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        }}

      /*  static String readFile(Context context){
            File file = new File(context.getFilesDir(), SyncStateContract.Constants.ITEM_DATA);
            if (file.exists()) {
            try {
                FileInputStream fileInputStream = context.openFileInput(SyncStateContract.Constants.ITEM_DATA);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                arrayList = (ArrayList<ClipData.Item>) objectInputStream.readObject();
                objectInputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("Items", "file not found");
        }
            return ;
}*/
}
