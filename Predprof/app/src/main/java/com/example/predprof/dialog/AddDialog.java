package com.example.predprof.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.example.predprof.R;
import com.example.predprof.adapter.ProductAdapter;
import com.example.predprof.data.ProductsDbHelper;

import java.util.ArrayList;

public class AddDialog extends DialogFragment {
    ProductsDbHelper dbHelper;
    Context context;
    ArrayList<String> productsArrayList;
    ProductAdapter productAdapter;
    public AddDialog(Context context, ArrayList<String> productsArrayList, ProductAdapter productAdapter) {
        this.context = context;
        this.productsArrayList = productsArrayList;
        this.productAdapter = productAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dbHelper = new ProductsDbHelper(context);
        final View addView = getLayoutInflater().inflate(R.layout.add_sheet, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(addView);
        AppCompatButton btSave = addView.findViewById(R.id.save);
        EditText etName = addView.findViewById(R.id.editProductName);
        btSave.setOnClickListener(v1 -> {
            String text = etName.getText().toString();
            if(!text.isEmpty()) {
                dbHelper.addProduct(text);
                productsArrayList.add(text);
                productAdapter.notifyItemInserted(productsArrayList.size() - 1);
                requireDialog().cancel();
            }
        });
        return builder.create();
    }
}
