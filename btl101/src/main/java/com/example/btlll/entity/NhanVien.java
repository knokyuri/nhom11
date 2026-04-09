package com.example.btlll.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NhanVien {
    @Id
    private String maNV;
    private String tenNV;
    private Integer tuoi;
    private Double luong;
    private String bangCap;
    private Integer soNamLamViec;
    private Integer soNgayNghiPhep;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private TaiKhoan taiKhoan;

    @ManyToOne
    @JoinColumn(name = "MaPB") // Khớp với cột MaPB trong SQL
    private PhongBan phongBan;
}