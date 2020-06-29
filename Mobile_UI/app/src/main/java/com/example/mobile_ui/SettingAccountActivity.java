package com.example.mobile_ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
import com.example.mobile_ui.Model.chooseImage;
import com.example.mobile_ui.Model.ratingDialog;
import com.example.mobile_ui.Retrofit.GetImgFormUrl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class SettingAccountActivity extends AppCompatActivity {
    Button changeOrSave;
    ConstraintLayout container_user_img;
    ImageView user_infor_img;
    Button touchToChangeImg;
    EditText user_name;
    int countRequest = 0;
//    RadioGroup radioSex;
//    RadioButton radioMale;
//    RadioButton radioFemale;
//    TextView date_of_birth;
    EditText address;
//    EditText phone;
//    EditText pass;
//    LinearLayout container_again_pass;
//    EditText againPass;
//    LinearLayout container_pass;

    final int REQUEST_CODE_CAMERA=123;
    final int REQUEST_CODE_FOLDER=456;
    final int REQUEST_CALL_RW = 234;
    Uri uri;
    boolean changeImg = false;
    ProgressDialog pd;

    private void anhxa(){
        RequestQueue queue = Volley.newRequestQueue(SettingAccountActivity.this);
        pd = new ProgressDialog(SettingAccountActivity.this);
        pd.setTitle("Chờ 1 lát ...");
        changeOrSave = findViewById(R.id.changeOrSave);changeOrSave.setText("change");
        container_user_img = findViewById(R.id.container_user_img);
        user_infor_img = findViewById(R.id.user_infor_img);
        touchToChangeImg = findViewById(R.id.touchToChangeImg);
        user_name = findViewById(R.id.user_name);
//        radioSex = findViewById(R.id.radioSex);
//        radioFemale = findViewById(R.id.radioFemale);
//        radioMale = findViewById(R.id.radioMale);
//        date_of_birth = findViewById(R.id.date_of_birth);
//        date_of_birth.setVisibility(View.GONE);
        address = findViewById(R.id.address);
//        phone = findViewById(R.id.phone);
//        pass = findViewById(R.id.pass);
//        container_pass = findViewById(R.id.container_pass);
//        container_again_pass = findViewById(R.id.container_again_pass);
//        againPass = findViewById(R.id.againPass);
        // chọn ảnh từ máy hay file
        touchToChangeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //giao diện
                if (ContextCompat.checkSelfPermission(SettingAccountActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // request the permission - cấp quyền
                    ActivityCompat.requestPermissions(SettingAccountActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CALL_RW);
                } else {
                    final chooseImage chooseImageProfile = new chooseImage();
                    chooseImageProfile.show(getSupportFragmentManager(), "");
                }
                //set sự kiện
//                takePhoto.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(intent,REQUEST_CODE_CAMERA);
//                        alert.cancel();
//                    }
//                });
//                fromDevice.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent = new Intent(Intent.ACTION_PICK);
//                        intent.setType("image/*");
//                        startActivityForResult(intent,REQUEST_CODE_FOLDER);
//                        alert.cancel();
//                    }
//                });
//                close.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        alert.cancel();
//                    }
//                });
//                alert.show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);
        anhxa();
        getUser();
        offStateElement();
        setEventChangeOrSave();
    }

    void setEventChangeOrSave(){
        changeOrSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(changeOrSave.getText() =="change"){
                    changeOrSave.setText("save");
                    onStateElement();
                    //hàm post thay đổi lên server ở đây
                    return;
                }else{
                    //xác nhận dữ liệu
//                    if(!pass.getText().toString().equals(againPass.getText().toString())) {
//                        Toast.makeText(SettingAccountActivity.this,"Mật khẩu xác nhận không đúng !",Toast.LENGTH_SHORT).show();
//                        return;
//                    }
                    changeOrSave.setText("change");
                    offStateElement();
                    //post dữ liệu
                    postData(); //chưa hoàn thành hàm này
                    return;
                }
            }
        });
    }

    //trạng thái có thể sửa và up thay đổi
    void onStateElement(){
//        setOnClick(user_infor_img,date_of_birth,true);
        touchToChangeImg.setVisibility(View.VISIBLE);
//        user_name.setEnabled(true);
//        for (int i = 0; i < radioSex.getChildCount(); i++) {
//            radioSex.getChildAt(i).setEnabled(true);
//        }
        address.setEnabled(true);
//        phone.setEnabled(true);
//        container_again_pass.setVisibility(View.VISIBLE);
//        container_pass.setVisibility(View.VISIBLE);
    }

    // trạng thái chỉ xem
    void offStateElement(){
//        setOnClick(user_infor_img,date_of_birth,false);
        touchToChangeImg.setVisibility(View.GONE);
//        user_name.setEnabled(false);
//        for (int i = 0; i < radioSex.getChildCount(); i++) {
//            radioSex.getChildAt(i).setEnabled(false);
//        }
        address.setEnabled(false);
//        phone.setEnabled(false);
//        container_again_pass.setVisibility(View.GONE);
//        container_pass.setVisibility(View.GONE);
    }

    //post data
    private void postData(){
//        int selectedId = radioSex.getCheckedRadioButtonId();
//        RadioButton radioButton = (RadioButton) findViewById(selectedId);
        pd.show();
        countRequest ++;
        upDateInfo(address.getText().toString(), null);
        if (changeImg) {
            countRequest++;
            uploadFileServer();
        }
//        Toast.makeText(SettingAccountActivity.this,"lưu thành công !",Toast.LENGTH_SHORT).show();

    }

    public void upDateInfo(String address, String urlImg) {
        JSONObject params = new JSONObject();
        if (urlImg == null) {
            try {
                params.put("address", address);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (address == null) {
            try {
                params.put("avatar_url", urlImg);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final String url = "http://112.137.129.216:5001/api/profile/update_profile";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        countRequest --;
//                        user_name.setText(countRequest+response.toString());
                        if (countRequest == 0) {
                            pd.cancel();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(SettingAccountActivity.this);
                            builder.setTitle("Thông báo");
                            builder.setMessage("Cập nhật thành công.");
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
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SettingAccountActivity.this, "error", Toast.LENGTH_LONG).show();
//                System.out.println("error");
                NetworkResponse response = error.networkResponse;
                if (response != null) {

                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        user_name.setText(res);
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
                SharedPreferences sharedPreferences = SettingAccountActivity.this.getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("LOGIN_TOKEN","");
                headers.put("x-access-token", token);
                return headers;
            }
        };
        Volley.newRequestQueue(SettingAccountActivity.this).add(jsonObjReq);
    }
    //set sự kiện khi click vào thay đổi ảnh + thay đổi ngày sinh
    /*private void setOnClick(ImageView img,TextView txt,boolean bl){
        if(!bl){
            container_user_img.setOnClickListener(null);
            txt.setOnClickListener(null);
            return;
        }else{
            container_user_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //giao diện
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingAccountActivity.this);
                    final AlertDialog alert = builder.create();
//                    final AlertDialog.Builder alert = new AlertDialog.Builder(SettingAccountActivity.this);
                    LinearLayout layout = new LinearLayout(SettingAccountActivity.this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    final TextView fromDevice = new TextView(SettingAccountActivity.this);
                    fromDevice.setText("Chọn từ thiết bị");
                    fromDevice.setPadding(30, 30, 30, 30);
                    fromDevice.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                    final TextView takePhoto = new TextView(SettingAccountActivity.this);
                    takePhoto.setText("Chụp ảnh");
                    takePhoto.setPadding(30, 30, 30, 30);
                    takePhoto.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
                    final Button close = new Button(SettingAccountActivity.this);
                    close.setText("Hủy");
                    layout.addView(fromDevice);
                    layout.addView(takePhoto);
                    layout.addView(close);
                    alert.setView(layout);
                    //set sự kiện
                    takePhoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent,REQUEST_CODE_CAMERA);
                            alert.cancel();
                        }
                    });
                    fromDevice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            startActivityForResult(intent,REQUEST_CODE_FOLDER);
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
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String date = date_of_birth.getText().toString();
                    String[] dateSplit = date.split("/");
                    int year = Integer.parseInt(dateSplit[2]);
                    int month = Integer.parseInt(dateSplit[1])-1;
                    int day = Integer.parseInt(dateSplit[0]);
                    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            date_of_birth.setText(dayOfMonth+"/"+ (month+1)+"/" +year);
                        }
                    };

                    DatePickerDialog dialog = new DatePickerDialog(
                            SettingAccountActivity.this,
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            mDateSetListener,year,month,day
                    );

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            });
        }
    }*/

    //lắng nghe sự kiện đổi ảnh (gọi chụp ảnh Hay chọn từ file)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        System.out.println(requestCode);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode ==RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            user_infor_img.setImageBitmap(bitmap);
            return;
        }else if(requestCode==REQUEST_CODE_FOLDER && resultCode ==RESULT_OK && data!=null){
            uri = data.getData();
            changeImg = true;
//            uploadFileServer();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                user_infor_img.setImageBitmap(bitmap);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void getUser() {
        // call api get status
        JSONObject params = new JSONObject();

        final String url = "http://112.137.129.216:5001/api/users/get_status";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
//                        System.out.println(response.toString());
                        try {
                            JSONObject dataUser = response.getJSONObject("data");
                            String urlAvatar = (String) dataUser.get("avatar_url");
//                                user_infor_img.setImageBitmap(GetImgFormUrl.getBitmapImgFromUrl(urlAvatar));
                            Glide.with(SettingAccountActivity.this)
                                    .load(urlAvatar).override(50, 50).centerCrop()
                                    .into(user_infor_img);
                                user_name.setText((String) dataUser.get("fullname"));
//                                String sex =(String) dataUser.get("gender");
//                                System.out.println("sex : "+ sex);
//                                System.out.println(sex=="0");
//                                if(sex.equals("0")) radioFemale.setChecked(true); else radioMale.setChecked(true);
                                //date_of_birth.setText("11/8/1999");
                                address.setText((String) dataUser.get("address"));
                                //phone.setText("0966947994");
                                //pass.setText("12345");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(SettingAccountActivity.this, "error", Toast.LENGTH_LONG).show();
                System.out.println("error");
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        System.out.println(res);
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
                SharedPreferences sharedPreferences = SettingAccountActivity.this.getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("LOGIN_TOKEN","");
//                System.out.println(token);
                headers.put("x-access-token", token);
                return headers;
            }
        };
        Volley.newRequestQueue(SettingAccountActivity.this).add(jsonObjReq);
    }

    // 4 function de upload file len server
    public interface UploadService {
        @Multipart
        @POST("api/file/store-image")
        Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);
    }
    private void uploadFileServer() {
        if (uri == null) return;
        // Hàm call api sẽ mất 1 thời gian nên mình show 1 dialog nhé.

        File file = new File(getRealPathFromURI(uri));
        // Khởi tạo RequestBody từ file đã được chọn
        RequestBody requestBody = RequestBody.create(
                MediaType.parse(getContentResolver().getType(uri)),
                file);
        // Trong retrofit 2 để upload file ta sử dụng Multipart, khai báo 1 MultipartBody.Part
        // uploaded_file là key mà mình đã định nghĩa trong khi khởi tạo server
        MultipartBody.Part filePart =
                MultipartBody.Part.createFormData("image", file.getName(), requestBody);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://112.137.129.216:5001/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();
        UploadService service = retrofit.create(UploadService.class);
        Call<ResponseBody> call = service.uploadFile(filePart);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

//                user_name.setText(response.toString());
//                    Toast.makeText(SettingAccountActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                if (response == null || response.body() == null) {
                    return;
                }
                try {
                    String responseUrl = response.body().string();
                    JSONObject jsonObject = new JSONObject(responseUrl);
                    String urlImg = (String) jsonObject.get("image-url");
                    upDateInfo(null, urlImg);
//                        Toast.makeText(SettingAccountActivity.this, responseUrl, Toast.LENGTH_LONG).show();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                    Toast.makeText(SettingAccountActivity.this, t.getMessage()+"|abcd", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getRealPathFromURI(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_RW) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                final chooseImage chooseImageProfile = new chooseImage();
                chooseImageProfile.show(getSupportFragmentManager(), "");
            }
        }
    }
}