package com.wuxiankeneng.jian.service;

import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.TestCard;
import com.wuxiankeneng.jian.bean.db.TestDA;
import com.wuxiankeneng.jian.factory.TestFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("trader")
public class TestService {
    @GET
    @Path("test/{taId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<TestCard> test(@PathParam("taId") String taId){
        TestDA testDA = TestFactory.findById(taId);
        if (testDA==null)
            return ResponseModel.buildError("没找到");
        testDA = TestFactory.load(testDA);
        return ResponseModel.buildOk(new TestCard(testDA));
    }

    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<TestDA> create(){
        TestFactory.create();

        return ResponseModel.buildOk();
    }

}
