package com.example.mobile_ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mobile_ui.Adapter.ImgProductAdpter;
import com.example.mobile_ui.Model.bitmapUri;
import com.example.mobile_ui.Retrofit.GetImgFormUrl;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StallDetailProductActivity extends AppCompatActivity {
    Button changeOrSave;
    Button BtnAddImgFromCamera;
    Button BtnAddImgFromFolder;
    int REQUEST_CODE_CAMERA=123;
    int REQUEST_CODE_FOLDER=456;

    ArrayList<bitmapUri> imgProductsList;//nơi lưu dữ liệu ảnh
    ArrayList<String> nameBrand = new ArrayList<>();
    ArrayList<String> codeBrand = new ArrayList<>();
    ArrayList<String> nameCategory = new ArrayList<>();
    ArrayList<String> codeCategory = new ArrayList<>();

    RecyclerView imgProductsView;
    ImgProductAdpter imgProductAdpter;
    Spinner dropdownCategory, spinnerBrandPro;

    EditText edtName;
    EditText edtDescription;
    ArrayAdapter<String> adapterCategory;
    ArrayAdapter<String> adapterBrand;
//    Spinner dropdown;//nơi select option loại hàng
//    String[] typeOfPro = new String[]{"quần áo", "giày dép", "điện tử"};//nơi lưu dữ liệu về loại hàng

    EditText price;
    EditText number;
    String idPro;
    int countRequest = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall_detail_product);

        //anh xa
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idPro = bundle.getString("idPro");
        RequestQueue queue = Volley.newRequestQueue(StallDetailProductActivity.this);

        changeOrSave = findViewById(R.id.changeOrSave);changeOrSave.setText("change");
        BtnAddImgFromCamera = findViewById(R.id.addImgFromCamera);
        BtnAddImgFromFolder = findViewById(R.id.addImgFromFolder);
        edtName = findViewById(R.id.edtName);
        edtDescription =findViewById(R.id.edtDescript);
        dropdownCategory = findViewById(R.id.typeProduc);
        spinnerBrandPro = findViewById(R.id.spinnerBrandPro);
        price = findViewById(R.id.price);
        number = findViewById(R.id.number);

        //setting base
