package meatmeet.meatmeet.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Comment {
	private long recipeId;
	@NonNull
	private String memberId;
	private String comment;
	private LocalDateTime commentDate;
	
}
