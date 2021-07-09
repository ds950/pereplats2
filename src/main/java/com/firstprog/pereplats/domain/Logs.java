package com.firstprog.pereplats.domain;

import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.Date;

@Entity
@Scope("session")
@Table(name = "logs")
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    private Date date;
    private String username;
    private String description;


    public Logs(Date date, String username, String description) {
        this.date = date;
        this.username = username;
        this.description = description;
    }

    public Logs() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return String.format("'%s','%s','%s']", date, username, description);
    }

}


