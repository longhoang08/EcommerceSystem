package com.example.mobile_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_ui.Adapter.CartProductShopAdapter;
import com.example.mobile_ui.Model.CartShop;
import com.example.mobile_ui.Model.OrderProduct;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class CartProductActivity extends AppCompatActivity {

    ListView listViewProductCart;
    CheckBox checkBoxAllProductCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_product);

        listViewProductCart = findViewById(R.id.listViewProductCart);
        final List<CartShop> listCartShop = new ArrayList<>();
        loadDataCartShop(listCartShop);
        CartProductShopAdapter cartProductShopAdapter = new CartProductShopAdapter(listCartShop);
        listViewProductCart.setAdapter(cartProductShopAdapter);
        // set lai chiều cao cho listView để nhìn toàn bộ trên màn hình
        setHeightListViewCartProduct(listCartShop);
        // khách hàng chọn tất cả sản phẩm để mua
        checkBoxAllProductCart = findViewById(R.id.checkBoxAllProductCart);
        checkBoxAllProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < listCartShop.size(); i++) {
                    CheckBox checkBoxShopProduct = getViewByPosition(listViewProductCart, i).findViewById(R.id.checkBoxShopProduct);
                    if (checkBoxShopProduct.isChecked() != checkBoxAllProductCart.isChecked()) {
                        checkBoxShopProduct.performClick();
                    }
                }
            }
        });
    }


    private View getViewByPosition(ListView listView, int position) {
        int firstItemPosition = listView.getFirstVisiblePosition();
        int lastItemPosition = firstItemPosition+listView.getChildCount()-1;
        if (position < firstItemPosition || position > lastItemPosition) {
            return listView.getAdapter().getView(position, null, listView);
        } else {
            int childIndex = position-firstItemPosition;
            return listView.getChildAt(childIndex);
        }
    }
    private void setHeightListViewCartProduct(List<CartShop> listCartShop) {
        // 60 - kich thuoc layout item shop, 97 - kich thuoc layout 1 san pham trong gio hang
        int heightParent = 0;
        for (int i = 0; i < listCartShop.size(); i ++) {
            int heightChildren = 97*listCartShop.get(i).getListOrderProduct().size();
            heightParent += 60+heightChildren;
        }
        // chuyen dp->px
        float scale = getResources().getDisplayMetrics().density;
        heightParent = (int) (heightParent*scale+0.5f);
        // thêm chiều dài - như kiểu marginBottom 8dp
        heightParent += (int) (8*scale+0.5f);
        listViewProductCart.setLayoutParams(new LinearLayout.LayoutParams(-1, heightParent));
    }


    private void loadDataCartShop(List<CartShop> listCartShop) {
        for (int i = 0; i < 2; i++) {
            List<OrderProduct> listOrderProduct = new ArrayList<>();
            if (i == 0) {
                listOrderProduct.add(new OrderProduct(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 40000, 2, 12));
                listOrderProduct.add(new OrderProduct(R.drawable.icon_pineapple, "Redmi Note 7", 40000, 2, 12));
            } else {
                listOrderProduct.add(new OrderProduct(R.drawable.icon_dragon_fruit, "Redmi Note 7", 40000, 1, 10));
            }
            listCartShop.add(new CartShop("Hoàng Hà Mobile", listOrderProduct));
        }
    }
}
