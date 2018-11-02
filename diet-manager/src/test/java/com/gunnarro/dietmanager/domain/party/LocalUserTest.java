package com.gunnarro.dietmanager.domain.party;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.gunnarro.useraccount.domain.user.LocalUser;
import com.gunnarro.useraccount.domain.user.Privilege;
import com.gunnarro.useraccount.domain.user.Role;
import com.gunnarro.useraccount.repository.table.user.RolesTable.RolesEnum;

public class LocalUserTest {

    @Test
    public void newuser() {
        LocalUser localUser = new LocalUser();
        localUser.setUserId("userid");
        localUser.setUsername("username");
        localUser.setPassword("pwd");
        List<Role> roles = new ArrayList<Role>();
        Role role = new Role(2, RolesEnum.ROLE_USER.name());
        List<Privilege> privileges = new ArrayList<Privilege>();
        privileges.add(new Privilege(1, "READ_PRIVILEGE"));
        privileges.add(new Privilege(2, "WRITE_PRIVILEGE"));
        role.setPrivileges(privileges);
        roles.add(role);
        localUser.setRoles(roles);
        System.out.println(localUser.getRoles().get(0).getName());
        System.out.println(localUser.getRoles().size());
        System.out.println(localUser.isUser());
        System.out.println(localUser.isAdmin());
        System.out.println(localUser.isGuest());
    }
}
