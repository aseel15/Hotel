package com.example.hotel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotel.model.Room;

import java.io.Serializable;
import java.util.List;

public class CaptionedEmAdapter extends RecyclerView.Adapter<CaptionedEmAdapter.ViewHolder>{
    private Button[]detailButtons;
    private CaptionedEmAdapter.OnItemClickListener mListener;
    private Context context;
    List<Room> rooms;
    String dateCheckIn;
    String dateCheckOut;

    public CaptionedEmAdapter(Context context,List<Room>rooms, String dateCheckIn, String dateCheckOut){
        this.context=context;
        this.detailButtons=detailButtons;
        this.rooms=rooms;
        this.dateCheckIn=dateCheckIn;
        this.dateCheckOut=dateCheckOut;
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(CaptionedEmAdapter.OnItemClickListener listener){
        mListener=listener;
    }
    @Override
    public CaptionedEmAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_caption_em,
                parent,
                false);

        return new CaptionedEmAdapter.ViewHolder(v);
    }

    public int getImage(String imageName) {

        int drawableResourceId = context.getResources().getIdentifier(imageName, null, context.getPackageName());

        return drawableResourceId;
    }
    @Override
    public void onBindViewHolder(CaptionedEmAdapter.ViewHolder holder, int position) {


        CardView cardView = holder.cardView;
        ImageView img = (ImageView) cardView.findViewById(R.id.imageEm);



        Glide.with(context).load(getImage("@drawable/"+/*images[position])*/rooms.get(position).getImageURL())).into(img);
        TextView txtRoomType = (TextView)cardView.findViewById(R.id.roomTypeTxtEm);
        txtRoomType.setText("Room Type : "+/*roomTypes[position]*/rooms.get(position).getRoomType());
        TextView txtPrice = (TextView)cardView.findViewById(R.id.priceTxtEm);
        txtPrice.setText("Price : "+rooms.get(position).getPrice());
        Button detailButton=(Button) cardView.findViewById(R.id.btnDetailEm);

        detailButton.setOnClickListener(view -> {
            Intent intent = new Intent(detailButton.getContext(), DetailActivityEm.class);
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
