package com.example.hotel;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.hotel.model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CaptionedEmAdapter extends RecyclerView.Adapter<CaptionedEmAdapter.ViewHolder>{
    private Button[]detailButtons;
    private CaptionedEmAdapter.OnItemClickListener mListener;
    private Context context;
    List<Room> rooms;
    String dateCheckIn;
    String dateCheckOut;
    public ArrayList<String> conflictDeleted=new ArrayList<>();

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
    TextView txtPrice;
    int size=0;
    @Override
    public void onBindViewHolder(CaptionedEmAdapter.ViewHolder holder, int position) {


        CardView cardView = holder.cardView;
        ImageView img = (ImageView) cardView.findViewById(R.id.imageEm);
        Glide.with(context).load("http://10.0.2.2:80/RoomDataBase/images/"+rooms.get(position).getImageURL()+".jpg").into(img);

        TextView txtRoomType = (TextView)cardView.findViewById(R.id.roomTypeTxtEm);
        txtRoomType.setText("Room Type : "+rooms.get(position).getRoomType());

        txtPrice = (TextView)cardView.findViewById(R.id.priceTxtEm);
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
        Button deleteButton=(Button) cardView.findViewById(R.id.btnDeleteEm);
        populateReservedRooms(rooms.get(position).getId());
        deleteButton.setOnClickListener(view -> {

            if(size==0) {
                Toast.makeText(context, "delete room",
                        Toast.LENGTH_SHORT).show();
               // deleteRoom(rooms.get(position).getId());
               /* rooms.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, rooms.size());
                holder.itemView.setVisibility(View.GONE);*/
            }
            else{
               // populateReservedRooms(rooms.get(position).getId());
                Toast.makeText(context, "The room is reserved !",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void populateReservedRooms(int roomId){
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String BASE_URL = "http://10.0.2.2:80/FinalProject/selectRRoomsAfterNow.php?roomsID=" + roomId+"&check_Out="+date;
        RequestQueue queue = Volley.newRequestQueue(context);


        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray roomList=new JSONArray(response);
                            for(int i=0;i<roomList.length();i++){
                                JSONObject jsonObject= roomList.getJSONObject(i);
                                int id = jsonObject.getInt("roomsID");
                                conflictDeleted.add(id+"");
                            }
                            size=conflictDeleted.size();
                            Toast.makeText(context, "size = "+conflictDeleted.size(),
                                    Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {

                            /*txtPrice.setText("Price : "+e.getMessage());

                            Toast.makeText(context, "size = "+response,
                                    Toast.LENGTH_LONG).show();*/
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
    public void deleteRoom(int roomId){
        String url="http://10.0.2.2:80/RoomDataBase/deleteRoomByEm.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textTry.setText(error.getMessage());
                Toast.makeText(context,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType(){
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {
                //ServiceFromTable service=new ServiceFromTable(userId,roomId,totalPrice);
                Map<String, String> params = new HashMap<>();
                //by shared preference
                params.put("id",roomId+"");

                return params;
            }
        };
        queue.add(request);

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
