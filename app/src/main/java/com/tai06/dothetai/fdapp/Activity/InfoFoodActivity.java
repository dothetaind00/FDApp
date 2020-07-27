package com.tai06.dothetai.fdapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.tai06.dothetai.fdapp.OOP.Sanpham;
import com.tai06.dothetai.fdapp.R;
import com.tai06.dothetai.fdapp.URL.Link;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InfoFoodActivity extends AppCompatActivity {
    public static final int MSG_INFO_FOOD = 1;

    private Toolbar toolbar;
    private ImageView image_product,ic_remove,ic_add;
    private int sL_sp = 1, sum_money = 0;
    private int tien1 = 0,tien2 = 0,tien3 =0,tong = 0;
    private TextView name_product,detail_product,soluong;
    private Button tongtien;
    private Sanpham sanpham;
    private String text1 ="",text2="",text3="";
    public  String ghichu = "";
    private Handler handler;
    private CheckBox checkBox1,checkBox2,checkBox3;
    private String ma_kh,email,ten_kh,sdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_food);
        init();
        initHandler();
        getFood();
        Click_checkbox(sanpham.getGia_sp());
        Click_event();
    }

    private void init(){
        toolbar = findViewById(R.id.toolbar_info);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image_product = findViewById(R.id.image_product);
        name_product = findViewById(R.id.name_product);
        detail_product = findViewById(R.id.detail_product);
        soluong = findViewById(R.id.soluong);
        tongtien = findViewById(R.id.tongtien);
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        ic_add = findViewById(R.id.ic_add);
        ic_remove = findViewById(R.id.ic_remove);
        sanpham = (Sanpham) getIntent().getSerializableExtra("food");
    }

    private void Click_event(){
        tongtien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), HoaDonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ma_kh",ma_kh);
                bundle.putString("email",email);
                bundle.putString("ten_kh",ten_kh);
                bundle.putString("sdt",sdt);
                intent.putExtra("user",bundle);
                intent.putExtra("ghichu",ghichu);
                intent.putExtra("soluong",soluong.getText().toString().trim());
                intent.putExtra("tongtien",tongtien.getText().toString().trim());
                intent.putExtra("info",sanpham);
                startActivity(intent);
            }
        });
    }

    private void Click_checkbox(int gia_mh) {
        soluong.setText(String.valueOf(sL_sp));
        ic_remove.setEnabled(false);
        ic_remove.setImageResource(R.drawable.ic_remove_silver);
        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int sl = Integer.parseInt(soluong.getText().toString().trim());
                if (isChecked){ //mặc định ischecked = true
                    tien1 = 23000;
                    tong = tong + 23000;
                    sum_money = (sl * gia_mh) + tong;
                    tongtien.setText(String.valueOf(sum_money) + "VNĐ");
                    text1 = text1 + "Miếng bò whopper nhỏ +23000VNĐ";
                    ghichu = text1+"\n"+text2+"\n"+text3;
                }else{
                    tien1 = 0;
                    tong = tong - 23000;
                    sum_money = (sl * gia_mh) + tong;
                    tongtien.setText(String.valueOf(sum_money) + "VNĐ");
                    text1 = "";
                    ghichu = text1+text2+"\n"+text3;
                }
            }
        });
        checkBox2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int sl = Integer.parseInt(soluong.getText().toString().trim());
                if (isChecked){ //mặc định ischecked = true
                    tien2 = 15000;
                    tong = tong + 15000;
                    sum_money = (sl * gia_mh) + tong;
                    tongtien.setText(String.valueOf(sum_money) + "VNĐ");
                    text2 = text2 + "Thịt xông khói +15000VNĐ";
                    ghichu = text1+"\n"+text2+"\n"+text3;
                }else{
                    tien2 = 0;
                    tong = tong - 15000;
                    sum_money = (sl * gia_mh) + tong;
                    tongtien.setText(String.valueOf(sum_money) + "VNĐ");
                    text2 ="";
                    ghichu = text1+"\n"+text2+text3;
                }
            }
        });
        checkBox3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int sl = Integer.parseInt(soluong.getText().toString().trim());
                if (isChecked){ //mặc định ischecked = true
                    tien3 = 12000;
                    tong = tong + 12000;
                    sum_money = (sl * gia_mh) + tong;
                    tongtien.setText(String.valueOf(sum_money) + "VNĐ");
                    text3 = text3 + "Phô mai mỹ +12000VNĐ";
                    ghichu = text1+"\n"+text2+"\n"+text3;
                }else{
                    tien3 = 0;
                    tong = tong - 12000;
                    sum_money = (sl * gia_mh) + tong;
                    tongtien.setText(String.valueOf(sum_money) + "VNĐ");
                    text3 = "";
                    ghichu = text1+"\n"+text2+text3;
                }
            }
        });

        ic_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sL_sp == 1) {
                    ic_remove.setEnabled(false);
                    ic_remove.setImageResource(R.drawable.ic_remove_silver);
                } else {
                    sL_sp = sL_sp - 1;
                    soluong.setText(String.valueOf(sL_sp));
                    sum_money = (sL_sp * gia_mh) + tong;
                    tongtien.setText(String.valueOf(sum_money) + "VNĐ");
                }
            }
        });
        ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sL_sp = sL_sp + 1;
                sum_money = (sL_sp * gia_mh) + tong;
                ic_remove.setEnabled(true);
                ic_remove.setImageResource(R.drawable.ic_remove);
                soluong.setText(String.valueOf(sL_sp));
//                sum_money = (slmh * gia_mh) + tong;
                tongtien.setText(String.valueOf(sum_money) + "VNĐ");
            }
        });
    }

    private void initHandler() {
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MSG_INFO_FOOD:
                        sanpham = (Sanpham) msg.obj;
                        setAdapter(sanpham);
                        break;
                }
            }
        };
    }

    private void setAdapter(Sanpham sanpham){
        Picasso.get().load(sanpham.getImage()).into(image_product);
        name_product.setText(sanpham.getTen_sp());
        detail_product.setText(sanpham.getMota_sp());
        tongtien.setText(String.valueOf(sanpham.getGia_sp())+"VNĐ");
    }

    private void getFood(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(InfoFoodActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Link.URL_Combo, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Sanpham sanpham = new Sanpham();
                            JSONObject jsonObject = new JSONObject(response);
                            sanpham.setMa_sp(jsonObject.getInt("ma_sp"));
                            sanpham.setMa_lh(jsonObject.getInt("ma_lh"));
                            sanpham.setTen_sp(jsonObject.getString("ten_sp"));
                            sanpham.setGia_sp(jsonObject.getInt("gia_sp"));
                            sanpham.setImage(jsonObject.getString("image"));
                            sanpham.setMota_sp(jsonObject.getString("mota_sp"));
                            Message msg = new Message();
                            msg.what = MSG_INFO_FOOD;
                            msg.obj = sanpham;
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(InfoFoodActivity.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi!\n " + error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("ma_sp",String.valueOf(sanpham.getMa_sp()));
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        thread.start();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}