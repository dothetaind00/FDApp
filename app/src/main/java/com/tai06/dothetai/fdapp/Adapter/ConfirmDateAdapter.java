package com.tai06.dothetai.fdapp.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tai06.dothetai.fdapp.AdminActivity.ConfirmDonhangActivity;
import com.tai06.dothetai.fdapp.OOP.CTHD;
import com.tai06.dothetai.fdapp.OOP.Shipper;
import com.tai06.dothetai.fdapp.R;
import com.tai06.dothetai.fdapp.URL.Link;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfirmDateAdapter extends RecyclerView.Adapter<ConfirmDateAdapter.ViewHolder> {

    public static final int MSG = 1;
    private Handler handler;
    private ConfirmAdapter confirmAdapter;
    private List<CTHD> arrCTHD;

    private List<CTHD> mList;
    private Context mContext;

    public ConfirmDateAdapter(List<CTHD> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ConfirmDateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_date, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmDateAdapter.ViewHolder holder, int position) {
        CTHD cthd = mList.get(position);
        holder.confirm_date.setText(cthd.getNgaydat_hd());
        initHandler(holder.recycle_dsdh);
        getConfirm(cthd.getNgaydat_hd());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView confirm_date;
        RecyclerView recycle_dsdh;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            confirm_date = itemView.findViewById(R.id.confirm_date);
            recycle_dsdh = itemView.findViewById(R.id.recycle_dsdh);
        }

    }

    private void initHandler(RecyclerView recyclerView) {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case MSG:
                        arrCTHD = new ArrayList<>();
                        arrCTHD.addAll((Collection<? extends CTHD>) msg.obj);
                        SetConfirmAdapter(arrCTHD,recyclerView);
                        break;
                }
            }
        };
    }


    private void SetConfirmAdapter(List<CTHD> list,RecyclerView recyclerView) {
        confirmAdapter = new ConfirmAdapter(list, mContext);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(confirmAdapter);
        recyclerView.setHasFixedSize(true);
        confirmAdapter.notifyDataSetChanged();
    }

    public void getConfirm(String ngaydat) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestQueue requestQueue = Volley.newRequestQueue(mContext);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Link.URL_postdatedonhang, new Response.Listener<String>() {
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
                            }
                            Message msg = new Message();
                            msg.what = MSG;
                            msg.obj = list;
                            handler.sendMessage(msg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Error" + error.toString());
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> param = new HashMap<>();
                        param.put("ngaydat_hd", ngaydat);
                        return param;
                    }
                };
                requestQueue.add(stringRequest);
            }
        });
        thread.start();
    }
}