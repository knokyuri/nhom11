package com.example.btlll.controller;

import com.example.btlll.entity.SuKien;
import com.example.btlll.entity.TaiKhoan;
import com.example.btlll.repository.SuKienRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class SuKienApiController {

    @Autowired
    private SuKienRepository suKienRepository;

    // 📌 Lấy danh sách sự kiện theo user
    @GetMapping
    public ResponseEntity<List<SuKien>> getEvents(HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body(List.of());
        }

        List<SuKien> list = suKienRepository.findByTaiKhoan_Id(user.getId());
        return ResponseEntity.ok(list);
    }

    // 📌 Thêm sự kiện
    @PostMapping
    public ResponseEntity<String> addEvent(@RequestBody SuKien sk,
                                           HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("NOT_LOGIN");
        }

        sk.setTaiKhoan(user);

        if (sk.getNgayBatDau() == null) {
            sk.setNgayBatDau(LocalDateTime.now());
        }

        suKienRepository.save(sk);

        return ResponseEntity.ok("OK");
    }

    // 📌 Xóa sự kiện
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable Integer id,
                                              HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).body("NOT_LOGIN");
        }

        if (!suKienRepository.existsById(id)) {
            return ResponseEntity.status(404).body("NOT_FOUND");
        }

        suKienRepository.deleteById(id);

        return ResponseEntity.ok("DELETED");
    }
}