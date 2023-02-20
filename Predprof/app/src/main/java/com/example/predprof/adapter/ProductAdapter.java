package com.example.predprof.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.predprof.R;
import com.example.predprof.dialog.EditDialog;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final LayoutInflater inflater;
    ArrayList<String> productsArrayList;
    private Context context;
    private ProductAdapter productAdapter;
    public ProductAdapter(LayoutInflater inflater, Context context, ArrayList<String> productsArrayList) {
        this.inflater = inflater;
        this.context = context;
        this.productsArrayList = productsArrayList;
        productAdapter = this;
    }

    private class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.productName);
            tvName.setOnClickListener(v -> {
                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                EditDialog dialogFragment = new EditDialog(context, tvName.getText().toString(), productsArrayList, productAdapter);
                dialogFragment.show(manager, "editDialog");
            });
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String name = productsArrayList.get(position);
        ((ViewHolder)holder).tvName.setText(name);

    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }
}
