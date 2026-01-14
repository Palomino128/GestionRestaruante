package com.saborperuano.gestionrestaurante.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributes {

    @ModelAttribute("request")
    public HttpServletRequest request(HttpServletRequest request) {
        return request;
    }
}
