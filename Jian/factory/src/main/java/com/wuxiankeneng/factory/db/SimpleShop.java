package com.wuxiankeneng.factory.db;


import com.wuxiankeneng.factory.tools.DiffUiDataCallback;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

import java.util.List;
import java.util.Objects;

public class SimpleShop extends LitePalSupport implements DiffUiDataCallback.UiDataDiffer<SimpleShop> {
    private String name;
    private String icon;
    @Column(unique = true)
    private String sId;
    private String desc;
    private boolean isBusiness;

    //配送时间
    private String deliveryDate;
    //配送费
    private String deliveryPrice;
    //起送金额
    private String minimumPrice;
    //销量
    private String sales;

    private List<SimpleGoods> simpleGoods;

    public boolean isBusiness() {
        return isBusiness;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<SimpleGoods> getSimpleGoods() {
        return simpleGoods;
    }

    public void setSimpleGoods(List<SimpleGoods> simpleGoods) {
        this.simpleGoods = simpleGoods;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(String minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }

    @Override
    public boolean isSame(SimpleShop old) {
        return this == old || Objects.equals(sId, old.sId);
    }

    @Override
    public boolean isUiContentSame(SimpleShop old) {
        return this == old || (
                Objects.equals(name, old.name)
                        && Objects.equals(desc, old.desc)
                        && Objects.equals(isBusiness, old.isBusiness)
                        && Objects.equals(icon, old.icon)
                        && Objects.equals(deliveryDate, old.deliveryDate)
                        && Objects.equals(deliveryPrice, old.deliveryPrice)
                        && Objects.equals(minimumPrice, old.minimumPrice)
                        && Objects.equals(sales, old.sales)
                        && isListEqual(simpleGoods, old.simpleGoods)
        );
    }

    //对数组中的元素进行对比
    private static boolean isListEqual(List<SimpleGoods> newList, List<SimpleGoods> oldList) {

        if (newList == oldList)//是不是同一个对象
            return true;
        if (newList == null || oldList == null)//一个为空,不等
            return false;
        if (newList.size() != oldList.size())//先判断大小是否等
            return false;
        if (oldList.size() == 0)//如果list中数据为0 则返回true
            return true;

        for (SimpleGoods newCard : newList) {//遍历对比  拿新的第一个和旧的比,找不到就返回false  找到就下一个,同上,
            boolean isSame = false;
            for (SimpleGoods oldCard : oldList) {
                isSame = (newCard.getIcon().equals(oldCard.getIcon()) && newCard.getName().equals(oldCard.getName())); //如果找到就结束当前for,
                if (isSame)
                    break;
            }
            if (!isSame) //如果一轮对比完都没找到,就返回false
                return false;
        }

        return true;
    }
}
