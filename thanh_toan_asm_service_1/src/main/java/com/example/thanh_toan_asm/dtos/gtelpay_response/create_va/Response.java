package com.example.thanh_toan_asm.dtos.gtelpay_response.create_va;

import java.util.Date;

public class Response {
    public String account_number;
    public String account_name;
    public String status;
    public Date start_at;
    public String expire_at;
    public int balance;
    public String account_type;
    public int max_amount;
    public int min_amount;
    public int equal_amount;
    public int number_of_transfer;
    public String signature;
    public String qr_code;
    public String qr_code_image;
}
