package com.example.btlll.controller;

import com.example.btlll.entity.*;
import com.example.btlll.repository.ChamCongRepository;
import com.example.btlll.repository.HoatDongRepository;
import com.example.btlll.service.*;
import com.example.btlll.dto.ChamCongForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class NhanVienController {

    @Autowired private HoatDongRepository hoatDongRepo;
    @Autowired private NhanVienService nvService;
    @Autowired private PhongBanService pbService;
    @Autowired private ChamCongRepository ccRepository;
    @Autowired private AuthService authService;

    // ================== HOME ==================
    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // 🔥 Nhân viên theo user
        List<NhanVien> dsNV = nvService.getByUserId(user.getId());
        model.addAttribute("dsNV", dsNV);

        // 🔥 FIX: chỉ lấy phòng ban của user
        List<PhongBan> dsPB = pbService.getByUser(user.getId());

        model.addAttribute("dsPB", dsPB);

        return "index";
    }

    // ================== NHÂN VIÊN ==================
    @PostMapping("/save")
    public String save(NhanVien nv, @RequestParam String maPB, HttpSession session) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        nv.setTaiKhoan(user);

        PhongBan pb = new PhongBan();
        pb.setMaPB(maPB);
        nv.setPhongBan(pb);

        nvService.save(nv);
        return "redirect:/home";
    }

    @PostMapping("/update")
    public String updateNV(NhanVien nv, @RequestParam String maPB, HttpSession session) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        nv.setTaiKhoan(user);

        PhongBan pb = new PhongBan();
        pb.setMaPB(maPB);
        nv.setPhongBan(pb);

        nvService.save(nv);
        return "redirect:/home?updateSuccess";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable String id, HttpSession session) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        nvService.delete(id, user);
        return "redirect:/home";
    }

    // ================== CHẤM CÔNG ==================
    @PostMapping("/cham-cong/save")
    public String saveCC(@ModelAttribute ChamCongForm form, HttpSession session) {
        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        if (form != null && form.getItems() != null) {
            LocalDate today = LocalDate.now();

            for (var item : form.getItems()) {
                if (item.getMaNV() != null) {

                    ChamCong cc = ccRepository
                            .findByNhanVien_MaNVAndNgay(item.getMaNV(), today) // 🔥 FIX underscore
                            .orElse(new ChamCong());

                    if (cc.getId() == null) {
                        NhanVien nv = new NhanVien();
                        nv.setMaNV(item.getMaNV());
                        cc.setNhanVien(nv);
                        cc.setNgay(today);
                    }

                    cc.setTrangThai(item.getTrangThai());
                    cc.setGhiChu(item.getGhiChu());

                    ccRepository.save(cc);
                }
            }
        }

        // 🔥 LOG hoạt động
        HoatDong hd = new HoatDong();
        hd.setNoiDung("Chấm công ngày " + LocalDate.now());
        hd.setTaiKhoan(user);

        hoatDongRepo.save(hd);

        return "redirect:/home?ccSuccess";
    }

    // ================== PHÒNG BAN ==================

    // 🔹 Thêm phòng ban
    @PostMapping("/phong-ban/add")
    public String addPhongBan(PhongBan pb, HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        pb.setTaiKhoan(user); // 🔥 GÁN USER

        pbService.save(pb);

        return "redirect:/home";
    }

    // 🔹 Xóa phòng ban
    @GetMapping("/phong-ban/delete/{maPB}")
    public String deletePhongBan(@PathVariable String maPB, HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        pbService.delete(maPB, user.getId());

        return "redirect:/home";
    }

    // 🔹 Tìm kiếm phòng ban
    @GetMapping("/phong-ban/search")
    public String searchPhongBan(
            @RequestParam(name = "keyword", required = false) String keyword,
            HttpSession session,
            Model model) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        // 🔒 Check đăng nhập
        if (user == null) {
            return "redirect:/login";
        }

        List<PhongBan> result;

        // 🔥 Fix null + empty keyword
        if (keyword == null || keyword.trim().isEmpty()) {
            result = pbService.getByUser(user.getId());
        } else {
            result = pbService.search(user.getId(), keyword.trim());
        }

        // 🔥 BẮT BUỘC: load đủ dữ liệu cho index.html
        model.addAttribute("dsPB", result);
        model.addAttribute("dsNV", nvService.getByUserId(user.getId()));

        // (optional nhưng nên có để UI ổn định)
        model.addAttribute("keyword", keyword);

        return "index";
    }

    @PostMapping("/phong-ban/save-all")
    public String saveAllPhongBan(@RequestParam Map<String, String> params,
                                  HttpSession session) {

        TaiKhoan user = (TaiKhoan) session.getAttribute("user");

        if (user == null) {
            return "redirect:/login";
        }

        // 🔥 Lấy danh sách PB của user
        List<PhongBan> dsPB = pbService.getByUser(user.getId());

        for (PhongBan pb : dsPB) {

            String maPB = pb.getMaPB();

            // lấy value từ input
            String nganSachStr = params.get("nganSach_" + maPB);
            String tienThuongStr = params.get("tienThuong_" + maPB);

            if (nganSachStr != null) {
                pb.setNganSach(Double.parseDouble(nganSachStr));
            }

            if (tienThuongStr != null) {
                pb.setTienThuong(Double.parseDouble(tienThuongStr));
            }

            pbService.save(pb);
        }

        return "redirect:/home";
    }
}