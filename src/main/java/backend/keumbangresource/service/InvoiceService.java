package backend.keumbangresource.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import backend.keumbangresource.dto.OrderRequestDto;
import backend.keumbangresource.dto.OrderUpdateRequestDto;
import backend.keumbangresource.entity.Invoice;
import backend.keumbangresource.entity.InvoiceType;
import backend.keumbangresource.repository.InvoiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {
	private final InvoiceRepository invoiceRepository;
	
	public Invoice createOrder(Long userId, OrderRequestDto dto) {
		Invoice invoice = Invoice.builder()
				.userId(userId)
				.type(dto.getType())
				.item(dto.getItem())
				.amount(dto.getAmount())
				.price(dto.getPrice())
				.address(dto.getAddress())
				.build();
		return invoiceRepository.save(invoice);
	}

	@Transactional
	public Invoice updateOrder(Long userId, OrderUpdateRequestDto order) {
		Invoice invoice = invoiceRepository.findById(order.getId())
				.orElseThrow(() -> new IllegalArgumentException("order not found"));
		if (invoice.getUserId() != userId)
			throw new IllegalArgumentException("dont have permission");
		
		invoice.updateState(order.getState());
		return invoice;
	}

	public Page<Invoice> getOrders(Long userId, String type, String start, String end, Pageable pageable) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime startDate = null, endDate = null;
		if (start != null && end != null) {
			startDate = LocalDate.parse(start, dtf).atStartOfDay();
			endDate = LocalDate.parse(end, dtf).atTime(23, 59, 59);
		}
		
		if (type != null) {
			if (start != null && end != null) {
				return invoiceRepository.findByUserIdAndTypeAndOrderedAtBetween(userId, InvoiceType.valueOf(type), startDate, endDate, pageable);
			}
			else {
				return invoiceRepository.findByUserIdAndType(userId, InvoiceType.valueOf(type), pageable);
			}
		} else if (start != null && end != null) {
			return invoiceRepository.findByUserIdAndOrderedAtBetween(userId, startDate, endDate, pageable);
		}
		return invoiceRepository.findByUserId(userId, pageable);
	}
}
