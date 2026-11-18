package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class Departamento implements ElementoHospitalar {
    private final String nome;
    private final List<ElementoHospitalar> filhos = new ArrayList<>();

    public Departamento(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome do departamento deve ser informado");
        }
        this.nome = nome;
    }

    public void adicionar(ElementoHospitalar elemento) {
        Objects.requireNonNull(elemento, "elemento nao pode ser nulo");
        if (elemento == this || (elemento instanceof Departamento d && d.contem(this))) {
            throw new IllegalArgumentException("Nao e permitido criar ciclos na hierarquia");
        }
        filhos.add(elemento);
    }

    public boolean remover(ElementoHospitalar elemento) {
        return filhos.remove(elemento);
    }

    public List<ElementoHospitalar> getFilhos() {
        return List.copyOf(filhos);
    }

    private boolean contem(ElementoHospitalar procurado) {
        if (this == procurado) {
            return true;
        }
        for (ElementoHospitalar filho : filhos) {
            if (filho == procurado || (filho instanceof Departamento d && d.contem(procurado))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public int totalLeitos() {
        return filhos.stream().mapToInt(ElementoHospitalar::totalLeitos).sum();
    }

    @Override
    public String descrever(String recuo) {
        String cabecalho = recuo + "+ Departamento: " + nome;
        if (filhos.isEmpty()) {
            return cabecalho;
        }
        return cabecalho + "\n" + filhos.stream()
                .map(filho -> filho.descrever(recuo + "  "))
                .collect(Collectors.joining("\n"));
    }
}
