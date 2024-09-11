package backend.keumbangresource.dto;

import backend.keumbangresource.entity.InvoiceState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateRequestDto {
	private String id;
	private InvoiceState state;
}
