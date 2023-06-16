package meatmeet.meatmeet.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
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
    
    RowMapper<Order> orderItemRowMapprt = (rs, rowNum) -> {
    	Order orderItem = new Order();
    	
    	orderItem.setOrderId(rs.getString("order_id"));
    	orderItem.setItemId(rs.getInt("item_id"));
    	orderItem.setItemName(rs.getString("item_name"));
    	orderItem.setPrice(rs.getInt("price"));
    	orderItem.setQuantity(rs.getInt("quantity"));
    	
    	return orderItem;
    };
    
    public void saveOrderInfo(Order order) {
    	SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
    			.withTableName("order_info");
    			
    	Map<String, Object> parameter = new HashMap<>();
    	parameter.put("order_id", order.getOrderId());
    	parameter.put("member_id", order.getMemberId());
    	parameter.put("user_name", order.getUserName());
    	parameter.put("phone", order.getPhone());
    	parameter.put("address", order.getAddress());
    	parameter.put("request", order.getRequest());
    	parameter.put("order_date", order.getOrderDate());
    	parameter.put("payment", order.getPayment());
    	parameter.put("totalPrice", order.getTotalPrice());
    	
    	jdbcInsert.execute(new MapSqlParameterSource(parameter));
    }
    
    public void saveOrderItem(String orderId, List<Cart> orderItems) {
    	SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
    			.withTableName("order_item");
    	
    	for(Cart orderItem: orderItems) {
    		Map<String, Object> parameter = new HashMap<>();
    		parameter.put("order_id", orderId);
    		parameter.put("item_id", orderItem.getItemId());
    		parameter.put("item_name", orderItem.getItemName());
    		parameter.put("quantity", orderItem.getQuantity());
    		parameter.put("price", orderItem.getPrice());
    		
    		jdbcInsert.execute(new MapSqlParameterSource(parameter));
    	}
    }
    
    public List<Order> findByMemberId(String memberId) {
    	String sql = "select * from order_info where member_id = ?";
    	return jdbcTemplate.query(sql, OrderInfoRowMapper, memberId);
    }
    
    public List<Order> findByOrderId(String orderId) {
    	String sql = "select * from order_item where order_id = ?";
    	return jdbcTemplate.query(sql, orderItemRowMapprt, orderId);
    }
} 