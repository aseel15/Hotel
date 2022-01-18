package com.example.hotel;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CaptionImageAPI extends RecyclerView.Adapter<CaptionImageAPI.ViewHolder> {
    Context context;
    ArrayList<String>imageList;
    int size=0;
    public CaptionImageAPI(Context context, ArrayList<String>imageList){
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

        return new ViewHolder(v);
    }
    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, null, context.getPackageName());

        return drawableResourceId;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView img = (ImageView) cardView.findViewById(R.id.imageAPI);
        Glide.with(context).load(imageList.get(position)).into(img);

        // Glide.with(context).load("http://10.0.2.2:80/RoomDataBase/images/"+rooms.get(position).getImageURL()+".jpg").into(img);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;

        }

    }
}