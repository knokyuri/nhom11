-- ==========================================================
-- 1. KHỞI TẠO DATABASE VÀ CẤU HÌNH QUYỀN TRUY CẬP
-- ==========================================================
USE master;
GO

IF EXISTS (SELECT * FROM sys.databases WHERE name = 'QuanLyNhanVien')
BEGIN
    ALTER DATABASE QuanLyNhanVien SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QuanLyNhanVien;
END
GO

CREATE DATABASE QuanLyNhanVien;
GO

USE QuanLyNhanVien;
GO

-- Tạo Login và User cho Java (nếu chưa có)
IF NOT EXISTS (SELECT * FROM sys.server_principals WHERE name = 'java_user')
BEGIN
    CREATE LOGIN java_user WITH PASSWORD = '123456';
END
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = 'java_user')
BEGIN
    CREATE USER java_user FOR LOGIN java_user;
END
GO

ALTER ROLE db_owner ADD MEMBER java_user;
GO

-- ==========================================================
-- 2. TẠO CÁC BẢNG DỮ LIỆU
-- ==========================================================

-- 🔹 Bảng 1: Tài khoản (Quản lý đăng nhập)
CREATE TABLE TaiKhoan (
    Id INT IDENTITY PRIMARY KEY,
    Username VARCHAR(50) UNIQUE NOT NULL,
    Password VARCHAR(100) NOT NULL
);
GO

-- 🔹 Bảng 2: Phòng Ban (Quản lý ngân sách & thưởng)
CREATE TABLE PhongBan (
    MaPB VARCHAR(10) PRIMARY KEY,
    TenPB NVARCHAR(50) NOT NULL,
    NganSach DECIMAL(18,2) DEFAULT 0,
    TienThuong DECIMAL(18,2) DEFAULT 0
);
GO

-- 🔹 Bảng 3: Nhân Viên
CREATE TABLE NhanVien (
    MaNV VARCHAR(10) PRIMARY KEY,
    TenNV NVARCHAR(100) NOT NULL,
    Tuoi INT,
    Luong DECIMAL(12,2),
    BangCap NVARCHAR(50),
    SoNamLamViec INT DEFAULT 0,
    SoNgayNghiPhep INT DEFAULT 12,
    user_id INT,        -- Liên kết với tài khoản quản lý
    MaPB VARCHAR(10),   -- Liên kết với phòng ban
    
    FOREIGN KEY (user_id) REFERENCES TaiKhoan(Id),
    FOREIGN KEY (MaPB) REFERENCES PhongBan(MaPB)
);
GO

-- 🔹 Bảng 4: Chấm Công (Lưu lịch sử hàng ngày)
CREATE TABLE ChamCong (
    Id INT IDENTITY PRIMARY KEY,
    MaNV VARCHAR(10) NOT NULL,
    Ngay DATE DEFAULT GETDATE(),
    TrangThai NVARCHAR(20), -- N'Có mặt', N'Vắng mặt', N'Muộn'
    GhiChu NVARCHAR(255),
    
    FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV) ON DELETE CASCADE
);
GO

-- ==========================================================
-- 3. CHÈN DỮ LIỆU MẪU (SEED DATA)
-- ==========================================================

-- 🔹 Thêm Tài khoản
INSERT INTO TaiKhoan (Username, Password) VALUES 
('user1', '123456'),
('user2', '123456');
GO

-- 🔹 Thêm Phòng Ban
INSERT INTO PhongBan (MaPB, TenPB, NganSach, TienThuong) VALUES 
('PB01', N'Công nghệ thông tin', 500000000, 10000000),
('PB02', N'Kế toán', 200000000, 5000000),
('PB03', N'Nhân sự', 150000000, 4000000);
GO

-- 🔹 Thêm Nhân viên (Gán MaPB và user_id tương ứng)
INSERT INTO NhanVien (MaNV, TenNV, Tuoi, Luong, BangCap, SoNamLamViec, SoNgayNghiPhep, user_id, MaPB) VALUES
('NV001', N'Nguyễn Văn An', 25, 8000000, N'Cử nhân', 2, 12, 1, 'PB01'),
('NV002', N'Trần Thị Bình', 30, 12000000, N'Thạc sĩ', 5, 15, 1, 'PB02'),
('NV003', N'Lê Văn Cường', 28, 9500000, N'Cử nhân', 3, 14, 1, 'PB03'),
('NV004', N'Phạm Thị Dung', 35, 15000000, N'Thạc sĩ', 8, 18, 2, 'PB01'),
('NV005', N'Hoàng Văn Em', 40, 20000000, N'Tiến sĩ', 12, 20, 2, 'PB01');
GO

-- 🔹 Thêm Chấm công mẫu
INSERT INTO ChamCong (MaNV, Ngay, TrangThai, GhiChu) VALUES 
('NV001', GETDATE(), N'Có mặt', N'Đúng giờ'),
('NV002', GETDATE(), N'Muộn', N'Trễ 10 phút'),
('NV003', GETDATE(), N'Vắng mặt', N'Nghỉ ốm');
GO

-- Kiểm tra dữ liệu
SELECT nv.MaNV, nv.TenNV, pb.TenPB, tk.Username as Manager
FROM NhanVien nv
JOIN PhongBan pb ON nv.MaPB = pb.MaPB
JOIN TaiKhoan tk ON nv.user_id = tk.Id;