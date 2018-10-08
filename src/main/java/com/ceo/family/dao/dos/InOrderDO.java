package com.ceo.family.dao.dos;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "in_order")
public class InOrderDO {
    private long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String model;
    private int status;
    private Timestamp stoneDate;
    private long totalCount;
    private long currentCount;
    private long sellCount;


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
    @Column(name = "model", nullable = false, length = 100)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
    @Column(name = "stone_date", nullable = false)
    public Timestamp getStoneDate() {
        return stoneDate;
    }

    public void setStoneDate(Timestamp stoneDate) {
        this.stoneDate = stoneDate;
    }

    @Basic
    @Column(name = "total_count", nullable = false)
    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    @Basic
    @Column(name = "current_count", nullable = false)
    public long getCurrentCount() {
        return currentCount;
    }

    public void setCurrentCount(long currentCount) {
        this.currentCount = currentCount;
    }

    @Basic
    @Column(name = "sell_count", nullable = false)
    public long getSellCount() {
        return sellCount;
    }

    public void setSellCount(long sellCount) {
        this.sellCount = sellCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InOrderDO inOrderDO = (InOrderDO) o;
        return id == inOrderDO.id &&
                status == inOrderDO.status &&
                totalCount == inOrderDO.totalCount &&
                currentCount == inOrderDO.currentCount &&
                sellCount == inOrderDO.sellCount &&
                Objects.equals(createdAt, inOrderDO.createdAt) &&
                Objects.equals(updatedAt, inOrderDO.updatedAt) &&
                Objects.equals(model, inOrderDO.model) &&
                Objects.equals(stoneDate, inOrderDO.stoneDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createdAt, updatedAt, model, status, stoneDate, totalCount, currentCount, sellCount);
    }
}
