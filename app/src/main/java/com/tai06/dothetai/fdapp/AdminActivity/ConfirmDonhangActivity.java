package com.tai06.dothetai.fdapp.AdminActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.tai06.dothetai.fdapp.Adapter.ConfirmAdapter;
import com.tai06.dothetai.fdapp.Adapter.DonHangAdapter;
import com.tai06.dothetai.fdapp.Fragment.DonHang.ShipClientFragment;
import com.tai06.dothetai.fdapp.OOP.CTHD;
import com.tai06.dothetai.fdapp.R;
import com.tai06.dothetai.fdapp.URL.Link;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ConfirmDonhangActivity extends AppCompatActivity {
    public static final int MSG_CONFIRM =1;

    private Toolbar toolbar_confirm;
    private TextView empty_donhang;
    private RecyclerView recycle_confirm;
    private Handler handler;
    private List<CTHD> arrCTHD;
    private ConfirmAdapter confirmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_donhang);
        init();
        initHandler();
        getConfirm();
    }

    private void init(){
        toolbar_confirm = findViewById(R.id.toolbar_confirm);
        setSupportActionBar(toolbar_confirm);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recycle_confirm = findViewById(R.id.recycle_confirm);
        empty_donhang = findViewById(R.id.empty_donhang);
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MSG_CONFIRM:
                        arrCTHD = new ArrayList<>();
                        arrCTHD.addAll((Collection<? extends CTHD>)msg.obj);
                        SetConfirmAdapter(arrCTHD);
                        break;
                }
            }
        };
    }

    private void SetConfirmAdapter(List<CTHD> list){
        confirmAdapter = new ConfirmAdapter(list, ConfirmDonhangActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(ConfirmDonhangActivity.this, 1, GridLayoutManager.VERTICAL, false);
        recycle_confirm.setLayoutManager(gridLayoutManager);
        recycle_confirm.setAdapter(confirmAdapter);
        recycle_confirm.setHasFixedSize(true);
        confirmAdapter.notifyDataSetChanged();
    }

    private void getConfirm(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(ConfirmDonhangActivity.this);
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Link.URL_getAllDonhang, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<CTHD> list = new ArrayList<>();
                        for (int i=0;i<response.length();i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                list.add(new CTHD(
                                        jsonObject.getInt("ma_hd"),
                                        jsonObject.getString("ten_kh"),
                                        jsonObject.getString("diachi"),
                                        jsonObject.getString("sdt"),
                                        jsonObject.getString("ngaydat_hd"),
                                        jsonObject.getString("ngaygiao_hd"),
                                        jsonObject.getString("ten_sp"),
                                        jsonObject.getString("image"),
                                        jsonObject.getInt("sl_sp"),
                                        jsonObject.getInt("gia_sp"),
                                        jsonObject.getInt("thanhtien"),
                                        jsonObject.getString("ghichu"),
                                        jsonObject.getString("trangthai"),
                                        jsonObject.getString("vanchuyen")
                                ));
                                Message msg = new Message();
                                msg.what = MSG_CONFIRM;
                                msg.obj = list;
                                handler.sendMessage(msg);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ConfirmDonhangActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonArrayRequest);
            }
        });
        thread.start();
    }

    public void ShowHide(ImageView imageView, TableLayout tableLayout) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageView.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_expand_more).getConstantState())) {
                    tableLayout.setVisibility(View.VISIBLE);
                    imageView.setImageResource(R.drawable.ic_expand_less);
                } else {
                    tableLayout.setVisibility(View.GONE);
                    imageView.setImageResource(R.drawable.ic_expand_more);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}