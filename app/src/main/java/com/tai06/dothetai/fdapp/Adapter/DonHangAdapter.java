package com.tai06.dothetai.fdapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tai06.dothetai.fdapp.OOP.CTHD;
import com.tai06.dothetai.fdapp.R;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> {

    private List<CTHD> mList;
    private Fragment mFrag;

    public DonHangAdapter(List<CTHD> mList, Fragment mFrag) {
        this.mList = mList;
        this.mFrag = mFrag;
    }

    @NonNull
    @Override
    public DonHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_donhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangAdapter.ViewHolder holder, int position) {
        CTHD cthd = mList.get(position);
//        Picasso.get().load(cthd.getImage()).into(holder.image_product);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_product;
        TextView name_product,price,status,soluong;
        Button cancel_donhang;

        //chitietdonhang
        TextView ten_kh,diachi,sdt,ngaydat_hd,ngaygiao_hd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_product = itemView.findViewById(R.id.image_product);
            name_product = itemView.findViewById(R.id.name_product);
            price = itemView.findViewById(R.id.price);
            soluong = itemView.findViewById(R.id.soluong);
            status = itemView.findViewById(R.id.status);
            cancel_donhang = itemView.findViewById(R.id.cancel_donhang);

            //chitiethoadon
            ten_kh = itemView.findViewById(R.id.ten_kh);
            diachi = itemView.findViewById(R.id.diachi);
            sdt = itemView.findViewById(R.id.sdt);
            ngaydat_hd = itemView.findViewById(R.id.ngaydat_hd);
            ngaygiao_hd = itemView.findViewById(R.id.ngaygiao_hd);
        }
    }
}
