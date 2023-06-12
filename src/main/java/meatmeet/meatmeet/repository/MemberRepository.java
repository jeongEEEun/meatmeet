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
import meatmeet.meatmeet.domain.Member;

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
	
	// 회원 id로 조회
	public Optional<Member> findByMemberId(String memberId) {
		String sql = "select * from member where member_id = ?";
		List<Member> result = jdbcTemplate.query(sql, memberRowMapper, memberId);
		
		return result.stream().findFirst();
	}
	
	// 회원가입(정보 저장)
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
}
