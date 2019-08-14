package com.wuxiankeneng.jian.service;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.RecommendCard;
import com.wuxiankeneng.jian.bean.db.Recommend;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.factory.SchoolFactory;
import org.jvnet.hk2.internal.Collector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("student")
public class StudentService {
    @GET
    @Path("loadRecommend/{schoolId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<RecommendCard>> loadRecommend(@PathParam("schoolId") String schoolId) {
        if (Strings.isNullOrEmpty(schoolId)) {
            return ResponseModel.buildParameterError();
        }

        School school = SchoolFactory.findById(schoolId);
        if (school == null)
            return ResponseModel.buildError("没找到学校");

        List<Recommend> recommends = SchoolFactory.loadRecommend(school);
        if (recommends == null || recommends.size() == 0)
            return ResponseModel.buildServiceError();


        return ResponseModel.buildOk(recommends.stream()
                .map(RecommendCard::new)
                .collect(Collectors.toList()));
    }
}
