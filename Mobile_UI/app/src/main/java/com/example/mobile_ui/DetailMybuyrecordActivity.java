package com.example.mobile_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobile_ui.Adapter.ListOfBuyProductAdapter;
import com.example.mobile_ui.Model.MyBuyRecord;
import com.example.mobile_ui.Model.Product;

import java.util.ArrayList;

public class DetailMybuyrecordActivity extends AppCompatActivity {
    TextView nameOfShop;
    TextView address;
    ListView listOfBuyProduct;
    TextView money;
    ImageView proImg;
    TextView transport;
    TextView moneySum;
//    TextView review;
//    LinearLayout footer;
//    TextView buyAgain;
    MyBuyRecord myBuyRecord;

    void anhxa(){
        nameOfShop = findViewById(R.id.nameOfShop);
        address = findViewById(R.id.address);
//        listOfBuyProduct = findViewById(R.id.listOfBuyProduct);
        money = findViewById(R.id.money);
        transport = findViewById(R.id.transport);
        moneySum = findViewById(R.id.moneySum);
        proImg = findViewById(R.id.proImg);
//        footer = findViewById(R.id.footer);
//        review = findViewById(R.id.review);
//        buyAgain = findViewById(R.id.buyAgain);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_mybuyrecord);
        anhxa();
        //lấy dữ liệu đơn hàng từ id đơn hàng
        getData();
        String url = "https://sohanews.sohacdn.com/thumb_w/660/2013/medium-60e2bb5d1f6c4f9a93fec522c3454a1d-650-1376558219843-crop1376560855343p-crop1376560863617p.jpg";
        Glide.with(DetailMybuyrecordActivity.this)
                .load(url).override(80, 80).centerCrop()
                .into(proImg);
        //hiện listview
//        ListOfBuyProductAdapter adapter = new ListOfBuyProductAdapter(myBuyRecord.getBuyProduct(),myBuyRecord.getBuyNum());
//        listOfBuyProduct.setAdapter(adapter);
//        UIUtils.setListViewHeightBasedOnItems(listOfBuyProduct);
        //đổ dữ liệu các view còn lại
//        nameOfShop.setText(myBuyRecord.getNameOfShop());
        //address.setText(myBuyRecord.get);
//        money.setText(myBuyRecord.getMoney()+"");
//        transport.setText(transformFee(myBuyRecord.getMoney())+"");
//        moneySum.setText( (myBuyRecord.getMoney()+transformFee(myBuyRecord.getMoney())) + "");
        //trạng thái đơn hàng là đã giao mới được đánh giá
//        String state = "dagiao";
//        if(!myBuyRecord.getState().equals(state)) footer.setVisibility(View.GONE);
//        Toast.makeText(DetailMybuyrecordActivity.this,
//                "Đánh giá thành công : "+ myBuyRecord.getState(),
//                Toast.LENGTH_SHORT).show();

        /*review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailMybuyrecordActivity.this);
                final AlertDialog alert = builder.create();
                LinearLayout layout = new LinearLayout(DetailMybuyrecordActivity.this);
                layout.setOrientation(LinearLayout.VERTICAL);
                final RatingBar rate = new RatingBar(DetailMybuyrecordActivity.this);
                LinearLayout layoutWrapRate = new LinearLayout(DetailMybuyrecordActivity.this);
                rate.setNumStars(5);
                rate.setRating(1);
                layoutWrapRate.addView(rate);
                final EditText edt = new EditText(DetailMybuyrecordActivity.this);
                edt.setHint("nhập đánh giá của bạn");
                edt.setPadding(30, 30, 30, 30);
                final Button close = new Button(DetailMybuyrecordActivity.this);
                final Button submit = new Button(DetailMybuyrecordActivity.this);
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
                        Toast.makeText(DetailMybuyrecordActivity.this,
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
        int idOfMybyrecord = 0;
        if (bundle != null) {
            idOfMybyrecord = bundle.getInt("idOfMybyrecord");
        }
        ArrayList<Product> x=new ArrayList<Product>();
        x.add(new Product(""+R.drawable.icon_kiwi_fruit,"banana",12000,120));
//        x.add(new Product(""+R.drawable.icon_dragon_fruit,"thanh long",25000,100));
        ArrayList<Integer> y=new ArrayList<Integer>();
        y.add(new Integer(2));
//        y.add(new Integer(3));
        myBuyRecord = new MyBuyRecord(1, "SHop A","dagiao", x,y,25000);
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

