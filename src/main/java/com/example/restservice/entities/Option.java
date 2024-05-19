package com.example.restservice.entities;
import  jakarta.persistence.Entity;
import  jakarta.persistence.GeneratedValue;
import  jakarta.persistence.GenerationType;
import  jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "options")
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String descricao;

   

    public Option() {

    }

    public Integer getId() {
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }
}


