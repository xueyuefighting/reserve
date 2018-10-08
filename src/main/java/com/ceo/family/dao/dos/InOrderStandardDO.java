package com.ceo.family.dao.dos;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "in_order_standard")
public class InOrderStandardDO {
    private long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private long inOrderId;
    private int status;
    private String standard;
    private long price;

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = false)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Basic
    @Column(name = "in_order_id", nullable = false)
    public long getInOrderId() {
        return inOrderId;
    }

    public void setInOrderId(long inOrderId) {
        this.inOrderId = inOrderId;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "standard", nullable = false, length = 100)
    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Basic
    @Column(name = "price", nullable = false)
    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InOrderStandardDO that = (InOrderStandardDO) o;
        return id == that.id &&
                inOrderId == that.inOrderId &&
                status == that.status &&
                price == that.price &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(standard, that.standard);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createdAt, updatedAt, inOrderId, status, standard, price);
    }
}
