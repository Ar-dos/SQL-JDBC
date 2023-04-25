package edu.school21.chat.repositories;

import edu.school21.chat.models.Room;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

    private Connection connection;

    public UsersRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public List<User> findAll(int page, int size) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        List<User> res = new ArrayList<>();
        try {
            String SQL = "SELECT * FROM users ORDER BY id ASC";
            pstmt = connection.prepareStatement(SQL);
            resultSet = pstmt.executeQuery();
            while (resultSet.next())
                res.add(new User(resultSet.getLong("id"), resultSet.getString("login"), resultSet.getString("password"), new ArrayList<>(), new ArrayList<>()));

            SQL = "SELECT *  FROM chatrooms ORDER BY owner_id , id ASC";
            pstmt = connection.prepareStatement(SQL);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                for (int i = 0; i < res.size(); i++) {
                    if (res.get(i).getId() == resultSet.getLong("owner_id")) {
                        Room room = new Room(resultSet.getLong("id"), resultSet.getString("name"), res.get(i), null);
                        res.get(i).getCreatedRooms().add(room);
                    }

                }
            }
            SQL = "SELECT * FROM users_chatrooms INNER JOIN chatrooms on room_id = chatrooms.id  ORDER BY user_id , room_id ASC";
            pstmt = connection.prepareStatement(SQL);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                for (int i = 0; i < res.size(); i++) {
                    if (res.get(i).getId() == resultSet.getLong("user_id")) {
                        Room room = new Room(resultSet.getLong("id"), resultSet.getString("name"), null, null);
                        for (int j = 0; j < res.size(); j++) {
                            if (res.get(j).getId() == resultSet.getLong("owner_id"))
                                room.setOwner(res.get(j));
                        }
                        res.get(i).getVisitedRooms().add(room);
                    }
                }

            }


        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return res.subList(page * size, page * size + size > res.size() ? res.size() : page * size + size);
    }

    public class NotSavedSubEntityException extends RuntimeException {
        public NotSavedSubEntityException(String errorMessage) {
            super(errorMessage);
        }
    }
}
