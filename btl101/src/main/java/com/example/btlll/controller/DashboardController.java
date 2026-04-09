package com.example.btlll.controller;

import com.example.btlll.dto.DashboardDTO;
import com.example.btlll.entity.HoatDong;
import com.example.btlll.entity.TaiKhoan;
import com.example.btlll.repository.HoatDongRepository;
import com.example.btlll.service.ChamCongService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private ChamCongService chamCongService;

    @Autowired
    private HoatDongRepository hoatDongRepository;

    // 🔹 API dashboard stats
    @GetMapping
    public DashboardDTO getDashboard(HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        if (user == null) {
            throw new RuntimeException("Chưa đăng nhập");
        }

        Integer userId = user.getId();

        int present = chamCongService.countPresentToday(userId);
        int absent = chamCongService.countAbsentToday(userId);
        int late = chamCongService.countLateToday(userId);

        int total = present + absent + late;

        return new DashboardDTO(present, absent, late, total);
    }

    // 🔥 FIX: thêm API hoạt động gần đây
    @GetMapping("/activities")
    public List<HoatDong> getActivities(HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        if (user == null) {
            throw new RuntimeException("Chưa đăng nhập");
        }

        return hoatDongRepository
                .findTop5ByTaiKhoanIdOrderByThoiGianDesc(user.getId());
    }
}