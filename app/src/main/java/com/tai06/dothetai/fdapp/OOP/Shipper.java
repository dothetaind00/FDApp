package com.tai06.dothetai.fdapp.OOP;

import java.io.Serializable;

public class Shipper implements Serializable {
    private int ma_ship;
    private int ma_hd;
    private String shipper;
    private String ngaydat;

    public Shipper() {
    }

    public Shipper(int ma_ship, int ma_hd, String shipper, String ngaydat) {
        this.ma_ship = ma_ship;
        this.ma_hd = ma_hd;
        this.shipper = shipper;
        this.ngaydat = ngaydat;
    }

    public int getMa_ship() {
        return ma_ship;
    }

    public void setMa_ship(int ma_ship) {
        this.ma_ship = ma_ship;
    }

    public int getMa_hd() {
        return ma_hd;
    }

    public void setMa_hd(int ma_hd) {
        this.ma_hd = ma_hd;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getNgaydat() {
        return ngaydat;
    }

    public void setNgaydat(String ngaydat) {
        this.ngaydat = ngaydat;
    }
}
