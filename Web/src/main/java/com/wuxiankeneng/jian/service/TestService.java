package com.wuxiankeneng.jian.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("trader")
public class TestService {
    @GET
    @Path("test")
    public String test(){
        return "测试";
    }
}
