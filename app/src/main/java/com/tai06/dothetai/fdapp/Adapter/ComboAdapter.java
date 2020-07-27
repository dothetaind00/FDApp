package com.tai06.dothetai.fdapp.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tai06.dothetai.fdapp.OOP.Sanpham;

import java.util.List;

public class ComboAdapter extends RecyclerView.Adapter<ComboAdapter.ViewHolder> {

    private List<Sanpham> mList;
    private Fragment mFrag;

    public ComboAdapter(List<Sanpham> mList, Fragment mFrag) {
        this.mList = mList;
        this.mFrag = mFrag;
    }

    @NonNull
    @Override
    public ComboAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ComboAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
