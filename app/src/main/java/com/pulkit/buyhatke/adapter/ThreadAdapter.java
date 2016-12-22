package com.pulkit.buyhatke.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pulkit.buyhatke.R;
import com.pulkit.buyhatke.pojo.Message;

import java.util.ArrayList;


/**
 * @author pulkitmital
 * @date 22-12-2016
 */

public class ThreadAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public ArrayList<Message> getMessageArrayList() {
        return mMessageArrayList;
    }

    private ArrayList<Message> mMessageArrayList;

    private final int VIEW_TYPE_LEFT = 0;
    private final int VIEW_TYPE_RIGHT = 1;

    public ThreadAdapter() {
        this.mMessageArrayList = new ArrayList<>();
    }


    public void addData(ArrayList<Message> pMessageArrayList) {
        mMessageArrayList.clear();
        mMessageArrayList.addAll(pMessageArrayList);
        notifyDataSetChanged();
    }

    public void updateData(Message pMessage) {
        mMessageArrayList.add(pMessage);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case VIEW_TYPE_LEFT:
                View v1 = inflater.inflate(R.layout.layout_thread_left, parent, false);
                viewHolder = new LeftViewHolder(v1);
                break;
            case VIEW_TYPE_RIGHT:
                View v2 = inflater.inflate(R.layout.layout_thread_right, parent, false);
                viewHolder = new RightViewHolder(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.layout_thread_left, parent, false);
                viewHolder = new LeftViewHolder(v);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Message modelStartup = mMessageArrayList.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_LEFT:
                LeftViewHolder lLeftViewHolder = (LeftViewHolder) holder;
                lLeftViewHolder.tvBody.setText(modelStartup.getBody());
                lLeftViewHolder.tvDate.setText(modelStartup.getDate());
                break;
            case VIEW_TYPE_RIGHT:
                RightViewHolder lRightViewHolder = (RightViewHolder) holder;
                lRightViewHolder.tvBody.setText(modelStartup.getBody());
                lRightViewHolder.tvDate.setText(modelStartup.getDate());
                break;
        }

    }


    @Override
    public int getItemCount() {
        return mMessageArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mMessageArrayList.get(position).getType().equals("1"))
            return VIEW_TYPE_LEFT;
        return VIEW_TYPE_RIGHT;
    }

    class RightViewHolder extends RecyclerView.ViewHolder {
        TextView tvBody;

        TextView tvDate;

        private RightViewHolder(View itemView) {
            super(itemView);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }

    class LeftViewHolder extends RecyclerView.ViewHolder {
        TextView tvBody;

        TextView tvDate;

        private LeftViewHolder(View itemView) {
            super(itemView);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
        }
    }

}

