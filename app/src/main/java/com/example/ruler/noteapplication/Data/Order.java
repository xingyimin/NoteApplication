package com.example.ruler.noteapplication.Data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.ToOne;

import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Order {
    @Id private Long id;
    private Date date;
    private long customerId;
    @Generated(hash = 171035120)
    public Order(Long id, Date date, long customerId) {
        this.id = id;
        this.date = date;
        this.customerId = customerId;
    }
    @Generated(hash = 1105174599)
    public Order() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public long getCustomerId() {
        return this.customerId;
    }
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
