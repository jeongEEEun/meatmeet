/*
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
		
		recipe.setRecipeId(rs.getLong("recipe_id"));
		recipe.setMemberId(rs.getString("member_id"));
		recipe.setCategory1(rs.getString("category1"));
		recipe.setCategory2(rs.getString("category2"));
		recipe.setTitle(rs.getString("title"));
		recipe.setIngre(rs.getString("ingre"));
		recipe.setSauce(rs.getString("sauce"));
		recipe.setStep(rs.getString("step"));
		recipe.setImgName(rs.getString("img_name"));
		recipe.setImgPath(rs.getString("img_path"));
		
		return recipe;
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
	
	
}
*/