package backend.keumbangresource.service;

import org.springframework.stereotype.Service;

import backend.keumbangresource.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InvoiceService {
	private final InvoiceRepository invoiceRepository;
	
	
}
