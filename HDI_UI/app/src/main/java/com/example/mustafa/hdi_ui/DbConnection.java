package com.example.mustafa.hdi_ui;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.*;

public class DbConnection {

    @SuppressLint("NewApi")
    public Connection connection(){
        final String driver = "com.mysql.jdbc.Driver";
        final String DBUN = "User Name Here";
        final String DBP = "Password here";
        final String URL = "URL HERE";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        java.sql.Connection connection = null;

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, DBUN, DBP);
        }catch (SQLException se) {
            Log.e("Eror fom SQL", se.getMessage());
        }catch (ClassNotFoundException e) {
            Log.e(("Eror from class"), e.getMessage());
        }catch (Exception ex) {
            Log.e(("Eror from exception"), ex.getMessage());
        }
        return connection;


    }
}
