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
        
        order.setOrderId(rs.getString("order_id"));
        order.setMemberId(rs.getString("member_id"));
        order.setItemId(rs.getInt("item_id"));
        order.setPrice(rs.getInt("price"));
        order.setQuantity(rs.getInt("quantity"));
        order.setUserName(rs.getString("user_name"));
        order.setPhone(rs.getString("phone"));
        order.setAddress(rs.getString("address"));
        order.setRequest(rs.getString("request"));
        order.setOrderDate(rs.getDate("order_date").toLocalDate());
        order.setPayment(rs.getString("payment"));
        
        return order;
    };
    
    public List<Order> findByOrderId(String orderId) {
        String sql = "SELECT * FROM order_list WHERE order_id = ?";
        return jdbcTemplate.query(sql, orderRowMapper, orderId);
    }
    
    public List<Order> findByMemberId(String memberId) {
    	String sql = "SELECT * FROM order_list WHERE order_id = ?";
    	return jdbcTemplate.query(sql, orderRowMapper, memberId);
    }

    public List<Order> save(List<Order> orders) {
    	SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
    			.withTableName("order_list")
    			.usingColumns("order_id", "member_id", "item_id", "price", "quantity", 
    					"user_name", "phone", "address", "request", "order_date", "payment");
    	
    	for(Order order: orders) {
    		Map<String, Object> parameter = new HashMap<>();
    		parameter.put("order_id", order.getOrderId());
    		parameter.put("member_id", order.getMemberId());
    		parameter.put("item_id", order.getItemId());
    		parameter.put("price", order.getPrice());
    		parameter.put("quantity", order.getQuantity());
    		parameter.put("user_name", order.getUserName());
    		parameter.put("phone", order.getPhone());
    		parameter.put("address", order.getAddress());
    		parameter.put("request", order.getRequest());
    		parameter.put("order_date", order.getOrderDate());
    		parameter.put("payment", order.getPayment());
    		
    		jdbcInsert.execute(new MapSqlParameterSource(parameter));
    	}
    	
    	
    	return findByOrderId(orders.get(0).getOrderId());
    }

    public void deleteByOrderId(Long orderId) {
        String sql = "DELETE FROM order_list WHERE order_id = ?";
        jdbcTemplate.update(sql, orderId);
    }
} 