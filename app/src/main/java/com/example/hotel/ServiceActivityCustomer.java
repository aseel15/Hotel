package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotel.model.Room;
import com.example.hotel.model.ServiceItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceActivityCustomer extends AppCompatActivity {

    Spinner spinnerService;
    Spinner spinnerRoomId;
    ArrayList<ServiceItem>serviceItems;
    ArrayList<String>selectedServices;
    ListView selectedView;
    ArrayAdapter<String>arrayAdapter;
    ArrayList<String>roomIdList;
    ArrayAdapter<String>roomIdAdapter;
    String roomIdChosen;
    TextView textView;
    String n=String.valueOf(1);
    private String BASE_URL = "http://10.0.2.2/RoomDataBase/getRoomId.php?userId=" + n;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_customer);
        queue = Volley.newRequestQueue(this);
        textView=findViewById(R.id.databa);
        spinnerService=findViewById(R.id.spinnerService);
        spinnerRoomId=findViewById(R.id.spinnerRoomId);
        serviceItems=getServiceItems();
        selectedView=findViewById(R.id.listViewServices);
        ServiceAdapter serviceAdapter=new ServiceAdapter(ServiceActivityCustomer.this,serviceItems);
        spinnerService.setAdapter(serviceAdapter);
        selectedServices=new ArrayList<>();
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,selectedServices);
        spinnerService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                ServiceItem serviceItem=(ServiceItem) parentView.getSelectedItem();
                if(!serviceItem.getNameService().equalsIgnoreCase("select")) {
                    selectedServices.add(serviceItem.getNameService());
                    arrayAdapter.notifyDataSetChanged();
                    selectedView.setAdapter(arrayAdapter);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        selectedView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                String serviceDeleted=adapterView.getItemAtPosition(i).toString();
                selectedServices.remove(i);
                arrayAdapter.notifyDataSetChanged();
                selectedView.setAdapter(arrayAdapter);
                return true;
            }

        });
        roomIdList=new ArrayList<>();
        getRoomId();
        roomIdAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,roomIdList);
        spinnerRoomId.setAdapter(roomIdAdapter);
        spinnerRoomId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                String serviceItem=(String) parentView.getSelectedItem();
                if(!serviceItem.equalsIgnoreCase("select")) {
                    roomIdChosen=serviceItem;
                    textView.setText(roomIdChosen);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    private ArrayList<ServiceItem> getServiceItems() {
        serviceItems=new ArrayList<>();
        serviceItems.add(new ServiceItem("select",R.drawable.ic_toiletries));
        serviceItems.add(new ServiceItem("Beverage",R.drawable.beverage));
        serviceItems.add(new ServiceItem("Extra Toiletries", R.drawable.toiletries));
        serviceItems.add(new ServiceItem("Extra Towels",R.drawable.towels));
        serviceItems.add(new ServiceItem("Food",R.drawable.fastfood));
        serviceItems.add(new ServiceItem("Valet Parking",R.drawable.keys));
        serviceItems.add(new ServiceItem("House Keeping",R.drawable.housecleaning));

        return serviceItems;
    }
    public void getRoomId(){
        roomIdList.add("select");
        StringRequest request = new StringRequest(Request.Method.GET, BASE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray roomList=new JSONArray(response);
                            for(int i=0;i<roomList.length();i++){
                                JSONObject jsonObject= roomList.getJSONObject(i);
                                int id = jsonObject.getInt("roomsID");
                                roomIdList.add(id+"");
                            }
                            textView.setText(roomIdList.size()+"");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            Toast.makeText(ServiceActivityCustomer.this, error.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    });
        queue.add(request);
    }


    public void btnRequestClick(View view) {
        Intent intent= new Intent(this,AcceptServiceByEmployee.class);
        //get the id from the shared preference
         intent.putExtra("userId","1");
         intent.putExtra("roomId",roomIdChosen);
         intent.putExtra("userRequest",selectedServices);
         startActivity(intent);


    }
}