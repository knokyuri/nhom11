package com.example.btlll.service;

import com.example.btlll.dto.DashboardDTO;
import com.example.btlll.entity.ChamCong;
import com.example.btlll.repository.ChamCongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChamCongService {

    @Autowired
    private ChamCongRepository repo;

    public void save(ChamCong cc) {
        repo.save(cc);
    }

    public int countByStatus(Integer userId, String status) {
        return repo.countByUserAndStatusToday(userId, status);
    }

    // 🔥 thêm lại để không lỗi controller
    public int countPresentToday(Integer userId) {
        return countByStatus(userId, "Có mặt");
    }

    public int countAbsentToday(Integer userId) {
        return countByStatus(userId, "Vắng mặt");
    }

    public int countLateToday(Integer userId) {
        return countByStatus(userId, "Muộn");
    }

    public DashboardDTO getDashboard(Integer userId) {

        int present = countPresentToday(userId);
        int absent = countAbsentToday(userId);
        int late = countLateToday(userId);

        int total = present + absent + late;

        return new DashboardDTO(present, absent, late, total);
    }
}