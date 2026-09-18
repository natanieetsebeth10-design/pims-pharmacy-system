package pims.dao;

import java.sql.*;
import java.math.BigDecimal;
import java.util.List;
import pims.model.CartItem;
import pims.util.DBConnection;

public class SaleDAO {

    public int checkout(List<CartItem> cart, int userId, BigDecimal total) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            String saleSql = "INSERT INTO sales (total_amount, user_id) VALUES (?, ?)";
            int saleId;
            try (PreparedStatement ps = conn.prepareStatement(saleSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setBigDecimal(1, total);
                ps.setInt(2, userId);
                ps.executeUpdate();

                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        saleId = keys.getInt(1);
                    } else {
                        throw new SQLException("Failed to retrieve generated sale_id.");
                    }
                }
            }
            
            String itemSql = "INSERT INTO sale_items (sale_id, medicine_id, quantity_sold, price_at_sale) VALUES (?, ?, ?, ?)";
            String stockSql = "UPDATE medicines SET quantity_in_stock = quantity_in_stock - ? WHERE medicine_id = ? AND quantity_in_stock >= ?";

            try (PreparedStatement itemPs = conn.prepareStatement(itemSql);
                 PreparedStatement stockPs = conn.prepareStatement(stockSql)) {

                for (CartItem ci : cart) {
                    itemPs.setInt(1, saleId);
                    itemPs.setInt(2, ci.getMedicine().getMedicineId());
                    itemPs.setInt(3, ci.getQuantity());
                    itemPs.setBigDecimal(4, ci.getMedicine().getPrice());
                    itemPs.executeUpdate();

                    stockPs.setInt(1, ci.getQuantity());
                    stockPs.setInt(2, ci.getMedicine().getMedicineId());
                    stockPs.setInt(3, ci.getQuantity());
                    int updated = stockPs.executeUpdate();

                    if (updated == 0) {
                        
                        throw new SQLException("There is insufficient stock for " + ci.getMedicine().getName()
                                + ".");
                    }
                }
            }

            conn.commit();
            return saleId;

        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ignored) {}
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ignored) {}
            }
        }
    }
}