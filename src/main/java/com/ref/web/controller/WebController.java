package com.ref.web.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ref.service.RequestTrackerService;
import com.ref.web.service.WebService;

@Controller
@RequestMapping(value = "")
public class WebController {
    @Resource
    private WebService webService;
    @Resource
    private RequestTrackerService requestTrackerService;

    @GetMapping("/redirect")
    public void redirect(final Map<String, Object> model, @RequestParam Map<String, String> requestParams, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        String tansNoString = requestParams.get("transno");
        webService.updateDefaultModel(model, request, "redirect");

        if (tansNoString != null) {
            httpServletResponse.setHeader("Location", requestTrackerService.getResponseRedirectionUrl(requestParams));
            httpServletResponse.setStatus(302);
        } else {
            httpServletResponse.setHeader("Location", "redirect");
            httpServletResponse.setStatus(302);
        }
    }

    @GetMapping("/**")
    public void others(final Map<String, Object> model, @RequestParam Map<String, String> requestParams, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        webService.updateDefaultModel(model, request, "redirect");
    }
}
