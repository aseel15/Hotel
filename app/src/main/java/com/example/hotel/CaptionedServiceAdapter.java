package com.example.hotel;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotel.model.Room;
import com.example.hotel.model.ServiceFromTable;
import com.example.hotel.model.ServiceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaptionedServiceAdapter extends RecyclerView.Adapter<CaptionedServiceAdapter.ViewHolder>{
    private Button[]acceptButtons;
    private Button[]declineButtons;
    private ArrayList<String> services;
    ArrayList<ServiceFromTable>servicesFromTable;
    private int id;
    private int roomIdUser;
    Context context;
    private RequestQueue queue;
    private static final String BASE_URL = "http://10.0.2.2:80/RoomDataBase/getServices.php";

    public CaptionedServiceAdapter(Context context, ArrayList<String> services, int id, int roomIdUser) {
        this.acceptButtons = acceptButtons;
        this.declineButtons = declineButtons;
        this.services = services;
        this.id = id;
        this.roomIdUser=roomIdUser;
        this.context=context;

        queue = Volley.newRequestQueue(context);
        servicesFromTable=new ArrayList<>();
       // getServiceTable();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_caption_service,
                parent,
                false);

        return new CaptionedServiceAdapter.ViewHolder(v);
    }
    TextView serviceName;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        serviceName=(TextView)cardView.findViewById(R.id.serviceName);

        //serviceName.setText(services.get(position));
        Button acceptButton=(Button) cardView.findViewById(R.id.btnAccept);
        //if he click accept the service will be added to the database
        acceptButton.setOnClickListener(view -> {
            getServiceTable(id);
           // serviceName.setText(servicesFromTable.size()+"");
           // addServices(/*id,roomIdUser,5*/);
        });
        Button declineButton=(Button) cardView.findViewById(R.id.btnDecline);
        //the user should get notification about the declined service
        declineButton.setOnClickListener(view -> {
            getServiceTable(id);


        });
    }

    public void getServiceTable(int id){

        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ServicesList=new JSONArray(response);
                            for(int i=0;i<ServicesList.length();i++){
                                JSONObject jsonObject= ServicesList.getJSONObject(i);
                                int id= jsonObject.getInt("id");
                                int roomId= jsonObject.getInt("roomId");
                                int userId= jsonObject.getInt("userId");
                                int price = jsonObject.getInt("totalPrice");
                                servicesFromTable.add(new ServiceFromTable(id,roomId,userId,price));
                            }
                            boolean flag=false;

                            for(int i=0;i<servicesFromTable.size();i++){
                               // serviceName.append("exi"+servicesFromTable.get(i).getRoomId());
                                if(id==servicesFromTable.get(i).getUserId())
                                 if(roomIdUser==servicesFromTable.get(i).getRoomId()) {
                                     int idService=servicesFromTable.get(i).getId();
                                     updateService(idService,servicesFromTable.get(i).getTotalPrice());
                                    //call updateService();
                                    serviceName.setText("enter to update");
                                    flag=true;
                                    break;
                                }
                            }
                            if(!flag) {
                                addServices();
                                serviceName.setText("enter to add");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e(error.toString());
            }
        });
        queue.add(request);
    }


    public void addServices(/*int userId, int roomId,int totalPrice*/ ){
        //if the user id is not found in the service table
        String url="http://10.0.2.2:80/RoomDataBase/addService.php";
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

                params.put("roomId", roomIdUser+"");
                params.put("userId", id+"");
                params.put("totalPrice", "6");
                return params;
            }
        };
        queue.add(request);


    }
    public void updateService(int id,int totalPrice){
        //if the user id is in the table then update his price
        String url="http://10.0.2.2:80/RoomDataBase/updateService.php?id="+id;
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

                int newPrice=totalPrice+5;
                params.put("totalPrice", newPrice+"");
                return params;
            }
        };
        queue.add(request);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView cardView){
            super(cardView);
            this.cardView = cardView;

        }

    }
}
