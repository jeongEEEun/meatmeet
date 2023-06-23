package meatmeet.meatmeet.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Item;

@SpringBootTest
@Slf4j
class ItemServiceTest {
	
	@Autowired private ItemService itemService;

		
	@Test
	void UpdateItemPrice() throws IOException {
		Item beforeItem = itemService.findAllItem().get(0);
		itemService.readCsv();
		
		Item afterItem = itemService.findAllItem().get(0);
		
		assertThat(beforeItem.getYesterdayPrice()).isEqualTo(afterItem.getTodayPrice());
	}
}