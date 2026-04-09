package com.example.btlll.entity;

import com.example.btlll.entity.TaiKhoan;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "SuKien")
public class SuKien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String tieuDe;
    private String moTa;

    private java.time.LocalDateTime ngayBatDau;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 🔥 nên có
    @com.fasterxml.jackson.annotation.JsonIgnore
    private TaiKhoan taiKhoan;
}