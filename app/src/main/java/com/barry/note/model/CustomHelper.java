package com.barry.note.model;

import android.net.Uri;
import android.os.Environment;
import android.view.View;

import com.barry.note.R;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * Created by Administrator on 2017/5/9.
 */
public class CustomHelper{
    private View rootView;

    public static CustomHelper of(View rootView){
        return new CustomHelper(rootView);
    }
    private CustomHelper(View rootView) {
        this.rootView = rootView;

    }


    public void onClick(View view,TakePhoto takePhoto) {
        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        switch (view.getId()){
            case R.id.btnPickBySelect:
                        takePhoto.onPickMultipleWithCrop(5,getCropOptions());

                break;
            case R.id.btnPickByTake:
                    takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
                break;
            default:
                break;
        }
    }
    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();

            builder.setWithOwnGallery(true);
            builder.setCorrectImage(true);

        takePhoto.setTakePhotoOptions(builder.create());

    }
    private void configCompress(TakePhoto takePhoto){

        int maxSize= 102400;
        int width=800;
        int height=800;
        boolean showProgressBar=true;//是否展示进度条
        boolean enableRawFile = true;//压缩后是否保存原图
        CompressConfig config;

            LubanOptions option=new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config= CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);

        takePhoto.onEnableCompress(config,showProgressBar);


    }
    private CropOptions getCropOptions(){
        int height= 800;
        int width= 800;
        CropOptions.Builder builder=new CropOptions.Builder();
            builder.setAspectX(width).setAspectY(height);

        builder.setWithOwnCrop(false);
        return builder.create();
    }

}
