package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.BaseResponse;
import com.example.thanh_toan_asm.dtos.CreateVAResponse;
import com.example.thanh_toan_asm.dtos.GlobalValue;
import com.example.thanh_toan_asm.dtos.gtelpay_request.CreateVirtualAccountRequest;
import com.example.thanh_toan_asm.dtos.gtelpay_response.create_token.CreateTokenResponse;
import com.example.thanh_toan_asm.dtos.gtelpay_response.create_va.CreateVirtualAccountResponse;
import com.example.thanh_toan_asm.dtos.qrcode.QRCodeGenerator;
import com.example.thanh_toan_asm.entitys.UserBearerToken;
import com.example.thanh_toan_asm.network.OkHttpClientSingleton;
import com.example.thanh_toan_asm.repositorys.UserBearerTokenRepository;
import com.example.thanh_toan_asm.signature.RSADigitalSignature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.zxing.WriterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Slf4j
public class AsmService {

    @Autowired
    UserBearerTokenRepository userBearerTokenRepository;
    private Boolean success;
    private String message;
    int statusCode = 0;
    OkHttpClientSingleton client = OkHttpClientSingleton.getInstance();
    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBearerToken createAuthenticationToken(String merchantCode, String password) {
        JsonObject jsonRequestBody = new JsonObject();
        jsonRequestBody.addProperty("merchant_code", merchantCode);
        jsonRequestBody.addProperty("password", password);
        MediaType JSON = MediaType.get("application/json");
        RequestBody body = RequestBody.create(jsonRequestBody.toString(), JSON);
        CreateTokenResponse tokenResponse = null;
        UserBearerToken userBearerToken = userBearerTokenRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("No data in table"));
        try {
            if (userBearerToken.getExpired_at().compareTo(LocalDateTime.now()) < 0) {
                String response = client.post(GlobalValue.apiCreateToken, body, null);
                tokenResponse = objectMapper.readValue(response, CreateTokenResponse.class);
                userBearerToken.setToken(tokenResponse.response.token);
                userBearerToken.setCreateAt(LocalDateTime.now());
                userBearerToken.setExpired_at(
                        tokenResponse.response.expired_at.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                userBearerToken.setUpdateAt(LocalDateTime.now());
                userBearerTokenRepository.save(userBearerToken);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userBearerToken;
    }

    public String createQrBank(CreateVirtualAccountResponse createVirtualAccountResponse,String description) throws IOException, WriterException, WriterException {
        String amountLength="";
        String descriptionLength="";

        if(String.valueOf(createVirtualAccountResponse.response.equal_amount).length()<10){
            amountLength="0"+String.valueOf(createVirtualAccountResponse.response.equal_amount).length();
        }else {
            amountLength=""+String.valueOf(createVirtualAccountResponse.response.equal_amount).length();
        }
        if(description.length()<10){
            descriptionLength="0"+description.length();
        }else {
            descriptionLength=""+description.length();
        }
        String qrPath1="000201010212";
        String qrTagValue3="000697041801"+createVirtualAccountResponse.response.account_number.length()+createVirtualAccountResponse.response.account_number;
        String qrTag3="0010A00000072701"+qrTagValue3.length()+qrTagValue3+"0208QRIBFTTA";
        String qrTag4="08"+descriptionLength+description;
        String qrTag4Length="";
        if(qrTag4.length()<10){
            qrTag4Length="0"+qrTag4.length();
        }else {
            qrTag4Length=""+qrTag4.length();
        }
        String qrPath2="38"+qrTag3.length()+qrTag3+"5303704"+54+amountLength+createVirtualAccountResponse.response.equal_amount+"5802VN"+"62"+qrTag4Length+qrTag4+"6304";
        String qrData=qrPath1+qrPath2;

        String qrCRC=calculateCRC(qrData);
        System.out.println(qrData+qrCRC);
        return QRCodeGenerator.generateQRCodeImage(qrData+qrCRC);
    }
    public static String calculateCRC(String data) {
        int crc = 0xFFFF; // Giá trị khởi tạo

        for (int i = 0; i < data.length(); i++) {
            crc ^= data.charAt(i) << 8;
            for (int j = 0; j < 8; j++) {
                if ((crc & 0x8000) != 0) {
                    crc = (crc << 1) ^ 0x1021; // Đa thức CRC-CCITT
                } else {
                    crc <<= 1;
                }
            }
        }
        crc &= 0xFFFF; // Giữ lại 16 bit cuối cùng
        return String.format("%04X", crc); // Trả về kết quả ở dạng hex (4 ký tự)
    }

    public ResponseEntity<CreateVAResponse<CreateVirtualAccountResponse>> createVirtualAccount(
            CreateVirtualAccountRequest request) throws Exception {
        JsonObject jsonRequestBody = new JsonObject();
        jsonRequestBody.addProperty("merchant_code", "ASM_1");
        jsonRequestBody.addProperty("account_name", request.getAccountName());
        jsonRequestBody.addProperty("map_id", request.getMapId());
        jsonRequestBody.addProperty("map_type", request.getMapType());
        jsonRequestBody.addProperty("account_type", request.getAccountType());
        jsonRequestBody.addProperty("bank_code", request.getBankCode());
        jsonRequestBody.addProperty("max_amount", request.getMaxAmount());
        jsonRequestBody.addProperty("min_amount", request.getMinAmount());
        jsonRequestBody.addProperty("equal_amount", request.getEqualAmount());
        String message = "merchant_code=" + "ASM_1" + "&account_name=" + request.getAccountName()
                + "&map_id=" + request.getMapId() + "&map_type=" + request.getMapType() + "&account_type="
                + request.getAccountType() + "&bank_code=" + request.getBankCode();
        byte[] messageBytes = message.getBytes();
        byte[] signatureBytes = RSADigitalSignature.signData(messageBytes,
                RSADigitalSignature.loadPrivateKey(GlobalValue.pathSignatureKey));
        jsonRequestBody.addProperty("signature", RSADigitalSignature.toBase64(signatureBytes));
        System.out.println(jsonRequestBody);
        MediaType JSON = MediaType.get("application/json");
        RequestBody body = RequestBody.create(jsonRequestBody.toString(), JSON);
        CreateVirtualAccountResponse createTokenResponse = null;
        UserBearerToken userBearerToken = createAuthenticationToken("ASM_1", "Gtel@asm1");

        String qrLink="";
        try {
            String response = client.post(GlobalValue.apiCreateVirtualAcc, body, userBearerToken.getToken());
            createTokenResponse = objectMapper.readValue(response, CreateVirtualAccountResponse.class);
            qrLink=createQrBank(createTokenResponse,request.getDescription());
            success = true;
            log.info("ResponseCreateToken : {}", response);
            message = "createVirtualAccount success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (IOException e) {
            success = false;
            message = "createVirtualAccount Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            e.printStackTrace();
        }

        return new ResponseEntity<>(
                new CreateVAResponse(success, message,qrLink, createTokenResponse), HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<String>> updateVirtualAccount(String merchantCode, String password,
            String bearerToken) {
        JsonObject jsonRequestBody = new JsonObject();
        jsonRequestBody.addProperty("merchant_code", merchantCode);
        jsonRequestBody.addProperty("account_name", password);
        jsonRequestBody.addProperty("map_id", merchantCode);
        jsonRequestBody.addProperty("map_type", password);
        jsonRequestBody.addProperty("account_type", merchantCode);
        jsonRequestBody.addProperty("bank_code", password);
        jsonRequestBody.addProperty("max_amount", merchantCode);
        jsonRequestBody.addProperty("min_amount", password);
        jsonRequestBody.addProperty("equal_amount", merchantCode);
        jsonRequestBody.addProperty("signature", password);
        MediaType JSON = MediaType.get("charset=utf-8");
        RequestBody body = RequestBody.create(jsonRequestBody.toString(), JSON);
        try {
            String response = client.post(GlobalValue.apiUpdateVirtualAcc, body, bearerToken);
            System.out.println(response);
            success = true;
            message = "updateVirtualAccount data success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (IOException e) {
            success = false;
            message = "updateVirtualAccount Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message, null), HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<String>> getDetailVirtualAccount(String merchantCode, String password,
            String bearerToken) {
        JsonObject jsonRequestBody = new JsonObject();
        jsonRequestBody.addProperty("merchant_code", merchantCode);
        jsonRequestBody.addProperty("account_number", password);
        jsonRequestBody.addProperty("account_name", merchantCode);
        jsonRequestBody.addProperty("bank_code", password);
        jsonRequestBody.addProperty("signature", merchantCode);
        MediaType JSON = MediaType.get("charset=utf-8");
        RequestBody body = RequestBody.create(jsonRequestBody.toString(), JSON);
        try {
            String response = client.post(GlobalValue.apiDetailVirtualAcc, body, bearerToken);
            System.out.println(response);
            success = true;
            message = "getDetailVirtualAccount data success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (IOException e) {
            success = false;
            message = "getDetailVirtualAccount Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message, null), HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<String>> closeVirtualAccount(String merchantCode, String password,
            String bearerToken) {
        JsonObject jsonRequestBody = new JsonObject();
        jsonRequestBody.addProperty("merchant_code", merchantCode);
        jsonRequestBody.addProperty("account_number", password);
        jsonRequestBody.addProperty("close_reason", merchantCode);
        jsonRequestBody.addProperty("signature", password);
        MediaType JSON = MediaType.get("charset=utf-8");
        RequestBody body = RequestBody.create(jsonRequestBody.toString(), JSON);
        try {
            String response = client.post(GlobalValue.apiCloseVirtualAcc, body, bearerToken);
            System.out.println(response);
            success = true;
            message = "closeVirtualAccount data success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (IOException e) {
            success = false;
            message = "closeVirtualAccount Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message, null), HttpStatusCode.valueOf(statusCode));
    }

    public ResponseEntity<BaseResponse<String>> reopenVirtualAccount(String merchantCode, String password,
            String bearerToken) {
        JsonObject jsonRequestBody = new JsonObject();
        jsonRequestBody.addProperty("merchant_code", merchantCode);
        jsonRequestBody.addProperty("account_number", password);
        jsonRequestBody.addProperty("signature", password);
        MediaType JSON = MediaType.get("charset=utf-8");
        RequestBody body = RequestBody.create(jsonRequestBody.toString(), JSON);
        try {
            String response = client.post(GlobalValue.apiCloseVirtualAcc, body, bearerToken);
            System.out.println(response);
            success = true;
            message = "reopenVirtualAccount data success!!!";
            statusCode = HttpStatus.OK.value();
        } catch (IOException e) {
            success = false;
            message = "reopenVirtualAccount Fail!!!";
            statusCode = HttpStatus.BAD_REQUEST.value();
            e.printStackTrace();
        }
        return new ResponseEntity<>(
                new BaseResponse(success, message, null), HttpStatusCode.valueOf(statusCode));
    }

}
