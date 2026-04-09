package com.example.btlll.repository;

import com.example.btlll.entity.SuKien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SuKienRepository extends JpaRepository<SuKien, Integer> {
    // Hàm này dùng để lấy lịch trình riêng của từng User
    List<SuKien> findByTaiKhoan_Id(Integer userId);
}