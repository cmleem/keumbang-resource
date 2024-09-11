package backend.keumbangresource.dto;

import java.time.LocalDateTime;

import backend.keumbangresource.entity.Invoice;
import backend.keumbangresource.entity.InvoiceType;
import backend.keumbangresource.entity.Item;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceResponseDto {
	String id;
	LocalDateTime orderdAt;
	Long userId;
	InvoiceType type;
	String state;
	Item item;
	Double amount;
	Long price;
	String address;
	
	public static InvoiceResponseDto toDto(Invoice invoice) {
		String state = null;
		switch(invoice.getState()) {
		case ORDERED:
			state = "주문 완료";
			break;
		case DEPOSITED:
			state = invoice.getType() == InvoiceType.BUY ? "입금 완료" : "송금 완료";
			break;
		case DELIVERED:
			state = invoice.getType() == InvoiceType.BUY ? "발송 완료" : "수령 완료";
		}
		
		return InvoiceResponseDto.builder()
				.id(invoice.getId())
				.orderdAt(invoice.getOrderedAt())
				.userId(invoice.getUserId())
				.type(invoice.getType())
				.state(state)
				.item(invoice.getItem())
				.amount(invoice.getAmount())
				.price(invoice.getPrice())
				.address(invoice.getAddress())
				.build();
	}
}
