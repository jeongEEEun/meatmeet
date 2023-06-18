package meatmeet.meatmeet.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ItemServiceTest {
	
	@Autowired private ItemService itemService;
		
	@Test
	void requestApi() {
		String date = "20220630";
		String breedingCode = "4301";
		String itemCode = "21";
		
		itemService.requestApi(date, breedingCode, itemCode);
	}
	
	@Test
	void parser() {
	}
}