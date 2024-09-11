package backend.keumbangresource.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Builder
@Getter
public class Invoice {
	@Id
	String id;
	
	LocalDateTime orderedAt;
	
	@Column(nullable = false)
	Long userId;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	InvoiceType type;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@Builder.Default
	InvoiceState state = InvoiceState.ORDERED;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	Item item;
	
	@Column(nullable = false)
	Double amount;
	
	@Column(nullable = false)
	Long price;
	
	@Column(nullable = false)
	String address;
	
	public Invoice(String id, LocalDateTime orderedAt, Long userId, InvoiceType type,
			InvoiceState state, Item item, Double amount, Long price, String address) {
		this.orderedAt = LocalDateTime.now();
		this.id = type.toString() + "_" + userId + "_"+ this.getOrderedAt();
		this.userId = userId;
		this.type = type;
		this.state = state;
		this.item = item;
		this.amount = amount;
		this.price = price;
		this.address = address;
	}
	
	public void updateState(InvoiceState newState) {
		this.state = newState;
	}
}
