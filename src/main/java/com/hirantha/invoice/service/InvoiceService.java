package com.hirantha.invoice.service;

import com.hirantha.invoice.dto.InvoiceDto;

public interface InvoiceService {

  InvoiceDto generateInvoice(InvoiceDto invoice);

}
