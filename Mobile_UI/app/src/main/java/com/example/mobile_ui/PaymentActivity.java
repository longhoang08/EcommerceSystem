package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mobile_ui.Adapter.PaymentAdapter;
import com.example.mobile_ui.Model.Payment;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    ExpandHeightGridView simpleListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        simpleListView = findViewById(R.id.simpleListView);
        ArrayList<Payment> payments = new ArrayList<>();
        getDataPayment(payments);
        PaymentAdapter paymentAdapter = new PaymentAdapter(payments);
        simpleListView.setAdapter(paymentAdapter);
    }

    private void getDataPayment(ArrayList<Payment> payments) {
        String url = "https://image.thanhnien.vn/660/uploaded/ngocthanh/2019_12_21/diep-van-bon1_qnej.jpg";
        payments.add(new Payment("Shop Thành An", url, "Sữa milo", 20000, 2, 10000, 5000));
        payments.add(new Payment("Shop Thành An", url, "Sữa milo", 20000, 2, 10000, 5000));
        payments.add(new Payment("Shop Thành An", url, "Sữa milo", 20000, 2, 10000, 5000));
    }
}