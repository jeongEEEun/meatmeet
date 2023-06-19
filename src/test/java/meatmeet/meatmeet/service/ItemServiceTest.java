package meatmeet.meatmeet.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

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
	void xmlToJson() {
		String date = "20220630";
		String breedingCode = "4301";
		String itemCode = "21";
		
		String xml = itemService.requestApi(date, breedingCode, itemCode);
		itemService.xmlToItemObject(xml);
		
	}
	
//	@Test
//	void requestTestCovid19() {
//		String xml = itemService.requestApi();
//		itemService.xmlToItemObject(xml);
//	}
	
}