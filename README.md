# Hệ Thống Quản Lý Cửa Hàng Bán Điện Thoại – PhoneShop

Đây là một ứng dụng desktop viết bằng ngôn ngữ Java, hỗ trợ quản lý toàn diện cho cửa hàng bán điện thoại. Dự án được xây dựng theo mô hình nhiều lớp (BUS – DTO – DAL – GUI), kết hợp với cơ sở dữ liệu MySQL và giao diện đồ họa bằng Java Swing.

## 🔧 Tính Năng Chính

- Quản lý sản phẩm: thêm, sửa, xóa thông tin điện thoại
- Quản lý kho và thương hiệu (Samsung, OPPO, v.v.)
- Quản lý nhân viên và phân quyền người dùng
- Lưu trữ và truy xuất thông tin khách hàng
- Lập hóa đơn và quản lý đơn hàng
- Giao diện đồ họa thân thiện với người dùng

## 📁 Cấu Trúc Dự Án

- `GUI/` – Giao diện người dùng (Java Swing)
- `DTO/` – Các lớp đối tượng trung gian dữ liệu
- `BUS/` – Lớp xử lý nghiệp vụ
- `DAL/` – Lớp truy xuất cơ sở dữ liệu
- `assets/` – Hình ảnh và tài nguyên giao diện
- `mobileshop.sql` – Tệp khởi tạo cơ sở dữ liệu MySQL

## 🛠 Công Nghệ Sử Dụng

- Java (JDK 8 trở lên)
- Java Swing
- MySQL
- NetBeans (sử dụng `build.xml`)
- JDBC

## ⚙️ Hướng Dẫn Cài Đặt

1. Clone repository về máy:
   ```bash
   git clone https://github.com/pqtoannn/Webbanhang.git
