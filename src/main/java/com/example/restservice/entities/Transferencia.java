package com.example.restservice.entities;
import  jakarta.persistence.Entity;
import  jakarta.persistence.GeneratedValue;
import  jakarta.persistence.GenerationType;
import  jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "transferencias")
public class Transferencia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    private LocalDateTime data;
    private String conta_remetente;
    private String conta_destinatario;
    private Integer id_usuario_remetente;
    private Integer id_suario_destinatario;
    private double valor;

    public Transferencia() {

    }

    public Integer getId() {
        return this.id;
    }

    public String getConta_remetente() {
        return this.conta_remetente;
    }
    public String getConta_destinatario(){return this.conta_destinatario;}
    public LocalDateTime getData(){return this.data;}

    public double getValor() {return this.valor;}

    public Integer getId_usuario_remetente() {  return this.id_usuario_remetente;}
    public Integer getId_suario_destinatario() {  return this.id_suario_destinatario;}

}



