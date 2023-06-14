package meatmeet.meatmeet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Item {
	private int itemId;
	private int todayPrice;
	private int yesterdayPrice;
	private String itemName;
	private String itemUnit;
}