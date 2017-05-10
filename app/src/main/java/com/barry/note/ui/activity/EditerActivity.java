package com.barry.note.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.barry.note.R;
import com.barry.note.database.DBManager;
import com.barry.note.database.Note;
import com.barry.note.model.MyEvent;
import com.jph.takephoto.model.TImage;
import com.lqr.audio.AudioRecordManager;
import com.lqr.audio.IAudioRecordListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.ColorPicker;
import jp.wasabeef.richeditor.RichEditor;
import mabeijianxi.camera.MediaRecorderActivity;
import mabeijianxi.camera.model.AutoVBRMode;
import mabeijianxi.camera.model.MediaRecorderConfig;

/**
 * Created by Administrator on 2017/5/7.
 */
public class EditerActivity extends BaseActivity {
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.search)
    ImageButton mSearch;
    @BindView(R.id.Ed_title)
    EditText mEdTitle;
    @BindView(R.id.editor)
    RichEditor mEditor;
    @BindView(R.id.action_undo)
    ImageButton mActionUndo;
    @BindView(R.id.action_redo)
    ImageButton mActionRedo;
    @BindView(R.id.action_bold)
    ImageButton mActionBold;
    @BindView(R.id.action_italic)
    ImageButton mActionItalic;
    @BindView(R.id.action_subscript)
    ImageButton mActionSubscript;
    @BindView(R.id.action_superscript)
    ImageButton mActionSuperscript;
    @BindView(R.id.action_strikethrough)
    ImageButton mActionStrikethrough;
    @BindView(R.id.action_underline)
    ImageButton mActionUnderline;
    @BindView(R.id.action_heading1)
    ImageButton mActionHeading1;
    @BindView(R.id.action_heading2)
    ImageButton mActionHeading2;
    @BindView(R.id.action_heading3)
    ImageButton mActionHeading3;
    @BindView(R.id.action_heading4)
    ImageButton mActionHeading4;
    @BindView(R.id.action_heading5)
    ImageButton mActionHeading5;
    @BindView(R.id.action_heading6)
    ImageButton mActionHeading6;
    @BindView(R.id.action_txt_color)
    ImageButton mActionTxtColor;
    @BindView(R.id.action_bg_color)
    ImageButton mActionBgColor;
    @BindView(R.id.action_indent)
    ImageButton mActionIndent;
    @BindView(R.id.action_outdent)
    ImageButton mActionOutdent;
    @BindView(R.id.action_align_left)
    ImageButton mActionAlignLeft;
    @BindView(R.id.action_align_center)
    ImageButton mActionAlignCenter;
    @BindView(R.id.action_align_right)
    ImageButton mActionAlignRight;
    @BindView(R.id.action_insert_bullets)
    ImageButton mActionInsertBullets;
    @BindView(R.id.action_insert_numbers)
    ImageButton mActionInsertNumbers;
    @BindView(R.id.action_insert_image)
    ImageButton mActionInsertImage;
    @BindView(R.id.action_insert_link)
    ImageButton mActionInsertLink;
    @BindView(R.id.action_insert_checkbox)
    ImageButton mActionInsertCheckbox;
    @BindView(R.id.recordvideo)
    ImageButton mRecordvideo;
    @BindView(R.id.insert_audio)
    ImageButton mInsert_audio;
    private ColorPicker mPicker;
    private int mScreenWidth;
    private int mScreenHeight;
    private Note mNote;
    private View mAudioinflate;



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MyEvent event) {
        int type = event.getTYPE();
        switch (type){
            case 1:
                ArrayList<TImage> images = event.getImages();
                for (TImage img : images) {
                    mEditor.insertImage(img.getCompressPath(), "无法加载");
                }
                break;
            case 0:
                String videUrl = event.getVideUrl();
                mEditor.setHtml((mEditor.getHtml()==null?"&nbsp;":mEditor.getHtml())+"<video src='"+videUrl+"' width='200' controls>不支持视频</video>&nbsp;");
                break;

        }


    }


    @Override
    public int getLayoutResId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void init() {
        super.init();

        EventBus.getDefault().register(this);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();

        mScreenWidth = defaultDisplay.getWidth();
        mScreenHeight = defaultDisplay.getHeight();
        mSearch.setVisibility(View.VISIBLE);
        mSearch.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mSearch.setImageResource(R.mipmap.right);


        mNote = (Note) getIntent().getSerializableExtra("Note");
        if (mNote != null) {
            mBarTitle.setText("修改事务");
            mEdTitle.setText(mNote.getTitle());
            mEditor.setHtml(mNote.getContent());

        } else {
            mBarTitle.setText("添加事务");
        }
        mBack.setVisibility(View.VISIBLE);

        mEdTitle.setBackgroundColor(Color.TRANSPARENT);
        mEdTitle.setTextSize(15);
        mEdTitle.setPadding(10, 10, 10, 10);
        mEdTitle.setHint(" 输入标题");

        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(15);
        mEditor.setBackgroundColor(Color.TRANSPARENT);
        mEditor.setEditorFontColor(Color.argb(10, 0, 0, 0));
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("输入内容");




        mPicker = new ColorPicker(this);

    }


    @OnClick({R.id.back, R.id.Ed_title, R.id.editor, R.id.action_undo, R.id.action_redo, R.id.action_bold, R.id.action_italic, R.id.action_subscript, R.id.action_superscript, R.id.action_strikethrough, R.id.action_underline, R.id.action_heading1, R.id.action_heading2, R.id.action_heading3, R.id.action_heading4, R.id.action_heading5, R.id.action_heading6, R.id.action_txt_color, R.id.action_bg_color, R.id.action_indent, R.id.action_outdent, R.id.action_align_left, R.id.action_align_center, R.id.action_align_right, R.id.action_insert_bullets, R.id.action_insert_numbers, R.id.action_insert_image, R.id.action_insert_link, R.id.action_insert_checkbox, R.id.search, R.id.recordvideo,R.id.insert_audio})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_undo:
                mEditor.undo();
                break;
            case R.id.action_redo:
                mEditor.redo();
