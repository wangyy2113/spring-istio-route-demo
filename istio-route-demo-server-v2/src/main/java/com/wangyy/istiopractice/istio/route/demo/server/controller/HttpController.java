package com.wangyy.istiopractice.istio.route.demo.server.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/http")
public class HttpController {

    @GetMapping("/greet")
    public Object greet() {
        return "Http Greeting from server-v2";
    }
}
