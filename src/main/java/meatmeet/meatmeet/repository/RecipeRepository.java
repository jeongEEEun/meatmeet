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

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.domain.Recipe;

@Slf4j
@Repository
public class RecipeRepository {
	private final JdbcTemplate jdbcTemplate;

	public RecipeRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private RowMapper<Recipe> recipeRowMapper = (rs, rowNum) -> {
		Recipe recipe = new Recipe();
		recipe.setRecipeId(rs.getLong("recipe_id"));
		recipe.setMemberId(rs.getString("member_id"));
		recipe.setCategory1(rs.getString("category1"));
		recipe.setCategory2(rs.getString("category2"));
		recipe.setTitle(rs.getString("title"));
		recipe.setIngre(rs.getString("ingre"));
		recipe.setSauce(rs.getString("sauce"));
		recipe.setStep(rs.getString("step"));
		recipe.setView(rs.getInt("view"));
		recipe.setImgName(rs.getString("img_name"));
		recipe.setImgPath(rs.getString("img_path"));
		return recipe;
	};

	private RowMapper<Cart> cartRowMapper = (rs, rowNum) -> {
		Cart cart = new Cart();
		cart.setItemId(rs.getInt("item_id"));
		cart.setMemberId(rs.getString("member_id"));
		cart.setItemName(rs.getString("item_name"));
		cart.setQuantity(rs.getInt("quantity"));
		return cart;

	};

	private RowMapper<Item> itemRowMapper = (rs, rowNum) -> {
		Item item = new Item();
		item.setItemId(rs.getInt("item_id"));
		item.setTodayPrice(rs.getInt("todayPrice"));
		item.setYesterdayPrice(rs.getInt("yesterdayPrice"));
		item.setItemName(rs.getString("itemName"));
		item.setItemUnit(rs.getString("itemUnit"));
		return item;

	};

	public List<Recipe> findAll() {
		return jdbcTemplate.query("select * from recipe", recipeRowMapper);
	}

	public List<Recipe> findType(String category1) {
		String sql = "select * from recipe where category1 like ?";
		return jdbcTemplate.query(sql, recipeRowMapper, "%" + category1 + "%");
	}

	public List<Recipe> findPart(String category2) {
		String sql = "select * from recipe where category2 like ?";
		return jdbcTemplate.query(sql, recipeRowMapper, "%" + category2 + "%");
	}

	public int updateCnt(Long recipeId) {
		String sql = "update recipe set view = view + 1 where recipe_id = ?";
		return jdbcTemplate.update(sql, recipeId);
	}

//	public Cart cartAdd(Cart cart) {
//		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("cart").usingColumns("memberId",
//				"itemId", "itemName", "price", "quantity");
//		Map<String, Object> parameter = new HashMap<>();
//		parameter.put("memberId", cart.getMemberId());
//		parameter.put("itemId", cart.getItemId());
//		parameter.put("itemName", cart.getItemName());
//		parameter.put("price", cart.getPrice());
//		parameter.put("quantity", cart.getQuantity());
//		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameter));
//
//		cart.setItemId(key.intValue());
//		return cart;
//	}
//
	public Optional<Item> findItemById(int itemId) {
		List<Item> result = jdbcTemplate.query("select * from item where item_id = ?", itemRowMapper, itemId);
		return result.stream().findFirst();
	}

	public void cartAdd(Cart cart) {
		String sql = "INSERT INTO cart (member_id, item_id, quantity) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, cart.getMemberId(), cart.getItemId(), cart.getQuantity());
	}
}