package com.example.btlll.controller;

import com.example.btlll.entity.TaiKhoan;
import com.example.btlll.repository.NhanVienRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ThongKeController {

    @Autowired
    private NhanVienRepository nvRepo;

    // ================== THỐNG KÊ ==================
    @GetMapping("/api/thong-ke/data")
    @ResponseBody
    public Map<String, Object> getThongKeData(HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");
        Map<String, Object> data = new HashMap<>();

        if (user != null) {
            data.put("avgLuong", nvRepo.getLuongTrungBinh(user.getId()));
            data.put("bangCapData", nvRepo.countByBangCap(user.getId()));
        } else {
            data.put("avgLuong", 0);
            data.put("bangCapData", new HashMap<>());
        }

        return data;
    }
}