package com.example.btlll.repository;

import com.example.btlll.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, Integer> {
    TaiKhoan findByUsername(String username);
}