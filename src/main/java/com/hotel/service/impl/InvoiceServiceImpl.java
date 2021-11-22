package com.hotel.service.impl;

import com.hotel.model.dao.InvoiceRepository;
import com.hotel.model.entity.Invoice;
import com.hotel.model.entity.OrderBooking;
import com.hotel.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {
    InvoiceRepository invoiceRepository;

    @Override
    public List<Invoice> getAll() {
        return invoiceRepository.findAll();
    }

}


