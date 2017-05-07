package com.barry.note.ui.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barry.note.Adapter.MyAdapter;
import com.barry.note.R;
import com.barry.note.database.DBManager;
import com.barry.note.database.Note;
import com.barry.note.presenter.OnItemClickListener;
import com.barry.note.widget.ListViewDecoration;
import com.yanzhenjie.recyclerview.swipe.Closeable;
import com.yanzhenjie.recyclerview.swipe.OnSwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.richeditor.RichEditor;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.bar_title)
    TextView mBarTitle;
    @BindView(R.id.back)
    ImageView mBack;
    @BindView(R.id.search)
    ImageButton mSearch;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.recyclerview)
    SwipeMenuRecyclerView mRecyclerview;
    @BindView(R.id.activity_main)
    CoordinatorLayout mActivityMain;
    private List<Note> mNotes;
    private MyAdapter mMyAdapter;
    private int mScreenWidth;
    private int mScreenHeight;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        super.init();

        Display defaultDisplay = getWindowManager().getDefaultDisplay();

        mScreenWidth = defaultDisplay.getWidth();
        mScreenHeight = defaultDisplay.getHeight();

        mSearch.setVisibility(View.VISIBLE);
        mBarTitle.setText("Note");


        mNotes = DBManager.getInstance().queryNote();
    }

    private void initRecyclerView() {
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.addItemDecoration(new ListViewDecoration(this));

        // 为SwipeRecyclerView的Item创建菜单就两句话，不错就是这么简单：
        // 设置菜单创建器。
        mRecyclerview.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听。
        mRecyclerview.setSwipeMenuItemClickListener(menuItemClickListener);

        mNotes = DBManager.getInstance().queryNote();
        mMyAdapter = new MyAdapter(mNotes);
        mMyAdapter.setOnItemClickListener(onItemClickListener);
        mRecyclerview.setAdapter(mMyAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
    }


    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.item_height);

            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;


            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(MainActivity.this)
                        .setText("删除") // 文字，还可以设置文字颜色，大小等。。
                        .setTextColor(Color.WHITE)
//                        .setBackgroundDrawable()
                        .setBackgroundColor(Color.RED)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。


                SwipeMenuItem addItem = new SwipeMenuItem(MainActivity.this)
                        .setText("编辑")
                        .setBackgroundColor(Color.BLUE)
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加一个按钮到右侧菜单。
            }
        }
    };

    /**
     * Item点击监听。
     */
    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(final int position) {
            View inflate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dailog_detai, null);

            TextView title = (TextView) inflate.findViewById(R.id.tv_title);
            RichEditor content = (RichEditor) inflate.findViewById(R.id.editor);
            content.setInputEnabled(false);
            title.setText(mNotes.get(position).getTitle());
            content.setHtml(mNotes.get(position).getContent());
            content.setEditorHeight(350);
            content.setEditorFontSize(15);
            content.setBackgroundColor(Color.TRANSPARENT);
            content.setEditorFontColor(Color.argb(10, 0, 0, 0));
            content.setPadding(10, 10, 10, 10);

            new AlertDialog.Builder(MainActivity.this)
                    .setView(inflate)
                    .setNegativeButton("编辑", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            gotoEdit(position);

                        }
                    })
                    .setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            deleNote(position);
                        }
                    })
                    .show().getWindow().setLayout((int) (mScreenWidth), (int) (mScreenHeight * 0.8));
        }
    };
    /**
     * 菜单点击监听。
     */
    private OnSwipeMenuItemClickListener menuItemClickListener = new OnSwipeMenuItemClickListener() {
        /**
         * Item的菜单被点击的时候调用。
         * @param closeable       closeable. 用来关闭菜单。
         * @param adapterPosition adapterPosition. 这个菜单所在的item在Adapter中position。
         * @param menuPosition    menuPosition. 这个菜单的position。比如你为某个Item创建了2个MenuItem，那么这个position可能是是 0、1，
         * @param direction       如果是左侧菜单，值是：SwipeMenuRecyclerView#LEFT_DIRECTION，如果是右侧菜单，值是：SwipeMenuRecyclerView
         *                        #RIGHT_DIRECTION.
         */
        @Override
        public void onItemClick(Closeable closeable, int adapterPosition, int menuPosition, int direction) {
            closeable.smoothCloseMenu();// 关闭被点击的菜单。

//            Toast.makeText(mContext, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            // TODO 如果是删除：推荐调用Adapter.notifyItemRemoved(position)，不推荐Adapter.notifyDataSetChanged();


            switch (menuPosition) {
                case 0:
                    deleNote(adapterPosition);
                    break;
                case 1:
                    gotoEdit(adapterPosition);
                    break;
            }
        }
    };

    private void deleNote(int adapterPosition) {
        DBManager.getInstance().DeleteNote(mNotes.get(adapterPosition));
        mNotes.remove(adapterPosition);

        mMyAdapter.notifyItemRemoved(adapterPosition);
        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
    }

    private void gotoEdit(int position) {
        Intent intent = new Intent(MainActivity.this, EditerActivity.class);
        intent.putExtra("Note", mNotes.get(position));
        startActivity(intent);
    }


    @OnClick({R.id.search, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search:
                goTo(SearchActivity.class,false);

                break;
            case R.id.fab:
                goTo(EditerActivity.class, false);
                break;
        }
    }


}
