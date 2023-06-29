package meatmeet.meatmeet.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import meatmeet.meatmeet.domain.Cart;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.domain.Member;
import meatmeet.meatmeet.domain.Order;

class OrderServiceTest {

	@Autowired private OrderService orderService;
	@Autowired private RecipeService recipeService;
	
	@Test
	void saveOrderTest() {
		Member member = new Member("홍길동", "h@mail.com", "hong", "1111");
		
		recipeService.cartAdd(new Cart("hong", 1, 1));
		recipeService.cartAdd(new Cart("hong", 2, 1));
		recipeService.cartAdd(new Cart("hong", 3, 1));
		
		Order order = new Order();
		order.setMemberId("hong");
		order.setUserName("홍길동");
		order.setPhone("010-0000-0000");
		order.setAddress("XX시 XX구 XX로1234");
		order.setRequest("문 앞에 놔주세요");
		order.setPayment("toss");
		
		orderService.saveOrder(order);
		
		List<Order> resultItem = orderService.findOrderItemByMemberId("hong");
	}

}
