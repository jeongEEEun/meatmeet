package meatmeet.meatmeet.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Recipe {
	private long recipeId;
	private String memberId;
	private String category1;
	private String category2;
	private String title;
	private String ingre;
	private String sauce;
	private String step;
	private String imgName;
	private String imgPath;
}