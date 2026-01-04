package com.development.expense.service;

import com.development.expense.dto.ApiResponse;
import com.development.expense.rest.TelegramClient;
import com.development.expense.rest.dto.SentOTPRequest;
import org.springframework.stereotype.Service;

@Service
public class VerificationService {
    private final TelegramClient telegramClient;

    public VerificationService(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public ApiResponse sendOTP(SentOTPRequest request) {
        return telegramClient.sendOTP(request);
    }
}
