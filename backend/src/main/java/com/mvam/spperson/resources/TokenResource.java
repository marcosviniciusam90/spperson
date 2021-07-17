package com.mvam.spperson.resources;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/tokens")
public class TokenResource implements SwaggerSecuredRestController {

    @Value("${security.oauth2.client.enable-https}")
    private boolean isEnableHttps;

    @DeleteMapping("/revoke")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoke(HttpServletRequest request, HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", null)
                .httpOnly(true)		// proíbe JavaScript de ler
                .secure(isEnableHttps)		// se true, só irá aceitar HTTPS
                .path(request.getContextPath() + "/oauth/token")
                .maxAge(0)
                .sameSite(isEnableHttps ? "None": "Lax")
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
