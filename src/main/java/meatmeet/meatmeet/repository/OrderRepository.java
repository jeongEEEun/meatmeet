package meatmeet.meatmeet.repository;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Order;

@Repository
@Slf4j
public class OrderRepository {
    private final JdbcTemplate jdbcTemplate;
    
    public OrderRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    RowMapper<Order> OrderInfoRowMapper = (rs, rowNum) -> {
    	Order orderInfo = new Order();
    	
    	orderInfo.setOrderId(rs.getString("order_id"));
    	orderInfo.setMemberId(rs.getString("member_id"));
    	orderInfo.setUserName(rs.getString("user_name"));
    	orderInfo.setPhone(rs.getString("phone"));
    	orderInfo.setAddress(rs.getString("address"));
    	orderInfo.setRequest(rs.getString("request"));
    	orderInfo.setOrderDate(rs.getDate("order_date").toLocalDate());
    	orderInfo.setPayment(rs.getString("payment"));
    	orderInfo.setTotalPrice(rs.getInt("total_price"));
    	
    	return orderInfo;
    };
    
    RowMapper<Order> orderItemRowMapper = (rs, rowNum) -> {
    	Order orderItem = new Order();
    	
    	orderItem.setOrderId(rs.getString("order_id"));
    	orderItem.setItemId(rs.getInt("item_id"));
    	orderItem.setItemName(rs.getString("item_name"));
    	orderItem.setPrice(rs.getInt("price"));
    	orderItem.setQuantity(rs.getInt("quantity"));
    	
    	return orderItem;
    };
    
    public void saveOrder(List<Order> orders) {
    	SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
    			.withTableName("orders");
    	
    	for(Order order: orders) {
    		Map<String, Object> parameter = new HashMap<>();
    		
        	log.info("[repository] orderId >> " + order.getOrderId());
        	log.info("[repository] OrderDate >> " + order.getOrderDate());
        	log.info("[repository] OrderAddress >> " + order.getAddress());
    		
    		parameter.put("order_id", order.getOrderId());
    		parameter.put("member_id", order.getMemberId());
    		parameter.put("user_name", order.getUserName());
    		parameter.put("phone", order.getPhone());
    		parameter.put("address", order.getAddress());
    		parameter.put("request", order.getRequest());
    		parameter.put("order_date", order.getOrderDate());
    		parameter.put("payment", order.getPayment());
    		parameter.put("totalPrice", order.getTotalPrice());
    		parameter.put("item_id", order.getItemId());
    		parameter.put("item_name", order.getItemName());
    		parameter.put("quantity", order.getQuantity());
    		parameter.put("price", order.getPrice());
    		
    		jdbcInsert.execute(new MapSqlParameterSource(parameter));
    	}
    }
    
    public List<Order> findByMemberId(String memberId) {
    	String sql = "select * from orders where member_id = ?";
    	List<Order> orderInfo = jdbcTemplate.query(sql, OrderInfoRowMapper, memberId);
    	
    	for(int i=0; i<orderInfo.size() -1 ; i++) {
    		if(orderInfo.get(i).getOrderId().equals(orderInfo.get(i + 1).getOrderId())) {
    			orderInfo.remove(i + 1);
    		}
    	}
    	
    	return orderInfo;
    }
    
    public List<Order> findByOrderId(String orderId) {
    	String sql = "select * from orders where order_id = ?";
    	return jdbcTemplate.query(sql, orderItemRowMapper, orderId);
    }
    
    public void deleteOrder(String orderId) {
    	String sql = "delete from orders where order_id = ?";
    	jdbcTemplate.update(sql, orderId);
    }
}