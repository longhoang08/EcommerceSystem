package com.example.mobile_ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.example.mobile_ui.Model.chooseImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;


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

public class AddProductActivity extends AppCompatActivity {
    Button BtnAddImgFromCamera;
    Button BtnAddImgFromFolder;

    ArrayList<bitmapUri> imgProductsList;//nơi lưu dữ liệu ảnh
    RecyclerView imgProductsView;
    ImgProductAdpter imgProductAdpter;
    JSONArray urlImg = new JSONArray();

    Spinner dropdownCategory, spinnerBrandPro;
//    String[] typeOfPro = new String[]{"quần áo", "giày dép", "điện tử"};//nơi lưu dữ liệu về loại hàng
//    String[] typeOfBrand = new String[]{"rượu chè", "cờ bạc", "ma túy"};//nơi lưu dữ liệu về thương hiệu
    ArrayList<String> nameBrand = new ArrayList<>();
    ArrayList<String> codeBrand = new ArrayList<>();
    ArrayList<String> nameCategory = new ArrayList<>();
    ArrayList<String> codeCategory = new ArrayList<>();

    int REQUEST_CODE_CAMERA=123;
    int REQUEST_CODE_FOLDER=456;
    int REQUEST_CALL_RW = 234; //cho phep quyen doc
    int REQUEST_CALL_CM = 465; //cho phep quyen doc
    ImageButton addPro;
    EditText edtDescript, edtName, edtPrice, edtStock;
    int countRequest = 0;
    ArrayAdapter<String> adapterCategory;
    ArrayAdapter<String> adapterBrand;
    String namePicture = "";

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        //anh xa
        pd = new ProgressDialog(AddProductActivity.this);
        pd.setTitle("Chờ 1 lát ...");
        RequestQueue queue = Volley.newRequestQueue(AddProductActivity.this);
        BtnAddImgFromCamera = findViewById(R.id.addImgFromCamera);
        BtnAddImgFromFolder = findViewById(R.id.addImgFromFolder);
        dropdownCategory = findViewById(R.id.typeProduc);
        spinnerBrandPro = findViewById(R.id.spinnerBrandPro);
        addPro = findViewById(R.id.addPro);
        edtDescript = findViewById(R.id.edtDescript);
        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtStock = findViewById(R.id.edtStock);
        // get category
        getCategory();
        getBrand();
        //them san pham
        addPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addProduct();
            }
        });
        //Xử lý chọn ảnh
        imgProductsList = new ArrayList<bitmapUri>();
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
                if (ContextCompat.checkSelfPermission(AddProductActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // request the permission - cấp quyền
                    ActivityCompat.requestPermissions(AddProductActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CALL_CM);
                } else {
                    namePicture = new Random().nextInt(100000)+".PNG";
                    Intent m_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(m_intent, REQUEST_CODE_CAMERA);
                }
            }
        });

        BtnAddImgFromFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddProductActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    // request the permission - cấp quyền
                    ActivityCompat.requestPermissions(AddProductActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CALL_RW);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_CODE_FOLDER);
                }
            }
        });

        //xử lý chọn loại hàng

    }

    private void getBrand() {
        JSONObject params = new JSONObject();
        String url = "http://112.137.129.216:5001/api/product/brands/choosable";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONArray brand = response.getJSONObject("data").getJSONArray("brands");
                            for (int i = 0; i < brand.length(); i++) {
                                nameBrand.add((String) brand.getJSONObject(i).get("name"));
                                codeBrand.add((String) brand.getJSONObject(i).get("code"));
                            }
                            adapterBrand = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item, nameBrand);
                            spinnerBrandPro.setAdapter(adapterBrand);
