package com.example.thanh_toan_asm.services;

import com.example.thanh_toan_asm.dtos.logins.PartnerLoginDtos;
import com.example.thanh_toan_asm.dtos.logins.UserLoginRequest;
import com.example.thanh_toan_asm.dtos.logins.UserLoginResponse;
import com.example.thanh_toan_asm.entitys.UserUntity;
import com.example.thanh_toan_asm.repositorys.UserRepository;
import com.example.thanh_toan_asm.untils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.StringJoiner;

@Service
@Slf4j
public class LoginService {

    private Boolean success;
    private String message;
    private String jwt = null;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private PartnerLoginDtos data;

    public UserLoginResponse login(UserLoginRequest userLoginRequest){

        if (Objects.equals(userLoginRequest.getUsername(), "") && Objects.equals(userLoginRequest.getPassword(), "")){
            success = false;
            message = "Login fail, username and password empty !!!";
        }else  {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(), userLoginRequest.getPassword()));
                UserUntity userEntity = userRepository.findFirstByUserName(userLoginRequest.getUsername());
                if (Objects.equals(userEntity.getStatus(), "0")){
                    data = null;
                    success = false;
                    message = "Login Fail, Username No Active !!!";
                    jwt = null;
                    return UserLoginResponse.builder()
                            .success(success)
                            .message(message)
                            .data(data)
                            .token(jwt)
                            .build();
                }
                data = PartnerLoginDtos.builder()
                        .id(userEntity.getId())
                        .status(userEntity.getStatus())
                        .updateAt(userEntity.getUpdateAt())
                        .role(userEntity.getRole())
                        .address(userEntity.getAddress())
                        .detail(userEntity.getDetail())
                        .createAt(userEntity.getCreateAt())
                        .userName(userEntity.getUserName())
                        .avatar(userEntity.getAvatar())
                        .fullName(userEntity.getFullName())
                        .phone(userEntity.getPhone())
                        .email(userEntity.getEmail())
                        .build();
                jwt = jwtUtil.generateToken(userLoginRequest.getUsername(), buildScope(userEntity), userEntity.getUserName());
                success = true;
                message = "Login success !!!";
            }catch (Exception ex){
                log.info("Login Fail: {}",ex.toString());
                data = null;
                success = false;
                message = "Login Fail, Username or password false !!!";
                jwt = null;
            }
        }

        return UserLoginResponse.builder()
                .success(success)
                .message(message)
                .data(data)
                .token(jwt)
                .build();
    }

    private String buildScope(UserUntity user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        stringJoiner.add("ROLE_" + user.getRole());
        return stringJoiner.toString();
    }

}
