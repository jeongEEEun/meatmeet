package meatmeet.meatmeet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
	private Long recipeId;
	
	@NonNull
	private String memberId;
	@NonNull
	private String category1;
	@NonNull
	private String category2;
	@NonNull
	private String title;
	@NonNull
	private String ingre;
	@NonNull
	private String sauce;
	@NonNull
	private String step;
	
	private String imgName;
	private String imgPath;
}