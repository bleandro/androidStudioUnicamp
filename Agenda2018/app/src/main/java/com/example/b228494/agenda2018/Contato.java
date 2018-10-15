package com.example.b228494.agenda2018;

public class Contato {
    private long _id;
    private String nome;
    private int telefone;
    private String email;

    public Contato() { }

    public Contato(long _id, String nome, int telefone, String email) {
        this._id = _id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "\nNome: " + this.getNome() +
               "\nTelefone: " + this.getTelefone() +
               "\nEmail: " + this.getEmail() +
               "\n";
    }
}
