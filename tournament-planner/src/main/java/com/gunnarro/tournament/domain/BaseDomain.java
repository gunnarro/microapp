package com.gunnarro.tournament.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.dandelion.core.util.StringUtils;
import com.gunnarro.tournament.endpoint.rest.RestResponse;

/**
 * Common domain object
 * 
 * @author admin
 * 
 */
public abstract class BaseDomain extends RestResponse implements Serializable{

    private static final long serialVersionUID = -4340377387275807526L;
    private Integer id;
    private String name;
    private long createdTime;
    // All foreign keys used in the DB model
    private Integer fkAddressId;
    private Integer fkPersonId;
    private Integer fkClubId;
    private Integer fkTeamId;
    private Integer fkContactId;
    private Integer fkSeasonId;
    private Integer fkStatusId;
    private Integer fkTaskStatusId;
    private Integer fkRefereeId;
    private Integer fkTeamleadId;
    private Integer fkCoachId;
    private Integer fkLeagueId;
    private Integer fkLeagueCategoryId;
    private Integer fkLeagueStatusId;
    private Integer fkLeagueRuleId;
    private Integer fkTemaRoleId;
    private Integer fkPlayerPositionId;

    /**
     * default constructor
     */
    public BaseDomain() {
    }

    public BaseDomain(Integer id) {
        this.id = id;
    }

