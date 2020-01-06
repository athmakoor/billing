package com.ref.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ref.bean.SubscriptionDTO;
import com.ref.bean.UnsubscribeDTO;
import com.ref.service.RequestTrackerService;
import com.ref.service.impl.SubscriptionMapperServiceImpl;
import com.ref.web.service.WebService;

@RestController
@RequestMapping("/")
public class SubscriptionController {
    @Resource
    private WebService webService;
    @Resource
    private SubscriptionMapperServiceImpl subscriptionMapperService;
    @Resource
    private RequestTrackerService requestTrackerService;

    @GetMapping("notification")
    public void notification(@RequestParam Map<String, String> requestParams,final Map<String, Object> model, HttpServletRequest request){
        webService.updateDefaultModel(model, request, "notification");
        requestTrackerService.updateNotification(requestParams);
    }

    @GetMapping("subscribe")
    public void subscribe(@RequestParam Map<String, String> requestParams,final Map<String, Object> model, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        webService.updateDefaultModel(model, request, "subscribe");
        String redirectUrl = requestTrackerService.saveAndGetSubscriptionRedirectUrl(new SubscriptionDTO(requestParams));

        if (redirectUrl != null) {
            httpServletResponse.setHeader("Location", redirectUrl);
            httpServletResponse.setStatus(302);
        }
    }

    @GetMapping("unsubscribe")
    public void unsubscribe(@RequestParam Map<String, String> requestParams,final Map<String, Object> model, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        webService.updateDefaultModel(model, request, "unsubscribe");
        String redirectUrl = requestTrackerService.saveAndGetUnSubscribeRedirectUrl(new UnsubscribeDTO(requestParams));

        if (redirectUrl != null) {
            httpServletResponse.setHeader("Location", redirectUrl);
            httpServletResponse.setStatus(302);
        }
    }
}
