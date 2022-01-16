package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class AcceptServiceByEmployee extends AppCompatActivity {

    ArrayList<String>services;
    RecyclerView recyclerView;
    int id;
    int roomIdUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_service_by_employee);
        Intent intent = getIntent();
        services=(ArrayList<String>) intent.getSerializableExtra("userRequest");
        String idStr=intent.getStringExtra("userId");
        id=Integer.parseInt(idStr);
        roomIdUser=Integer.parseInt(intent.getStringExtra("roomId"));
        recyclerView=findViewById(R.id.service_recycler);
        populateServices();

    }
    public void populateServices(){
        TextView text=findViewById(R.id.textTT);
        text.setText("size = "+roomIdUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(AcceptServiceByEmployee.this));
        CaptionedServiceAdapter adapter = new CaptionedServiceAdapter(AcceptServiceByEmployee.this,services,id, roomIdUser);

        recyclerView.setAdapter(adapter);

    }
}