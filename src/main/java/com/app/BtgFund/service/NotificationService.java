package com.app.BtgFund.service;

import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.domain.UserAccount;

public interface NotificationService {

    NotificationChannel channel();

    void send(UserAccount user, String message);
}
