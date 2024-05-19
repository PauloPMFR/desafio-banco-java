package com.example.restservice.entities;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nome;
    private String senha;
    @OneToOne
    @JoinColumn(name="perfil_id")
    private Perfil perfil;
    private String conta;
    private double saldo;



    public Usuario() {

    }

    public Integer getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }
    public String getSenha(){return this.senha;}
    public Perfil getPerfil_id(){return this.perfil;}

    public double getSaldo() {return this.saldo;}

    public String getConta() {  return this.conta;}


}



