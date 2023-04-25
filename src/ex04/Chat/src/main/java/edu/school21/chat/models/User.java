package edu.school21.chat.models;

import java.util.Objects;
import java.util.List;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Room> createdRooms;
    private List<Room> visitedRooms;

    public User(Long id, String login, String password, List<Room> createdRooms, List<Room> visitedRooms) {
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

    public List<Room> getCreatedRooms() {
        return createdRooms;
    }

    public List<Room> getVisitedRooms() {
        return visitedRooms;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedRooms(List<Room> createdRooms) {
        this.createdRooms = createdRooms;
    }

    public void setVisitedRooms(List<Room> visitedRooms) {
        this.visitedRooms = visitedRooms;
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

