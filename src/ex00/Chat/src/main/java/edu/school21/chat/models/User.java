package edu.school21.chat.models;

import java.util.Objects;
import java.util.List;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdRooms;
    private List<Chatroom> visitedRooms;

    public User(Long id, String login, String password, List<Chatroom> createdRooms, List<Chatroom> visitedRooms) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdRooms = createdRooms;
        this.visitedRooms = visitedRooms;
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public List<Chatroom> getCreatedRooms() {
        return createdRooms;
    }

    public List<Chatroom> getVisitedRooms() {
        return visitedRooms;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id) && login.equals(user.login) && password.equals(user.password) && Objects.equals(createdRooms, user.createdRooms) && Objects.equals(visitedRooms, user.visitedRooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdRooms, visitedRooms);
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + id +
                ", Login='" + login + '\'' +
                ", Password='" + password + '\'' +
                ", CreatedRooms=" + createdRooms +
                ", VisitedRooms=" + visitedRooms +
                '}';
    }
}

