package com.example.btlll.service;

import com.example.btlll.entity.NhanVien;
import com.example.btlll.entity.HoatDong;
import com.example.btlll.repository.NhanVienRepository;
import com.example.btlll.repository.HoatDongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.btlll.entity.TaiKhoan;

import java.util.List;

@Service
public class NhanVienService {

    @Autowired
    private NhanVienRepository repo;

    @Autowired
    private HoatDongRepository hoatDongRepo;

    public List<NhanVien> getByUserId(Integer id) {
        return repo.findByTaiKhoanId(id);
    }

    public void save(NhanVien nv) {
        repo.save(nv);

        HoatDong hd = new HoatDong();
        hd.setNoiDung("Thêm/Cập nhật nhân viên: " + nv.getTenNV());

        // 🔥 GẮN USER
        hd.setTaiKhoan(nv.getTaiKhoan());

        hoatDongRepo.save(hd);
    }

    public void delete(String id, TaiKhoan user) {
        repo.deleteById(id);

        HoatDong hd = new HoatDong();
        hd.setNoiDung("Xóa nhân viên mã: " + id);

        // 🔥 GẮN USER
        hd.setTaiKhoan(user);

        hoatDongRepo.save(hd);
    }
}