package pims.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pims.util.DBConnection;

public class ReportDAO {

    //For sales report
    public List<Object[]> getSalesReport() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT s.sale_id, s.sale_date, u.full_name, s.total_amount " +
                     "FROM sales s LEFT JOIN users u ON s.user_id = u.user_id " +
                     "ORDER BY s.sale_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("sale_id"),
                    rs.getTimestamp("sale_date"),
                    rs.getString("full_name"),
                    rs.getBigDecimal("total_amount")
                });
            }
        }
        return list;
    }

    // for item sales report
    public List<Object[]> getItemWiseSalesReport() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT m.name, SUM(si.quantity_sold) AS total_qty, " +
                     "SUM(si.quantity_sold * si.price_at_sale) AS total_revenue " +
                     "FROM sale_items si JOIN medicines m ON si.medicine_id = m.medicine_id " +
                     "GROUP BY m.medicine_id, m.name " +
                     "ORDER BY total_qty DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getString("name"),
                    rs.getInt("total_qty"),
                    rs.getBigDecimal("total_revenue")
                });
            }
        }
        return list;
    }

    //for low stock report ----------
    public List<Object[]> getLowStockReport() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT medicine_id, name, quantity_in_stock, reorder_level " +
                     "FROM medicines WHERE quantity_in_stock <= reorder_level " +
                     "ORDER BY quantity_in_stock ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("medicine_id"),
                    rs.getString("name"),
                    rs.getInt("quantity_in_stock"),
                    rs.getInt("reorder_level")
                });
            }
        }
        return list;
    }

    //for expiry report
    public List<Object[]> getExpiryReport() throws SQLException {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT medicine_id, name, expiry_date, quantity_in_stock " +
                     "FROM medicines " +
                     "WHERE expiry_date <= DATE_ADD(CURDATE(), INTERVAL 1 MONTH) " +
                     "ORDER BY expiry_date ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Object[]{
                    rs.getInt("medicine_id"),
                    rs.getString("name"),
                    rs.getDate("expiry_date"),
                    rs.getInt("quantity_in_stock")
                });
            }
        }
        return list;
    }
}