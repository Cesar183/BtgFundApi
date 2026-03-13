package com.app.BtgFund.service;

import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.domain.UserAccount;

/**
 * Strategy contract for sending notifications through a specific channel.
 */
public interface NotificationService {

    /**
     * Declares channel handled by this implementation.
     *
     * @return supported channel
     */
    NotificationChannel channel();

    /**
     * Sends message to a user.
     *
     * @param user destination user
     * @param message message content
     */
    void send(UserAccount user, String message);
}
