package meatmeet.meatmeet.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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
	
	// API 요청
	public String requestApi(String date, String breedingCode, String itemCode) {
		final String BASE_URL = "http://data.ekape.or.kr/openapi-data/service/user/grade/consumerPriceDaily";
		final String API_KEY = "CHhEXQKCoCD9Ig4zworWrj+25Q6PpBcC7giNHu/B88j8wjgPOBnK/Ybd2hhlzIYaLhBdXtw6lPizIeyu9IcNEg==";
		
		String serviceKey = API_KEY;
		String standYmd = date;
		String judgeKind = breedingCode;
		String itemCd = itemCode;
		
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory(BASE_URL);
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
		
		WebClient webclient = WebClient
				.builder()
				.baseUrl(BASE_URL)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
		
		String response = webclient.get()
				.uri(uriBuileder -> uriBuileder
					.queryParam("serviceKey", serviceKey)
					.queryParam("standYmd", standYmd)
					.queryParam("judgeKind", judgeKind)
					.queryParam("itemCd", itemCd)
					.build())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		
		log.info(response.toString());
		
		return response;
	}
	
	public Item parser(String xml) throws JsonMappingException, JsonProcessingException {
		ObjectMapper xmlMapper = new XmlMapper();
		Item item = null;
		
		item = xmlMapper.readValue(xml, Item.class);
		
		return item;
	}
	
	public void requestAndUpdateItemPrice() {
		String[] breedingCodes = {"4301", "4304", "9901", "9903", "9908"};	// 소, 돼지, 닭, 계란, 우유
		String[] cowItemCode = {"21", "22", "36", "40", "50"};				// 안심, 등심, 설도, 양지, 갈비
		String[] pigItemCode = {"25", "27", "28", "68"};
		
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		String today = now.format(formatter);
		
		List<Item> items = new ArrayList<>();
		
		for(String breedingCode: breedingCodes) {
			if(breedingCode.equals("4301")) {
				for(String itemCode: cowItemCode) {
					String xml = this.requestApi(today, breedingCode, itemCode);
//					items.add(item);
				}
			} else if(breedingCode.equals("4304" )) {
				for(String itemCode: pigItemCode) {
					String xml = this.requestApi(today, breedingCode, itemCode);
//					items.add(item);
				}
			} else {
				String xml = this.requestApi(today, breedingCode, today);
//				items.add(item);
			}
		}
		
		itemRepository.updateItem(items);
	}
}
