package com.barry.note.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.barry.note.R;
import com.barry.note.database.Note;
import com.barry.note.presenter.OnItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyAdapter extends SwipeMenuAdapter<MyAdapter.DefaultViewHolder> {

    private List<Note> mNotes;

    private OnItemClickListener mOnItemClickListener;

    public MyAdapter(List<Note> nodes) {
        this.mNotes = nodes;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return mNotes == null ? 0 : mNotes.size();
    }

    @Override
    public View onCreateContentView(ViewGroup parent, int viewType) {
        return LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
    }

    @Override
    public MyAdapter.DefaultViewHolder onCompatCreateViewHolder(View realContentView, int viewType) {
        DefaultViewHolder viewHolder = new DefaultViewHolder(realContentView);
        viewHolder.mOnItemClickListener = mOnItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.DefaultViewHolder holder, int position) {
        holder.setData(mNotes.get(position));
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvDate;
        ImageView ivClock;
        OnItemClickListener mOnItemClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDate = (TextView) itemView.findViewById(R.id.tv_date);
            ivClock= (ImageView) itemView.findViewById(R.id.iv_clock);
        }

        public void setData(Note node) {
            this.tvTitle.setText(node.getTitle());
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(node.getCreateTime());
            this.tvDate.setText(date);

            boolean clock = node.getClock();
//           if(clock)
//               this.ivClock.setImageResource(R.mipmap.clock1);
//            else
//               this.ivClock.setImageResource(R.mipmap.clock2);

        }


        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

}
