package com.wuxiankeneng.jian.service;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.api.admin.RecommendModel;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.RecommendCard;
import com.wuxiankeneng.jian.bean.db.Admin;
import com.wuxiankeneng.jian.bean.db.Recommend;
import com.wuxiankeneng.jian.bean.db.School;
import com.wuxiankeneng.jian.factory.AdminFactory;
import com.wuxiankeneng.jian.factory.SchoolFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("admin")
public class AdminService extends BaseService {
    @POST
    @Path("school/create/{schoolName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<School> createSchool(@PathParam("schoolName") String schoolName) {
        if (Strings.isNullOrEmpty(schoolName)) {
            return ResponseModel.buildParameterError();
        }
//        Admin admin = getAdminSelf();

        School school = SchoolFactory.findByName(schoolName);
        if (school != null)
            return ResponseModel.buildHaveNameError();

        school = SchoolFactory.create(schoolName);

        if (school == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk();

    }

    @POST
    @Path("recommend/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<RecommendCard> createRecommend(RecommendModel model) {
        if (model == null
                || Strings.isNullOrEmpty(model.getImgUrl())
                || Strings.isNullOrEmpty(model.getShopIdOrAdvertUrl())
                || Strings.isNullOrEmpty(model.getSchoolId())
                || !(model.getType() == 1 || model.getType() == 0)) {
            return ResponseModel.buildParameterError();
        }

        School school = SchoolFactory.findById(model.getSchoolId());
        if (school == null)
            return ResponseModel.buildError("没找到学校");

        Recommend recommend = AdminFactory.createRecommend(model, school);
        if (recommend == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk(new RecommendCard(recommend));
    }
}
