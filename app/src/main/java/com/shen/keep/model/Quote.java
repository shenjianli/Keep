package com.shen.keep.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by edianzu on 2016/8/31.
 *
 * 实现Serializable接口，以支持序列化，这样这个对象可以使用Intent来进行传递
 *
 */
@Entity
public class Quote {

    /**
     * 标题
     内容
     背景图片
     标题颜色
     内容颜色
     app图片
     app名字
     倒计时（毫秒）
     */
    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "content")
    private String content;

    @Property(nameInDb = "bgImgUrl")
    private String bgImgUrl;

    @Property(nameInDb = "titleColor")
    private String titleColor;

    @Property(nameInDb = "contentColor")
    private String contentColor;

    @Property(nameInDb = "appImgUrl")
    private String appImgUrl;

    @Property(nameInDb = "appName")
    private String appName;

    public long getCountTime() {
        return countTime;
    }

    public void setCountTime(long countTime) {
        this.countTime = countTime;
    }

    private long countTime;


    public Quote(){
        title = "";
        content = "";
        bgImgUrl = "";
        titleColor = "";
        contentColor = "";
        appImgUrl = "";
        appName = "";
        countTime = 5;
    }

    @Generated(hash = 784098725)
    public Quote(Long id, String title, String content, String bgImgUrl,
            String titleColor, String contentColor, String appImgUrl,
            String appName, long countTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.bgImgUrl = bgImgUrl;
        this.titleColor = titleColor;
        this.contentColor = contentColor;
        this.appImgUrl = appImgUrl;
        this.appName = appName;
        this.countTime = countTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBgImgUrl() {
        return bgImgUrl;
    }

    public void setBgImgUrl(String bgImgUrl) {
        this.bgImgUrl = bgImgUrl;
    }

    public String getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(String titleColor) {
        this.titleColor = titleColor;
    }

    public String getContentColor() {
        return contentColor;
    }

    public void setContentColor(String contentColor) {
        this.contentColor = contentColor;
    }

    public String getAppImgUrl() {
        return appImgUrl;
    }

    public void setAppImgUrl(String appImgUrl) {
        this.appImgUrl = appImgUrl;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", bgImgUrl='" + bgImgUrl + '\'' +
                ", titleColor='" + titleColor + '\'' +
                ", contentColor='" + contentColor + '\'' +
                ", appImgUrl='" + appImgUrl + '\'' +
                ", appName='" + appName + '\'' +
                ", countTime=" + countTime +
                '}';
    }

    @Override
    public boolean equals(Object quote) {
        if(quote instanceof Quote) {
            if(content.equals(((Quote) quote).getContent()) && title.equals(((Quote) quote).getTitle())){
               return true;
            }
        }
        return false;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
