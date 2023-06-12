package meatmeet.meatmeet.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Member {
	private String memberName;
	private String memberEmail;
	
	@NonNull
	private String memberId;
	@NonNull
	private String memberPw;
}
