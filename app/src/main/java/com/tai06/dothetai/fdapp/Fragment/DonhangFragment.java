package com.tai06.dothetai.fdapp.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tai06.dothetai.fdapp.Adapter.DonHangAdapter;
import com.tai06.dothetai.fdapp.OOP.HoaDon;
import com.tai06.dothetai.fdapp.R;

import java.util.List;

public class DonhangFragment extends Fragment {

    public static final int MSG_DONHANG = 1;

    private View view;
    private RecyclerView recycle_donhang;
    private Handler handler;
    private List<HoaDon> arrHoadon;
    private DonHangAdapter donHangAdapter;
    private ProgressDialog progressDialog;
    private TextView empty_donhang;
    private String id_person,email,name,sdt;

    public DonhangFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donhang, container, false);
        return view;
    }
}