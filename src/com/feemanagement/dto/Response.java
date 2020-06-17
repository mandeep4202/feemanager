/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dto;

/**
 * @author Mandeep Singh
 */
public class Response {

    /**
     * This field the status like action is successfull or not
     */
    private byte status;

    /**
     * This field contain the various message like error message, successfull
     * message etc
     */
    private String message;

    /**
     * This field used to transfer data in the form of object when action is
     * successfull
     */
    private Object data;

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
