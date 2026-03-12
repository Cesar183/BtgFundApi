package com.app.BtgFund.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.app.BtgFund.domain.NotificationChannel;
import com.app.BtgFund.domain.UserAccount;

@Component
public class NotificationDispatcher {

    private final Map<NotificationChannel, NotificationService> handlers = new EnumMap<>(NotificationChannel.class);

    public NotificationDispatcher(List<NotificationService> services) {
        services.forEach(service -> handlers.put(service.channel(), service));
    }

    public void send(UserAccount user, String message) {
        NotificationService service = handlers.get(user.getPreferredNotificationChannel());
        if (service != null) {
            service.send(user, message);
        }
    }
}
