package com.example.btlll.repository;

import com.example.btlll.entity.PhongBan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List; // 🔥 thiếu import

public interface PhongBanRepository extends JpaRepository<PhongBan, String> {

    List<PhongBan> findByTaiKhoanId(Integer userId);

    List<PhongBan> findByTaiKhoanIdAndTenPBContainingIgnoreCase(Integer userId, String keyword);
}