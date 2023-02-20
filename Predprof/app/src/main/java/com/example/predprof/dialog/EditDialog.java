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

public class EditDialog extends DialogFragment {
    ProductsDbHelper dbHelper;
    Context context;
    String prevName;
    ArrayList<String> productsArrayList;
    ProductAdapter productAdapter;
    public EditDialog(Context context, String prevName, ArrayList<String> productsArrayList, ProductAdapter productAdapter) {
        this.context = context;
        this.prevName = prevName;
        this.productsArrayList = productsArrayList;
        this.productAdapter = productAdapter;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dbHelper = new ProductsDbHelper(context);
        final View editView = getLayoutInflater().inflate(R.layout.edit_sheet, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(editView);
        AppCompatButton btSave = editView.findViewById(R.id.save);
        AppCompatButton btRemove = editView.findViewById(R.id.remove);
        EditText etName = editView.findViewById(R.id.editProductName);
        etName.setText(prevName);
        btSave.setOnClickListener(v -> {
            String text = etName.getText().toString();
            if(!text.isEmpty()) {
                dbHelper.updateProduct(prevName, text);
                int index = productsArrayList.indexOf(prevName);
                productsArrayList.set(index,text);
                productAdapter.notifyItemChanged(index);
                requireDialog().cancel();
            }
        });
        btRemove.setOnClickListener(v -> {
            dbHelper.deleteProduct(prevName);
            int index = productsArrayList.indexOf(prevName);
            productsArrayList.remove(index);
            productAdapter.notifyItemRemoved(index);
            requireDialog().cancel();
        });
        return builder.create();
    }
}
