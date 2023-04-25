package edu.school21.chat.repositories;

import edu.school21.chat.models.Room;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

    private Connection connection;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public Optional<Message> findById(int id) {
        return findById(new Long(id));
    }

    @Override
    public Optional<Message> findById(Long id) {
        Message res = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String SQL = "SELECT messages.id as id, users.id as user_id, login, password, chatrooms.id as rooms_id, name, text, date_time from messages inner join chatrooms on chatrooms.id = chatroom_id inner join users on users.id = author_id where messages.id = ?";
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                User author = new User(resultSet.getLong("user_id"), resultSet.getString("login"), resultSet.getString("password"), null, null);
                Room room = new Room(resultSet.getLong("rooms_id"), resultSet.getString("name"), null, null);
                LocalDateTime date_time = LocalDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getTimestamp("date_time").getTime()), ZoneOffset.UTC);
                res = new Message(resultSet.getLong("id"), author, room, resultSet.getString("text"), date_time);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }


        return Optional.of(res);
    }

    public void save(Message message) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String SQL = "SELECT * FROM users where id = ? AND login = ? AND password = ?";
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, message.getAuthor().getId());
            pstmt.setString(2, message.getAuthor().getLogin());
            pstmt.setString(3, message.getAuthor().getPassword());
            resultSet = pstmt.executeQuery();
            if (!resultSet.next())
                throw new NotSavedSubEntityException("Author not found!");
            SQL = "SELECT * FROM chatrooms inner join users_chatrooms on room_id = Chatrooms.id where id = ? AND name = ? AND user_id = ?";
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, message.getRoom().getId());
            pstmt.setString(2, message.getRoom().getName());
            pstmt.setLong(3, message.getAuthor().getId());
            resultSet = pstmt.executeQuery();
            if (!resultSet.next())
                throw new NotSavedSubEntityException("Chatroom not found!");
            SQL = "INSERT INTO messages (author_id, chatroom_id, text, date_time) VALUES (  ? , ?, ?, ?)";
            pstmt = connection.prepareStatement(SQL, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, message.getAuthor().getId());
            pstmt.setLong(2, message.getRoom().getId());
            pstmt.setString(3, message.getText());
            pstmt.setObject(4, message.getDateTime());
            pstmt.executeUpdate();
            connection.commit();
            resultSet = pstmt.getGeneratedKeys();
            if (resultSet.next())
                message.setId(resultSet.getLong("id"));
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public void update(Message message) {
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            String SQL = "UPDATE messages SET author_id = ? , chatroom_id = ?, text = ? , date_time = ? WHERE id = ?";
            pstmt = connection.prepareStatement(SQL);
            pstmt.setLong(1, message.getAuthor().getId());
            pstmt.setLong(2, message.getRoom().getId());
            pstmt.setString(3, message.getText());
            pstmt.setObject(4, message.getDateTime());
            pstmt.setLong(5, message.getId());
            pstmt.executeUpdate();
            connection.commit();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

    }

    public class NotSavedSubEntityException extends RuntimeException {
        public NotSavedSubEntityException(String errorMessage) {
            super(errorMessage);
        }
    }
}
