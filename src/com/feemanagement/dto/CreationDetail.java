/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feemanagement.dto;

import java.sql.Date;

/**
 *
 * @author 786
 */
public class CreationDetail {

    private Date creationDate;
    private Date lastModifiedDate;
    private String createdBy;
    private String lastModifiedBY;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBY() {
        return lastModifiedBY;
    }

    public void setLastModifiedBY(String lastModifiedBY) {
        this.lastModifiedBY = lastModifiedBY;
    }
}
