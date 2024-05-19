package com.example.restservice.entities;
import  jakarta.persistence.Entity;
import  jakarta.persistence.GeneratedValue;
import  jakarta.persistence.GenerationType;
import  jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;

@Entity
@Table(name = "extrato")
public class Extrato {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String descricao;
    private String senha;
    private Integer usuario_id;
    private String conta;
    private double valor;
    private LocalDateTime data;
    private String tipo_op;

    public Extrato() {

    }

    public Integer getId() {
        return this.id;
    }

    public String getDescricao() {
        return this.descricao;
    }
    public String getSenha(){return this.senha;}
    public Integer getUsuario_id_id(){return this.usuario_id;}
    public LocalDateTime getData(){return this.data;}
    public double getValor() {return this.valor;}
    public String getTipo_op() {
        return this.tipo_op;
    }
    public String getConta() {  return this.conta;}


}



