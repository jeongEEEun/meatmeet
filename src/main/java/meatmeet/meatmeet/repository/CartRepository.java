package meatmeet.meatmeet.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {
    private final JdbcTemplate jdbcTemplate;

    public CartRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 장바구니 아이템의 아이템 이름을 조회합니다.
     *
     * @param itemId 아이템 ID
     * @return 아이템 이름
     */
    public String getItemName(int itemId) {
        String sql = "SELECT item_name FROM item WHERE item_id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, itemId);
    }

    /**
     * 장바구니 아이템의 가격을 조회합니다.
     *
     * @param itemId 아이템 ID
     * @return 가격
     */
    public int getItemPrice(int itemId) {
        String sql = "SELECT today_price FROM item WHERE item_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, itemId);
    }
    /**
     * 장바구니에서 아이템을 삭제합니다.
     */
    public void removeFromCart(String memberId, int itemId) {
        String sql = "DELETE FROM cart WHERE member_id = ? AND item_id = ?";
        jdbcTemplate.update(sql, memberId, itemId);
    }

    /**
     * 장바구니에 담긴 아이템의 총 수량을 조회합니다.
     */
    public int getTotalQuantityInCart(String memberId) {
        String sql = "SELECT SUM(quantity) FROM cart WHERE member_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, memberId);
    }
}


