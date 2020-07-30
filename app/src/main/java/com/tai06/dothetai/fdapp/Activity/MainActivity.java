package com.tai06.dothetai.fdapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.tai06.dothetai.fdapp.Fragment.DonhangFragment;
import com.tai06.dothetai.fdapp.Fragment.TrangchuFragment;
import com.tai06.dothetai.fdapp.LoginSignup.ChangepswActivity;
import com.tai06.dothetai.fdapp.LoginSignup.LoginActivity;
import com.tai06.dothetai.fdapp.OOP.CTHD;
import com.tai06.dothetai.fdapp.OOP.HoaDon;
import com.tai06.dothetai.fdapp.OOP.KhachHang;
import com.tai06.dothetai.fdapp.R;
import com.tai06.dothetai.fdapp.URL.Link;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final int MSG_HEADER_VIEW = 1;

    private DrawerLayout mDrawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView navigationView;
    private boolean back_home;
    private Handler handler;
    private KhachHang khachHang;
    private TextView ten_kh,email_kh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initHandler();
        getInfoKH();
    }

    private void init(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.draw_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MSG_HEADER_VIEW:
                        khachHang = (KhachHang) msg.obj;
                        headView(khachHang);
                        setInfo(khachHang);
                        break;
                }
            }
        };
    }

    private void setInfo(KhachHang khachHang){
        Fragment fragment = new TrangchuFragment();
        Bundle bundle = new Bundle();
        bundle.putString("ma_kh", String.valueOf(khachHang.getMa_kh()));
        bundle.putString("email",khachHang.getEmail());
        bundle.putString("ten_kh",khachHang.getTen_kh());
        bundle.putString("sdt",khachHang.getSdt());
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,fragment).commit();
        navigationView.setCheckedItem(R.id.menu_home);
    }

    //Phần setup Navigation

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void displayView(int id){
        switch (id){
            case R.id.menu_home:
                showFragment(new TrangchuFragment());
                back_home = true;
                break;
            case R.id.menu_donhang:
                showFragment(new DonhangFragment());
                back_home = false;
                break;
            case R.id.menu_doimatkhau:
                Intent intent1 = new Intent(MainActivity.this, ChangepswActivity.class);
                intent1.putExtra("ma_kh",String.valueOf(khachHang.getMa_kh()));
                intent1.putExtra("email",khachHang.getEmail());
                intent1.putExtra("password",khachHang.getPassword());
                startActivity(intent1);
                break;
            case R.id.menu_dangxuat:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.manager:
//                Intent intent2 = getIntent();
//                String email = intent2.getStringExtra("email");
//                if (email.equals("a")){
//                    item.setVisible(true);
//                }
                break;
        }
    }

    private void showFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        bundle.putString("ma_kh", String.valueOf(khachHang.getMa_kh()));
        bundle.putString("email",khachHang.getEmail());
        bundle.putString("ten_kh",khachHang.getTen_kh());
        bundle.putString("sdt",khachHang.getSdt());
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container,fragment).commit();
    }

    //phần setup menu search

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search_bar:
                Intent intent = new Intent(MainActivity.this,SearchViewActivity.class);
                intent.putExtra("user",khachHang);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //phần set thông tin headview

    private void headView(KhachHang khachHang){
        View view = navigationView.getHeaderView(0);
        email_kh = view.findViewById(R.id.email_kh);
        ten_kh = view.findViewById(R.id.ten_kh);
        email_kh.setText(khachHang.getEmail());
        ten_kh.setText(khachHang.getTen_kh());
    }

    //Phần get thông tin khách hàng từ email đăng nhập

    private void getInfoKH(){
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Link.URL_getInfoKH, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            KhachHang kh = new KhachHang();
                            JSONObject jsonObject = new JSONObject(response);
                            kh.setMa_kh(jsonObject.getInt("ma_kh"));
                            kh.setEmail(jsonObject.getString("email"));
                            kh.setPassword(jsonObject.getString("password"));
                            kh.setTen_kh(jsonObject.getString("ten_kh"));
                            kh.setSdt(jsonObject.getString("sdt"));
                            kh.setImage(jsonObject.getString("image"));
                            Message msg = new Message();
                            msg.what = MSG_HEADER_VIEW;
                            msg.obj = kh;
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Error" + error.toString());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("email",email);
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        thread.start();
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        if (!back_home){ // nếu view hiện tại không phải là homefragment
            displayView(R.id.menu_home); // sẽ hiển thị view home fragment
        }
        else{
            moveTaskToBack(true); // nếu là view home fragment ,sẽ thoát khỏi app
//            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        getInfoKH();
        super.onResume();
    }
}