/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.helper;

import com.feemanagement.dto.SessionBean;

/**
 * This is a helper class which set the session to the combo boxes
 *
 * @author Mandeep Singh
 */
public class SessionClass {

    /**
     * This method provide a array of the session
     *
     * @return
     */
    public static SessionBean[] getSessionValue() {
        SessionBean sb1 = null;
        String[] sessionDate = {"01/03/2014 to 28/02/2015", "01/03/2015 to 29/02/2016", "01/03/2016 to 28/02/2017", "01/03/2017 to 28/02/2018", "01/03/2018 to 28/02/2019", "01/03/2019 to 28/02/2020", "01/03/2020 to 28/02/2021", "01/03/2021 to 28/02/2022", "01/03/2022 to 28/02/2023", "01/03/2023 to 28/02/2024"};
        SessionBean[] sessionBeans = new SessionBean[10];
        int index = 0;
        for (int i = 700; i < 710; i++) {
            sb1 = new SessionBean();
            sb1.setSessionId(i);
            sb1.setStudentSession(sessionDate[index]);
            sessionBeans[index] = sb1;
            index++;
        }
        return sessionBeans;
    }
}
