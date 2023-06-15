package meatmeet.meatmeet.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Item;

@Repository
@Slf4j
public class CartRepository {
    private final JdbcTemplate jdbcTemplate;

    public CartRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    RowMapper<Cart> cartRowMapper = (rs, rowNum) -> {
    	Cart cart = new Cart();
    	
    	cart.setMemberId(rs.getString("member_id"));
    	cart.setItemId(rs.getInt("item_id"));
    	cart.setQuantity(rs.getInt("quantity"));
    	
    	return cart;
    };
    
    RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
    	Item item = new Item();
    	
    	item.setItemId(rs.getInt("item_id"));
    	item.setItemName(rs.getString("item_name"));
    	item.setTodayPrice(rs.getInt("today_price"));
    	item.setYesterdayPrice(rs.getInt("yesterday_price"));
    	item.setItemUnit(rs.getString("item_unit"));
    	
    	return item;
    };
    
    public List<Cart> findCartByMemberId(String memberId) {
    	String sql = "select * from cart where member_id = ?";
    	List<Cart> cartItems = jdbcTemplate.query(sql, cartRowMapper, memberId);
    	
    	for(Cart cart: cartItems) {
    		Optional<Item> i = this.findByItemId(cart.getItemId());
    		
    		if(i.isPresent()) {
    			Item item = i.get();
    			
    			cart.setItemName(item.getItemName());
    			cart.setPrice(item.getTodayPrice());
    		}
    	}
    	
    	return cartItems;
    }
    
    public Optional<Item> findByItemId(int itemId) {
    	String sql = "select * from item where item_id = ?";
    	Optional<Item> item = jdbcTemplate.query(sql, itemRowMapper, itemId).stream().findAny();
    	return item;
    }
    
    public void updateQuantity(String memberId, int itemId, int quantity) {
    	String sql = "update cart set quantity = quantity + ? where member_id = ? and item_id = ?";
    	jdbcTemplate.update(sql, itemId, memberId, quantity);
    }
    
    public void resetCart(String memberId) {
    	jdbcTemplate.update("delete from cart where member_id = ?", memberId);
    }
}