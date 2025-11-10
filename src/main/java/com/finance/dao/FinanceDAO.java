package com.finance.dao;

import com.finance.model.Transaction;
import java.sql.*;
import java.util.*;

public class FinanceDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/finance_tracker_db?useSSL=false&serverTimezone=UTC";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private static final String INSERT_SQL =
        "INSERT INTO transactions (type, category, amount, date, note) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM transactions ORDER BY date DESC";
    private static final String DELETE_SQL = "DELETE FROM transactions WHERE id = ?";
    private static final String TOTAL_INCOME_SQL = "SELECT SUM(amount) FROM transactions WHERE type='Income'";
    private static final String TOTAL_EXPENSE_SQL = "SELECT SUM(amount) FROM transactions WHERE type='Expense'";

    private Connection getConnection() throws SQLException {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public void addTransaction(Transaction t) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, t.getType());
            ps.setString(2, t.getCategory());
            ps.setDouble(3, t.getAmount());
            ps.setString(4, t.getDate());
            ps.setString(5, t.getNote());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setType(rs.getString("type"));
                t.setCategory(rs.getString("category"));
                t.setAmount(rs.getDouble("amount"));
                t.setDate(rs.getString("date"));
                t.setNote(rs.getString("note"));
                list.add(t);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public double getTotalIncome() {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(TOTAL_INCOME_SQL);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0.0;
    }

    public double getTotalExpense() {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(TOTAL_EXPENSE_SQL);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getDouble(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0.0;
    }

    public Map<String, Double> getExpenseByCategory() {
        Map<String, Double> map = new LinkedHashMap<>();
        String sql = "SELECT category, SUM(amount) AS total FROM transactions WHERE type='Expense' GROUP BY category";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) map.put(rs.getString("category"), rs.getDouble("total"));
        } catch (Exception e) { e.printStackTrace(); }
        return map;
    }

    public void deleteTransaction(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