//        imgProductsList = new ArrayList<bitmapUri>();
//        imgProductsView = findViewById(R.id.imgProductsRecyclerView);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        imgProductsView.setLayoutManager(mLayoutManager);
//        imgProductAdpter = new ImgProductAdpter(
//                imgProductsList,StallDetailProductActivity.this,false
//        );
//        imgProductsView.setAdapter(imgProductAdpter);
        //
        BtnAddImgFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,REQUEST_CODE_CAMERA);
            }
        });
        BtnAddImgFromFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_FOLDER);
            }
        });
        //
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeOfPro);
//        dropdown.setAdapter(adapter);
        //==========================================================================================
        fillData();
        //đặt trạng thái người dùng chỉ có thể xem
        offStateElement();

        //ấn vào nút sửa
        //nút sửa đổi text thành lưu
        //set sự kiện cho cho các ảnh, bấm vào có alert "bạn chắc xóa ảnh chứ ?"33333
        //các ô giá trị có thể sửa

        //ấn lưu
        //nút lưu chuyển thành sửa
        //chỉ xem được các ảnh, bỏ sự kiện chọn xóa và 2 nút thêm
        //các ô giá trị không thể sửa
        changeOrSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeOrSave.getText() =="change"){
                    changeOrSave.setText("save");
                    onStateElement();
//                    imgProductAdpter.setStateOfEventItem(true);
//                    imgProductAdpter.notifyDataSetChanged();
                    //hàm post thay đổi lên server ở đây
                    return;
                }else{
                    changeOrSave.setText("change");
                    offStateElement();
                    imgProductAdpter.setStateOfEventItem(false);
                    imgProductAdpter.notifyDataSetChanged();
                    return;
                }
            }
        });

    }

    //lắng nghe sự kiện thêm ảnh (gọi chụp ảnh Hay chọn từ file)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode ==RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgProductsList.add(new bitmapUri(bitmap, null));
            imgProductAdpter.notifyDataSetChanged();
            return;
        }else
        if(requestCode==REQUEST_CODE_FOLDER && resultCode ==RESULT_OK && data!=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgProductsList.add(new bitmapUri(bitmap, null));
                imgProductAdpter.notifyDataSetChanged();
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //trạng thái có thể sửa và up thay đổi
    void onStateElement(){
        BtnAddImgFromCamera.setVisibility(View.VISIBLE);
        BtnAddImgFromFolder.setVisibility(View.VISIBLE);

        edtName.setEnabled(true);
        edtDescription.setEnabled(true);
        dropdownCategory.setEnabled(true);
        spinnerBrandPro.setEnabled(true);
        price.setEnabled(true);
        number.setEnabled(true);

        //setEventDelImg();
    }

    // trạng thái chỉ xem
    void offStateElement(){
        BtnAddImgFromCamera.setVisibility(View.GONE);
        BtnAddImgFromFolder.setVisibility(View.GONE);

        edtName.setEnabled(false);
        edtDescription.setEnabled(false);
        dropdownCategory.setEnabled(false);
        spinnerBrandPro.setEnabled(false);
        price.setEnabled(false);
        number.setEnabled(false);
    }

    //get dữ liệu
    void fillData(){
        getProduct();
        //img
//        imgProductsList.add(new bitmapUri(GetImgFormUrl.getBitmapImgFromUrl("https://thethao99.com/wp-content/uploads/2020/04/85094047_202036030982266_380222704411738112_n.jpg"), null));
//        imgProductsList.add(new bitmapUri(GetImgFormUrl.getBitmapImgFromUrl("https://datastandard.blob.core.windows.net/botimg/5b604ff3e28282087477a30b.jpg"), null));
//        imgProductAdpter.notifyDataSetChanged();
//        //name
//        edtName.setText("Mi tom");
//        //mo ta
//        edtDescription.setText("day la san pham rat tot cho suc khoe, ban nen an nhieu vao");
//        //loai sp
//        selectSpinnerValue(dropdown,"điện tử");
//        //gia
//        price.setText("20000");
//        //kho hang
//        number.setText("200");
    }

    //truyền giá trị cho spinner
    private void selectSpinnerValue(Spinner spinner, String myString)
    {
        for(int i = 0; i < spinner.getCount(); i++){
            if(spinner.getItemAtPosition(i).toString().equals(myString)){
                spinner.setSelection(i);
                break;
            }
        }
    }

    //xóa ảnh
//    private void setEventDelImg(){
//        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
//
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                int pos= viewHolder.getAdapterPosition();
//                imgProductsList.remove(pos);
//                imgProductAdpter.notifyDataSetChanged();
//            }
//        });
//        helper.attachToRecyclerView(imgProductsView);
//    }


    private void getProduct() {
        RequestQueue queue = Volley.newRequestQueue(StallDetailProductActivity.this);
        JSONObject params = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(idPro);
        try {
            params.put("skus", jsonArray);
        } catch (JSONException e) {
//            System.out.println("OK");
        }
        final String url = "http://112.137.129.216:5001/api/product/search";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(DetailProductActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONArray data = response.getJSONObject("data").getJSONArray("products");
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject product = data.getJSONObject(i);
                                JSONArray urlImages = product.getJSONArray("images");
                                imgProductsList = new ArrayList<bitmapUri>();
                                for (int j = 0; j < urlImages.length(); j++) {
                                    String urlImg = ((String) urlImages.getJSONObject(j).get("url"));
                                    imgProductsList.add(new bitmapUri(urlImg));
                                }
                                imgProductsView = findViewById(R.id.imgProductsRecyclerView);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                imgProductsView.setLayoutManager(mLayoutManager);
                                imgProductAdpter = new ImgProductAdpter(
                                        imgProductsList,StallDetailProductActivity.this,false
                                );
                                imgProductsView.setAdapter(imgProductAdpter);

                                int Price = 0;
                                Object obj = product.getJSONObject("prices").get("price");
                                if (obj instanceof java.lang.Integer) {
                                    Price = (Integer) obj;
                                } else {
                                    Price = ((Double) obj).intValue();
                                }
                                price.setText(Price + "");
                                String name = (String) product.get("name");
                                edtName.setText(name);
                                int stock = (int) product.getJSONObject("stock").get("in_stock");

                                //System.out.println(cate+" "+br);
//                                System.out.println(cate+"|"+br);

                                number.setText(""+stock);
                                String description = (String) product.get("description");
                                edtDescription.setText(description);

                                String cate = (String) product.getJSONArray("categories").getJSONObject(0).get("name");
                                String br = (String) product.getJSONObject("brand").get("name");
                                nameCategory.add(cate);
                                adapterCategory = new ArrayAdapter<>(StallDetailProductActivity.this, android.R.layout.simple_spinner_dropdown_item, nameCategory);
                                dropdownCategory.setAdapter(adapterCategory);
                                nameBrand.add(br);
                                adapterBrand = new ArrayAdapter<>(StallDetailProductActivity.this, android.R.layout.simple_spinner_dropdown_item, nameBrand);
                                spinnerBrandPro.setAdapter(adapterBrand);
                                // set event
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        System.out.println(res);
//                        Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        Volley.newRequestQueue(StallDetailProductActivity.this).add(jsonObjReq);
    }

}
