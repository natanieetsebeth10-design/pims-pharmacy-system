package pims.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Medicine {
    private int medicineId;
    private String name;
    private String company;
    private String medicineType;
    private BigDecimal price;
    private int quantityInStock;
    private int reorderLevel;
    private Date expiryDate;
    private Integer supplierId;

    public Medicine(int medicineId, String name, String company, String medicineType,
                     BigDecimal price, int quantityInStock, int reorderLevel,
                     Date expiryDate, Integer supplierId) {
        this.medicineId = medicineId;
        this.name = name;
        this.company = company;
        this.medicineType = medicineType;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.reorderLevel = reorderLevel;
        this.expiryDate = expiryDate;
        this.supplierId = supplierId;
    }

    public int getMedicineId() { return medicineId; }
    public String getName() { return name; }
    public String getCompany() { return company; }
    public String getMedicineType() { return medicineType; }
    public BigDecimal getPrice() { return price; }
    public int getQuantityInStock() { return quantityInStock; }
    public int getReorderLevel() { return reorderLevel; }
    public Date getExpiryDate() { return expiryDate; }
    public Integer getSupplierId() { return supplierId; }
}