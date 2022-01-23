package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotel.model.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddRoom extends Activity {

    private Spinner spinRoomType, spinBedType, spinNumOfBeds;
    private EditText edtSize,edtPrice;
    int req_Code=1;
    String urlImage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        spinBedType=findViewById(R.id.bedTypeAdded);
        spinRoomType=findViewById(R.id.roomTypeAdded);
        spinNumOfBeds=findViewById(R.id.numOfBAdded);
        edtSize=findViewById(R.id.roomSizeAddedn);
        edtPrice=findViewById(R.id.PriceAdded);


       // populateImages();

    }


    public void btnClkAddRoom(View view) {
        String priceTxt=edtPrice.getText().toString();
        String sizeTxt=edtSize.getText().toString();
        if(priceTxt.isEmpty())
            Toast.makeText(AddRoom.this, ("Please enter the price"), Toast.LENGTH_SHORT).show();
        else if(sizeTxt.isEmpty())
            Toast.makeText(AddRoom.this, ("Please enter the size"), Toast.LENGTH_SHORT).show();
        else if(urlImage.isEmpty())
            Toast.makeText(AddRoom.this, ("Please choose the image "), Toast.LENGTH_SHORT).show();
        else {
            //call database method
            //edtSize.setText("added in right");
            AddRoomToDB();
            Toast.makeText(AddRoom.this, ("room size "+sizeTxt), Toast.LENGTH_SHORT).show();
        }


    }
    public void AddRoomToDB(){
        String url="http://10.0.2.2:80/FinalProject/addRoomToDB.php";

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //textTry.setText(response);
                        Toast.makeText(AddRoom.this,
                                response, Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AddRoom.this,
                        "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public String getBodyContentType(){
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();


                params.put("price", edtPrice.getText().toString());
                params.put("imageName", urlImage);
                params.put("roomType", spinRoomType.getSelectedItem().toString());
                params.put("bedType",spinBedType.getSelectedItem().toString());
                params.put("numOfBeds",spinNumOfBeds.getSelectedItem().toString());
                params.put("roomSize",edtSize.getText().toString());

                return params;
            }
        };
        queue.add(request);
    }

    public void btnImgOnClick(View view) {
        startActivityForResult(new Intent(this, ImageHotelActivity.class), req_Code);
    }
    public void onActivityResult(int reqCode, int resultCode, Intent data){
        if(reqCode==req_Code){
            if(resultCode==RESULT_OK){
                urlImage=data.getData().toString();
                //edtPrice.setText(data.getData().toString());
                // Toast.makeText(this, data.getData().toString(), Toast.LENGTH_SHORT);

            }
        }


    }
}