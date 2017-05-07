package com.barry.note.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.barry.note.R;
import com.barry.note.database.DBManager;
import com.barry.note.database.Note;
import com.barry.note.ui.activity.EditerActivity;

import java.util.List;

import jp.wasabeef.richeditor.RichEditor;

public class ListAdapter  extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    Context mContext;
    List<Note> notes;
    public ListAdapter(Context context, List<Note> notes ) {
        mContext=context;
        this.notes=notes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        ViewHolder holder=new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Note note = notes.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvDate.setText(note.getCreateTime().toString());
        holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View inflate = LayoutInflater.from(mContext).inflate(R.layout.dailog_detai, null);

                TextView title = (TextView) inflate.findViewById(R.id.tv_title);
                RichEditor content = (RichEditor) inflate.findViewById(R.id.editor);
                content.setInputEnabled(false);
                title.setText(note.getTitle());
                content.setHtml(note.getContent());
                content.setEditorHeight(350);
                content.setEditorFontSize(15);
                content.setBackgroundColor(Color.TRANSPARENT);
                content.setEditorFontColor(Color.argb(10, 0, 0, 0));
                content.setPadding(10, 10, 10, 10);

                new AlertDialog.Builder(mContext)
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
                        .show();
            }
        });
    }

    private void deleNote(int adapterPosition) {
        DBManager.getInstance().DeleteNote(notes.get(adapterPosition));
        notes.remove(adapterPosition);
        Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
    }

    private void gotoEdit(int position) {
        Intent intent = new Intent(mContext, EditerActivity.class);
        intent.putExtra("Note", notes.get(position));
        mContext.startActivity(intent);
    }


    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvDate;
        ImageView ivClock;
        RelativeLayout mRelativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
           tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            ivClock= (ImageView) itemView.findViewById(R.id.iv_clock);
            mRelativeLayout= (RelativeLayout) itemView.findViewById(R.id.item);
        }

    }
}