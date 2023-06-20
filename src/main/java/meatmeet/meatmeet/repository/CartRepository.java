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
    	item.setPart(rs.getString("part"));
    	item.setTodayPrice(rs.getInt("today_price"));
    	item.setYesterdayPrice(rs.getInt("yesterday_price"));
    	item.setItemUnit(rs.getString("item_unit"));
    	
    	return item;
    };

    public List<Cart> findCartByMemberId(String memberId) {
    	String sql = "select * from cart where member_id = ?";
    	List<Cart> cartItems = jdbcTemplate.query(sql, cartRowMapper, memberId);
    	
    	for(Cart cart: cartItems) {
    		Optional<Item> item = this.findByItemId(cart.getItemId());
    		
    		if(item.isPresent()) {
    			Item i = item.get();
    			String itemName = String.format("%s(%s)/%s", i.getItemName(), i.getPart(), i.getItemUnit());
    			
    			cart.setItemName(itemName);
    			cart.setPrice(item.get().getTodayPrice());
    			
    			log.info("[cartRepository] itemName >> " + cart.getItemName());
    		}
    	}

    	return cartItems;
    }
    
    public Optional<Item> findByItemId(int itemId) {
    	String sql = "select * from item where item_id = ?";
    	Optional<Item> item = jdbcTemplate.query(sql, itemRowMapper, itemId).stream().findAny();
    	return item;
    }
    
    public int totalPrice(String memberId) {
    	List<Cart> cartItems = findCartByMemberId(memberId);
    	int totalPrice = 0;
    	
    	for(Cart cart: cartItems) {
    		totalPrice += cart.getPrice() * cart.getQuantity();
    	}
    	return totalPrice;
    }
    
    public void updateQuantity(String memberId, int itemId, int quantity) {
    	String sql = "update cart set quantity = quantity + ? where member_id = ? and item_id = ?";
    	jdbcTemplate.update(sql, itemId, memberId, quantity);
    }
    
    public void resetCart(String memberId) {
    	jdbcTemplate.update("delete from cart where member_id = ?", memberId);
    }
}
