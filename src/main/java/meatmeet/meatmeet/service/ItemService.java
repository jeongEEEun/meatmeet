package meatmeet.meatmeet.service;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.xml.sax.SAXException;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.repository.ItemRepository;

@Service
@Slf4j
public class ItemService {
	private final ItemRepository itemRepository;
	
	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}
	
	public List<Item> findAllItem() {
		return itemRepository.findAllItem();
	}
	
//	// API 요청
//	public String requestApi(String date, String breedingCode, String itemCode) {
//		final String BASE_URL = "http://data.ekape.or.kr/openapi-data/service/user/grade/consumerPriceDaily";
//		final String API_KEY = "5j/M2hOWcEjVZIPWd1Vv66PbmI14YTUf7NOF0/IuAyen741A6LPwG4cm8w/YkSRFBT1MsT/qXs9eLEjBMAlN6w==";
//		
//		String serviceKey = API_KEY;
//		String standYmd = date;
//		String judgeKind = breedingCode;
//		String itemCd = itemCode;
//		
//		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
//		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
//		
//		WebClient webclient = WebClient
//				.builder()
//				.baseUrl(BASE_URL)
//				.build();
//		
//		String response = webclient.get()
//				.uri(uriBuileder -> uriBuileder
//					.queryParam("serviceKey", serviceKey)
//					.queryParam("standYmd", standYmd)
//					.queryParam("judgeKind", judgeKind)
//					.queryParam("itemCd", itemCd)
//					.build())
//				.retrieve()
//				.bodyToMono(String.class)
//				.block();
//
//		// covid19 데이터 테스트
////		String response = webclient.get()
////				.uri(uriBuileder -> uriBuileder
////						.queryParam("serviceKey", serviceKey)
////						.queryParam("pageNo", "1")
////						.queryParam("numOfRows", "500")
////						.queryParam("apiType", "xml")
////						.queryParam("status_dt", "20200425")
////						.build())
////				.retrieve()
////				.bodyToMono(String.class)
////				.block();
//		
//		return response;
//	}
//	
//	public Item xmlToItemObject(String xml) {
//		JSONObject jsonObj = XML.toJSONObject(xml);
//		log.info("JSONObj >> " + jsonObj);
//		
//		Item item = new Item();
//		
//		return item;
//	}
//
//	@Scheduled(cron = "0 0 9 * * *")
//	public void requestAndUpdateItemPrice() {
//		String[] breedingCodes = {"4301", "4304", "9901", "9903", "9908"};	// 소, 돼지, 닭, 계란, 우유
//		String[] cowItemCode = {"21", "22", "36", "40", "50"};				// 안심, 등심, 설도, 양지, 갈비
//		String[] pigItemCode = {"25", "27", "28", "68"};
//		
//		LocalDate now = LocalDate.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
//		String today = now.format(formatter);
//		
//		List<Item> items = new ArrayList<>();
//		
//		for(String breedingCode: breedingCodes) {
//			if(breedingCode.equals("4301")) {
//				for(String itemCode: cowItemCode) {
//					String xml = this.requestApi(today, breedingCode, itemCode);
//					Item item = this.xmlToItemObject(xml);
//					items.add(item);
//				}
//			} else if(breedingCode.equals("4304")) {
//				for(String itemCode: pigItemCode) {
//					String xml = this.requestApi(today, breedingCode, itemCode);
//					Item item = this.xmlToItemObject(xml);
//					items.add(item);
//				}
//			} else {
//				String xml = this.requestApi(today, breedingCode, today);
//				Item item = this.xmlToItemObject(xml);
//				items.add(item);
//			}
//		}
//		
//		itemRepository.updateItem(items);
//	}
}
