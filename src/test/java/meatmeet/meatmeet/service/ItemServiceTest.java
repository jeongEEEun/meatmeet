package meatmeet.meatmeet.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
	@Autowired private S3Service s3Service;
		
	@Test
	void UpdateItemPrice() throws IOException {
		itemService.readCsv();
		
		List<Item> result = itemService.findAllItem();
		
		assertThat(result.size()).isEqualTo(12);
	}
	
	@Test
	void getCsv() throws IOException {
		String uploadDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		String fileName = "csv/price" + uploadDate + ".csv";
		
		log.info("fileName >> " + fileName);
		
		s3Service.getCsv(fileName);
	}
}