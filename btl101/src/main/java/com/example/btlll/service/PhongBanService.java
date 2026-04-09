package com.example.btlll.service;

import com.example.btlll.entity.PhongBan;
import com.example.btlll.repository.PhongBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhongBanService {

    @Autowired
    private PhongBanRepository repo;

    // 🔹 Lấy phòng ban theo user
    public List<PhongBan> getByUser(Integer userId) {
        return repo.findByTaiKhoanId(userId);
    }

    // 🔹 Thêm / cập nhật
    public void save(PhongBan pb) {
        repo.save(pb);
    }

    // 🔹 Xóa (có kiểm tra quyền)
    public void delete(String maPB, Integer userId) {

        PhongBan pb = repo.findById(maPB)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));

        // 🔒 Check quyền
        if (pb.getTaiKhoan() == null ||
                !pb.getTaiKhoan().getId().equals(userId)) {
            throw new RuntimeException("Không có quyền xóa!");
        }

        repo.delete(pb); // 🔥 tốt hơn deleteById
    }

    // 🔹 Tìm kiếm
    public List<PhongBan> search(Integer userId, String keyword) {

        // 🔥 tránh lỗi null
        if (keyword == null || keyword.trim().isEmpty()) {
            return repo.findByTaiKhoanId(userId);
        }

        return repo.findByTaiKhoanIdAndTenPBContainingIgnoreCase(userId, keyword);
    }
}