package de.himbiss.klim;

import de.himbiss.klim.servlets.beans.Comment;
import de.himbiss.klim.servlets.beans.Post;
import de.himbiss.klim.servlets.beans.User;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Vincent on 17.12.2016.
 */
public class DAO {
    private static final DAO instance = new DAO();

    public static DAO getInstance() {
        return instance;
    }

    private final Connection connection;

    private DAO() {
        Connection conn = null;
        try (InputStream is = new BufferedInputStream(new FileInputStream("jdbc.properties"))) {
            Properties connectionProps = new Properties();
            connectionProps.load(is);
            connectionProps.put("user", connectionProps.getProperty("username"));
            connectionProps.put("pwd", connectionProps.getProperty("password"));
            conn = DriverManager.getConnection(connectionProps.getProperty("url"), connectionProps);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        connection = conn;
        System.out.println("Connected to database");
    }

    public List<User> getAllFollowers(int userId) {
        try {
            String query = "SELECT * FROM friends WHERE user_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> friends = new LinkedList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("friend_id");
                User friend = getUser(id);
                if (friend != null) {
                    friends.add(friend);
                }
            }
            return friends;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int createPosting(int userId, int profileUserId, String content) {
        try {
            String query = "INSERT INTO `posts` (`creation_time`, `posted_to`, `creator`, `content`) VALUES (CURRENT_TIMESTAMP, ?, ?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, profileUserId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setString(3, content);
            int updateCount = preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (updateCount > 0 && generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean followUser (int userId, int friendId) {
        try {
            String query = "INSERT INTO `friends` (`user_id`, `friend_id`) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, friendId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unfollowUser (int userId, int friendId) {
        try {
            String query = "DELETE FROM `friends` WHERE user_id = ? AND friend_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, friendId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Post getPost(int postId) {
        try {
            String query = "SELECT * FROM posts where id = ? ORDER BY creation_time DESC;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int creator = resultSet.getInt("creator");
                Date creationTime = resultSet.getDate("creation_time");
                String content = resultSet.getString("content").replace("\n", "<br>");
                int postedTo = resultSet.getInt("posted_to");
                return new Post(id, creator, postedTo, creationTime, content);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Post> getAllPostsToUser(int userId) {
        try {
            String query = "SELECT * FROM posts where posted_to = ? ORDER BY creation_time DESC;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Post> posts = new LinkedList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int creator = resultSet.getInt("creator");
                Date creationTime = resultSet.getDate("creation_time");
                String content = resultSet.getString("content").replace("\n", "<br>");
                int postedTo = resultSet.getInt("posted_to");
                posts.add(new Post(id, creator, postedTo, creationTime, content));
            }
            return posts;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Comment> getAllComments(int postId) {
        try {
            String query = "SELECT * FROM comments where post_id = ? ORDER BY time ASC;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Comment> comments = new LinkedList<>();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                Date creationTime = resultSet.getDate("time");
                String content = resultSet.getString("content");
                comments.add(new Comment(id, postId, creationTime, content));
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    public User getUser(String username) {
        try {
            String query = "SELECT * FROM users WHERE username = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                Date creationTime = resultSet.getDate("create_time");
                String avatar = resultSet.getString("avatar");
                String background = resultSet.getString("background");
                String description = resultSet.getString("description");
                return new User(id, username, Arrays.asList(description.split("::")), avatar, background, creationTime, email);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUser(int userId) {
        try {
            String query = "SELECT * FROM users WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                Date creationTime = resultSet.getDate("create_time");
                String avatar = resultSet.getString("avatar");
                String background = resultSet.getString("background");
                String description = resultSet.getString("description");
                return new User(userId, username, Arrays.asList(description.split("::")), avatar, background, creationTime, email);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
