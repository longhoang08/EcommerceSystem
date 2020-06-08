package com.example.mobile_ui;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class DialogFilterActivity extends Activity {

    Spinner spinnerCategory, spinnerAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_filter);
        setTitle("Lọc sản phẩm");

        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerAddress = findViewById(R.id.spinnerAddress);

        // set dữ liệu
        String[] arrCategory = {"Rượu chè", "Cờ bạc", "Ma túy"};
        String[] arrAddress = {"New York", "Sao Hỏa", "Sao diêm vương"};
        ArrayAdapter<String> adapterCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrCategory);
        ArrayAdapter<String> adapterAddress = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrAddress);
        adapterAddress.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        adapterCategory.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerAddress.setAdapter(adapterAddress);
        spinnerCategory.setAdapter(adapterCategory);
        // set sự kiện

    }
}
