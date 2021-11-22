package com.hotel.controller;

import com.hotel.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class InvoiceController {
    private InvoiceService invoiceService;

    @GetMapping(value = "/invoices")
    public String invoices(Model model) {
        var invoices = invoiceService.getAll();
        model.addAttribute("invoices", invoices);
        return "invoices";
    }
}
