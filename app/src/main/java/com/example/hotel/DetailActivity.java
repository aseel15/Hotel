package com.example.hotel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotel.model.Room;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
    String dateCheckIn;
    String dateCheckOut;
    List<Room>roomsList;
    int roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        String item = intent.getStringExtra("roomNum");
        dateCheckIn=intent.getStringExtra("checkInDate");
        dateCheckOut=intent.getStringExtra("checkOutDate");
        roomNumber = Integer.parseInt(item);
        roomsList=(List<Room>) getIntent().getSerializableExtra("arrayList");
        populateData(roomNumber);

    }
    public Room getRoomObject(int objectNumber){
        for(int i=0;i<roomsList.size();i++)
            if(objectNumber==roomsList.get(i).getId())
                return roomsList.get(i);
        return null;
    }
    public void populateData(int roomNum){

        Room room=getRoomObject(roomNum);
        ListView listView=findViewById(R.id.listView);
        ArrayList<String> arr=new ArrayList<>();
        arr.add("Room Number : "+room.getId());
        arr.add("Room Type : "+room.getRoomType());
        arr.add("Price : "+room.getPrice()+"$");
        arr.add("Room Size : "+room.getRoomSize());
        arr.add("Bed Type : "+room.getBedType());
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arr);
        listView.setAdapter(arrayAdapter);
    }
    public void postData(){
        String url="http://10.0.2.2:80/RoomDataBase/reserveRoom.php";
        RequestQueue queue = Volley.newRequestQueue(DetailActivity.this);
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                    Toast.makeText(DetailActivity.this,
                            jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // on below line we are displaying a success toast message.

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DetailActivity.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType(){
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                params.put("roomsID", roomNumber+"");
                //by shared preference
                params.put("userId", "1");
                params.put("check_In", dateCheckIn);
                params.put("check_Out",dateCheckOut);
                params.put("totalPrice",roomsList.get(roomNumber).getPrice()+"");

                return params;
            }
        };
        queue.add(request);
    }



    public void btnReserveOnClick(View view) {
        if(dateCheckIn==null||dateCheckOut==null){
            Toast.makeText(DetailActivity.this,
                    "You should enter check in & check out date", Toast.LENGTH_SHORT).show();
        }
        else{
            //call method to post it to database
            postData();
        }
    }
}