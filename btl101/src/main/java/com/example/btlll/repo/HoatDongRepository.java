package com.example.btlll.repository;

import com.example.btlll.entity.HoatDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HoatDongRepository extends JpaRepository<HoatDong, Integer> {

    // Lấy 5 hoạt động mới nhất
    List<HoatDong> findTop5ByTaiKhoanIdOrderByThoiGianDesc(Integer userId);
}