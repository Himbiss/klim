package de.himbiss.klim;

import de.himbiss.klim.servlets.beans.Comment;
import de.himbiss.klim.servlets.beans.Photo;
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
                User friend = getUserById(id);
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

    public boolean deletePosting(int postId) {
        try {
            String query = "DELETE FROM `posts` WHERE id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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

    public int createPhoto(int userId, String caption) {
        try {
            String query = "INSERT INTO `photos` (`user`, `caption`, `upload_time`) VALUES (?, ?, CURRENT_TIMESTAMP);";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, userId);
            preparedStatement.setString(2, caption);
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

    public List<Photo> getAllPhotosOfUser(int userId) {
        try {
            String query = "SELECT * FROM `photos` where `user` = ? ORDER BY `upload_time` DESC;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Photo> photos = new LinkedList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String caption = resultSet.getString("caption").replace("\n", "<br>");
                Date uploadTime = resultSet.getDate("upload_time");
                photos.add(new Photo(id, userId, caption, uploadTime));
            }
            return photos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Photo> getAllPhotosOfPost(int postId) {
        try {
            String query = "SELECT * FROM photos JOIN posts_photos on id=photo_id where post_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, postId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Photo> photos = new LinkedList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user");
                String caption = resultSet.getString("caption").replace("\n", "<br>");
                Date uploadTime = resultSet.getDate("upload_time");
                photos.add(new Photo(id, userId, caption, uploadTime));
            }
            return photos;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean followUser (int userId, int followerId) {
        try {
            String query = "INSERT INTO `friends` (`user_id`, `friend_id`) VALUES (?, ?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, followerId);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean unfollowUser (int userId, int followerId) {
        try {
            String query = "DELETE FROM `friends` WHERE user_id = ? AND friend_id = ?;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, followerId);
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
                return new Post(id, getUserById(creator), postedTo, creationTime, content, getAllPhotosOfPost(id));
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Photo getPhoto(int photoId) {
        try {
            String query = "SELECT * FROM photos where id = ? ORDER BY upload_time DESC;";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, photoId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("user");
                String caption = resultSet.getString("caption").replace("\n", "<br>");
                Date uploadTime = resultSet.getDate("upload_time");
                return new Photo(photoId, userId, caption, uploadTime);
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
                posts.add(new Post(id, getUserById(creator), postedTo, creationTime, content, getAllPhotosOfPost(id)));
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

    public User getUserByUserName(String username) {
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
                String role = resultSet.getString("role");
                return new User(id, username, Arrays.asList(description.split("::")), avatar, background, creationTime, email, role);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User getUser(String userIdOrUserName) {
        char c = userIdOrUserName.charAt(0);
        if (c <= '9' && c >='0') {
            return getUserById(Integer.parseInt(userIdOrUserName));
        }
        else {
            return getUserByUserName(userIdOrUserName);
        }
    }

    public User getUserById(int userId) {
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
                String role = resultSet.getString("role");
                return new User(userId, username, Arrays.asList(description.split("::")), avatar, background, creationTime, email, role);
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
