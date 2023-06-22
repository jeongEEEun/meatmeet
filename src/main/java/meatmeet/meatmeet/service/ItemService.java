package meatmeet.meatmeet.service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

<<<<<<< HEAD
=======
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
>>>>>>> origin/aws
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;

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
	
	public List<Item> readCsv() throws IOException {
		String filePath = "C:\\web\\final_project\\축산물 시세.csv";
		List<Item> readCsvItems = new CsvToBeanBuilder<Item>(new FileReader(filePath))
				.withType(Item.class).build().parse();
		
		return readCsvItems;
	}
	
	public void updateItemPrice() throws IOException {
		List<Item> items = this.readCsv();
		itemRepository.updateItem(items);
	}
}
