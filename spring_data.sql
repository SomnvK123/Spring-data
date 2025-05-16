create database spring_data;

use spring_data;

DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS products;


select * from orders;
select * from products;


-- Bảng products
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng customers
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Bảng orders: 
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) DEFAULT 'pending',
    total_amount DECIMAL(10,2),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Bảng order_items
CREATE TABLE order_items (
    order_item_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(product_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

SELECT 
    c.customer_id,
    c.name,
    SUM(oi.quantity * oi.unit_price) AS total_revenue
FROM 
    customers c
JOIN 
    orders o ON c.customer_id = o.customer_id
JOIN 
    order_items oi ON o.order_id = oi.order_id
JOIN 
    products p ON oi.product_id = p.product_id
WHERE 
    p.name = 'Laptop Dell XPS 13'
GROUP BY 
    c.customer_id, c.name
ORDER BY 
    total_revenue DESC;



-- top 5
SELECT 
    p.product_id,
    p.name,
    SUM(oi.quantity) AS total_quantity_sold
FROM 
    products p
JOIN 
    order_items oi ON p.product_id = oi.product_id
GROUP BY 
    p.product_id, p.name
ORDER BY 
    total_quantity_sold DESC
LIMIT 5;

-- 
SELECT 
    c.customer_id,
    c.name,
    SUM(oi.quantity * oi.unit_price) AS total_revenue
FROM 
    customers c
JOIN 
    orders o ON c.customer_id = o.customer_id
JOIN 
    order_items oi ON o.order_id = oi.order_id
GROUP BY 
    c.customer_id, c.name
ORDER BY 
    total_revenue DESC;


-- Tính tổng giá trị đơn hàng theo tháng
SELECT 
    p.name,
    DATE_FORMAT(o.order_date, '%Y-%m') AS order_month,
    SUM(oi.quantity * oi.unit_price) AS total_order_value
FROM 
    orders o
JOIN 
    order_items oi ON o.order_id = oi.order_id
JOIN 
    products p ON oi.product_id = p.product_id
GROUP BY 
    p.name,
    order_month
ORDER BY 
    order_month DESC;

    
    
    -- data mau
    -- Insert into products
INSERT INTO products (name, description, price, stock_quantity) VALUES
('Laptop Dell XPS 13', 'Laptop cao cấp siêu mỏng, nhẹ', 1500.00, 20),
('iPhone 15 Pro', 'Điện thoại thông minh cao cấp của Apple', 1200.00, 30),
('Samsung Galaxy S24', 'Điện thoại Android mới nhất', 1100.00, 25),
('Apple Watch Series 9', 'Đồng hồ thông minh Apple', 500.00, 50),
('Sony WH-1000XM5', 'Tai nghe chống ồn đỉnh cao', 350.00, 40),
('Asus ROG Strix G16', 'Laptop gaming hiệu suất cao', 2000.00, 10),
('Canon EOS R8', 'Máy ảnh Mirrorless Full-frame', 1800.00, 15),
('Kindle Paperwhite', 'Máy đọc sách Kindle Paperwhite 2023', 150.00, 60),
('Logitech MX Master 3S', 'Chuột không dây cao cấp', 100.00, 80),
('Anker PowerCore 20000', 'Pin sạc dự phòng dung lượng cao', 80.00, 100),
('AirPods Pro 2', 'Tai nghe Bluetooth Apple AirPods Pro Gen 2', 250.00, 45),
('GoPro Hero 12 Black', 'Camera hành trình GoPro', 400.00, 18),
('MSI Optix MAG342CQR', 'Màn hình cong gaming 34 inch', 600.00, 12),
('Apple MacBook Air M3', 'Laptop MacBook Air với chip M3', 1300.00, 22),
('Samsung 980 Pro 1TB SSD', 'Ổ cứng SSD PCIe 4.0 tốc độ cao', 200.00, 35);

-- Insert into customers
INSERT INTO customers (name, email, phone, address) VALUES
('Nguyễn Văn A', 'a.nguyen@example.com', '0909123456', '123 Đường A, Quận 1, TP.HCM'),
('Trần Thị B', 'b.tran@example.com', '0912345678', '456 Đường B, Quận 3, TP.HCM'),
('Lê Văn C', 'c.le@example.com', '0987654321', '789 Đường C, Quận 5, TP.HCM'),
('Phạm Thị D', 'd.pham@example.com', '0933222111', '101 Đường D, Quận 7, TP.HCM'),
('Hoàng Văn E', 'e.hoang@example.com', '0922112233', '202 Đường E, Quận 9, TP.HCM');

-- Insert into orders
INSERT INTO orders (customer_id, status, total_amount) VALUES
(1, 'completed', 2500.00),
(2, 'pending', 1500.00),
(3, 'shipped', 3200.00),
(4, 'completed', 600.00),
(5, 'cancelled', 1200.00),
(1, 'pending', 350.00),
(2, 'completed', 1800.00);

-- Insert into order_items
INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES
(1, 1, 1, 1500.00),
(1, 2, 1, 1200.00),
(2, 3, 1, 1100.00),
(2, 10, 5, 80.00),
(3, 6, 1, 2000.00),
(3, 7, 1, 1800.00),
(4, 5, 1, 350.00),
(4, 11, 1, 250.00),
(5, 4, 2, 500.00),
(5, 9, 2, 100.00),
(6, 8, 1, 150.00),
(7, 14, 1, 1300.00),
(7, 15, 2, 200.00);




