package com.tai06.dothetai.fdapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;
import com.tai06.dothetai.fdapp.Adapter.ViewFragmentAdapter;
import com.tai06.dothetai.fdapp.Fragment.OderFragment;
import com.tai06.dothetai.fdapp.Fragment.PayHoadonFragment;
import com.tai06.dothetai.fdapp.OOP.KhachHang;
import com.tai06.dothetai.fdapp.OOP.Phuong;
import com.tai06.dothetai.fdapp.OOP.Quan;
import com.tai06.dothetai.fdapp.OOP.Sanpham;
import com.tai06.dothetai.fdapp.R;
import com.tai06.dothetai.fdapp.URL.Check;
import com.tai06.dothetai.fdapp.URL.Link;
import com.tai06.dothetai.fdapp.URL.RandomCode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

public class HoaDonActivity extends AppCompatActivity {
    public static final int MSG_SanPham = 1;
    public static final int MSG_KhachHang = 2;

    private Toolbar toolbar_hoadon;
    private Handler handler;
    private Sanpham sanpham;
    private KhachHang khachHang;
    private TabLayout mTabs;
    private View mIndicator;
    private ViewPager mViewPager;
    private ViewFragmentAdapter viewFragmentAdapter;
    private int indicatorWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);
        init();
        initHandler();
        getSanPham();
        getKhachHang();
        setViewFrag();
        Click_event();
    }

    private void init() {
        //toolbar
        toolbar_hoadon = findViewById(R.id.toolbar_hoadon);
        setSupportActionBar(toolbar_hoadon);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTabs = findViewById(R.id.tab);
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);
    }

    private void setViewFrag(){
        Intent intent = getIntent();
        String note = intent.getStringExtra("ghichu");
        String sl = intent.getStringExtra("soluong");
        String tong = intent.getStringExtra("tongtien");

        viewFragmentAdapter = new ViewFragmentAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putSerializable("sanpham",sanpham);
        bundle.putSerializable("khachhang",khachHang);

        bundle.putString("ghichu",note);
        bundle.putString("soluong",sl);
        bundle.putString("tongtien",tong);

        Fragment frag_pay = new PayHoadonFragment();
        frag_pay.setArguments(bundle);
        viewFragmentAdapter.AddFragment(frag_pay,"Giao hàng tận nơi");
        Fragment frag_oder = new OderFragment();
        frag_oder.setArguments(bundle);
        viewFragmentAdapter.AddFragment(frag_oder,"Nhận tại cửa hàng");

        mViewPager.setAdapter(viewFragmentAdapter);
        mTabs.setupWithViewPager(mViewPager);
    }

    private void Click_event() {
        mTabs.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidth = mTabs.getWidth()/mTabs.getTabCount();
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                mIndicator.setLayoutParams(indicatorParams);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (positionOffset+position) * indicatorWidth ;
                params.leftMargin = (int) translationOffset;
                mIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initHandler() {
        handler = new Handler() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MSG_SanPham:
                        sanpham = new Sanpham();
                        sanpham = (Sanpham) msg.obj;
                        break;
                    case MSG_KhachHang:
                        khachHang = new KhachHang();
                        khachHang = (KhachHang) msg.obj;
                        break;
                }
            }
        };
    }

    private void getSanPham(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sanpham = (Sanpham) getIntent().getSerializableExtra("sanpham");
                Message msg = new Message();
                msg.what = MSG_SanPham;
                msg.obj = sanpham;
                handler.sendMessage(msg);
            }
        });
        thread.start();
    }

    private void getKhachHang(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                khachHang = (KhachHang) getIntent().getSerializableExtra("khachhang");
                Message msg = new Message();
                msg.what = MSG_KhachHang;
                msg.obj = khachHang;
                handler.sendMessage(msg);
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