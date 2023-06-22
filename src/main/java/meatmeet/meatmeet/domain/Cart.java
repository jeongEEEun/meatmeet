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
@NoArgsConstructor
public class Cart {
	@NonNull
    private String memberId;
	@NonNull
    private int itemId;
    @NonNull
    private int quantity;
    
    private String itemName;
    private int price;
}
