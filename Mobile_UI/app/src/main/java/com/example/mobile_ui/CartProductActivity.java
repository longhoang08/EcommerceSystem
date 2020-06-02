package com.example.mobile_ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_ui.Adapter.CartProductAdapter;
import com.example.mobile_ui.Model.OrderProduct;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class CartProductActivity extends AppCompatActivity {

    ExpandHeightGridView expandHeightGridViewProductCart;
    CheckBox checkBoxAllProductCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_product);

        expandHeightGridViewProductCart = findViewById(R.id.expandHeightGridViewProductCart);
        final List<OrderProduct> listCartProduct = new ArrayList<>();
        loadDataCartShop(listCartProduct);
        CartProductAdapter cartProductAdapter = new CartProductAdapter(listCartProduct);
        expandHeightGridViewProductCart.setAdapter(cartProductAdapter);
        // khách hàng chọn tất cả sản phẩm để mua
        checkBoxAllProductCart = findViewById(R.id.checkBoxAllProductCart);
        checkBoxAllProductCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < listCartProduct.size(); i++) {
                    CheckBox checkBoxShopProduct = getViewByPosition(expandHeightGridViewProductCart, i).findViewById(R.id.checkBoxProduct);
                    if (checkBoxShopProduct.isChecked() != checkBoxAllProductCart.isChecked()) {
                        checkBoxShopProduct.performClick();
                    }
                }
            }
        });
    }


    private View getViewByPosition(ExpandHeightGridView expandHeightGridView, int position) {
        int firstItemPosition = expandHeightGridView.getFirstVisiblePosition();
        int lastItemPosition = firstItemPosition+expandHeightGridView.getChildCount()-1;
        if (position < firstItemPosition || position > lastItemPosition) {
            return expandHeightGridView.getAdapter().getView(position, null, expandHeightGridView);
        } else {
            int childIndex = position-firstItemPosition;
            return expandHeightGridView.getChildAt(childIndex);
        }
    }


    private void loadDataCartShop(List<OrderProduct> listCartProduct) {
        listCartProduct.add(new OrderProduct(R.drawable.icon_kiwi_fruit, "Redmi Note 7", 40000, 2, 12));
        listCartProduct.add(new OrderProduct(R.drawable.icon_pineapple, "Redmi Note 7", 40000, 2, 12));
        listCartProduct.add(new OrderProduct(R.drawable.icon_dragon_fruit, "Redmi Note 7", 40000, 2, 18));
        listCartProduct.add(new OrderProduct(R.drawable.icon_pineapple, "Redmi Note 7", 40000, 1, 20));
        listCartProduct.add(new OrderProduct(R.drawable.ic_home_black_24dp, "Redmi Note 7", 40000, 2, 12));
        listCartProduct.add(new OrderProduct(R.drawable.icon_pineapple, "Redmi Note 7", 40000, 2, 12));
        listCartProduct.add(new OrderProduct(R.drawable.icon_dragon_fruit, "Redmi Note 7", 40000, 2, 18));
        listCartProduct.add(new OrderProduct(R.drawable.gradient_background, "Redmi Note 7", 40000, 1, 20));
    }
}
