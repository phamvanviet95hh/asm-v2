package com.example.thanh_toan_asm.dtos.gtelpay_response.create_token;

import java.util.Date;

public class Response{
    public String token;
    public Date expired_at;

    public String getToken() {
        return token;
    }

    public Date getExpired_at() {
        return expired_at;
    }
}