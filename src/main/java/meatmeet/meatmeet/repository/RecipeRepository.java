package meatmeet.meatmeet.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import meatmeet.meatmeet.domain.Recipe;

@Repository
public class RecipeRepository {
	private final JdbcTemplate jdbcTemplate;
	
	public RecipeRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<Recipe> recipeRowMapper = (rs, rowNum) -> {
		Recipe recipe = new Recipe();
		
		recipe.setRecipe_id(rs.getInt("recipe_id"));
		recipe.setMember_id(rs.getString("member_id"));
		recipe.setCategory1(rs.getString("category1"));
		recipe.setCategory2(rs.getString("category2"));
		recipe.setTitle(rs.getString("title"));
		recipe.setIngre(rs.getString("ingre"));
		recipe.setSauce(rs.getString("sauce"));
		recipe.setStep(rs.getString("step"));
		recipe.setImg_name(rs.getString("img_name"));
		recipe.setImg_path(rs.getString("img_path"));
		
		return recipe;
	};
	
	
	public List<Recipe> findAll() {
		return jdbcTemplate.query("select * from recipe", recipeRowMapper);
	}
	
	public Optional<Recipe> findType(String category1) {
		String sql = "select * from recipe where category1 like ?";
		List<Recipe> result = jdbcTemplate.query(sql, recipeRowMapper, "%"+category1+"%");
		return result.stream().findAny();
	}
	
	public Optional<Recipe> findPart(String category2) {
		String sql = "select * from recipe where category2 like ?";
		List<Recipe> result = jdbcTemplate.query(sql, recipeRowMapper, "%"+category2+"%");
		return result.stream().findAny();
	}
	
	
}
