package com.firstprog.pereplats.domain;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Entity
@Scope("session")
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    private String userid;
    private String username;
    @Transient
    private String user_fio;
    private String user_tel;
    @Transient
    private String upravlenie;
    private String uprcode;

    @Transient
    private String role;
    private int active;
    private Date date;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User(int active, String userid, String username, String user_tel, String upravlenie, String uprcode, String role, Date date) {
        this.active = active;
        this.userid = userid;
        this.username = username;
        this.user_tel = user_tel;
        this.upravlenie = upravlenie;
        this.uprcode = uprcode;
        this.role = role;
        this.date = date;
    }

    public User() {
    }

    public User(String username) {
        this.username = username;
        this.active = 0;
        this.date = new Date();
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    public String getUser_fio() {
        return user_fio;
    }

    public void setUser_fio(String user_fio) {
        this.user_fio = user_fio;
    }

    public String getUpravlenie() {
        return upravlenie;
    }

    public void setUpravlenie(String upravlenie) {
        this.upravlenie = upravlenie;
    }

    public String getUprcode() {
        return uprcode;
    }

    public void setUprcode(String uprcode) {
        this.uprcode = uprcode;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public int isActive() {
        return active;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format("'%s','%s','%s','%s','%s','%s','%s','%s']", active, userid, username, uprcode, role, user_tel, date);
    }

}


