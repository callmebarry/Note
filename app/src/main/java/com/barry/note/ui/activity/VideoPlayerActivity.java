package com.barry.note.ui.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.barry.note.R;
import com.barry.note.model.MyEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.MediaRecorderBase;
import mabeijianxi.camera.util.DeviceUtils;
import mabeijianxi.camera.util.StringUtils;
import mabeijianxi.camera.views.SurfaceVideoView;


/**
 * Created by Administrator on 2017/5/9.
 */
public class VideoPlayerActivity extends BaseActivity implements
        SurfaceVideoView.OnPlayStateListener, OnErrorListener,
        OnPreparedListener, OnCompletionListener,
        OnInfoListener {


    @BindView(R.id.videoview)
    SurfaceVideoView mVideoview;
    @BindView(R.id.loading)
    ProgressBar mLoading;
    @BindView(R.id.insert_video)
    Button mInsertVideo;
    @BindView(R.id.btn_cancel)
    Button mBtnCancel;
    @BindView(R.id.root)
    RelativeLayout mRoot;
    @BindView(R.id.bar_title)
    TextView bar_title;
    /**
     * 播放路径
     */
    private String mPath;
    /**
     * 是否需要回复播放
     */
    private boolean mNeedResume;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void init() {
        super.init();
this.setTitle("");
        bar_title.setText("视频预览");
        // 防止锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent intent = getIntent();
        mPath = intent.getStringExtra(MediaRecorderActivity.VIDEO_URI);
        if (StringUtils.isEmpty(mPath)) {
            finish();
            return;
        }

       

        int screenWidth = getScreenWidth(this);
        int videoHight = (int) (screenWidth / (MediaRecorderBase.SMALL_VIDEO_WIDTH / (MediaRecorderBase.SMALL_VIDEO_HEIGHT * 1.0f)));
        mVideoview.getLayoutParams().height = videoHight;
        mVideoview.requestLayout();

        mVideoview.setOnPreparedListener(this);
        mVideoview.setOnPlayStateListener(this);
        mVideoview.setOnErrorListener(this);

        mVideoview.setOnInfoListener(this);
        mVideoview.setOnCompletionListener(this);
        mVideoview.setVideoPath(mPath);
    }

    public int getScreenWidth(Activity context) {
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(mDisplayMetrics);
        int W = mDisplayMetrics.widthPixels;
        return W;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mVideoview != null && mNeedResume) {
            mNeedResume = false;
            if (mVideoview.isRelease())
                mVideoview.reOpen();
            else
                mVideoview.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mVideoview != null) {
            if (mVideoview.isPlaying()) {
                mNeedResume = true;
                mVideoview.pause();
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mVideoview != null) {
            mVideoview.release();
            mVideoview = null;
        }
        super.onDestroy();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mVideoview.setVolume(SurfaceVideoView.getSystemVolumn(this));
        mVideoview.start();
        mLoading.setVisibility(View.GONE);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {// 跟随系统音量走
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
                mVideoview.dispatchKeyEvent(this, event);
                break;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onStateChanged(boolean isPlaying) {
        mLoading.setVisibility(isPlaying ? View.GONE : View.VISIBLE);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        if (!isFinishing()) {
            // 播放失败
        }
        finish();
        return false;

    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        if (!isFinishing())
            mVideoview.reOpen();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        switch (what) {
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                // 音频和视频数据不正确
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                if (!isFinishing())
                    mVideoview.pause();
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                if (!isFinishing())
                    mVideoview.start();
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                if (DeviceUtils.hasJellyBean()) {
                    mVideoview.setBackground(null);
                } else {
                    mVideoview.setBackgroundDrawable(null);
                }
                break;
        }
        return false;
    }

    @OnClick({R.id.insert_video, R.id.btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert_video:
                MyEvent myEvent=new MyEvent();
                myEvent.setTYPE(MyEvent.videotype);
                myEvent.setVideUrl(mPath);
                EventBus.getDefault().post(myEvent);
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
}
