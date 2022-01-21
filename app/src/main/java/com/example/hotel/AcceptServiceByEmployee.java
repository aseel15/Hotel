package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotel.model.ServiceFromTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AcceptServiceByEmployee extends AppCompatActivity {

    ArrayList<ServiceFromTable>services=new ArrayList<>();;
    RecyclerView recyclerView;
    int id;
    int roomIdUser;
    private static final String BASE_URL = "http://10.0.2.2:80/RoomDataBase/getServiceFromEm.php";
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_service_by_employee);
        queue = Volley.newRequestQueue(this);
        recyclerView=findViewById(R.id.service_recycler);

        getServices();

    }
    public void getServices(){
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
                                String serviceName= jsonObject.getString("serviceName");
                                int price = jsonObject.getInt("price");
                                services.add(new ServiceFromTable(id,roomId,userId,serviceName,price));
                            }
                            populateServices();

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
    public void populateServices(){

        recyclerView.setLayoutManager(new LinearLayoutManager(AcceptServiceByEmployee.this));
        CaptionedServiceAdapter adapter = new CaptionedServiceAdapter(AcceptServiceByEmployee.this,services);

        recyclerView.setAdapter(adapter);

    }
}