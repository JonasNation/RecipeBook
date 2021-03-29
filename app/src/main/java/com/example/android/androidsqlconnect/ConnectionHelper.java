package com.example.android.androidsqlconnect;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * ConnectionHelper purpose is to connect the android app to an SQL database
 * using a JDBC driver.
 * The Connectionhelper must be instantiated on main activity java file before
 * it can be used.
 */

public class ConnectionHelper {
    Connection con;
    String username, password, ip, port, database;

    @SuppressLint("NewApi")
    public Connection connectionclass() {
        // credentials to sign into my Azure SQL server
        ip = "recipeserver.database.windows.net";
        port = "1433";
        database = "recipeDB";
        username = "jonasNation@recipeserver";
        password = "Liamadrianmorgan81";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {
            // driver used for connection
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            // connection string to SQL database
            ConnectionURL = "jdbc:jtds:sqlserver://"+ip+":"+port+";"+"databasename="+database+";user="+username+";password="+password+";encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            // the Driver Manager gets the connection using the connection string assigned variable ConnectionURL
            connection = DriverManager.getConnection(ConnectionURL);
        }catch (Exception ex) {
            Log.e("Error; ", ex.getMessage());
        }
        return connection;
    }
}
