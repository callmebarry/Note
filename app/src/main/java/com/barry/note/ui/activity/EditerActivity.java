package com.barry.note.ui.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.barry.note.R;
import com.barry.note.database.DBManager;
import com.barry.note.database.Note;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.ColorPicker;
import jp.wasabeef.richeditor.RichEditor;

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
    @BindView(R.id.action_blockquote)
    ImageButton mActionBlockquote;
    @BindView(R.id.action_insert_image)
    ImageButton mActionInsertImage;
    @BindView(R.id.action_insert_link)
    ImageButton mActionInsertLink;
    @BindView(R.id.action_insert_checkbox)
    ImageButton mActionInsertCheckbox;
    @BindView(R.id.btn_save)
    ImageButton mBtnSave;
    private ColorPicker mPicker;
    private int mScreenWidth;
    private int mScreenHeight;
    private Note mNote;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_edit;
    }

    @Override
    protected void init() {
        super.init();
        Display defaultDisplay = getWindowManager().getDefaultDisplay();

        mScreenWidth = defaultDisplay.getWidth();
        mScreenHeight = defaultDisplay.getHeight();


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


    @OnClick({R.id.back, R.id.Ed_title, R.id.editor, R.id.action_undo, R.id.action_redo, R.id.action_bold, R.id.action_italic, R.id.action_subscript, R.id.action_superscript, R.id.action_strikethrough, R.id.action_underline, R.id.action_heading1, R.id.action_heading2, R.id.action_heading3, R.id.action_heading4, R.id.action_heading5, R.id.action_heading6, R.id.action_txt_color, R.id.action_bg_color, R.id.action_indent, R.id.action_outdent, R.id.action_align_left, R.id.action_align_center, R.id.action_align_right, R.id.action_insert_bullets, R.id.action_insert_numbers, R.id.action_blockquote, R.id.action_insert_image, R.id.action_insert_link, R.id.action_insert_checkbox, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.action_undo:
                mEditor.undo();
                break;
            case R.id.action_redo:
                mEditor.redo();
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
            case R.id.action_blockquote:
                mEditor.setBlockquote();
                break;
            case R.id.action_insert_image:
                mEditor.insertImage("http://www.1honeywan.com/dachshund/image/7.21/7.21_3_thumb.JPG",
                        "dachshund");
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
                        .show().getWindow().setLayout((int) (mScreenWidth * 0.8), (int) (mScreenHeight * 0.4));

                break;
            case R.id.action_insert_checkbox:
                mEditor.insertTodo();
                break;
            case R.id.btn_save:
                String title = mEdTitle.getText().toString();
                String content = mEditor.getHtml().toString();
                if (mNote != null) {
                    mNote.setTitle(title);
                    mNote.setContent(content);
                    DBManager.getInstance().updataNote(mNote);
                } else {
                    Note note = new Note();
                    note.setTitle(title);
                    note.setContent(content);
                    note.setCreateTime(new Date());
                    DBManager.getInstance().saveNote(note);
                }
                finish();
                break;
            case R.id.back:
                finish();
                break;
        }
    }
}
