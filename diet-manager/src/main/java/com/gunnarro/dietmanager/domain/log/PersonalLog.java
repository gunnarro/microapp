package com.gunnarro.dietmanager.domain.log;

import com.gunnarro.dietmanager.domain.BaseDomain;

public class PersonalLog extends BaseDomain {

    private static final long serialVersionUID = 3799683509174086447L;

    private String lastModifiedByUser;

    /**
     * default constructor
     */
    public PersonalLog() {
        // for unit tests
    }

}
