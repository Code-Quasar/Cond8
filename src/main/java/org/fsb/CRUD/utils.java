package org.fsb.CRUD;

import org.fsb.DAO.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class utils {
    public static List<List<String>> acceptPair(Object... keyValuePairs) {
        List<String> fields = new ArrayList<>();
        List<String> values = new ArrayList<>();
        List<List<String>> data = new ArrayList<>();

        if (keyValuePairs.length % 2 != 0) {
            throw new IllegalArgumentException("Give correct number of parameters");
        }
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            fields.add(keyValuePairs[i].toString());
            values.add(keyValuePairs[i + 1].toString());
        }
        data.add(fields);
        data.add(values);
        return data;
    }
    public static List<String> acceptList(String... fields) {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, fields);
        return list;
    }
    public static List<String> getTableSchema(String tableName, String... exception) {
        List<String> list = new ArrayList<>();
        List<String> ex = Arrays.stream(exception).collect(Collectors.toList());
        String sql = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'TEST' AND TABLE_NAME = ?";

        try {
            Connection conn = DBconnection.getInstance();
             PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, tableName);
            ResultSet rs = preparedStatement.executeQuery();

            // Collect column names
            while (rs.next()) {
                if(!ex.contains(rs.getString("COLUMN_NAME"))) {
                    list.add(rs.getString("COLUMN_NAME"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
