package com.ref.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ref.bean.SubscriptionDTO;
import com.ref.bean.UnsubscribeDTO;
import com.ref.service.RequestTrackerService;
import com.ref.service.impl.SubscriptionMapperServiceImpl;

@RestController
@RequestMapping("/")
public class SubscriptionController {
    @Resource
    private SubscriptionMapperServiceImpl subscriptionMapperService;
    @Resource
    private RequestTrackerService requestTrackerService;

    @GetMapping("notification")
    public void notification(@RequestParam Map<String, String> requestParams){
        requestTrackerService.updateNotification(requestParams);
    }

    @GetMapping("Paackages/processpackage/")
    public void subscribe(@RequestParam Map<String, String> requestParams, HttpServletResponse httpServletResponse) {
        String redirectUrl = requestTrackerService.saveAndGetSubscriptionRedirectUrl(new SubscriptionDTO(requestParams));

        if (redirectUrl != null) {
            httpServletResponse.setHeader("Location", redirectUrl);
            httpServletResponse.setStatus(302);
        }
    }

    @GetMapping("Unsubscribe")
    public void unsubscribe(@RequestParam Map<String, String> requestParams, HttpServletResponse httpServletResponse) {
        String redirectUrl = requestTrackerService.saveAndGetUnSubscribeRedirectUrl(new UnsubscribeDTO(requestParams));

        if (redirectUrl != null) {
            httpServletResponse.setHeader("Location", redirectUrl);
            httpServletResponse.setStatus(302);
        }
    }
}
