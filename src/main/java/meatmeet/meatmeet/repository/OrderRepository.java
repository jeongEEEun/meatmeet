package meatmeet.meatmeet.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
        
        order.setOrderId(rs.getInt("order_id"));
        order.setMemberId(rs.getString("member_id"));
        order.setItemId(rs.getInt("item_id"));
        order.setUserName(rs.getString("user_name"));
        order.setPhone(rs.getString("phone"));
        order.setAddress(rs.getString("address"));
        order.setRequest(rs.getString("request"));
        order.setPrice(rs.getInt("price"));
        order.setOrderDate(rs.getString("order_date"));
        order.setQuantity(rs.getString("quantity"));
        
        return order;
    };
    
    public Optional<Order> findByOrderId(int orderId) {
        String sql = "SELECT * FROM order_list WHERE order_id = ?";
        return jdbcTemplate.query(sql, orderRowMapper, orderId).stream().findAny();
    }

    public Optional<Order> save(Order order) {
    	SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
    			.withTableName("order_list")
    			.usingGeneratedKeyColumns("order_id");
    	
    	Map<String, Object> parameter = new HashMap<>();
    	parameter.put("member_id", order.getMemberId());
    	parameter.put("item_id", order.getItemId());
    	parameter.put("user_name", order.getUserName());
    	parameter.put("phone", order.getPhone());
    	parameter.put("address", order.getAddress());
    	parameter.put("request", order.getRequest());
    	parameter.put("price", order.getPrice());
    	parameter.put("orderDate", order.getOrderDate());
    	parameter.put("quantity", order.getQuantity());
    	
    	Number returnKey = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameter));
    	order.setOrderId(returnKey.intValue());
    	
    	return findByOrderId(order.getOrderId());
    }

    public List<Order> findByMemberId(String memberId) {
        String sql = "SELECT * FROM order_list WHERE order_id = ?";
        return jdbcTemplate.query(sql, orderRowMapper, memberId);
    }

    public void deleteById(Long orderId) {
        String sql = "DELETE FROM order_list WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }
} 