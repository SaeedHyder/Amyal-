package com.app.amyal.entities;

/**
 * Created by khan_muhammad on 12/7/2017.
 */

public class PackageItineraryEnt {


    String iconPath;
    String title;
    String desc;
    boolean isLastDay;

    public PackageItineraryEnt(String iconPath,String title,String desc,boolean isLastDay){
        setIconPath(iconPath);
        setTitle(title);
        setDesc(desc);
        setLastDay(isLastDay);
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isLastDay() {
        return isLastDay;
    }

    public void setLastDay(boolean lastDay) {
        isLastDay = lastDay;
    }
}
