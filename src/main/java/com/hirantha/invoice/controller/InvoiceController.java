package com.hirantha.invoice.controller;

import com.hirantha.invoice.dto.InvoiceDto;
import com.hirantha.invoice.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invoices")
public class InvoiceController {

  private final InvoiceService invoiceService;

  @PostMapping(value = "/discounts")
  public ResponseEntity<InvoiceDto> generateInvoice(@Valid @RequestBody InvoiceDto invoice) {
    InvoiceDto response = invoiceService.generateInvoice(invoice);
    return ResponseEntity.ok(response);
  }

}
