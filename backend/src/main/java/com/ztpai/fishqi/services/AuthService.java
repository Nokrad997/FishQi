package com.ztpai.fishqi.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.JwtDTO;
import com.ztpai.fishqi.DTO.LoginDTO;
import com.ztpai.fishqi.DTO.RefreshDTO;
import com.ztpai.fishqi.config.JwtTokenUtil;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.exceptions.ExpiredRefreshTokenException;

@Service
public class AuthService {
    private final CustomerSharedService customerSharedService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthService(CustomerSharedService customerSharedService, JwtTokenUtil jwtTokenUtil) {
        this.customerSharedService = customerSharedService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public JwtDTO loginCustomer(LoginDTO customer) throws BadCredentialsException {
        if (!this.customerSharedService.decodePassword(customer.getPassword(), customer.getEmail())) {

            throw new BadCredentialsException("Wrong email or password");
        }

        Customer signedInCustomer = this.customerSharedService.getCustomerByEmail(customer.getEmail());
        String access = this.jwtTokenUtil.generateToken(signedInCustomer.getEmail(),
                signedInCustomer.getIs_admin());
        String refresh = this.jwtTokenUtil.generateRefreshToken(signedInCustomer.getEmail(),
                signedInCustomer.getIs_admin());

        return new JwtDTO(access, refresh);
    }

    public RefreshDTO refreshAccessToken(RefreshDTO refresh) throws ExpiredRefreshTokenException {
        String access = this.jwtTokenUtil.refreshAccessToken(refresh.getRefreshToken());

        return new RefreshDTO(refresh.getRefreshToken(), access);
    }

    public boolean validateToken(RefreshDTO refresh) throws ExpiredRefreshTokenException {
        return this.jwtTokenUtil.validateToken(refresh.getRefreshToken(),
                this.jwtTokenUtil.getSubjectFromToken(refresh.getRefreshToken()));
    }
}
