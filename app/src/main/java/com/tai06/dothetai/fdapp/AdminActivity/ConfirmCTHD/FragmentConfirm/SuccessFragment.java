package com.tai06.dothetai.fdapp.AdminActivity.ConfirmCTHD.FragmentConfirm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.tai06.dothetai.fdapp.Adapter.RunningAdapter;
import com.tai06.dothetai.fdapp.Adapter.SuccessAdapter;
import com.tai06.dothetai.fdapp.OOP.CTHD;
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

public class SuccessFragment extends Fragment {

    public static final int MSG_CONFIRM = 1;

    private View view;
    private RecyclerView recycle_success;
    private Handler handler;
    private List<CTHD> arrCTHD;
    private TextView empty_donhang;
    private SuccessAdapter successAdapter;
    private SwipeRefreshLayout swipe_refesh;

    public SuccessFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_success, container, false);
        init();
        initHandler();
        setSwipe_refesh();
        return view;
    }

    private void init(){
        recycle_success = view.findViewById(R.id.recycle_success);
        empty_donhang = view.findViewById(R.id.empty_donhang);
        swipe_refesh = view.findViewById(R.id.swipe_refresh);
    }

    private void setSwipe_refesh(){
        swipe_refesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe_refesh.setRefreshing(false);
                        check_confirm("Đã nhận");
                    }
                },2000);
            }
        });
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MSG_CONFIRM:
                        arrCTHD = new ArrayList<>();
                        arrCTHD.addAll((Collection<? extends CTHD>) msg.obj);
                        setConfirmAdapter(arrCTHD);
                        break;
                }
            }
        };
    }

    private void setConfirmAdapter(List<CTHD> list){
        successAdapter = new SuccessAdapter(list,SuccessFragment.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
        recycle_success.setLayoutManager(gridLayoutManager);
        recycle_success.setAdapter(successAdapter);
        recycle_success.setHasFixedSize(true);
        successAdapter.notifyDataSetChanged();
    }

    private void check_confirm(String status){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Link.URL_check_confirm, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success")) {
                    empty_donhang.setVisibility(View.VISIBLE);
                    recycle_success.setVisibility(View.GONE);
                } else {
                    empty_donhang.setVisibility(View.GONE);
                    recycle_success.setVisibility(View.VISIBLE);
                    postStatusConfirmDH("Đã nhận");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                Log.d("AAA", "Error" + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("trangthai",status);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void postStatusConfirmDH(String trangthai) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Link.URL_postStatusConfirmDH, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<CTHD> list = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
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
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Error" + error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("trangthai",trangthai);
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
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
    public void onResume() {
        check_confirm("Đã nhận");
        super.onResume();
    }
}