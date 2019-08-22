package com.wuxiankeneng.jian.service;

import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.db.Goods;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.bean.db.Student;
import com.wuxiankeneng.jian.factory.GoodsFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("student")
public class GoodsService extends BaseService {

    //拿商品,用日销量去拿
    @GET
    @Path("getGoods/byDailySales")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<Goods>> getGoodsByDailySales() {
        Student student = getStudentSelf();

        School school = student.getSchool();
        List<Goods> goodsList = GoodsFactory.find(school);
        if (goodsList == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk(goodsList);
    }


}
