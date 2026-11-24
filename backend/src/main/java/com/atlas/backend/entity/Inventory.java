package com.atlas.backend.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id",nullable = false)
    private Warehouse warehouse;



    @Column(name="available_quantity",nullable = false)
    private int availableQuantity;

    @Column(name = "reserved_quantity",nullable = false)
    private int reservedQuantity;

    @Column(name = "created_at",nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate(){
        createdAt=LocalDateTime.now();
        updatedAt=createdAt;
    }

    @PreUpdate
    void onUpdate(){
        updatedAt=LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }


    public Product getProduct(){
        return product;
    }

    public void setProduct(Product product){
        this.product=product;
    }

    public Warehouse getWarehouse(){
        return warehouse;
    }

    public void setWarehouse(Warehouse warehouse){
        this.warehouse=warehouse;
    }

    public int getAvailableQuantity(){
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity){
        this.availableQuantity=availableQuantity;
    }


    public int getReservedQuantity(){
        return reservedQuantity;
    }

    public void setReservedQuantity(int reservedQuantity){
        this.reservedQuantity=reservedQuantity;
    }


    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }



}
