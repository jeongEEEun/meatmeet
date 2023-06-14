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

import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Recipe;

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
		cart.setQuantity(rs.getInt("quantity"));
		cart.setTodayPrice(rs.getInt("today_price"));
		cart.setYesterdayPrice(rs.getInt("yesterday_price"));
		return cart;
		
	};
	
	public List<Recipe> findAll() {
		return jdbcTemplate.query("select * from recipe", recipeRowMapper);
	}
	
	public List<Recipe> findType(String category1) {
		String sql = "select * from recipe where category1 like ?";
		return jdbcTemplate.query(sql, recipeRowMapper, "%"+category1+"%");
	}
	
	public List<Recipe> findPart(String category2) {
		String sql = "select * from recipe where category2 like ?";
		return jdbcTemplate.query(sql, recipeRowMapper, "%"+category2+"%");
	}
	
	public int updateCnt(Long recipeId) {
		String sql = "update recipe set view = view + 1 where recipe_id = ?";
		return jdbcTemplate.update(sql, recipeId);
	}
	
	public int cartAdd(Cart cart) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("cart").usingGeneratedKeyColumns("item_id");
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("member_id", cart.getMemberId());
		parameter.put("quantity", cart.getQuantity());
		parameter.put("today_price", cart.getTodayPrice());
		parameter.put("yesterday_price", cart.getYesterdayPrice());
		
		Number returnKey = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameter));
		cart.setItemId(returnKey.intValue());
		return cart.getItemId();
	}
}