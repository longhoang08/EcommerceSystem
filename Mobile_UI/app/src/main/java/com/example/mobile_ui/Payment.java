package com.example.mobile_ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class Payment extends Activity {
    ExpandHeightGridView simpleList;
    ListView shippingOrder;
    TextView totalMoney;

    String countryList[] = {"India", "China", "australia", "Portugle"};
    int flags[] = {R.drawable.india, R.drawable.china, R.drawable.australia, R.drawable.portugle};
    String productType[] = {"black", "blue", "green", "red"};
    double productOfPrice[] = {112342.3, 123213.4, 95123.5, 34.6};
    int quantityProduct[] = {1, 2, 3, 4};

    public void onCreate(Bundle savedInstanceState){
        double result = 0;
        for(int i = 0; i < productOfPrice.length; i++){
            result += productOfPrice[i];
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        totalMoney = (TextView) findViewById(R.id.textViewTotalPrice);
        totalMoney.setText(String.valueOf(result));
        simpleList = (ExpandHeightGridView) findViewById(R.id.simpleListView);
        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), countryList, flags, productType, productOfPrice, quantityProduct);
        simpleList.setAdapter(customAdapter);
    }
}
