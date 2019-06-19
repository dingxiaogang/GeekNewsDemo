package com.example.simpleshopping;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class CollectBean {
    @Id(autoincrement = true)
    @Property
    private Long id;
    @Property
    private String imagePath;
    @Property
    private String name;
    @Property
    private double price;
    @Generated(hash = 1021831771)
    public CollectBean(Long id, String imagePath, String name, double price) {
        this.id = id;
        this.imagePath = imagePath;
        this.name = name;
        this.price = price;
    }
    @Generated(hash = 420494524)
    public CollectBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CollectBean{" +
                "id=" + id +
                ", imagePath='" + imagePath + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
