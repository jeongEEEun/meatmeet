package meatmeet.meatmeet.domain;

import com.opencsv.bean.CsvBindByName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Item {
	private int itemId;
	
	@CsvBindByName(column = "item_name")
	private String itemName;
	@CsvBindByName(column = "part")
	private String part;
	@CsvBindByName(column = "item_unit")
	private String itemUnit;
	@CsvBindByName(column = "today_price")
	private int todayPrice;
	@CsvBindByName(column = "yesterday_price")
	private int yesterdayPrice;
	
	private String selector;
	private String imgName;
}
