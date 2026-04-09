package com.example.btlll.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "HoatDong")
public class HoatDong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String noiDung;

    @Column(nullable = false)
    private LocalDateTime thoiGian;

    // 🔥 FIX: đổi sang đúng tên cột trong DB
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private TaiKhoan taiKhoan;

    // 🔥 tự set thời gian
    @PrePersist
    public void prePersist() {
        if (this.thoiGian == null) {
            this.thoiGian = LocalDateTime.now();
        }
    }

    // Getter Setter
    public Integer getId() {
        return id;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public TaiKhoan getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(TaiKhoan taiKhoan) {
        this.taiKhoan = taiKhoan;
    }
}