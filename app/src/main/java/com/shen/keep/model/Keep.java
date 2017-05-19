package com.shen.keep.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by edianzu on 2017/5/19.
 */
@Entity
public class Keep {

    @Id(autoincrement = true)
    private Long id;

    @Property(nameInDb = "startTime")
    private long  startTime;

    @Property(nameInDb = "stopTime")
    private long stopTime;

    @Property(nameInDb = "startDate")
    private String startDate;
    @Property(nameInDb = "stopDate")
    private String stopDate;

    @Property(nameInDb = "keepTime")
    private String keepTime;

    @Property(nameInDb = "keepName")
    private String keepName;

    @Property(nameInDb = "pauseTime")
    private String pauseTime;

    @Generated(hash = 499721659)
    public Keep(Long id, long startTime, long stopTime, String startDate,
            String stopDate, String keepTime, String keepName, String pauseTime) {
        this.id = id;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.startDate = startDate;
        this.stopDate = stopDate;
        this.keepTime = keepTime;
        this.keepName = keepName;
        this.pauseTime = pauseTime;
    }

    @Generated(hash = 1241091606)
    public Keep() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStopTime() {
        return this.stopTime;
    }

    public void setStopTime(long stopTime) {
        this.stopTime = stopTime;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStopDate() {
        return this.stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getKeepTime() {
        return this.keepTime;
    }

    public void setKeepTime(String keepTime) {
        this.keepTime = keepTime;
    }

    public String getKeepName() {
        return this.keepName;
    }

    public void setKeepName(String keepName) {
        this.keepName = keepName;
    }

    @Override
    public String toString() {
        return "Keep{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", stopTime=" + stopTime +
                ", startDate='" + startDate + '\'' +
                ", stopDate='" + stopDate + '\'' +
                ", keepTime='" + keepTime + '\'' +
                ", keepName='" + keepName + '\'' +
                ", pauseTime='" + pauseTime + '\'' +
                '}';
    }

    public String getPauseTime() {
        return this.pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }
}
