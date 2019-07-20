package com.wuxiankeneng.factory.card;

public class Recommend {
    public static final int TYPE_ID = 0;
    public static final int TYPE_URL = 1;
    private String imgPath;
    private String urlOrId;
    private int type;

    public Recommend() {
    }

    public Recommend(String imgPath, String urlOrId, int type) {
        this.imgPath = imgPath;
        this.urlOrId = urlOrId;
        this.type = type;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getUrlOrId() {
        return urlOrId;
    }

    public void setUrlOrId(String urlOrId) {
        this.urlOrId = urlOrId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
