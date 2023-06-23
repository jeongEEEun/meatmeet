package meatmeet.meatmeet.service;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

import lombok.extern.slf4j.Slf4j;
import meatmeet.meatmeet.domain.Item;
import meatmeet.meatmeet.repository.ItemRepository;

@Service
@Slf4j
public class ItemService {
	private final ItemRepository itemRepository;
	private final S3Service s3Service;
	
	public ItemService(ItemRepository itemRepository, S3Service s3Service) {
		this.itemRepository = itemRepository;
		this.s3Service = s3Service;
	}
	
	public List<Item> findAllItem() {
		return itemRepository.findAllItem();
	}

	@Scheduled(cron = "0 0 9 * * *")
	public void readCsv() throws IOException {
		String uploadDate = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
		String fileName = "csv/price" + uploadDate + ".csv";
		String csv = s3Service.getCsv(fileName);
		
		List<Item> readCsvItems = new CsvToBeanBuilder<Item>(new StringReader(csv))
				.withType(Item.class).build().parse();
		
		itemRepository.updateItem(readCsvItems);
		
		log.info("[ItemService] 상품 가격 업데이트 >> " + uploadDate);
	}
}
