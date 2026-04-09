package com.example.btlll.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "PhongBan")
public class PhongBan {

    @Id
    @Column(name = "MaPB")
    private String maPB;

    @Column(name = "TenPB")
    private String tenPB;

    private Double nganSach;
    private Double tienThuong;

    // 🔥 THÊM USER
    @ManyToOne
    @JoinColumn(name = "user_id")
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "phongBan")
    private List<NhanVien> dsNhanVien;

    // ===== GETTER SETTER ĐẦY ĐỦ =====

    public String getMaPB() {
        return maPB;
    }

    public void setMaPB(String maPB) {
        this.maPB = maPB;
    }

    public String getTenPB() {
        return tenPB;
    }

    public void setTenPB(String tenPB) {
        this.tenPB = tenPB;
    }

    public Double getNganSach() {
        return nganSach;
    }

    public void setNganSach(Double nganSach) {
        this.nganSach = nganSach;
    }

    public Double getTienThuong() {
        return tienThuong;
    }

    public void setTienThuong(Double tienThuong) {
        this.tienThuong = tienThuong;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public List<NhanVien> getDsNhanVien() {
        return dsNhanVien;
    }

    public void setDsNhanVien(List<NhanVien> dsNhanVien) {
        this.dsNhanVien = dsNhanVien;
    }
}