package com.gunnarro.sportsteam.domain.party;

import java.util.Collection;

public class Role {

    public enum RoleTypesEnum {
        DEFAULT(1), PARENT(2), TEAMLEAD(3), COACH(4), CHAIRMAN(5), DEPUTY_CHAIRMAN(6), BOARD_MEMBER(7);
        int id;

        RoleTypesEnum(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static String[] names() {
            String[] names = new String[RoleTypesEnum.values().length];
            for (int i = 0; i < RoleTypesEnum.values().length; i++) {
                names[i] = RoleTypesEnum.values()[i].name();
            }
            return names;
        }

    }

    private int id;
    private int conctactId;
    private String role;
    private Collection<Privilege> privileges;

    public Role(int id, int conctactId, String role) {
        this.id = id;
        this.conctactId = conctactId;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    public int getConctactId() {
        return conctactId;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Role [id=" + id + ", conctactId=" + conctactId + ", role=" + role + "]";
    }

}
