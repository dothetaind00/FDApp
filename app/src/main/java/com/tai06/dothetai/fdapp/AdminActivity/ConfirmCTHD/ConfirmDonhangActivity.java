package com.tai06.dothetai.fdapp.AdminActivity.ConfirmCTHD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.tai06.dothetai.fdapp.Adapter.ConfirmAdapter;
import com.tai06.dothetai.fdapp.Adapter.ViewFragmentAdapter;
import com.tai06.dothetai.fdapp.AdminActivity.ConfirmCTHD.FragmentConfirm.RunningFragment;
import com.tai06.dothetai.fdapp.AdminActivity.ConfirmCTHD.FragmentConfirm.SuccessFragment;
import com.tai06.dothetai.fdapp.AdminActivity.ConfirmCTHD.FragmentConfirm.WaitingConfirmFragment;
import com.tai06.dothetai.fdapp.Fragment.DonHang.OderStoreFragment;
import com.tai06.dothetai.fdapp.Fragment.DonHang.ShipClientFragment;
import com.tai06.dothetai.fdapp.OOP.CTHD;
import com.tai06.dothetai.fdapp.OOP.KhachHang;
import com.tai06.dothetai.fdapp.R;
import com.tai06.dothetai.fdapp.URL.Link;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmDonhangActivity extends AppCompatActivity {

    private Toolbar toolbar_confirm;
    private TabLayout mTabs;
    private View mIndicator;
    private ViewPager mViewPager;
    private ViewFragmentAdapter viewFragmentAdapter;
    private int indicatorWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_donhang);
        init();
        Click_event();
    }

    private void init() {
        toolbar_confirm = findViewById(R.id.toolbar_confirm);
        setSupportActionBar(toolbar_confirm);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTabs = findViewById(R.id.tab);
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);
    }

    private void setViewFrag(){
        viewFragmentAdapter = new ViewFragmentAdapter(getSupportFragmentManager());

        Fragment frag_waitingconfirm = new WaitingConfirmFragment();
        viewFragmentAdapter.AddFragment(frag_waitingconfirm,"Đang chờ xác nhận");
        Fragment frag_running = new RunningFragment();
        viewFragmentAdapter.AddFragment(frag_running,"Đang vận chuyển");
        Fragment frag_success = new SuccessFragment();
        viewFragmentAdapter.AddFragment(frag_success,"Đã nhận");

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        setViewFrag();
        super.onResume();
    }
}