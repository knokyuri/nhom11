package com.example.btlll.repository;

import com.example.btlll.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NhanVienRepository extends JpaRepository<NhanVien, String> {

    List<NhanVien> findByTaiKhoanId(Integer userId);

    // Đồng bộ hàm tính lương trung bình
    @Query("SELECT COALESCE(AVG(nv.luong), 0) FROM NhanVien nv WHERE nv.taiKhoan.id = :userId")
    Double getLuongTrungBinh(@Param("userId") Integer userId);

    // Đồng bộ hàm thống kê theo bằng cấp
    @Query("SELECT nv.bangCap, COUNT(nv) FROM NhanVien nv WHERE nv.taiKhoan.id = :userId GROUP BY nv.bangCap")
    List<Object[]> countByBangCap(@Param("userId") Integer userId);
}