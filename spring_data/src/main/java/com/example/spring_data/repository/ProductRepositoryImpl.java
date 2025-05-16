package com.example.spring_data.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.spring_data.model.Product;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Product findById(int product_id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{product_id}, (rs, rowNum) -> {
            return new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock_quantity")
            );
        });
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDouble("price"),
                    rs.getInt("stock_quantity")
            );
        });
    }

    @Override
    public List<Product> top5BestSellingProducts() {
        String sql = "SELECT \n" +
                "    p.product_id,\n" +
                "    p.name,\n" +
                "    SUM(oi.quantity) AS total_quantity_sold\n" +
                "FROM \n" +
                "    products p\n" +
                "JOIN \n" +
                "    order_items oi ON p.product_id = oi.product_id\n" +
                "GROUP BY \n" +
                "    p.product_id, p.name\n" +
                "ORDER BY \n" +
                "    total_quantity_sold DESC\n" +
                "LIMIT 5;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            ;
            return new Product(
                    rs.getInt("product_id"),
                    rs.getString("name"),
                    null,
                    0,
                    rs.getInt("total_quantity_sold")
            );
        });
    }

    @Override
    public List<Product> revenueByProduct(String productName) {
        String sql = "SELECT c.customer_id, c.name, SUM(oi.quantity * oi.unit_price) AS total_revenue " +
                "FROM customers c " +
                "JOIN orders o ON c.customer_id = o.customer_id " +
                "JOIN order_items oi ON o.order_id = oi.order_id " +
                "JOIN products p ON oi.product_id = p.product_id " +
                "WHERE p.name = ? " +
                "GROUP BY c.customer_id, c.name " +
                "ORDER BY total_revenue DESC";

        return jdbcTemplate.query(sql, new Object[]{productName}, (rs, rowNum) -> {
            return new Product(
                    rs.getInt("customer_id"),
                    rs.getString("name"),
                    null,
                    rs.getDouble("total_revenue"),
                    0
            );
        });
    }

    @Override
    public List<Product> totalRevenueByMonth(String month) {
        String sql = "SELECT p.name, DATE_FORMAT(o.order_date, '%Y-%m') AS order_month, " +
                     "SUM(oi.quantity * oi.unit_price) AS total_order_value " +
                     "FROM orders o " +
                     "JOIN order_items oi ON o.order_id = oi.order_id " +
                     "JOIN products p ON oi.product_id = p.product_id " +
                     "WHERE DATE_FORMAT(o.order_date, '%Y-%m') = ? " +
                     "GROUP BY p.name, order_month " +
                     "ORDER BY order_month DESC";

        return jdbcTemplate.query(sql, new Object[]{month}, (rs, rowNum) -> {
            return new Product(
                0,
                rs.getString("p.name"),
                null,
                rs.getDouble("total_order_value"),
                0
            );
        });
    }

    @Override
    @Transactional
    public void batchInsert(List<Product> products) {
        String sql = "INSERT INTO products (name, description, price, stock_quantity) VALUES (?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, products, products.size(), (ps, product) -> {
            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setInt(4, product.getStockQuantity());
        });
    }
}