package com.example.btlll.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoan {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;
 private String username;
 private String password;

 @OneToMany(mappedBy = "taiKhoan", cascade = CascadeType.ALL)
 private List<NhanVien> dsNhanVien;
}