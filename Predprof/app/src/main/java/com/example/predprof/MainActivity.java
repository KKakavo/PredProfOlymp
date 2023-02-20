package com.example.predprof;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.predprof.adapter.ProductAdapter;
import com.example.predprof.data.ProductsDbHelper;
import com.example.predprof.dialog.AddDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AppCompatButton btAdd;
    private AppCompatButton btScan;
    private ArrayList<String> productsArrayList;
    private ProductsDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler);
        btAdd = findViewById(R.id.add);
        btScan = findViewById(R.id.scan);
        dbHelper = new ProductsDbHelper(this);
        productsArrayList = dbHelper.readProducts();
        ProductAdapter productAdapter = new ProductAdapter( LayoutInflater.from(this),MainActivity.this, productsArrayList);
        recyclerView.setAdapter(productAdapter);
        btAdd.setOnClickListener(v -> {
            FragmentManager manager = getSupportFragmentManager();
            AddDialog dialogFragment = new AddDialog(MainActivity.this, productsArrayList, productAdapter);
            dialogFragment.show(manager,"addDialog");
            productAdapter.notifyDataSetChanged();
        });
        btScan.setOnClickListener(v -> {});
    }



}