package org.fsb.CRUD;

import org.fsb.DAO.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CRUD {

    // Allow you to get data in the fields you want
    public  static Map<String, List<String> > getData(String tableName, String...f) {

        Map<String, List<String>> data = new HashMap<>();
        List<String> fields = (f.length == 0) ? utils.getTableSchema(tableName) : utils.acceptList(f);
        String field = String.join(", ", fields);

        String sql = "SELECT " + field + " FROM " + tableName ;

        try {
            Connection conn = DBconnection.getInstance();
            PreparedStatement preparedStm = conn.prepareStatement(sql);
            ResultSet rs = preparedStm.executeQuery();

            while (rs.next()) {
                for (String fieldName : fields) {
                    data.compute(fieldName, (k,v) -> v == null ? new ArrayList<>() : v).add(rs.getString(fieldName));
                }
            }
        }   catch (Exception e) {
                System.out.println(e.getMessage());
            }
        return data;
    }

    // Allow you to get Data in the fields you want with specific filter
    public  static Map<String, List<String>> getDataWithFilter(String tableName, String filter, String value, String...f) {

        Map<String, List<String>> data = new HashMap<>();
        List<String> fields = (f.length == 0) ? utils.getTableSchema(tableName) : utils.acceptList(f);
        String field = String.join(", ", fields);

        String sql = "SELECT " + field + " FROM " + tableName + " WHERE " + filter + " = ? " ;

        try {
            Connection conn = DBconnection.getInstance();
            PreparedStatement preparedStm = conn.prepareStatement(sql);
            preparedStm.setString(1, value);
            ResultSet rs = preparedStm.executeQuery();

            while (rs.next()) {
                for (String fieldName : fields) {
                    data.compute(fieldName, (k,v) -> v == null ? new ArrayList<>() : v).add(rs.getString(fieldName));
                }
            }
        }   catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }


    // Allow you to update the fields you want with specific id
    public static void updateDataWithID(String tableName,int id, Object... fieldsValuesPair) {
        Map<String, List<String>> data = new HashMap<>();

        List<String> fields = utils.acceptPair(fieldsValuesPair).get(0);
        List<String> values = utils.acceptPair(fieldsValuesPair).get(1);

        String field = String.join(" = ? , ", fields);
        String sql = "update " + tableName + " set " + field + " = ? WHERE id = ?";

        try {
            Connection conn = DBconnection.getInstance();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            for (int i = 0; i < fields.size(); i++) {
                preparedStatement.setString(i + 1, values.get(i));
            }

            preparedStatement.setInt(fields.size() + 1, id);
            preparedStatement.executeUpdate();

        }   catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Allow you to update fields you want with specific filter
    public static Map<String, List<String>> updateData(String tableName,String filter, String fValue, Object... fieldsValuesPair ) {
        Map<String, List<String>> data = new HashMap<>();

        List<String> fields = utils.acceptPair(fieldsValuesPair).get(0);
        List<String> values = utils.acceptPair(fieldsValuesPair).get(1);

        String field = String.join(" = ? , ", fields);
        String sql = "update " + tableName + " set " + field + " = ? where " + filter + " = ?";

        try {
            Connection conn = DBconnection.getInstance();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            for (int i = 0; i < fields.size(); i++) {
                preparedStatement.setString(i + 1, values.get(i));
            }

            preparedStatement.setString(fields.size() + 1, fValue);
            preparedStatement.executeUpdate();

        }   catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    // Allow you to update fields you want with specific filter
    public static void insertData(String tableName, String... valuesQuery ) {
        Map<String, List<String>> data = new HashMap<>();

        List<String> fields = utils.getTableSchema(tableName, "id");
        List<String> values = utils.acceptList(valuesQuery);

        String sym = fields.stream().map(x -> "?, ").collect(Collectors.joining());
        sym = sym.substring(0, fields.isEmpty() ? 1 : sym.length()-2);

        String field = String.join(", ", fields);
        String sql = "INSERT INTO " + tableName + " (" + field + ") VALUES (" + sym + ") ";

        try {
            Connection conn = DBconnection.getInstance();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            for (int i = 0; i < fields.size(); i++) {
                preparedStatement.setString(i + 1, values.get(i));
            }

            for (int i = 0; i < fields.size(); i++) {
                preparedStatement.setString(i + 1, values.get(i));
            }
            preparedStatement.executeUpdate();

        }   catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

