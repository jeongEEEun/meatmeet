package meatmeet.meatmeet.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Item;

@SpringBootTest
@Slf4j
class ItemServiceTest {
	
	@Autowired private ItemService itemService;
		
	@Test
	void readCsv() throws IOException {
		List<Item> result = itemService.readCsv();
		assertThat(result.size()).isEqualTo(12);
	}
	
	@Test
	void updateItemPrice() throws IOException {
		itemService.updateItemPrice();
		
		List<Item> result = itemService.findAllItem();
		
		assertThat(result.get(0).getTodayPrice()).isNotEqualTo(1000);
	}
}