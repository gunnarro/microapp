package com.gunnarro.sportsteam.domain.activity;

import java.io.Serializable;

public class Type implements Serializable {

    private static final long serialVersionUID = 6292046439999471027L;
    public final static String TYPE_DEFAULT = "DEFAULT";

    public static enum TounamentMatchTypes {
        GROUP_PLAY,
        QUARTER_FINAL_1,
        QUARTER_FINAL_2,
        QUARTER_FINAL_3,
        QUARTER_FINAL_4,
        SEMI_FINAL_1,
        SEMI_FINAL_2,
        BRONSE_FINAL,
        GOLD_FINAL;
    }
    public static enum MatchTypesEnum {
        LEAGUE(1, "LEAGUE"), TRAINING(2, "TRAINING"), CUP(3, "CUP"), TOURNAMENT(4, "TOURNAMENT");

        private String name;
        private int id;

        MatchTypesEnum(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    private Integer id;
    private String name;
    private String description;

    public Type() {
    }

    public Type(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDefaultType() {
        return this.name.equalsIgnoreCase(TYPE_DEFAULT);
    }

    public static Type createDefaultType() {
        Type type = new Type();
        type.setName(TYPE_DEFAULT);
        return type;
    }

    /**
     * Use by spring select list
     */
    @Override
    public String toString() {
        return name;
    }

}