    public BaseDomain(Integer id, String name) {
        this(id);
        this.name = name;
    }

    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasName() {
        return StringUtils.isNotBlank(name);
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public Integer getFkAddressId() {
        return fkAddressId;
    }

    public void setFkAddressId(Integer fkAddressId) {
        this.fkAddressId = fkAddressId;
    }

    public boolean hasFkAddressId() {
        return hasId(fkAddressId);
    }

    public Integer getFkClubId() {
        return fkClubId;
    }

    public void setFkClubId(Integer fkClubId) {
        this.fkClubId = fkClubId;
    }

    public boolean hasFkClubId() {
        return hasId(fkClubId);
    }

    public Integer getFkTeamId() {
        return fkTeamId;
    }

    public void setFkTeamId(Integer fkTeamId) {
        this.fkTeamId = fkTeamId;
    }

    public boolean hasFkTeamId() {
        return hasId(fkTeamId);
    }

    public boolean isTeamId() {
        return hasId(fkTeamId);
    }

    public Integer getFkPersonId() {
        return fkPersonId;
    }

    public void setFkPersonId(Integer fkPersonId) {
        this.fkPersonId = fkPersonId;
    }

    public boolean hasFkPersonId() {
        return hasId(fkPersonId);
    }

    public Integer getFkContactId() {
        return fkContactId;
    }

    public void setFkContactId(Integer fkContactId) {
        this.fkContactId = fkContactId;
    }

    public boolean hasFkContactId() {
        return hasId(this.fkContactId);
    }

    public Integer getFkSeasonId() {
        return fkSeasonId;
    }

    public void setFkSeasonId(Integer fkSeasonId) {
        this.fkSeasonId = fkSeasonId;
    }

    public boolean hasFkSeasonId() {
        return hasId(this.fkSeasonId);
    }

    public Integer getFkLeagueStatusId() {
        return fkLeagueStatusId;
    }

    public void setFkLeagueStatusId(Integer fkLeagueStatusId) {
        this.fkLeagueStatusId = fkLeagueStatusId;
    }

    public boolean hasFkLeagueStatusId() {
        return hasId(fkLeagueStatusId);
    }

    public Integer getFkLeagueRuleId() {
        return fkLeagueRuleId;
    }

    public void setFkLeagueRuleId(Integer fkLeagueRuleId) {
        this.fkLeagueRuleId = fkLeagueRuleId;
    }

    public boolean hasFkLeagueRuleId() {
        return hasId(fkLeagueRuleId);
    }

    public Integer getFkRefereeId() {
        return fkRefereeId;
    }

    public void setFkRefereeId(Integer fkRefereeId) {
        this.fkRefereeId = fkRefereeId;
    }

    public boolean hasFkRefereeId() {
        return hasId(fkRefereeId);
    }

    public Integer getFkStatusId() {
        return fkStatusId;
    }

    public void setFkStatusId(Integer fkStatusId) {
        this.fkStatusId = fkStatusId;
    }

    public boolean hasFkTaskStatusId() {
        return hasId(fkTaskStatusId);
    }

    public boolean hasFkStatusId() {
        return hasId(fkStatusId);
    }

    public Integer getFkTaskStatusId() {
        return fkTaskStatusId;
    }

    public void setFkTaskStatusId(Integer fkTaskStatusId) {
        this.fkTaskStatusId = fkTaskStatusId;
    }

    public Integer getFkPlayerPositionId() {
        return fkPlayerPositionId;
    }

    public boolean hasFkPlayerPositionId() {
        return hasId(fkPlayerPositionId);
    }

    public void setFkPlayerPositionId(Integer fkPlayerPositionId) {
        this.fkPlayerPositionId = fkPlayerPositionId;
    }

    public Integer getFkTeamleadId() {
        return fkTeamleadId;
    }

    public void setFkTeamleadId(Integer fkTeamleadId) {
        this.fkTeamleadId = fkTeamleadId;
    }

    public boolean hasFkTeamleadId() {
        return hasId(fkTeamleadId);
    }

    public Integer getFkCoachId() {
        return fkCoachId;
    }

    public void setFkCoachId(Integer fkCoachId) {
        this.fkCoachId = fkCoachId;
    }

    public boolean hasFkCoachId() {
        return hasId(fkCoachId);
    }

    public Integer getFkLeagueId() {
        return fkLeagueId;
    }

    public void setFkLeagueId(Integer fkLeagueId) {
        this.fkLeagueId = fkLeagueId;
    }

    public boolean hasFkLeagueId() {
        return hasId(fkLeagueId);
    }

    public Integer getFkLeagueCategoryId() {
        return fkLeagueCategoryId;
    }

    public void setFkLeagueCategoryId(Integer fkLeagueCategoryId) {
        this.fkLeagueCategoryId = fkLeagueCategoryId;
    }

    public boolean hasFkLeagueCategoryId() {
        return hasId(fkLeagueCategoryId);
    }

    protected boolean hasId(Integer id) {
        return id != null && id > 0;
    }

    public Integer getFkTemaRoleId() {
        return fkTemaRoleId;
    }

    public void setFkTemaRoleId(Integer fkTemaRoleId) {
        this.fkTemaRoleId = fkTemaRoleId;
    }

    public boolean hasFkTemaRoleId() {
        return hasId(this.fkTemaRoleId);
    }

    /**
     * Method to check if to number is equal.
     * @param currentId
     * @param id
     * @return
     */
    protected boolean hasIdChanged(Integer currentId, Integer id) {
        if (currentId != null && id != null) {
            return currentId.compareTo(id) != 0;
        } else if (currentId == null && id == null) {
            return false;
        }
        // if we get so far, the new and old id's must be different, i,e. one id
        // must be set, while the other is not.
        return true;
    }
    
    @Override
    public String toString() {
        return "BaseDomain [id=" + id + ", name=" + name + ", createdTime=" + createdTime + ", fkAddressId=" + fkAddressId + ", fkPersonId=" + fkPersonId
                + ", fkClubId=" + fkClubId + ", fkTeamId=" + fkTeamId + ", fkContactId=" + fkContactId + ", fkSeasonId=" + fkSeasonId + ", fkStatusId="
                + fkStatusId + ", fkTaskStatusId=" + fkTaskStatusId + ", fkRefereeId=" + fkRefereeId + ", fkTeamleadId=" + fkTeamleadId + ", fkCoachId="
                + fkCoachId + ", fkLeagueId=" + fkLeagueId + ", fkLeagueCategoryId=" + fkLeagueCategoryId + ", fkLeagueStatusId=" + fkLeagueStatusId
                + ", fkLeagueRuleId=" + fkLeagueRuleId + ", fkTemaRoleId=" + fkTemaRoleId + ", fkPlayerPositionId=" + fkPlayerPositionId + "]";
    }

}
