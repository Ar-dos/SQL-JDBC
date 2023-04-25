package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private Connection connection;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    @Override
    public Optional<Message> findById(Long id) {
        Message res = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            pstmt = connection.prepareStatement("SELECT messages.id, users.id as user_id, login, password, chatrooms.id as rooms_id, name, text, date_time from messages inner join chatrooms on chatrooms.id = chatroom_id inner join users on users.id = author_id where messages.id = ?");
            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                User author = new User(resultSet.getLong("user_id"), resultSet.getString("login"), resultSet.getString("password"), null, null);
                Chatroom room = new Chatroom(resultSet.getLong("rooms_id"), resultSet.getString("name"), null, null);
                LocalDateTime date_time = LocalDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getTimestamp("date_time").getTime()), ZoneOffset.UTC);

                res = new Message(resultSet.getLong("id"), author, room, resultSet.getString("text"), date_time);


            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }


        return Optional.of(res);
    }
}
