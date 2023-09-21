package com.example.case_study_car.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class AuthUtil {
    public static String getCurrentPageWithMessageParameter() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String currentUrl = request.getRequestURI();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return currentUrl + "?message=Login successfully";
        } else {
            return currentUrl + "?" + queryString + "&message=Login successfully";
        }
    }
}
