package meatmeet.meatmeet.repository;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Item;

@Repository
public class CartRepository {
    private final JdbcTemplate jdbcTemplate;

    public CartRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    private RowMapper<Cart> cartRowMapper = (rs, rowNum) -> {
    	Cart cart = new Cart();
    	
    	cart.setMemberId(rs.getString("member_id"));
    	cart.setItemId(rs.getInt("item_id"));
    	cart.setQuantity(rs.getInt("quantity"));
    	
    	return cart;
    };
    private RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
    	Item item = new Item();
    	
    	item.setItemName(rs.getString("item_name"));
    	item.setTodayPrice(rs.getInt("item_today_price"));
		return item;
    };
    
    public List<Cart> findByMemberId(String memberId) {
        String sql = "SELECT * FROM cart WHERE member_id = ?";
        List<Cart> result = jdbcTemplate.query(sql, cartRowMapper, memberId);
        return result;
    }
    
    public List<Item> getItemName(String memberId) {
        List<Cart> cartList = findByMemberId(memberId);
        List<Item> itemNameList = new ArrayList<>();

        for (Cart cart : cartList) {
            int itemId = cart.getItemId();
            String sql = "SELECT item_name FROM item WHERE item_id = ?";
            List<Item> itemName = jdbcTemplate.query(sql, itemRowMapper, itemId);
            itemNameList.addAll(itemName);
        }

        return itemNameList;
    }
    public List<Item> getItemPrice(String memberId) {
        List<Cart> cartList = findByMemberId(memberId);
        List<Item> itemTodayPriceList = new ArrayList<>();

        for (Cart cart : cartList) {
            int itemId = cart.getItemId();
            String sql = "SELECT today_price FROM item WHERE item_id = ?";
            List<Item> itemTodayPrice = jdbcTemplate.query(sql, itemRowMapper, itemId);
            itemTodayPriceList.addAll(itemTodayPrice);
        }

        return itemTodayPriceList;
    }
    
}

