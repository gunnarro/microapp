package com.gunnarro.dietmanager.domain.diet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.gunnarro.dietmanager.domain.BaseDomain;
import com.gunnarro.dietmanager.domain.statistic.KeyValuePair;
import com.gunnarro.dietmanager.utility.Utility;

public class MenuItem extends BaseDomain {

    public enum MealKategoryEnum {
        FISH, FRUIT, MEAT, VEGTABLES;
    }

    private static final long serialVersionUID = -400745004374201995L;

    private String category;
    private Integer causedConflict;
    private Integer notFollowedRule;
    private String conflictDescription;
    private Integer controlledByUserId;
    private String controlledByUsername;
    private Integer energy;
    private Integer fkDietMenuId;
    private String imageLink;
    private Integer preparedByUserId;
    private Integer primaryKeyId;
    private int selectedCount;
    private List<KeyValuePair> selectionTrends;

    /**
     * Default constructor used by unit tests only
     */
    public MenuItem() {
    }

    /**
     * only used by unit tests
     * 
     * @param createdDate
     */
    public MenuItem(String name, String description, Date createdDate) {
        setName(name);
        setDescription(description);
        setCreatedTime(createdDate.getTime());
        setSortByValue(name);
    }

    public static List<KeyValuePair> generateDefaltSelectionTrends(int days, String datePattern) {
        List<KeyValuePair> list = new ArrayList<>();
        for (int d = days - 1; d >= 0; d--) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -d);
            list.add(new KeyValuePair(Utility.formatTime(cal.getTimeInMillis(), datePattern), Boolean.FALSE.toString()));
        }
        return list;
    }

    public boolean addSelectionTrends(String selectedDate) {
        if (selectionTrends == null) {
            selectionTrends = new ArrayList<>();
        }
        KeyValuePair keyValuePair = new KeyValuePair(selectedDate, Boolean.TRUE.toString());
        int index = selectionTrends.indexOf(keyValuePair);
        if (index != -1) {
            selectionTrends.get(index).setValue(Boolean.TRUE.toString());
            return true;
        }
        return false;
    }

    public void createSelectionTrends(List<String> selectedDates) {
        for (String selectedDate : selectedDates) {
            addSelectionTrends(selectedDate);
        }
    }

    public String getCategory() {
        return category;
    }

    public Integer getCausedConflict() {
        return causedConflict;
    }

    public String getConflictDescription() {
        return conflictDescription;
    }

    public Integer getControlledByUserId() {
        return controlledByUserId;
    }

    public String getControlledByUsername() {
        return controlledByUsername;
    }

    public Integer getEnergy() {
        return energy;
    }

    public Integer getFkDietMenuId() {
        return fkDietMenuId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getImageTumbLink() {
        if (!StringUtils.isEmpty(imageLink)) {
            int lastIndexOf = imageLink.lastIndexOf("/");
            if (lastIndexOf >= 0) {
                return imageLink.substring(0, lastIndexOf) + "/tumbs" + imageLink.substring(lastIndexOf, imageLink.length());
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Integer getPreparedByUserId() {
        return preparedByUserId;
    }

    public Integer getPrimaryKeyId() {
        return primaryKeyId;
    }

    public int getSelectedCount() {
        return selectedCount;
    }

    public List<KeyValuePair> getSelectionTrends() {
        return selectionTrends;
    }

    public boolean hasConflict() {
        return causedConflict != null && causedConflict == 1 ? true : false;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Where 0 is false and 1 is true
     * 
     * @param causedConflict
     */
    public void setCausedConflict(Integer causedConflict) {
        this.causedConflict = causedConflict;
    }

    public void setConflictDescription(String conflictDescription) {
        this.conflictDescription = conflictDescription;
    }

    public void setControlledByUserId(Integer controlledByUserId) {
        this.controlledByUserId = controlledByUserId;
    }

    public void setControlledByUsername(String controlledByUsername) {
        this.controlledByUsername = controlledByUsername;
    }

    public Integer getNotFollowedRule() {
        return notFollowedRule;
    }

    public void setNotFollowedRule(Integer notFollowedRule) {
        this.notFollowedRule = notFollowedRule;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public void setFkDietMenuId(Integer fkDietMenuId) {
        this.fkDietMenuId = fkDietMenuId;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setPreparedByUserId(Integer preparedByUserId) {
        this.preparedByUserId = preparedByUserId;
    }

    public void setPrimaryKeyId(Integer primaryKeyId) {
        this.primaryKeyId = primaryKeyId;
    }

    public void setSelectedCount(int selectedCount) {
        this.selectedCount = selectedCount;
    }

    public void setSelectionTrends(List<KeyValuePair> selectionTrends) {
        this.selectionTrends = selectionTrends;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MenuItem [category=");
        builder.append(category);
        builder.append("\n, id=");
        builder.append(getId());
        builder.append("\n, createdDate=");
        builder.append(getCreatedDate());
        builder.append("\n, causedConflict=");
        builder.append(causedConflict);
        builder.append("\n, notFollowedRule=");
        builder.append(notFollowedRule);
        builder.append("\n, conflictDescription=");
        builder.append(conflictDescription);
        builder.append("\n, controlledByUserId=");
        builder.append(controlledByUserId);
        builder.append("\n, controlledByUsername=");
        builder.append(controlledByUsername);
        builder.append("\n, energy=");
        builder.append(energy);
        builder.append("\n, fkDietMenuId=");
        builder.append(fkDietMenuId);
        builder.append("\n, imageLink=");
        builder.append(imageLink);
        builder.append("\n, preparedByUserId=");
        builder.append(preparedByUserId);
        builder.append("\n, primaryKeyId=");
        builder.append(primaryKeyId);
        builder.append("\n, selectedCount=");
        builder.append(selectedCount);
        builder.append("\n, selectionTrends=");
        builder.append(selectionTrends);
        builder.append("]");
        return builder.toString();
    }

}
