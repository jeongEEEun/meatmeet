package meatmeet.meatmeet.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceTest {
	
	@Autowired private ItemService itemService;
		
	@Test
	void test() {
		itemService.requestApi();
	}
}