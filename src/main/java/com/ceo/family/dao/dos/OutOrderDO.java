package com.ceo.family.dao.dos;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "out_order")
public class OutOrderDO {
    private long id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int status;
    private long totalAmount;
    private long buyUserId;
    private Timestamp date;
    private long serailNo;

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
    @Column(name = "status", nullable = false)
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Basic
    @Column(name = "total_amount", nullable = false)
    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Basic
    @Column(name = "buy_user_id", nullable = false)
    public long getBuyUserId() {
        return buyUserId;
    }

    public void setBuyUserId(long buyUserId) {
        this.buyUserId = buyUserId;
    }

    @Basic
    @Column(name = "date", nullable = false)
    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Basic
    @Column(name = "serail_no", nullable = false)
    public long getSerailNo() {
        return serailNo;
    }

    public void setSerailNo(long serailNo) {
        this.serailNo = serailNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OutOrderDO that = (OutOrderDO) o;
        return id == that.id &&
                status == that.status &&
                totalAmount == that.totalAmount &&
                buyUserId == that.buyUserId &&
                serailNo == that.serailNo &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(updatedAt, that.updatedAt) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, createdAt, updatedAt, status, totalAmount, buyUserId, date, serailNo);
    }
}
