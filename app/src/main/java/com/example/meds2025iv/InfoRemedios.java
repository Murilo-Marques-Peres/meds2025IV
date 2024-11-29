package com.example.meds2025iv;

public class InfoRemedios {
    private String nome;
    private String info;

    public InfoRemedios(String nome, String info){
        this.nome = nome;
        this.info = info;
    }
    public String getNome(){
        return nome;
    }
    public void setNome(){
        this.nome = nome;
    }
    public String getInfo(){
        return info;
    }
    public void setInfo(){
        this.info = info;
    }
}
