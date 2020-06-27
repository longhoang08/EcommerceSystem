package com.example.mobile_ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_ui.Adapter.ListOfBuyProductAdapter;
import com.example.mobile_ui.Model.BuyRecord;
import com.example.mobile_ui.Model.Customer;
import com.example.mobile_ui.Model.MyBuyRecord;
import com.example.mobile_ui.Model.Product;

import java.util.ArrayList;

public class DetailBuyrecordActivity extends AppCompatActivity {
    TextView nameOfShop;
    TextView address;
//    ListView listOfBuyProduct;
    TextView money;
    TextView transport;
    TextView moneySum;
//    TextView review;
//    LinearLayout footer;
//    TextView buyAgain;
    BuyRecord buyRecord;

    void anhxa(){
        nameOfShop = findViewById(R.id.nameOfShop);
        address = findViewById(R.id.address);
//        listOfBuyProduct = findViewById(R.id.listOfBuyProduct);
        money = findViewById(R.id.money);
        transport = findViewById(R.id.transport);
        moneySum = findViewById(R.id.moneySum);
//        footer = findViewById(R.id.footer);
//        review = findViewById(R.id.review);
//        buyAgain = findViewById(R.id.buyAgain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buyrecord);
        anhxa();
        //lấy dữ liệu đơn hàng từ id đơn hàng
        getData();
        //hiện listview
//        ListOfBuyProductAdapter adapter = new ListOfBuyProductAdapter(new ArrayList<Product>(), new ArrayList<Integer>());
//        listOfBuyProduct.setAdapter(adapter);
//        UIUtils.setListViewHeightBasedOnItems(listOfBuyProduct);
        //đổ dữ liệu các view còn lại
//        nameOfShop.setText(buyRecord.getNameOfShop());
//        address.setText(buyRecord.get);
        money.setText(buyRecord.getMoney()+"");
        transport.setText(transformFee(buyRecord.getMoney())+"");
        moneySum.setText( (buyRecord.getMoney()+transformFee(buyRecord.getMoney())) + "");
//        //trạng thái đơn hàng là đã giao mới được đánh giá
//        String state = "dagiao";
//        if(!myBuyRecord.getState().equals(state)) footer.setVisibility(View.GONE);
//        Toast.makeText(DetailMybuyrecordActivity.this,
//                "Đánh giá thành công : "+ myBuyRecord.getState(),
//                Toast.LENGTH_SHORT).show();

/*        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailBuyrecordActivity.this);
                final AlertDialog alert = builder.create();
                LinearLayout layout = new LinearLayout(DetailBuyrecordActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final RatingBar rate = new RatingBar(DetailBuyrecordActivity.this);
                LinearLayout layoutWrapRate = new LinearLayout(DetailBuyrecordActivity.this);
                rate.setNumStars(5);
                rate.setRating(1);
                layoutWrapRate.addView(rate);
                final EditText edt = new EditText(DetailBuyrecordActivity.this);
                edt.setHint("nhập đánh giá của bạn");
                edt.setPadding(30, 30, 30, 30);
                final Button close = new Button(DetailBuyrecordActivity.this);
                final Button submit = new Button(DetailBuyrecordActivity.this);
                submit.setText("Gửi");
                close.setText("Hủy");
                layout.addView(layoutWrapRate);
                layout.addView(edt);
                layout.addView(submit);
                layout.addView(close);
                alert.setView(layout);
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int star = (int) rate.getRating();
                        Toast.makeText(DetailBuyrecordActivity.this,
                                "Đánh giá thành công : "+ star+" "+edt.getText(),
                                Toast.LENGTH_SHORT).show();
                        alert.cancel();
                    }
                });
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alert.cancel();
                    }
                });
                alert.show();
            }
        });
        buyAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    //sửa để lấy dữ liệu từ api
    private void getData(){
        //lấy ra id của đơn hàng
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            int idOfMybyrecord = bundle.getInt("idOfMybyrecord");
        }
        Customer an = new Customer(R.drawable.icon_kiwi_fruit,"Thành An","Nam",
                "11/8/1999","Hà Nam","0966947994","12345");
        String url = "https://image2.tienphong.vn/w665/Uploaded/2020/bzivobpc/2019_12_26/vanda_ip_man_4_paui.jpg";
        ArrayList<Product> x=new ArrayList<Product>();
        x.add(new Product(""+R.drawable.icon_kiwi_fruit,"banana",12000,120));
//        x.add(new Product(""+R.drawable.icon_dragon_fruit,"thanh long",25000,100));
        ArrayList<Integer> y=new ArrayList<Integer>();
        y.add(new Integer(2));
//        y.add(new Integer(3));
        buyRecord = new BuyRecord("danggiao", 3,an, x.get(0), y.get(0),25000);
    }

    //lấy ra tiền vận chuyển
    private int transformFee(Integer x){
        int x1=x.intValue();
        if(x1>200000) return 0;
        else if(x1>100000) return 10000;
        else return 0;
    }

    //đây chứa hàm để listView hiện toàn bộ item, không có scroll, không cần quan tâm
    /*public static class UIUtils {

//
//         * Sets ListView height dynamically based on the height of the items.
//         *
//         * @param listView to be resized
//         * @return true if the listView is successfully resized, false otherwise

        public static boolean setListViewHeightBasedOnItems(ListView listView) {

            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter != null) {

                int numberOfItems = listAdapter.getCount();

                // Get total height of all items.
                int totalItemsHeight = 0;
                for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                    View item = listAdapter.getView(itemPos, null, listView);
                    item.measure(0, 0);
                    totalItemsHeight += item.getMeasuredHeight();
                }

                // Get total height of all item dividers.
                int totalDividersHeight = listView.getDividerHeight() *
                        (numberOfItems - 1);

                // Set list height.
                ViewGroup.LayoutParams params = listView.getLayoutParams();
                params.height = totalItemsHeight + totalDividersHeight;
                listView.setLayoutParams(params);
                listView.requestLayout();

                return true;

            } else {
                return false;
            }

        }
    }*/
}
//chỉ truyền id của đơn hàng của tôi, nên gọi lên API sẽ lấy về đk mybuyrecord
//lấy ra arrayList Product, hiện ra theo listView
// kiểm tra state, nếu là "dagiao", hiện đánh giá, mua lại

