package com.barry.note.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.barry.note.Adapter.ListAdapter;
import com.barry.note.R;
import com.barry.note.database.DBManager;
import com.barry.note.database.Note;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/7.
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.search)
    ImageButton mSearch;
    @BindView(R.id.ed_key)
    EditText mEdKey;
    @BindView(R.id.btn_search)
    Button mBtnSearch;
    @BindView(R.id.lv_note)
    RecyclerView mLvNote;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void init() {
        super.init();

    }


    @OnClick({R.id.back, R.id.btn_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_search:
                search();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        search();
    }

    private void search() {
        mLvNote.setLayoutManager(new LinearLayoutManager(this));
        List<Note> notes = DBManager.getInstance().queryNoteByName(mEdKey.getText().toString());
        ListAdapter listAdapter=new ListAdapter(this,notes);
        mLvNote.setAdapter(listAdapter);
    }
}
