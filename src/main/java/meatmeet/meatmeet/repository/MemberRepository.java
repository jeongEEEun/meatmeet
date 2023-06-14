package meatmeet.meatmeet.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.data.repository.query.ReturnedType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Recipe;

@Repository
@Slf4j
public class MemberRepository {
	private final JdbcTemplate jdbcTemplate;
	
	public MemberRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private RowMapper<Member> memberRowMapper = (rs, rowNum) -> {
		Member member = new Member();
		
		member.setMemberName(rs.getString("member_name"));
		member.setMemberEmail(rs.getString("member_email"));
		member.setMemberId(rs.getString("member_id"));
		member.setMemberPw(rs.getString("member_pw"));
		
		return member;
	};
	
	private RowMapper<Recipe> recipeRowMapper = (rs, rowNum) -> {
		Recipe recipe = new Recipe();
		
		recipe.setRecipeId(rs.getLong("recipe_id"));
		recipe.setMemberId(rs.getString("member_id"));
		recipe.setCategory1(rs.getString("category1"));
		recipe.setCategory1(rs.getString("category2"));
		recipe.setTitle(rs.getString("title"));
		recipe.setIngre(rs.getString("ingre"));
		recipe.setSauce(rs.getString("sauce"));
		recipe.setStep(rs.getString("step"));
		recipe.setImgName(rs.getString("img_name"));
		recipe.setImgPath(rs.getString("img_path"));
		
		return recipe;
	};
	
	public Optional<Member> findByMemberId(String memberId) {
		String sql = "select * from member where member_id = ?";
		List<Member> result = jdbcTemplate.query(sql, memberRowMapper, memberId);
		return result.stream().findFirst();
	}
	
	public Optional<Member> saveMember(Member member) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("member")
				.usingColumns("member_name", "member_email", "member_id", "member_pw");
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("member_name", member.getMemberName());
		parameter.put("member_email", member.getMemberEmail());
		parameter.put("member_id", member.getMemberId());
		parameter.put("member_pw", member.getMemberPw());
		
		jdbcInsert.execute(new MapSqlParameterSource(parameter));
		
		return findByMemberId(member.getMemberId());
	}
	
	public Long saveRecipe(Recipe recipe) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("recipe")
				.usingGeneratedKeyColumns("recipe_id");
		
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("member_id", recipe.getMemberId());
		parameter.put("category1", recipe.getCategory1());
		parameter.put("category2", recipe.getCategory2());
		parameter.put("title", recipe.getTitle());
		parameter.put("ingre", recipe.getIngre());
		parameter.put("sauce", recipe.getSauce());
		parameter.put("step", recipe.getStep());
		parameter.put("img_name", recipe.getImgName());
		parameter.put("img_path", recipe.getImgPath());
		
		Number returnKey = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameter));
		recipe.setRecipeId(returnKey.longValue());
		
		return recipe.getRecipeId();
	}
	
	public List<Recipe> findRecipeByMemberId(String memberId) {
		String sql = "select * from recipe where member_id = ?"; 
		return jdbcTemplate.query(sql, recipeRowMapper, memberId);
	}
	
	public Optional<Recipe> findByRecipeId(Long recipeId) {
		String sql = "select * from item where item_id = ?";
		return jdbcTemplate.query(sql, recipeRowMapper,recipeId).stream().findFirst();
	}
	
	public Optional<Recipe> updateRecipe(Recipe recipe) {
		String sql = "update recipe set title = ?, ingre = ?, sauce = ?, step = ? where recipe_id = ?";
		jdbcTemplate.update(sql, recipe.getTitle(), recipe.getIngre(), recipe.getSauce(), recipe.getStep(), recipe.getRecipeId());
		return findByRecipeId(recipe.getRecipeId());
	}
	
	public void deleteRecipe(Long recipeId) {
		String sql = "delete from recipe where recipe_id = ?";
		jdbcTemplate.update(sql, recipeId);
	}
}
