package com.tai06.dothetai.fdapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;
import com.tai06.dothetai.fdapp.OOP.KhachHang;
import com.tai06.dothetai.fdapp.OOP.Sanpham;
import com.tai06.dothetai.fdapp.R;

public class OderFragment extends Fragment {

    public static final int MSG_Sanpham = 1;
    public static final int MSG_Khachhang = 2;

    private View view;
    private TextInputEditText name_kh,sdt_kh;
    private ImageView img_product;
    private TextView name_product,gia_sp,soluong_sp,ghichu_hd,tongtien_hd;
    private Button thanhtoan;
    private Sanpham sanpham;
    private KhachHang khachHang;
    private Handler handler;
    private String ma_kh, email, ma_sp,datemy,img;

    public OderFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_oder, container, false);
        init();
        initHandler();
        getDataIntent();
        getKhachHang();
        getSanPham();
        return view;
    }

    private void init(){
        name_kh = view.findViewById(R.id.name_kh);
        sdt_kh = view.findViewById(R.id.sdt_kh);
        img_product = view.findViewById(R.id.img_product);
        name_product = view.findViewById(R.id.name_product);
        gia_sp = view.findViewById(R.id.gia_sp);
        soluong_sp = view.findViewById(R.id.soluong_sp);
        ghichu_hd = view.findViewById(R.id.ghichu_hd);
        tongtien_hd = view.findViewById(R.id.tongtien_hd);
        thanhtoan = view.findViewById(R.id.thanhtoan);
    }

    private void initHandler(){
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    case MSG_Sanpham:
                        sanpham = new Sanpham();
                        sanpham = (Sanpham) msg.obj;
                        setSanpham(sanpham);
                        break;
                    case MSG_Khachhang:
                        khachHang = new KhachHang();
                        khachHang = (KhachHang) msg.obj;
                        setKhachHang(khachHang);
                        break;
                }
            }
        };
    }

    private void getDataIntent() {
        Bundle bundle = getArguments();
        String note = bundle.getString("ghichu");
        if (!note.trim().isEmpty()) {
            ghichu_hd.setText(bundle.getString("ghichu"));
        } else {
            ghichu_hd.setTextColor(getResources().getColor(R.color.bac));
            ghichu_hd.setText("(trống)");
        }

        soluong_sp.setText(bundle.getString("soluong"));
        tongtien_hd.setText(bundle.getString("tongtien"));
    }

    private void setSanpham(Sanpham sanpham){
        ma_sp = String.valueOf(sanpham.getMa_sp());
        Picasso.get().load(sanpham.getImage()).into(img_product);
        img = sanpham.getImage();
        name_product.setText(sanpham.getTen_sp());
        gia_sp.setText(String.valueOf(sanpham.getGia_sp())+"VNĐ");
    }

    private void setKhachHang(KhachHang khachHang){
        ma_kh = String.valueOf(khachHang.getMa_kh());
        email = khachHang.getEmail();
        name_kh.setText(khachHang.getTen_kh());
        sdt_kh.setText(khachHang.getSdt());
    }

    private void getSanPham(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                sanpham = (Sanpham) getArguments().getSerializable("sanpham");
                Message msg = new Message();
                msg.what = MSG_Sanpham;
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
                khachHang = (KhachHang) getArguments().getSerializable("khachhang");
                Message msg = new Message();
                msg.what = MSG_Khachhang;
                msg.obj = khachHang;
                handler.sendMessage(msg);
            }
        });
        thread.start();
    }
}