package meatmeet.meatmeet.service;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

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
	
	public void requestApi() {
		final String BASE_URL = "http://data.ekape.or.kr/openapi-data/service/user/grade/consumerPriceDaily";
		final String API_KEY = "CHhEXQKCoCD9Ig4zworWrj+25Q6PpBcC7giNHu/B88j8wjgPOBnK/Ybd2hhlzIYaLhBdXtw6lPizIeyu9IcNEg==";
		
		String serviceKey = API_KEY;
		String standYmd = "20220630";
		String judgeKind = "4301";
		String itemCd = "21";
		
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
	}
}
