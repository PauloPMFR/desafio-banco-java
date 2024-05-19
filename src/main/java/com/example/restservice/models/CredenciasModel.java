package com.example.restservice.models;

public class CredenciasModel {
    private String senha;
    private String conta;
    public CredenciasModel(String conta,String senha){
        this.conta=conta;
        this.senha=senha;


    }
    public String getSenha(){return this.senha;}
    public String getConta() {  return this.conta;}
}

