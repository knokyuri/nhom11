package com.example.btlll.dto;

public class DashboardDTO {

    private int present;
    private int absent;
    private int late;
    private int total;

    // 🔥 Constructor rỗng (QUAN TRỌNG)
    public DashboardDTO() {}

    public DashboardDTO(int present, int absent, int late, int total) {
        this.present = present;
        this.absent = absent;
        this.late = late;
        this.total = total;
    }

    public int getPresent() { return present; }
    public void setPresent(int present) { this.present = present; }

    public int getAbsent() { return absent; }
    public void setAbsent(int absent) { this.absent = absent; }

    public int getLate() { return late; }
    public void setLate(int late) { this.late = late; }

    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
}