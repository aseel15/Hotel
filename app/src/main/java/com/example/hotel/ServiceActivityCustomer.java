package com.example.hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.hotel.model.ServiceItem;

import java.util.ArrayList;

public class ServiceActivityCustomer extends AppCompatActivity {

    Spinner spinnerService;
    ArrayList<ServiceItem>serviceItems;
    ArrayList<String>selectedServices;
    ListView selectedView;
    ArrayAdapter<String>arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_customer);
        spinnerService=findViewById(R.id.spinnerService);
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


    public void btnRequestClick(View view) {
        Intent intent= new Intent(this,AcceptServiceByEmployee.class);
        //get the id from the shared preference
        // intent("userId",id);
        intent.putExtra("userRequest",selectedServices);


    }
}