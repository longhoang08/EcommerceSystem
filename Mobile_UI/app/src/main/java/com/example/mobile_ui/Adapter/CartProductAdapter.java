package com.example.mobile_ui.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mobile_ui.DetailProductActivity;
import com.example.mobile_ui.MainActivity;
import com.example.mobile_ui.Model.OrderProduct;
import com.example.mobile_ui.R;
import com.example.mobile_ui.View.ExpandHeightGridView;

import java.util.List;

public class CartProductAdapter extends BaseAdapter {
    private List<OrderProduct> listOrderProduct;

    public CartProductAdapter(List<OrderProduct> listOrderProduct) {
        this.listOrderProduct = listOrderProduct;
    }

    @Override
    public int getCount() {
        return listOrderProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_item, null);
            // anh xa
            final CheckBox checkBoxProduct = view.findViewById(R.id.checkBoxProduct);
            ImageView imageViewRepresentProduct = view.findViewById(R.id.imageViewRepresentProduct);
            TextView textViewNameProduct = view.findViewById(R.id.textViewNameProduct);
            final TextView textViewPriceProduct = view.findViewById(R.id.textViewPriceProduct);
            final EditText editTextQuantityProduct = view.findViewById(R.id.editTextQuantityProduct);
            final TextView textViewQuantityInStockProduct = view.findViewById(R.id.textViewQuantityInStockProduct);
            ImageView imageViewDeleteCart = view.findViewById(R.id.imageViewDeleteCart);
            // gan gia tri
            Glide.with(parent.getContext())
                    .load(listOrderProduct.get(position).getImageRepresent()).override(80, 80).centerCrop()
                    .into(imageViewRepresentProduct);
            String namePro = listOrderProduct.get(position).getNameProduct();
            if (namePro.length() > 30) {
                namePro = namePro.substring(0, 30)+"...";
            }
            imageViewRepresentProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(parent.getContext(), DetailProductActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("idPro", listOrderProduct.get(position).getId());
                    intent.putExtras(bundle);
                    parent.getContext().startActivity(intent);
                }
            });
            textViewNameProduct.setText(namePro);
            textViewPriceProduct.setText(listOrderProduct.get(position).getPrice()+" đ");
            editTextQuantityProduct.setText(listOrderProduct.get(position).getQuantity()+"");
            textViewQuantityInStockProduct.setText("Còn "+listOrderProduct.get(position).getQuantityInStock()+" sản phẩm");
            // set sự kiện
            // xóa sản phẩm khỏi giỏ hàng
            imageViewDeleteCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // open dialog xác nhận xóa
                    final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Xác nhận");
                    builder.setMessage("Bạn có muốn gỡ sản phẩm khỏi giỏ hàng");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // xoa san pham khoi luu
                            SharedPreferences sharedPreferences = parent.getContext().getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            MainActivity.cartProduct.removeCart(listOrderProduct.get(position).getId());
                            editor.putString("CART_PRODUCT", MainActivity.cartProduct.convertToString());
                            editor.commit();
                            listOrderProduct.remove(position);
                            ExpandHeightGridView expandHeightGridView = (ExpandHeightGridView) parent;
                            expandHeightGridView.setAdapter(CartProductAdapter.this);

                        }
                    });
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
            editTextQuantityProduct.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    String textQuantity = editTextQuantityProduct.getText().toString();
                    int quantity = 0;
                    if (textQuantity.isEmpty()) {
                        editTextQuantityProduct.setText("0");
                    } else {
                        quantity = Integer.parseInt(textQuantity);
                        if (quantity < 0) {
                            editTextQuantityProduct.setText("0");
                        } else {
                            int quantityInStock = Integer.parseInt(textViewQuantityInStockProduct.getText().toString().split(" ")[1]);
                            if (quantity > quantityInStock) {
                                editTextQuantityProduct.setText(quantityInStock+"");
                            }
                        }
                    }
                }
            });
            checkBoxProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // parent là ExpandHeightGridView, getParent lần lượt: ->LinearLayout->ScrollView->ConstraintLayout
                    TextView textViewTotalPrice = ((ViewGroup) parent.getParent().getParent().getParent()).findViewById(R.id.textViewTotalPrice);
                    // tổng giá
                    int totalPrice = Integer.parseInt(textViewTotalPrice.getText().toString().split(" ")[0]);
                    String textQuantity = editTextQuantityProduct.getText().toString();
                    int priceProduct = Integer.parseInt(textViewPriceProduct.getText().toString().split(" ")[0]);
                    listOrderProduct.get(position).setState(checkBoxProduct.isChecked());
                    if (checkBoxProduct.isChecked()) { // kiểm tra số lượng hàng hợp lý khi tính tiền
                        // chuẩn hóa số lượng nhập vào >= 0, <= hàng tồn kho
                        int quantity = 0;
                        if (textQuantity.isEmpty()) {
                            editTextQuantityProduct.setText("0");
                        } else {
                            quantity = Integer.parseInt(textQuantity);
                            if (quantity < 0) {
                                quantity = 0;
                                editTextQuantityProduct.setText("0");
                            } else {
                                int quantityInStock = Integer.parseInt(textViewQuantityInStockProduct.getText().toString().split(" ")[1]);
                                if (quantity > quantityInStock) {
                                    quantity = quantityInStock;
                                    editTextQuantityProduct.setText(quantityInStock+"");
                                }
                            }
                        }
                        // end chuẩn hóa số lượng hàng
                        listOrderProduct.get(position).setQuantity(quantity);
                        // vô hiệu nhập số lượng
                        editTextQuantityProduct.setEnabled(false);
                        totalPrice += priceProduct*quantity;
                        // sản phẩm này thuộc 1 shop, shop này có bao nhiêu sản phẩm đang chọn trong giỏ của khách
                    } else {
                        int quantity = Integer.parseInt(textQuantity);
                        totalPrice -= priceProduct*quantity;
                        // cho phép nhập số lượng
                        editTextQuantityProduct.setEnabled(true);
                    }
                    textViewTotalPrice.setText(totalPrice+" đ");
                }
            });
        } else {
            view = convertView;
        }
        return view;
    }
}
