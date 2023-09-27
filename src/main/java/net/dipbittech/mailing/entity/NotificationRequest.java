package net.dipbittech.mailing.entity;

import lombok.Data;

@Data
public class NotificationRequest {
    private String email;

    private String message;

    private String subject;
}
