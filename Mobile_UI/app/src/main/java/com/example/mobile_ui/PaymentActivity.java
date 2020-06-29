package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.example.mobile_ui.Adapter.PaymentAdapter;
import com.example.mobile_ui.Model.OrderProduct;
import com.example.mobile_ui.Model.Payment;
import com.example.mobile_ui.View.ExpandHeightGridView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    ExpandHeightGridView simpleListView;
    ArrayList<Payment> payments = new ArrayList<>();
    TextView totalFeeAllPro, totalTransport, totalSale, totalFee, textViewBuy, textViewTotalPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        totalFeeAllPro = findViewById(R.id.totalFeeAllPro);
        totalTransport = findViewById(R.id.totalTransport);
        textViewTotalPrice = findViewById(R.id.textViewTotalPrice);
        totalSale = findViewById(R.id.totalSale);
        totalFee = findViewById(R.id.totalFee);
        textViewBuy = findViewById(R.id.textViewBuy);
        textViewBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(PaymentActivity.this);
                pd.setTitle("Vui lòng chờ 1 lát ....");
                pd.show();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        pd.cancel();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(PaymentActivity.this);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Đặt hàng thành công.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }.start();
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String strListPro = bundle.getString("listPro");
//        System.out.println(strListPro);
        try {
            JSONArray listOrderProduct = new JSONArray(strListPro);
            getDataPayment(listOrderProduct);
            simpleListView = findViewById(R.id.simpleListView);
            PaymentAdapter paymentAdapter = new PaymentAdapter(payments);
            simpleListView.setAdapter(paymentAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        System.out.println(listOrderProduct.size());

    }

    private void getDataPayment(JSONArray listOrderProduct) {
        int ItotalFeeAllPro = 0, ItotalTransport = 0, ItotalSale = 0, ItotalFee = 0;
        for (int i = 0; i < listOrderProduct.length(); i++) {
            try {
                if ((boolean)listOrderProduct.getJSONObject(i).get("state")) {
                    String nameShop = (String) listOrderProduct.getJSONObject(i).get("nameShop");
                    //System.out.println(nameShop);
                    String urlImage = (String) listOrderProduct.getJSONObject(i).get("imageRepresent");
                    String namePro = (String) listOrderProduct.getJSONObject(i).get("nameProduct");
                    int price = (int) listOrderProduct.getJSONObject(i).get("price");
                    int quantity = (int) listOrderProduct.getJSONObject(i).get("quantity");
                    int feeTransport = transformFee(price*quantity);
                    int feeSale = 0;
                    payments.add(new Payment(nameShop, urlImage, namePro, price, quantity, feeTransport, feeSale));
                    ItotalFeeAllPro += price*quantity;
                    ItotalTransport += feeTransport;
                    ItotalSale += feeSale;
                    ItotalFee += ItotalFeeAllPro+ItotalTransport-ItotalSale;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        totalFeeAllPro.setText(ItotalFeeAllPro+ "đ");
        totalTransport.setText(ItotalTransport+ "đ");
        totalSale.setText(ItotalSale+ "đ");
        totalFee.setText(ItotalFee+ "đ");
        textViewTotalPrice.setText(ItotalFee +" đ");
//        payments.add(new Payment("Shop Thành An", url, "Sữa milo", 20000, 2, 10000, 5000));
//        payments.add(new Payment("Shop Thành An", url, "Sữa milo", 20000, 2, 10000, 5000));
//        payments.add(new Payment("Shop Thành An", url, "Sữa milo", 20000, 2, 10000, 5000));
    }

    // tinh phi van chuyen
    private int transformFee(Integer x){
        int x1=x.intValue();
        if(x1>200000) return 0;
        else if(x1>100000) return 10000;
        else return 5000;
    }
}