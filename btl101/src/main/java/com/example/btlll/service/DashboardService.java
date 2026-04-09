package com.example.btlll.service;

import com.example.btlll.dto.DashboardDTO;
import com.example.btlll.entity.HoatDong;
import com.example.btlll.repository.ChamCongRepository;
import com.example.btlll.repository.HoatDongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    @Autowired
    private ChamCongRepository chamCongRepo;

    @Autowired
    private HoatDongRepository hoatDongRepo;

    public DashboardDTO getDashboard(Integer userId) {

        int present = chamCongRepo.countByUserAndStatusToday(userId, "Có mặt");
        int absent = chamCongRepo.countByUserAndStatusToday(userId, "Vắng mặt");
        int late = chamCongRepo.countByUserAndStatusToday(userId, "Muộn");

        int total = present + absent + late;

        return new DashboardDTO(present, absent, late, total);
    }

    // 🔥 THÊM CÁI NÀY
    public List<HoatDong> getRecentActivities(Integer userId) {
        return hoatDongRepo.findTop5ByTaiKhoanIdOrderByThoiGianDesc(userId);
    }
}