package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImageHotelActivity extends AppCompatActivity {

    RecyclerView recycler;
    ArrayList<String> imagesList;
    String urlImage="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_hotel);
        recycler=findViewById(R.id.addRoom_recycler);
        populateImages();
    }
    public void populateImages(){
        imagesList=new ArrayList<>();
        String url ="https://api.unsplash.com/search/photos?client_id=a343tF8cTQJPm3NR2LZhMbZf0QUOLZ-MlOAw0GI2njQ&page=1&per_page=30&query=hotel%20bedroom";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url,
                        null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonArray=response.getJSONArray("results");
                            int length=jsonArray.length();

                            for(int i=0;i<length;i++) {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                String imageUrl = "";
                                JSONObject imgUrl =obj.getJSONObject("urls");
                                imageUrl = imgUrl.getString("small");
                                imagesList.add(imageUrl);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        recycler.setLayoutManager(new LinearLayoutManager(ImageHotelActivity.this));
                        CaptionRoomImg adapter = new CaptionRoomImg(ImageHotelActivity.this, imagesList);

                        recycler.setAdapter(adapter);
                        adapter.OnItemListener(new CaptionRoomImg.OnItemListener() {
                            @Override
                            public void onItemClick(int position) {
                                urlImage=imagesList.get(position);
                                Toast.makeText(ImageHotelActivity.this, "position"+position+" "+imagesList.get(position),
                                        Toast.LENGTH_LONG).show();
                                Intent data =new Intent();
                                data.setData(Uri.parse(urlImage));
                                setResult(RESULT_OK, data);
                                finish();

                            }
                        });
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error


                    }
                });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


}