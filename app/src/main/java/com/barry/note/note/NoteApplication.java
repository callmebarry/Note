package com.barry.note.note;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import com.barry.note.database.DBManager;
import com.lqr.audio.AudioRecordManager;

import java.io.File;

import mabeijianxi.camera.VCamera;
import mabeijianxi.camera.util.DeviceUtils;


/**
 * Created by Administrator on 2017/5/3.
 */

public class NoteApplication extends Application {


    private File mAudioDir;

    @Override
    public void onCreate() {
        super.onCreate();
        DBManager.getInstance().initDatabase(getApplicationContext());
        initSmallVideo(getApplicationContext());

        AudioRecordManager.getInstance(this).setMaxVoiceDuration(120);
        mAudioDir = new File(Environment.getExternalStorageDirectory(), "NOTE_AUDIO");
        if (!mAudioDir.exists()) {
            mAudioDir.mkdirs();
        }

        AudioRecordManager.getInstance(this).setAudioSavePath(mAudioDir.getAbsolutePath());
          }

    public static void initSmallVideo(Context context) {
        // 设置拍摄视频缓存路径
        File dcim = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/", "/sdcard-ext/")+ "/mabeijianxi/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/mabeijianxi/");
        }
        VCamera.setDebugMode(true);
        VCamera.initialize(context);
    }

}
