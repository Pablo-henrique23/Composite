package org.example;

public final class Setor implements ElementoHospitalar {
    private final String nome;
    private final int leitos;

    public Setor(String nome, int leitos) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do setor deve ser informado");
        }
        if (leitos < 0) {
            throw new IllegalArgumentException("Quantidade de leitos nao pode ser negativa");
        }
        this.nome = nome;
        this.leitos = leitos;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int totalLeitos() {
        return leitos;
    }

    @Override
    public String descrever(String recuo) {
        return recuo + "- Setor: " + nome + " (" + leitos + " leitos)";
    }
}
