package backend.keumbangresource.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	
	@CreatedDate
	Date orderedAt;
	
	@Column(nullable = false)
	Long userId;
	
	@Column(nullable = false)
	InvoiceType type;
	
	@Column(nullable = false)
	@Builder.Default
	InvoiceState state = InvoiceState.ORDERED;
	
	@Column(nullable = false)
	Item item;
	
	@Column(nullable = false)
	Double amount;
	
	@Column(nullable = false)
	Long price;
	
	@Column(nullable = false)
	String address;
}
