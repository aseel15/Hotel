package com.example.hotel;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.hotel.model.Room;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CaptionedImageAdapter extends RecyclerView.Adapter<CaptionedImageAdapter.ViewHolder> {

    private Button[]detailButtons;
    private OnItemClickListener mListener;
    private Context context;
    List<Room>rooms;
    String dateCheckIn;
    String dateCheckOut;

    public CaptionedImageAdapter(Context context,List<Room>rooms, String dateCheckIn, String dateCheckOut){
        this.context=context;
        this.detailButtons=detailButtons;
        this.rooms=rooms;
        this.dateCheckIn=dateCheckIn;
        this.dateCheckOut=dateCheckOut;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_image,
                parent,
                false);

        return new ViewHolder(v);
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, null, context.getPackageName());

        return drawableResourceId;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        CardView cardView = holder.cardView;
        ImageView img = (ImageView) cardView.findViewById(R.id.image);

        Glide.with(context).load("http://10.0.2.2:80/RoomDataBase/images/"+rooms.get(position).getImageURL()+".jpg").into(img);

        //Glide.with(context).load(getImage("@drawable/"+/*images[position])*/rooms.get(position).getImageURL())).into(img);
        TextView txtRoomType = (TextView)cardView.findViewById(R.id.roomTypeTxt);
        txtRoomType.setText("Room Type : "+/*roomTypes[position]*/rooms.get(position).getRoomType());
        TextView txtPrice = (TextView)cardView.findViewById(R.id.priceTxt);
        txtPrice.setText("Price : "+rooms.get(position).getPrice());
        Button detailButton=(Button) cardView.findViewById(R.id.btnDetail);

        detailButton.setOnClickListener(view -> {
            Intent intent = new Intent(detailButton.getContext(), DetailActivity.class);
            intent.putExtra("roomNum",rooms.get(position).getId()+"");
            intent.putExtra("checkInDate",dateCheckIn+"");
            intent.putExtra("checkOutDate",dateCheckOut+"");
            intent.putExtra("arrayList", (Serializable) rooms);

            detailButton.getContext().startActivity(intent);

        });

    }




    @Override
    public int getItemCount() {
        return rooms.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;

        }

    }
}
