package meatmeet.meatmeet.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import meatmeet.meatmeet.domain.Order;

@Repository
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    
    public OrderRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private RowMapper<Order> orderRowMapper = (rs, rowNum) -> {
        Order order = new Order();
        
        order.setOrderName(rs.getString("order_name"));
        order.setOrderPhone(rs.getString("order_phone"));
        order.setOrderAddress(rs.getString("order_address"));
        order.setOrderRequest(rs.getString("order_request"));
        
        return order;
    };
    
    public List<Order> findByOrderName(String orderName) {
        String sql = "SELECT * FROM orders WHERE order_name = ?";
        return jdbcTemplate.query(sql, orderRowMapper, orderName);
    }

    public void save(Order order) {
        String sql = "INSERT INTO orders (order_name, order_phone, order_address, order_request) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, order.getOrderName(), order.getOrderPhone(), order.getOrderAddress(), order.getOrderRequest());
    }

    public Order findById(Long orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        return jdbcTemplate.queryForObject(sql, orderRowMapper, orderId);
    }

    public void deleteById(Long orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }
}