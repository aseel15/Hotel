package com.example.hotel;


import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CaptionRoomImg extends RecyclerView.Adapter<CaptionRoomImg.ViewHolder> {
    Context context;
    ArrayList<String>imageList;
    int size=0;
    String url;
    private OnItemListener mOnItemListener;
    public CaptionRoomImg(Context context, ArrayList<String>imageList){
        this.context=context;
        this.imageList=imageList;
        size=imageList.size();

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_captioned_image_api,
                parent,
                false);

        return new ViewHolder(v, mOnItemListener);
    }
    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, null, context.getPackageName());

        return drawableResourceId;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textImg=(TextView)cardView.findViewById(R.id.imgUrlTry);
        ImageView img = (ImageView) cardView.findViewById(R.id.imageAPI);
        Glide.with(context).load(imageList.get(position)).into(img);
    }



    @Override
    public int getItemCount() {
        return imageList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder  {
        private CardView cardView;
        OnItemListener mOnItemListener;
        public ViewHolder(CardView cardView, OnItemListener onItemListener){
            super(cardView);
            this.cardView = cardView;
            mOnItemListener = onItemListener;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemListener!=null&&getAbsoluteAdapterPosition()!=RecyclerView.NO_POSITION){
                        onItemListener.onItemClick(getAbsoluteAdapterPosition());
                    }

                }
            });


        }


    }
    public interface OnItemListener{
        void onItemClick(int position);
    }
    public void OnItemListener (OnItemListener listener){
        this.mOnItemListener=listener;
    }
}