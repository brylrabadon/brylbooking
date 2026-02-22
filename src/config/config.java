package config;

import java.sql.*;
import javax.swing.JTable;
import net.proteanit.sql.DbUtils;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class config {

    // 1. Connect to SQLite Database
    public Connection connectDB() {
    Connection con = null;
    try {
        Class.forName("org.sqlite.JDBC"); 
        // Adding the timeout parameter is crucial for SQLite
        con = DriverManager.getConnection("jdbc:sqlite:booking.db?busy_timeout=5000");
    } catch (Exception e) {
        System.out.println("Connection Failed: " + e.getMessage());
    }
    return con;
}
    
    public void addRecord(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        pstmt.executeUpdate();
        System.out.println("Record added successfully!");
    } catch (SQLException e) {
        System.out.println("Error adding record: " + e.getMessage());
    }
}
    public String authenticate(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("type");
            }
        }
    } catch (SQLException e) {
        System.out.println("Login Error: " + e.getMessage());
    }
    return null;
}


    // 2. Optimized Display Data (Handles Search & Loading)
    public void displayData(String sql, JTable table, Object... params) {
    // Try-with-resources ensures Connection and PreparedStatement close automatically
    try (Connection conn = connectDB();
         PreparedStatement pst = conn.prepareStatement(sql)) {
        
        // Correctly set parameters if any are passed
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
        }
        
        try (ResultSet rs = pst.executeQuery()) {
            // DbUtils.resultSetToTableModel reads the entire result into memory 
            // and populates the table, allowing the ResultSet to close safely.
            table.setModel(DbUtils.resultSetToTableModel(rs));
        }
        
    } catch (SQLException e) {
        System.out.println("Display Error: " + e.getMessage());
    }
}

    // 3. Universal GetData Method (Used for fetching Profile info)
    public ResultSet getData(String sql, Object... values) {
    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (int i = 0; i < values.length; i++) {
            pstmt.setObject(i + 1, values[i]);
        }

        try (ResultSet rs = pstmt.executeQuery()) {
            // This copies the result set into memory
            CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.populate(rs);
            return crs; // Connection and PreparedStatement close here, but data stays!
        }
    } catch (SQLException e) {
        System.out.println("Error fetching data: " + e.getMessage());
        return null;
    }
}

    // 4. Fixed Delete Method
    public int deleteData(String sql, Object... values) {
        int rowsDeleted = 0;
        try (Connection conn = connectDB();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < values.length; i++) {
                pst.setObject(i + 1, values[i]);
            }
            
            rowsDeleted = pst.executeUpdate();
            System.out.println("Delete Successful!");
        } catch (SQLException e) {
            System.out.println("Delete Error: " + e.getMessage());
        }
        return rowsDeleted;
    }

    // 5. FIXED Update/Execute Method
    public int executeUpdate(String sql, Object... params) {
        // FIX: Changed 'connect' to 'connectDB()'
        try (Connection conn = connectDB(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
            
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
            return pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Update Error: " + e.getMessage());
            return 0;
        }
    }

    // 6. Get Latest ID
    public int getLatestID(String sql) {
        int id = 0;
        try (Connection conn = connectDB();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error getting ID: " + e.getMessage());
        }
        return id;
    }
}