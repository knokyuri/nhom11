package com.example.btlll.dto;

import lombok.Data;
import java.util.List;

@Data
public class ChamCongForm {
    private List<ChamCongItem> items;

    @Data
    public static class ChamCongItem {
        private String maNV;
        private String trangThai;
        private String ghiChu;
    }
}