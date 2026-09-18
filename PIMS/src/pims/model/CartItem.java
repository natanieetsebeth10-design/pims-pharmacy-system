/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pims.model;

import java.math.BigDecimal;

/**
 *
 * @author natan
 */
public class CartItem {
    private Medicine medicine;
    private int quantity;

    public CartItem(Medicine medicine, int quantity) {
        this.medicine = medicine;
        this.quantity = quantity;
    }

    public Medicine getMedicine() { return medicine; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getSubtotal() {
        return medicine.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
