package backend.keumbangresource.dto;

import backend.keumbangresource.entity.InvoiceType;
import backend.keumbangresource.entity.Item;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class OrderRequestDto {
	InvoiceType type;
	Item item;
	Double amount;
	Long price;
	String address;
	
	public OrderRequestDto(InvoiceType type, Item item, Double amount, Long price, String address) {
		this.type = type;
		this.item = item;
		this.amount = amount;
		this.price = price;
		this.address = address;
	}
}
