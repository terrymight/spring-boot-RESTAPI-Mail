package net.dipbittech.mailing.entity;

import lombok.Data;

import java.util.List;

@Data
public class SendHtmlRequest {
    private String email;

    private String name;

    private String subject;

    private String templateName = "newCustomer.html";

    private List<Offer> offerings;
}
