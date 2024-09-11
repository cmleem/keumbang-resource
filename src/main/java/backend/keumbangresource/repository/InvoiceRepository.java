package backend.keumbangresource.repository;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import backend.keumbangresource.entity.Invoice;
import backend.keumbangresource.entity.InvoiceType;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {
	Page<Invoice> findByUserId(Long userId, Pageable pageable);
	Page<Invoice> findByUserIdAndType(Long userId, InvoiceType type, Pageable pageable);
	Page<Invoice> findByUserIdAndOrderedAtBetween(Long userId, LocalDateTime start, LocalDateTime end, Pageable pageable);
	Page<Invoice> findByUserIdAndTypeAndOrderedAtBetween(Long userId, InvoiceType type, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
