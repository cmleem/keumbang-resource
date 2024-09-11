package backend.keumbangresource.controller;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import backend.keumbangresource.dto.InvoiceResponseDto;
import backend.keumbangresource.dto.OrderRequestDto;
import backend.keumbangresource.dto.OrderUpdateRequestDto;
import backend.keumbangresource.entity.Invoice;
import backend.keumbangresource.service.InvoiceService;
import backend.keumbangresource.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@RestController
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class InvoiceController {
	
	private final InvoiceService invoiceService; 
	
	@GetMapping("/")
	public ResponseEntity<?> get(@AuthenticationPrincipal CustomUserDetails principal) {
		return ResponseEntity.ok(principal.getUsername());
	}
	
	@PostMapping("/order")
	public ResponseEntity<?> order(@AuthenticationPrincipal CustomUserDetails principal,
			@RequestBody OrderRequestDto order){
		var invoice = invoiceService.createOrder(principal.getUserId(), order);
		return ResponseEntity.ok(InvoiceResponseDto.toDto(invoice));
	}
	
	@PatchMapping("/order")
	public ResponseEntity<?> updateOrder(@AuthenticationPrincipal CustomUserDetails principal,
			@RequestBody OrderUpdateRequestDto order){
		var invoice = invoiceService.updateOrder(principal.getUserId(), order);
		return ResponseEntity.ok(InvoiceResponseDto.toDto(invoice));
	}
	
	@GetMapping("/order")
	public ResponseEntity<?> getOrders(@AuthenticationPrincipal CustomUserDetails principal,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "start", required = false) String start,
			@RequestParam(value = "end", required = false) String end,
			@PageableDefault(size = 10, direction = Direction.DESC, sort = "id") Pageable pageable){
		Page<Invoice> invoices = invoiceService.getOrders(principal.getUserId(), type, start, end, pageable);
		
		var dtos = invoices.map(InvoiceResponseDto::toDto);
		return ResponseEntity.ok(dtos);
	}
}
