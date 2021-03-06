package com.tai06.dothetai.fdapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tai06.dothetai.fdapp.AdminActivity.ConfirmCTHD.FragmentConfirm.RunningFragment;
import com.tai06.dothetai.fdapp.AdminActivity.ConfirmCTHD.FragmentConfirm.SuccessFragment;
import com.tai06.dothetai.fdapp.OOP.CTHD;
import com.tai06.dothetai.fdapp.R;

import java.util.List;

public class SuccessAdapter extends RecyclerView.Adapter<SuccessAdapter.ViewHolder> {

    private List<CTHD> mList;
    private Fragment mFrag;

    public SuccessAdapter(List<CTHD> mList, Fragment mFrag) {
        this.mList = mList;
        this.mFrag = mFrag;
    }

    @NonNull
    @Override
    public SuccessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_confirm, parent, false);
        return new ViewHolder(view);

}
    @Override
    public void onBindViewHolder(@NonNull SuccessAdapter.ViewHolder holder, int position) {
        CTHD cthd = mList.get(position);
        Picasso.get().load(cthd.getImage()).into(holder.image_product);
        holder.name_product.setText(cthd.getTen_sp());
        holder.price.setText(String.valueOf(cthd.getGia_sp()));
        holder.status.setText(cthd.getTrangthai());
        holder.soluong.setText(String.valueOf(cthd.getSl_sp()));
        holder.ten_kh.setText(cthd.getTen_kh());
        holder.diachi.setText(cthd.getDiachi());
        holder.sdt.setText(cthd.getSdt());
        holder.ngaydat_hd.setText(cthd.getNgaydat_hd());
        holder.ngaygiao_hd.setText(cthd.getNgaygiao_hd());
        holder.thanhtoan.setText(String.valueOf(cthd.getThanhtien())+"VNĐ");
        holder.ic_expand_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SuccessFragment)mFrag).ShowHide(holder.ic_expand_more,holder.table_detail);
            }
        });

        holder.cancel_donhang.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image_product;
        ImageButton ic_expand_more;
        TextView name_product,price,status,soluong;
        Button confirm_donhang,cancel_donhang;
        TableLayout table_detail;
        //chitietdonhang
        TextView ten_kh,diachi,sdt,ngaydat_hd,ngaygiao_hd,thanhtoan;
        //cthd theo ngay
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image_product = itemView.findViewById(R.id.image_product);
            name_product = itemView.findViewById(R.id.name_product);
            price = itemView.findViewById(R.id.price);
            soluong = itemView.findViewById(R.id.soluong);
            status = itemView.findViewById(R.id.status);
            confirm_donhang = itemView.findViewById(R.id.confirm_donhang);
            cancel_donhang = itemView.findViewById(R.id.cancel_donhang);

            //chitiethoadon
            ten_kh = itemView.findViewById(R.id.ten_kh);
            diachi = itemView.findViewById(R.id.diachi);
            sdt = itemView.findViewById(R.id.sdt);
            ngaydat_hd = itemView.findViewById(R.id.ngaydat_hd);
            ngaygiao_hd = itemView.findViewById(R.id.ngaygiao_hd);
            thanhtoan = itemView.findViewById(R.id.thanhtoan);
            ic_expand_more = itemView.findViewById(R.id.ic_expand_more);
            table_detail = itemView.findViewById(R.id.table_detail);
        }
    }
}
