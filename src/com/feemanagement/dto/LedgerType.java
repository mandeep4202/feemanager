/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.dto;

/**
 *
 * @author 786
 */
public class LedgerType {
private int ledgerId;
private String ledgerName;

    public void setLedgerId(int ledgerId) {
        this.ledgerId = ledgerId;
    }

    public void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    @Override
    public String toString() {
        return ledgerName;
    }



}
