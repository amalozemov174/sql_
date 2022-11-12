package com.test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.stream.Collectors;

public class getDdData {

    private final Connection connection;

    public getDdData(Connection connection) {
        this.connection = connection;
    }

    private String readSqlFile(String fileName) {
        InputStream inputStream = getDdData.class.getClassLoader().getResourceAsStream(fileName);
        return new BufferedReader(new InputStreamReader(inputStream)).lines().collect(Collectors.joining(""));
    }

    public Boolean createDatabaseFromFile() {
        try (Statement statement = connection.createStatement();) {
            boolean execute = statement.execute(readSqlFile("dataFile.sql"));
            return execute;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertToUser(int id, String password, String name) throws SQLException {
        Statement statement = connection.createStatement();
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
        String request = "INSERT INTO lesson1.user (id, password, created_at) " + "VALUES (" + id + ", '" + password + "', '" + formattedDateStr + "', '" + name + "');";
        boolean execute = statement.execute(request);
    }

    public void insertToPost(int id, String text, int user_id) throws SQLException {
        Statement statement = connection.createStatement();
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
        String request = "INSERT INTO lesson1.post (id, text, created_at, user_id) " + "VALUES (" + id + ", '" + text + "', '" + formattedDateStr + "', " + user_id + ");";
        boolean execute = statement.execute(request);
    }

    public void insertToComment(int id, String text, int user_id, int post_id) throws SQLException {
        Statement statement = connection.createStatement();
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
        String request = "INSERT INTO lesson1.comment (id, text, post_id, user_id, created_at) " + "VALUES (" + id + ", '" + text + "', " + post_id + ", " + user_id + ", '" + formattedDateStr + "');";
        boolean execute = statement.execute(request);
    }

    public void insertToLike(Integer id, Integer user_id, Integer post_id, Integer comment_id) throws SQLException {
        Statement statement = connection.createStatement();
        LocalDateTime ldt = LocalDateTime.now();
        String formattedDateStr = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(ldt);
        String request = "INSERT INTO lesson1.like (id, user_id, post_id, comment_id) " + "VALUES (" + id + ", " + user_id + ", " + post_id + ", " + comment_id + ");";
        boolean execute = statement.execute(request);
    }

    public void getStatistics() throws SQLException {
//        select count(id) from lesson1.user;
//        select count(id) from lesson1.post;
//        select count(id) from lesson1.comment;
//        select count(id) from lesson1.like;
        String users = "";
        String posts = "";
        String comments = "";
        String likes = "";
        Statement statement = connection.createStatement();
        ResultSet resultSetUsers = statement.executeQuery("select count(id) from lesson1.user;");
        while (resultSetUsers.next()) {
            users = resultSetUsers.getString(1);
        }

        ResultSet resultSetPosts = statement.executeQuery("select count(id) from lesson1.post;");
        while (resultSetPosts.next()) {
            posts = resultSetPosts.getString(1);
        }

        ResultSet resultSetComments = statement.executeQuery("select count(id) from lesson1.comment;");
        while (resultSetComments.next()) {
            comments = resultSetComments.getString(1);
        }

        ResultSet resultSetLike = statement.executeQuery("select count(id) from lesson1.like;");
        while (resultSetLike.next()) {
            likes = resultSetLike.getString(1);
        }

        System.out.println("Всего: количество пользователей - " + users +
                "\nколичество постов - " + posts +
                "\nколичество комментариев - " + comments +
                "\nколичество лайков  - " + likes);
    }

    public void getUserDate(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSetUsers = statement.executeQuery("select count(id) from lesson1.user;");
        String [] userInfo = new String[4];
        String countOperations = "";
        ResultSet resultUserInfo = statement.executeQuery(" select lesson1.user.name, lesson1.user.created_at, lesson1.post.text from lesson1.user\n" +
                " inner join lesson1.post on lesson1.user.id = lesson1.post.user_id\n" +
                " inner join lesson1.comment on lesson1.user.id = lesson1.comment.user_id\n" +
                " where lesson1.post.id = (select MIN(lesson1.post.id) from lesson1.post where user_id = " + id + " group by id);");
        while (resultUserInfo.next()) {
            int columnCount = resultUserInfo.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                userInfo[i] = resultUserInfo.getString(i);
            }
        }

        ResultSet resultCountOperations = statement.executeQuery("select count(id) from lesson1.comment where lesson1.comment.user_id = 1;");
        while (resultCountOperations.next()) {
            countOperations = resultCountOperations.getString(1);
        }

        if(userInfo[1] == null) {
            System.out.println("Пользователя не существует");

        }
        else {
            System.out.println("Пользователь - " + userInfo[1] +
                    "\nДата создания - " + userInfo[2] +
                    "\nСамый первый пост - " + userInfo[2] +
                    "\nКоличество комментов - " + countOperations);
        }

        statement.close();
    }
}
