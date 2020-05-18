package org.acgcloud.account.dto;

import lombok.Data;

@Data
public class AccountRequest {
    private String account;
    private String mail;
    private String password;
    private String cookie;
}
