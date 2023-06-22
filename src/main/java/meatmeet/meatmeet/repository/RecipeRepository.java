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
import meatmeet.meatmeet.domain.Comment;
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
		recipe.setImgPath(rs.getString("img_path"));
		return recipe;
	};

	private RowMapper<Cart> cartRowMapper = (rs, rowNum) -> {
		Cart cart = new Cart();
		cart.setItemId(rs.getInt("item_id"));
		cart.setMemberId(rs.getString("member_id"));
		cart.setQuantity(rs.getInt("quantity"));
		return cart;
	};

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

	private RowMapper<Comment> commentRowMapper = (rs, rowNum) -> {
		Comment comment = new Comment();
		comment.setRecipeId(rs.getLong("recipe_id"));
		comment.setMemberId(rs.getString("member_id"));
		comment.setComment(rs.getString("comment"));
		return comment;
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

	public Optional<Recipe> findRecipeById(Long recipeId) {
		String sql = "select * from recipe where recipe_id = ?";
		return jdbcTemplate.query(sql, recipeRowMapper, recipeId).stream().findFirst();
	}

	public int updateCnt(Long recipeId) {
		String sql = "update recipe set view = COALESCE(view, 0) + 1 where recipe_id = ?";
		return jdbcTemplate.update(sql, recipeId);
	}

	public Optional<Item> findItemById(int itemId) {
		List<Item> result = jdbcTemplate.query("select * from item where item_id = ?", itemRowMapper, itemId);
		return result.stream().findFirst();
	}

	public void cartAdd(Cart cart) {
		String sql = "insert into cart (member_id, item_id, quantity) values (?, ?, ?)";
		int quantity = cart.getQuantity() > 0 ? cart.getQuantity() : 1;
		jdbcTemplate.update(sql, cart.getMemberId(), cart.getItemId(), quantity);
	}

	public List<Item> findItemAll() {
		String sql = "select * from item";
		return jdbcTemplate.query(sql, itemRowMapper);
	}

	public boolean itemExist(String memberId, int itemId) {
		String sql = "select count(*) from cart where member_id = ? and item_id = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, memberId, itemId);
		return count > 0;
	}

	public void saveComment(Comment comment) {
		log.info("repository");
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("comment")
				.usingColumns("recipe_id", "member_id", "comment");
		
		log.info("repository");
				
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("recipe_id", comment.getRecipeId());
		parameter.put("member_id", comment.getMemberId());
		parameter.put("comment", comment.getComment());
		log.info("repository");
		
		jdbcInsert.execute(new MapSqlParameterSource(parameter));
		log.info("repository");
	}
	
	public List<Comment> findCommentByRecipeId(Long recipeId) {
		String sql = "select * from comment where recipe_id = ?";
		return jdbcTemplate.query(sql, commentRowMapper, recipeId);
	}
	
	public void deleteCommentsByRecipeId(Long recipeId) {
		String sql = "delete from comment where recipe_id = ?";
		jdbcTemplate.update(sql, recipeId);
		
	}
}