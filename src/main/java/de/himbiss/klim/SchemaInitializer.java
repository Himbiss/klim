package de.himbiss.klim;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by himbiss on 31.03.17.
 */
public class SchemaInitializer {
    private final Connection connection;

    SchemaInitializer(Connection connection) {
        this.connection = connection;
    }

    void initialize() {
        try {
            System.out.println("initializing");
            createSchema();
            createCommentsTable();
            createFriendsTable();
            createPostsTable();
            createUsersTable();
            createPhotosTable();
            createPostsToPhotosTable();
            insertAdminUser();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException("unable to initialize database :(");
        }
    }

    private void createPostsToPhotosTable() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE klim.posts_photos (\n" +
                "  photo_id INTEGER NOT NULL,\n" +
                "  post_id INTEGER NOT NULL\n" +
                ")");
    }

    private void insertAdminUser() throws SQLException {
        connection.createStatement().executeUpdate("INSERT INTO klim.users (username, email, password, avatar, background, description) VALUES ('admin', 'admin@pleasechangeme.com', 'MD5:21232f297a57a5a743894a0e4a801fc3', 'https://placehold.it/150x150', 'https://placehold.it/350x150', 'admin::loves food')");
    }

    private void createUsersTable() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE klim.users (\n" +
                "  username VARCHAR(100) NOT NULL,\n" +
                "  email VARCHAR(100) NOT NULL,\n" +
                "  password VARCHAR(50) NOT NULL,\n" +
                "  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "  avatar VARCHAR(100) DEFAULT NULL,\n" +
                "  background VARCHAR(100) DEFAULT NULL,\n" +
                "  description VARCHAR(255) DEFAULT NULL,\n" +
                "  role VARCHAR(255) DEFAULT NULL,\n" +
                "  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)\n" +
                ")");
    }

    private void createPostsTable() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE klim.posts (\n" +
                "  id INTEGER NOT NULL G ENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                "  creator INTEGER NOT NULL,\n" +
                "  creation_time TIMESTAMP NOT NULL,\n" +
                "  content CLOB NOT NULL,\n" +
                "  posted_to INTEGER NOT NULL\n" +
                ")");
    }

    private void createFriendsTable() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE klim.friends (\n" +
                "  user_id INTEGER NOT NULL,\n" +
                "  friend_id INTEGER NOT NULL\n" +
                ")");
    }

    private void createPhotosTable() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE klim.photos (\n" +
                "  creator VARCHAR(100) NOT NULL,\n" +
                "  caption VARCHAR(255) NOT NULL,\n" +
                "  upload_time TIMESTAMP NOT NULL,\n" +
                "  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1)\n" +
                ")");
    }

    private void createCommentsTable() throws SQLException {
        connection.createStatement().executeUpdate("CREATE TABLE klim.comments (\n" +
                "  id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                "  post_id VARCHAR(45) NOT NULL,\n" +
                "  time TIMESTAMP NOT NULL,\n" +
                "  content VARCHAR(45) NOT NULL\n" +
                ")");
    }

    private void createSchema() throws SQLException {
        connection.createStatement().executeUpdate("CREATE SCHEMA klim");
    }

    public boolean isInitialized() {
        String query = "SELECT * FROM SYS.SYSSCHEMAS";
        try {
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()) {
                String name = resultSet.getString("SCHEMANAME");
                System.out.println(name);
                if ("KLIM".equals(name)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
