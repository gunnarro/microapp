package com.gunnarro.sportsteam.domain.view;

public class SearchActivity {

    String type;
    String name;
    String period;
    String periodTxt;
    boolean reload;
    int amount;

    public SearchActivity() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodTxt() {
        return periodTxt;
    }

    public void setPeriodTxt(String periodTxt) {
        this.periodTxt = periodTxt;
    }

    public boolean isReload() {
        return reload;
    }

    public void setReload(boolean reload) {
        this.reload = reload;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
