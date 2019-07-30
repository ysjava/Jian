package com.wuxiankeneng.factory.model.shop;

public class TypeBean {
    private String type;
    private int typeId;


    public TypeBean(String type, int typeId) {
        this.type = type;
        this.typeId = typeId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "type='" + type + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
