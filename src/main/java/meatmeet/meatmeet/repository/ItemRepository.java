package meatmeet.meatmeet.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Item;

@Repository
@Slf4j
public class ItemRepository {
	private final JdbcTemplate jdbcTemplate;
	
	public ItemRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
		Item item = new Item();
		
		item.setItemId(rs.getInt("item_id"));
		item.setTodayPrice(rs.getInt("today_price"));
		item.setYesterdayPrice(rs.getInt("yesterday_price"));
		item.setItemName(rs.getString("item_name"));
		item.setPart(rs.getString("part"));
		item.setItemUnit(rs.getString("item_unit"));
		item.setSelector(rs.getString("selector"));
		item.setImgName(rs.getString("img_name"));
		
		return item;
	};
	
	public List<Item> findAllItem() {
		String sql = "select * from item";
		return jdbcTemplate.query(sql, itemRowMapper);
	}
		
	public void updateItem(List<Item> items) {
		String sql = "update item set today_price = ?, yesterday_price = ? where item_name = ? and part = ?";
		
		for(Item item: items) {
			log.info("item >> " + item.getItemName());
			log.info("item >> " + item.getTodayPrice());
			log.info("--------------------------");
			
			jdbcTemplate.update(sql, 
					item.getTodayPrice(), item.getYesterdayPrice(), 
					item.getItemName(), item.getPart());
		}
	}
}
