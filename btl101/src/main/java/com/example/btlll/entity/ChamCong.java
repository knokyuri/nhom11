package com.example.btlll.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChamCong {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "MaNV")
    private NhanVien nhanVien;

    private LocalDate ngay;
    private String trangThai;
    private String ghiChu;
}