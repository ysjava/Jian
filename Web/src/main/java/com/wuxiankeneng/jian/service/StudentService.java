package com.wuxiankeneng.jian.service;

import com.google.common.base.Strings;
import com.wuxiankeneng.jian.bean.base.ResponseModel;
import com.wuxiankeneng.jian.bean.card.*;

import com.wuxiankeneng.jian.bean.db.*;
import com.wuxiankeneng.jian.factory.SchoolFactory;
import com.wuxiankeneng.jian.factory.ShopFactory;
import com.wuxiankeneng.jian.factory.StudentFactory;
import org.jvnet.hk2.internal.Collector;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("student")
public class StudentService extends BaseService {
    //根据学校id拿到首页的推荐卡片
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

    //拿到当前学校id下的所有店铺  返回的店铺的简单信息
    @GET
    @Path("getSimpleShops/{schoolId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<SimpleShopCard>> getShops(@PathParam("schoolId") String schoolId) {
        if (Strings.isNullOrEmpty(schoolId)) {
            return ResponseModel.buildParameterError();
        }

        School school = SchoolFactory.findById(schoolId);
        if (school == null)
            return ResponseModel.buildError("没找到学校");

        List<SimpleShopCard> shopList = ShopFactory.getShopsBySchoolId(school);
        if (shopList == null || shopList.size() == 0)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk(shopList);
    }

    //搜索店铺,主页界面搜索框的
    @GET
    @Path("searchShops/{shopName}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<SearchShopCard>> searchShops(@PathParam("shopName") String shopName) {
        if (Strings.isNullOrEmpty(shopName))
            return ResponseModel.buildParameterError();
        Student self = getStudentSelf();
        School school = self.getSchool();
        if (school == null)
            return ResponseModel.buildServiceError();

        List<SearchShopCard> cardList = ShopFactory.searchShops(shopName.trim(), school);

        if (cardList == null) {
            return ResponseModel.buildServiceError();
        }

        return ResponseModel.buildOk(cardList);

    }

    //根据店铺类型拿店铺
    @GET
    @Path("findShopsByType/{type}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<SimpleShopCard>> findShopsByType(@PathParam("type") int type) {

        if (!checkType(type))
            return ResponseModel.buildParameterError();
        Student self = getStudentSelf();
        School school = self.getSchool();
        List<SimpleShopCard> cardList = ShopFactory.findShopsByType(school, type);
        if (cardList == null)
            return ResponseModel.buildServiceError();

        return ResponseModel.buildOk(cardList);

    }

    //检查类型是否正确
    private boolean checkType(int type) {
        return type == Shop.TYPE_DRINK || type == Shop.TYPE_FAST_FOOD || type == Shop.TYPE_FRUITS || type == Shop.TYPE_MULTIPLE ||
                type == Shop.TYPE_NOODLE || type == Shop.TYPE_STIR_FRY || type == Shop.TYPE_SUPERMARKET;
    }

    //用店铺id拿到店铺的详细信息
    //用于店铺界面
    @GET
    @Path("getShop/{shopId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<ShopCard> getShopById(@PathParam("shopId") String shopId) {
        if (Strings.isNullOrEmpty(shopId))
            return ResponseModel.buildParameterError();
        Shop shop = ShopFactory.findById(shopId);
        if (shop == null)
            return ResponseModel.buildError("没找到店铺");
        //重新加载一次,因为商品集合是懒加载的
//        shop = ShopFactory.load(shop);


        return ResponseModel.buildOk(new ShopCard(shop, true));
    }

    //查询地址集合
    @GET
    @Path("getAddressList")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<AddressCard>> getAddressList() {
        Student student = getStudentSelf();
        List<Address> addressList = StudentFactory.getAddressList(student);
        if (addressList == null)
            return ResponseModel.buildServiceError();
        List<AddressCard> cards = addressList.stream()
                .map(AddressCard::new)
                .collect(Collectors.toList());

        return ResponseModel.buildOk(cards);
    }
}
