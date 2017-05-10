package com.barry.note.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.barry.note.R;
import com.barry.note.model.CustomHelper;
import com.barry.note.model.MyEvent;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class SimpleActivity extends TakePhotoActivity {

    private CustomHelper customHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView= LayoutInflater.from(this).inflate(R.layout.activity_simple,null);
        setContentView(contentView);
        customHelper=CustomHelper.of(contentView);
    }

    public void onClick(View view) {
        customHelper.onClick(view,getTakePhoto());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        MyEvent myEvent=new MyEvent();
        myEvent.setTYPE(MyEvent.imgtype);
        myEvent.setImages(images);
        EventBus.getDefault().post(myEvent);
        finish();
    }
}