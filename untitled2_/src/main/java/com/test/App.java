package com.test;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException {
        DdConnection dbConnbection = new DdConnection();
        getDdData getDdData = new getDdData(dbConnbection.getConnection());
        getDdData.createDatabaseFromFile();
       // getDdData.insertToUser(3, "czxc");
        //getDdData.insertToPost(3, "abcd", 3);
       // getDdData.insertToComment(3, "comment", 3, 3);
        //getDdData.insertToLike(3, 3, 3, null);
        getDdData.getStatistics();
        getDdData.getUserDate(99);
    }
}
