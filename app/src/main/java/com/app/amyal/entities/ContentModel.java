package com.app.amyal.entities;

/**
 * Created by Addi.
 */
public class ContentModel {

    int ContentID;
    String ContentKey;
    String ContentValue;
    String CreationDate;
    String UpdatedDate;
    String CreatedBy;
    String UpdatedBy;

    public int getContentID() {
        return ContentID;
    }

    public void setContentID(int contentID) {
        ContentID = contentID;
    }

    public String getContentKey() {
        return ContentKey;
    }

    public void setContentKey(String contentKey) {
        ContentKey = contentKey;
    }

    public String getContentValue() {
        return ContentValue;
    }

    public void setContentValue(String contentValue) {
        ContentValue = contentValue;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    public String getUpdatedDate() {
        return UpdatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        UpdatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getUpdatedBy() {
        return UpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        UpdatedBy = updatedBy;
    }
}
