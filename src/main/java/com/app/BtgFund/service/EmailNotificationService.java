package com.app.BtgFund.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.domain.UserAccount;

@Service
public class EmailNotificationService implements NotificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationService.class);

    @Override
    public NotificationChannel channel() {
        return NotificationChannel.EMAIL;
    }

    @Override
    public void send(UserAccount user, String message) {
        LOGGER.info("EMAIL to {}: {}", user.getEmail(), message);
    }
}
