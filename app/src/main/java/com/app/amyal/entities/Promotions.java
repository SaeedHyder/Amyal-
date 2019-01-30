
package com.app.amyal.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Promotions {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Descriptions")
    @Expose
    private String description;
    @SerializedName("Class")
    @Expose
    private String _class;
    @SerializedName("MinDate")
    @Expose
    private String minDate;
    @SerializedName("MaxDate")
    @Expose
    private String maxDate;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("Quantity")
    @Expose
    private String quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public String getMinDate() {
        return minDate;
    }

    public void setMinDate(String minDate) {
        this.minDate = minDate;
    }

    public String getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
