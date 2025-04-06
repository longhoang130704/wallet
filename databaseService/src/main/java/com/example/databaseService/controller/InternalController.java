package com.example.databaseService.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.databaseService.dto.request.internalPayment.InternalRequest;
import com.example.databaseService.service.InternalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/internal-transaction")
public class InternalController {
    private final InternalService internalService;

    public InternalController(InternalService internalService) {
        this.internalService = internalService;
    }

    @PostMapping
    public String proocessInternalTransaction(@Valid @RequestBody InternalRequest internalRequest) {

        internalService.processInternalTransaction(
                internalRequest.getFrom_wallet(),
                internalRequest.getFrom_branch(),
                internalRequest.getTo_wallet(),
                internalRequest.getTo_branch(),
                internalRequest.getAmount(),
                internalRequest.getDescription(),
                internalRequest.getMetadata());

        return "validate success";
    }
}
