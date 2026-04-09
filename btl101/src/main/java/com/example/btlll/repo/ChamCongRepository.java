package com.example.btlll.repository;

import com.example.btlll.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ChamCongRepository extends JpaRepository<ChamCong, Integer> {

    // 🔥 Query thống kê
    @Query("""
        SELECT COUNT(cc)
        FROM ChamCong cc
        JOIN cc.nhanVien nv
        WHERE nv.taiKhoan.id = :userId
        AND cc.trangThai = :status
        AND cc.ngay = CURRENT_DATE
    """)
    int countByUserAndStatusToday(Integer userId, String status);

    // 🔥 Method đúng (CHỈ GIỮ CÁI NÀY)
    Optional<ChamCong> findByNhanVien_MaNVAndNgay(String maNV, LocalDate ngay);
}