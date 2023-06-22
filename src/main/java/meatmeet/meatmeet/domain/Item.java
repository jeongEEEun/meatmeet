package meatmeet.meatmeet.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Item {
	private int itemId;
	
	@NonNull
	private int todayPrice;
	@NonNull
	private int yesterdayPrice;
	@NonNull
	private String itemName;
	@NonNull
	private String part;
	@NonNull
	private String itemUnit;
	@NonNull
	private String selector;
	
	private String imgName;
}
