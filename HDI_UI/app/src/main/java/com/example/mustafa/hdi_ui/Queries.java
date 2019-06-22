package com.example.mustafa.hdi_ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Queries {
    Connection connection;
    String result;
    Boolean isSucces = false;

    public List<Map<String,String>> mainPage(){
        List<Map<String,String>> data = null;
        data = new ArrayList<Map<String,String>>();
        try {
            DbConnection conn = new DbConnection();
            connection = conn.connection();
            if(connection == null){
                result = "Check Internet Connection";
            }else {
                String querry = "select Country, `HDI Rank`, HDI from hdi.ui_view;";
                Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery(querry);

                while (rs.next()) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("Country", rs.getString("Country"));
                    datanum.put("HDI Rank", rs.getString("HDI Rank"));
                    datanum.put("HDI", rs.getString("HDI"));
                    data.add(datanum);
                }
                result = "Succesfull";
                isSucces = true;
                connection.close();
            }
        }catch (Exception e){
            isSucces = false;
            result = e.getMessage();
        }
        return data;
    }


    public List<Map<String,String>> seconPage(String country){
        List<Map<String,String>> data = null;
        data = new ArrayList<Map<String,String>>();
        try {
            DbConnection conn = new DbConnection();
            connection = conn.connection();
            if(connection == null){
                result = "Check Internet Connection";
            }else {
                String querry = "CALL hdi.getCountryStats('" + country + "');";
                Statement stm = connection.createStatement();
                ResultSet rs = stm.executeQuery(querry);

                while (rs.next()) {
                    Map<String, String> datanum = new HashMap<String, String>();
                    datanum.put("StatTypeName", rs.getString("StatTypeName"));
                    datanum.put("Value", rs.getString("Value"));
                    data.add(datanum);
                }
                result = "Succesfull";
                isSucces = true;
                connection.close();
            }
        }catch (Exception e){
            isSucces = false;
            result = e.getMessage();
        }
        return data;
    }

}
