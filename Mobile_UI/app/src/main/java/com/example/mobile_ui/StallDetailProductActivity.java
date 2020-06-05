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
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobile_ui.Adapter.ImgProductAdpter;
import com.example.mobile_ui.Retrofit.GetImgFormUrl;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class StallDetailProductActivity extends AppCompatActivity {
    Button changeOrSave;
    ImageButton BtnAddImgFromCamera;
    ImageButton BtnAddImgFromFolder;
    int REQUEST_CODE_CAMERA=123;
    int REQUEST_CODE_FOLDER=456;

    ArrayList<Bitmap> imgProductsList;//nơi lưu dữ liệu ảnh
    RecyclerView imgProductsView;
    ImgProductAdpter imgProductAdpter;

    EditText edtName;
    EditText edtDescription;

    Spinner dropdown;//nơi select option loại hàng
    String[] typeOfPro = new String[]{"quần áo", "giày dép", "điện tử"};//nơi lưu dữ liệu về loại hàng

    EditText price;
    EditText number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall_detail_product);

        //anh xa
        changeOrSave = findViewById(R.id.changeOrSave);changeOrSave.setText("change");
        BtnAddImgFromCamera = findViewById(R.id.addImgFromCamera);
        BtnAddImgFromFolder = findViewById(R.id.addImgFromFolder);
        edtName = findViewById(R.id.edtName);
        edtDescription =findViewById(R.id.edtDescript);
        dropdown = findViewById(R.id.typeProduc);
        price = findViewById(R.id.price);
        number = findViewById(R.id.number);

        //setting base
        imgProductsList = new ArrayList<Bitmap>();
        imgProductsView = findViewById(R.id.imgProductsRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        imgProductsView.setLayoutManager(mLayoutManager);
        imgProductAdpter = new ImgProductAdpter(
                imgProductsList,StallDetailProductActivity.this,false
        );
        imgProductsView.setAdapter(imgProductAdpter);
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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, typeOfPro);
        dropdown.setAdapter(adapter);
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
                    imgProductAdpter.setStateOfEventItem(true);
                    imgProductAdpter.notifyDataSetChanged();
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

    //trạng thái có thể sửa và up thay đổi
    void onStateElement(){
        BtnAddImgFromCamera.setVisibility(View.VISIBLE);
        BtnAddImgFromFolder.setVisibility(View.VISIBLE);

        edtName.setEnabled(true);
        edtDescription.setEnabled(true);
        dropdown.setEnabled(true);
        price.setEnabled(true);
        number.setEnabled(true);

        setEventDelImg();
    }

    // trạng thái chỉ xem
    void offStateElement(){
        BtnAddImgFromCamera.setVisibility(View.GONE);
        BtnAddImgFromFolder.setVisibility(View.GONE);

        edtName.setEnabled(false);
        edtDescription.setEnabled(false);
        dropdown.setEnabled(false);
        price.setEnabled(false);
        number.setEnabled(false);
    }

    //get dữ liệu
    void fillData(){
        //img
        imgProductsList.add(GetImgFormUrl.getBitmapImgFromUrl("https://thethao99.com/wp-content/uploads/2020/04/85094047_202036030982266_380222704411738112_n.jpg"));
        imgProductsList.add(GetImgFormUrl.getBitmapImgFromUrl("https://datastandard.blob.core.windows.net/botimg/5b604ff3e28282087477a30b.jpg"));
        imgProductAdpter.notifyDataSetChanged();
        //name
        edtName.setText("Mi tom");
        //mo ta
        edtDescription.setText("day la san pham rat tot cho suc khoe, ban nen an nhieu vao");
        //loai sp
        selectSpinnerValue(dropdown,"điện tử");
        //gia
        price.setText("20000");
        //kho hang
        number.setText("200");
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
    private void setEventDelImg(){
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos= viewHolder.getAdapterPosition();
                imgProductsList.remove(pos);
                imgProductAdpter.notifyDataSetChanged();
            }
        });
        helper.attachToRecyclerView(imgProductsView);
    }

}