//                            Toast.makeText(ProductSearchActivity.this, data.toString(), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        System.out.println(res);
                        edtDescript.setText(res);
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
        Volley.newRequestQueue(AddProductActivity.this).add(jsonObjReq);
    }

    private void getCategory() {
        JSONObject params = new JSONObject();
        String url = "http://112.137.129.216:5001/api/product/categories/choosable";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            JSONArray category = response.getJSONObject("data").getJSONArray("categories");final ArrayList<String> nameCategory = new ArrayList<>();
                            for (int i = 0; i < category.length(); i++) {
                                nameCategory.add((String) category.getJSONObject(i).get("name"));
                                codeCategory.add((String) category.getJSONObject(i).get("code"));
                            }
                            adapterCategory = new ArrayAdapter<>(AddProductActivity.this, android.R.layout.simple_spinner_dropdown_item, nameCategory);
                            dropdownCategory.setAdapter(adapterCategory);
//                            Toast.makeText(ProductSearchActivity.this, data.toString(), Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        System.out.println(res);
                        edtDescript.setText(res);
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
        Volley.newRequestQueue(AddProductActivity.this).add(jsonObjReq);
    }

    private void addProduct() {
        pd.show();
        countRequest += imgProductsList.size();
        if (countRequest == 0) {
            upProduct();
            return;
        }
        for (int i = 0; i < imgProductsList.size(); i++) {
            uploadFileServer(imgProductsList.get(i).getUri());
        }

    }

    private void upProduct() {
        JSONObject params = new JSONObject();
        Random random = new Random();
        String sku = (random.nextInt(100000)+1000000)+"";
        try {
            // code san pham
//            edtName.setText(sku);
            params.put("sku", sku);
            params.put("brand_code", codeBrand.get(spinnerBrandPro.getSelectedItemPosition()));
            params.put("category_code", codeCategory.get(dropdownCategory.getSelectedItemPosition()));
            params.put("images_url", urlImg);
            params.put("description", edtDescript.getText().toString());
            params.put("name", edtName.getText().toString());
            if (!edtPrice.getText().toString().equals(""))
                params.put("price", Integer.parseInt(edtPrice.getText().toString()));
            if (!edtStock.getText().toString().equals(""))
            params.put("stock_changed", Integer.parseInt(edtStock.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "http://112.137.129.216:5001/api/seller/product/upsert";
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, url, params,
                new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                        pd.cancel();
//                        edtDescript.setText(response.toString());
                        final AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                        builder.setTitle("Thông báo");
                        builder.setMessage("Thêm sản phẩm thành công.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.cancel();
                NetworkResponse response = error.networkResponse;
                if (response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                        System.out.println(res);
                        final JSONObject responseMsg = new JSONObject(res);
                        final AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
                        builder.setTitle("Thông báo");
                        JSONObject jsonErr = responseMsg.getJSONObject("errors");
                        String msgError = "";
                        Iterator<String> keys = jsonErr.keys();
                        while (keys.hasNext()) {
                            msgError += (String)jsonErr.get(keys.next())+"\n";
                        }
                        builder.setMessage(msgError);
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
//                        Toast.makeText(getActivity(), res, Toast.LENGTH_LONG).show();
                    } catch (UnsupportedEncodingException | JSONException e1) {
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
                SharedPreferences sharedPreferences = AddProductActivity.this.getSharedPreferences("VALUABLE_APP", Context.MODE_PRIVATE);
                String token = sharedPreferences.getString("LOGIN_TOKEN","");
                headers.put("x-access-token", token);
                return headers;
            }
        };
        Volley.newRequestQueue(AddProductActivity.this).add(jsonObjReq);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CAMERA && resultCode ==RESULT_OK && data!=null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            File f = new File(Environment.getExternalStorageDirectory(), namePicture);
            try {
                f.createNewFile();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100 /*ignored for PNG*/, bos);

                byte[] bitmapdata = bos.toByteArray();

                FileOutputStream fos = new FileOutputStream(f);
                fos.write(bitmapdata);
                fos.flush();
                fos.close();

                imgProductsList.add(new bitmapUri(bitmap, Uri.fromFile(f)));
                imgProductAdpter.notifyDataSetChanged();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return;
        }else
        if(requestCode==REQUEST_CODE_FOLDER && resultCode ==RESULT_OK && data!=null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgProductsList.add(new bitmapUri(bitmap, uri));
                imgProductAdpter.notifyDataSetChanged();
                return;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    // 4 function để up image lên server
    public interface UploadService {
        @Multipart
        @POST("api/file/store-image")
        Call<ResponseBody> uploadFile(@Part MultipartBody.Part file);
    }
    private void uploadFileServer(Uri uri) {
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
        SettingAccountActivity.UploadService service = retrofit.create(SettingAccountActivity.UploadService.class);
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
                    String strUrlImg = (String) jsonObject.get("image-url");
                    countRequest --;
                    urlImg.put(strUrlImg);
                    if (countRequest == 0) {
                        upProduct();
                    }
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
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
        } else if(requestCode == REQUEST_CALL_CM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                File pictureDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureName = new Random().nextInt(100000)+".jpg";
                File imgFile = new File(pictureDir, pictureName);
                Uri pictureUri = Uri.fromFile(imgFile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                startActivityForResult(intent, REQUEST_CODE_CAMERA);
            }
        }
    }
}
