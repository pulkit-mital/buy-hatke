package com.pulkit.buyhatke.adapter;

import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulkit.buyhatke.R;
import com.pulkit.buyhatke.pojo.MessageRow;

import java.util.ArrayList;


/**
 * @author pulkitmital
 * @date 22-12-2016
 */
public abstract class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.DataHolder> {

    private ArrayList<MessageRow> mMessageRowArrayList;

    public abstract void messageClicked(String address);

    protected MessageAdapter() {
        mMessageRowArrayList = new ArrayList<>();
    }

    public void addMessages(ArrayList<MessageRow> pMessageRowArrayList) {
        mMessageRowArrayList.clear();
        mMessageRowArrayList.addAll(pMessageRowArrayList);
        notifyDataSetChanged();
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_message_item, parent, false);
        return new DataHolder(v);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        final MessageRow messageRow = mMessageRowArrayList.get(position);
        holder.tvName.setText(messageRow.getAddress() + " (" + String.valueOf(messageRow.getThreadCount()) + ")");
        holder.tvConversation.setText(messageRow.getBody());
        holder.tvDate.setText(messageRow.getDate());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                messageClicked(messageRow.getAddress());
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMessageRowArrayList.size();
    }

    static class DataHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvConversation;
        TextView tvDate;
        ImageView ivAvatar;
        CardView mView;

        DataHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvConversation = (TextView) itemView.findViewById(R.id.tvConversation);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            mView = (CardView) itemView.findViewById(R.id.rowContainer);
        }
    }


}

