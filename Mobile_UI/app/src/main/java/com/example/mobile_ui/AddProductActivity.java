package com.example.mobile_ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobile_ui.Adapter.ImgProductAdpter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddProductActivity extends AppCompatActivity {
    Button BtnAddImgFromCamera;
    Button BtnAddImgFromFolder;

    ArrayList<Bitmap> imgProductsList;//nơi lưu dữ liệu ảnh
    RecyclerView imgProductsView;
    ImgProductAdpter imgProductAdpter;

    Spinner dropdown, spinnerBrandPro;
    String[] typeOfPro = new String[]{"quần áo", "giày dép", "điện tử"};//nơi lưu dữ liệu về loại hàng
    String[] typeOfBrand = new String[]{"rượu chè", "cờ bạc", "ma túy"};//nơi lưu dữ liệu về thương hiệu

    int REQUEST_CODE_CAMERA=123;
    int REQUEST_CODE_FOLDER=456;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //anh xa
        BtnAddImgFromCamera = findViewById(R.id.addImgFromCamera);
        BtnAddImgFromFolder = findViewById(R.id.addImgFromFolder);
        dropdown = findViewById(R.id.typeProduc);
        spinnerBrandPro = findViewById(R.id.spinnerBrandPro);
        //Xử lý chọn ảnh
        imgProductsList = new ArrayList<Bitmap>();
        imgProductsView = findViewById(R.id.imgProductsRecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AddProductActivity.this, 1, GridLayoutManager.HORIZONTAL, false);
        imgProductsView.setLayoutManager(gridLayoutManager);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        imgProductsView.setLayoutManager(mLayoutManager);

        imgProductAdpter = new ImgProductAdpter(
                imgProductsList,AddProductActivity.this,true
        );
        imgProductsView.setAdapter(imgProductAdpter);

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

        //xử lý chọn loại hàng
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeOfPro);
        ArrayAdapter<String> adapterBrand = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeOfBrand);
        dropdown.setAdapter(adapter);
        spinnerBrandPro.setAdapter(adapterBrand);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode ==RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgProductsList.add(bitmap);
            imgProductAdpter.notifyDataSetChanged();
            return;
        }else
        if(requestCode==REQUEST_CODE_FOLDER && resultCode ==RESULT_OK && data!=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgProductsList.add(bitmap);
                imgProductAdpter.notifyDataSetChanged();
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
