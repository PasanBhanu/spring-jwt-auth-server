package com.softinklab.authserver.controller;

import com.softinklab.authserver.rest.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class TestController {
    @RequestMapping(method = RequestMethod.GET, path = "/authenticated", produces = "application/json")
    public BaseResponse test() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(200);
        baseResponse.setMessage("Authenticated");
        return baseResponse;
    }
}
