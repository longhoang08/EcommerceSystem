package com.example.mobile_ui.Model;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mobile_ui.ProductSearchActivity;
import com.example.mobile_ui.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class filterProduct extends AppCompatDialogFragment {
    ArrayList<String> nameBrand = new ArrayList<>();
    ArrayList<String> codeBrand = new ArrayList<>();
    ArrayList<String> nameCategory = new ArrayList<>();
    ArrayList<String> codeCategory = new ArrayList<>();
    Spinner spinnerCategory, spinnerBrand;
    TextView textViewApply;

    public filterProduct(ArrayList<String> nameBrand, ArrayList<String> codeBrand, ArrayList<String> nameCategory, ArrayList<String> codeCategory) {
        this.nameBrand = nameBrand;
        this.codeBrand = codeBrand;
        this.nameCategory = nameCategory;
        this.codeCategory = codeCategory;
        System.out.println(nameBrand.toString());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.filter_product, null);
        builder.setView(view);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        spinnerBrand = view.findViewById(R.id.spinnerBrand);
        textViewApply = view.findViewById(R.id.textViewApply);
        ArrayAdapter<String> adapterBrand = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, nameBrand);
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, nameCategory);
        spinnerBrand.setAdapter(adapterBrand);
        spinnerCategory.setAdapter(adapterCategory);

        textViewApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positionBrand, positionCategory;
                positionBrand = spinnerBrand.getSelectedItemPosition();
                positionCategory = spinnerCategory.getSelectedItemPosition();
                ProductSearchActivity.filterByField(codeBrand.get(positionBrand), codeCategory.get(positionCategory));
                getDialog().cancel();
            }
        });
        return builder.create();
    }

}
