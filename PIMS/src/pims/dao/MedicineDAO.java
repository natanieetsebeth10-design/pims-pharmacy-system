package pims.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import pims.model.Medicine;
import pims.util.DBConnection;

public class MedicineDAO {

    public void addMedicine(Medicine m) throws SQLException {
        String sql = "INSERT INTO medicines (name, company, medicine_type, price, quantity_in_stock, reorder_level, expiry_date, supplier_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getName());
            stmt.setString(2, m.getCompany());
            stmt.setString(3, m.getMedicineType());
            stmt.setBigDecimal(4, m.getPrice());
            stmt.setInt(5, m.getQuantityInStock());
            stmt.setInt(6, m.getReorderLevel());
            stmt.setDate(7, m.getExpiryDate());

            if (m.getSupplierId() != null) {
                stmt.setInt(8, m.getSupplierId());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            stmt.executeUpdate();
        }
    }

    public List<Medicine> getAllMedicines() throws SQLException {
        List<Medicine> list = new ArrayList<>();
        String sql = "SELECT * FROM medicines ORDER BY name";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        }
        return list;
    }

    public void updateMedicine(Medicine m) throws SQLException {
        String sql = "UPDATE medicines SET name=?, company=?, medicine_type=?, price=?, " +
                     "quantity_in_stock=?, reorder_level=?, expiry_date=?, supplier_id=? " +
                     "WHERE medicine_id=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, m.getName());
            stmt.setString(2, m.getCompany());
            stmt.setString(3, m.getMedicineType());
            stmt.setBigDecimal(4, m.getPrice());
            stmt.setInt(5, m.getQuantityInStock());
            stmt.setInt(6, m.getReorderLevel());
            stmt.setDate(7, m.getExpiryDate());

            if (m.getSupplierId() != null) {
                stmt.setInt(8, m.getSupplierId());
            } else {
                stmt.setNull(8, Types.INTEGER);
            }

            stmt.setInt(9, m.getMedicineId());
            stmt.executeUpdate();
        }
    }

    public void deleteMedicine(int medicineId) throws SQLException {
        String sql = "DELETE FROM medicines WHERE medicine_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, medicineId);
            stmt.executeUpdate();
        }
    }

    private Medicine mapRow(ResultSet rs) throws SQLException {
        Integer supplierId = rs.getObject("supplier_id") != null ? rs.getInt("supplier_id") : null;

        return new Medicine(
            rs.getInt("medicine_id"),
            rs.getString("name"),
            rs.getString("company"),
            rs.getString("medicine_type"),
            rs.getBigDecimal("price"),
            rs.getInt("quantity_in_stock"),
            rs.getInt("reorder_level"),
            rs.getDate("expiry_date"),
            supplierId
        );
    }
}