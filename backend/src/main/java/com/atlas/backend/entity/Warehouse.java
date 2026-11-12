package com.atlas.backend.entity;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="warehouses")
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;




    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 150)
    private String name;


    @Column(nullable = false, length = 255)
    private String location;


    @Column(nullable = false, length = 30)
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

     @PrePersist
     void createdAt(){
         createdAt=LocalDateTime.now();
         updatedAt=createdAt;
     }

     @PreUpdate
     void updatedAt(){
        updatedAt=LocalDateTime.now();
     }


    public Long getId(){
        return id;
    }



    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code=code;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }


    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location=location;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status=status;
    }


    public  LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
