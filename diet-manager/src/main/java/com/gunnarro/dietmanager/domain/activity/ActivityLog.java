package com.gunnarro.dietmanager.domain.activity;

import com.gunnarro.dietmanager.domain.BaseDomain;

public class ActivityLog extends BaseDomain {

    private static final long serialVersionUID = -180659968576477898L;

    public enum ActivityTypes {
        GAMING, SCHOOL, SCHOOL_WORK;
    }
    
    private String createdByUser;
    private String lastModifiedByUser;
    private Integer fromHour;
    private Integer toHour;
    // HIGH, MEDION, LOW
    private Integer intensitivity;
    // how I feel 1 to 10, where 1 is BAD and 10 is GREATE
    private Integer emotions;
}