//                <embed height='50' width='100' data='/storage/emulated/0/DCIM/music.mp3'/>

                break;
            case R.id.action_bold:
                mEditor.setBold();
                break;
            case R.id.action_italic:
                mEditor.setItalic();
                break;
            case R.id.action_subscript:
                mEditor.setSubscript();
                break;
            case R.id.action_superscript:
                mEditor.setSuperscript();
                break;
            case R.id.action_strikethrough:
                mEditor.setStrikeThrough();
                break;
            case R.id.action_underline:
                mEditor.setUnderline();
                break;
            case R.id.action_heading1:
                mEditor.setHeading(1);
                break;
            case R.id.action_heading2:
                mEditor.setHeading(2);
                break;
            case R.id.action_heading3:
                mEditor.setHeading(3);
                break;
            case R.id.action_heading4:
                mEditor.setHeading(4);
                break;
            case R.id.action_heading5:
                mEditor.setHeading(5);
                break;
            case R.id.action_heading6:
                mEditor.setHeading(6);
                break;
            case R.id.action_txt_color:

                mPicker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        mEditor.setTextColor(pickedColor);
                    }
                });
                mPicker.show();
                break;
            case R.id.action_bg_color:
                mPicker.setOnColorPickListener(new ColorPicker.OnColorPickListener() {
                    @Override
                    public void onColorPicked(int pickedColor) {
                        mEditor.setTextBackgroundColor(pickedColor);
                    }
                });
                mPicker.show();
                break;
            case R.id.action_indent:
                mEditor.setIndent();
                break;
            case R.id.action_outdent:
                mEditor.setOutdent();
                break;
            case R.id.action_align_left:
                mEditor.setAlignLeft();
                break;
            case R.id.action_align_center:
                mEditor.setAlignCenter();
                break;
            case R.id.action_align_right:
                mEditor.setAlignRight();
                break;
            case R.id.action_insert_bullets:
                mEditor.setBullets();
                break;
            case R.id.action_insert_numbers:
                mEditor.setNumbers();
                break;
            case R.id.action_insert_image:
                Intent intent = new Intent(this, SimpleActivity.class);
                startActivity(intent);
                break;
            case R.id.action_insert_link:
                final View inflate = LayoutInflater.from(this).inflate(R.layout.dailog_link, null);
                new AlertDialog.Builder(this)
                        .setView(inflate)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText content = (EditText) inflate.findViewById(R.id.link_content);
                                EditText href = (EditText) inflate.findViewById(R.id.link_href);
                                mEditor.insertLink(href.getText().toString(), content.getText().toString());
                            }
                        })
                        .show().getWindow().setLayout((int) (mScreenWidth * 0.8), (int) (mScreenHeight * 0.7));

                break;
            case R.id.action_insert_checkbox:
                mEditor.insertTodo();
                break;
            case R.id.search:
                Editable title = mEdTitle.getText();
                String content = mEditor.getHtml();
                if(title==null ||content==null ||title.length()<1 || content.length()<1){
                    Toast.makeText(this, "文本为空,不能保存", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mNote != null) {
                    mNote.setTitle(title.toString());
                    mNote.setContent(content);
                    DBManager.getInstance().updataNote(mNote);
                } else {
                    Note note = new Note();
                    note.setTitle(title.toString());
                    note.setContent(content);
                    note.setCreateTime(new Date());
                    DBManager.getInstance().saveNote(note);
                }
                finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.recordvideo:
                MediaRecorderConfig config = new MediaRecorderConfig.Buidler()
                        .doH264Compress(new AutoVBRMode()
                        )
                        .setMediaBitrateConfig(new AutoVBRMode()
                        )
                        .smallVideoWidth(480)
                        .smallVideoHeight(360)
                        .recordTimeMax(6 * 1000)
                        .maxFrameRate(20)
                        .captureThumbnailsTime(1)
                        .recordTimeMin((int) (1.5 * 1000))
                        .build();
                MediaRecorderActivity.goSmallVideoRecorder(this, VideoPlayerActivity.class.getName(), config);
                break;
            case R.id.insert_audio:
                mAudioinflate = LayoutInflater.from(this).inflate(R.layout.dailog_auido, null);
                TextView tv = (TextView) mAudioinflate.findViewById(R.id.bar_title);
                tv.setText("插入音频");
                Button btn_record = (Button) mAudioinflate.findViewById(R.id.btn_record);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                final AlertDialog dialog = builder.setView(mAudioinflate).show();
                dialog.getWindow().setLayout((int) (mScreenWidth * 0.75), (int) (mScreenHeight * 0.7));

                btn_record.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                AudioRecordManager.getInstance(EditerActivity.this).startRecord();
                                break;
                            case MotionEvent.ACTION_MOVE:
                                if (isCancelled(v, event)) {
                                    AudioRecordManager.getInstance(EditerActivity.this).willCancelRecord();
                                } else {
                                    AudioRecordManager.getInstance(EditerActivity.this).continueRecord();
                                }
                                break;
                            case MotionEvent.ACTION_UP:
                                AudioRecordManager.getInstance(EditerActivity.this).stopRecord();
                                AudioRecordManager.getInstance(EditerActivity.this).destroyRecord();

                                break;
                        }
                        return false;
                    }
                });
                AudioRecordManager.getInstance(this).setAudioRecordListener(new IAudioRecordListener() {
                    public PopupWindow mRecordWindow;
                    ImageView mStateIV;
                    TextView mStateTV, mTimerTV;

                    @Override
                    public void initTipView() {
                        View view = View.inflate(EditerActivity.this, R.layout.popup_audio_wi_vo, null);
                        mStateIV = (ImageView) view.findViewById(R.id.rc_audio_state_image);
                        mStateTV = (TextView) view.findViewById(R.id.rc_audio_state_text);
                        mTimerTV = (TextView) view.findViewById(R.id.rc_audio_timer);
                        mRecordWindow = new PopupWindow(view, -1, -1);
                        mRecordWindow.showAtLocation(mAudioinflate, 17, 0, 0);
                        mRecordWindow.setFocusable(true);
                        mRecordWindow.setOutsideTouchable(false);
                        mRecordWindow.setTouchable(false);
                    }

                    @Override
                    public void setTimeoutTipView(int counter) {
                        if (this.mRecordWindow != null) {
                            this.mStateIV.setVisibility(View.GONE);
                            this.mStateTV.setVisibility(View.VISIBLE);
                            this.mStateTV.setText(R.string.voice_rec);
                            this.mTimerTV.setText(String.format("%s", new Object[]{Integer.valueOf(counter)}));
                            this.mTimerTV.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void setRecordingTipView() {
                        if (this.mRecordWindow != null) {
                            this.mStateIV.setVisibility(View.VISIBLE);
                            this.mStateIV.setImageResource(R.mipmap.ic_volume_1);
                            this.mStateTV.setVisibility(View.VISIBLE);
                            this.mStateTV.setText(R.string.voice_rec);
                            this.mTimerTV.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void setAudioShortTipView() {
                        if (this.mRecordWindow != null) {
                            mStateIV.setImageResource(R.mipmap.ic_volume_wraning);
                            mStateTV.setText(R.string.voice_short);
                        }
                    }

                    @Override
                    public void setCancelTipView() {
                        if (this.mRecordWindow != null) {
                            this.mTimerTV.setVisibility(View.GONE);
                            this.mStateIV.setVisibility(View.VISIBLE);
                            this.mStateIV.setImageResource(R.mipmap.ic_volume_cancel);
                            this.mStateTV.setVisibility(View.VISIBLE);
                            this.mStateTV.setText(R.string.voice_cancel);
                            this.mStateTV.setBackgroundResource(R.drawable.corner_voice_style);
                        }
                    }

                    @Override
                    public void destroyTipView() {
                        if (this.mRecordWindow != null) {
                            this.mRecordWindow.dismiss();
                            this.mRecordWindow = null;
                            this.mStateIV = null;
                            this.mStateTV = null;
                            this.mTimerTV = null;
                        }
                    }

                    @Override
                    public void onStartRecord() {
                        //开始录制
                    }


                    @Override
                    public void onFinish(Uri audioPath, int duration) {
                        mEditor.setHtml((mEditor.getHtml() == null ? "&nbsp;" : mEditor.getHtml()) + "<audio controls='controls' height='100' width='100'>" +
                                "  <source src='" + audioPath + "' /></audio>&nbsp;");
                        dialog.dismiss();
                    }

                    @Override
                    public void onAudioDBChanged(int db) {
                        switch (db / 5) {
                            case 0:
                                this.mStateIV.setImageResource(R.mipmap.ic_volume_1);
                                break;
                            case 1:
                                this.mStateIV.setImageResource(R.mipmap.ic_volume_2);
                                break;
                            case 2:
                                this.mStateIV.setImageResource(R.mipmap.ic_volume_3);
                                break;
                            case 3:
                                this.mStateIV.setImageResource(R.mipmap.ic_volume_4);
                                break;
                            case 4:
                                this.mStateIV.setImageResource(R.mipmap.ic_volume_5);
                                break;
                            case 5:
                                this.mStateIV.setImageResource(R.mipmap.ic_volume_6);
                                break;
                            case 6:
                                this.mStateIV.setImageResource(R.mipmap.ic_volume_7);
                                break;
                            default:
                                this.mStateIV.setImageResource(R.mipmap.ic_volume_8);
                        }
                    }

                });
                break;
        }
    }
    private boolean isCancelled(View view, MotionEvent event) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (event.getRawX() < location[0] || event.getRawX() > location[0] + view.getWidth() || event.getRawY() < location[1] - 40) {
            return true;
        }
        return false;
    }

}
