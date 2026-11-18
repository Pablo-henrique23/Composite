package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompositeTest {

    @Test
    void setorDeveRetornarSuaQuantidadeDeLeitos() {
        Setor uti = new Setor("UTI", 12);
        assertEquals(12, uti.totalLeitos());
        assertEquals("UTI", uti.getNome());
    }

    @Test
    void departamentoDeveSomarLeitosDeSetoresEDepartamentos() {
        Departamento hospital = new Departamento("Hospital Central");
        Departamento urgencia = new Departamento("Urgencia");
        urgencia.adicionar(new Setor("UTI", 10));
        urgencia.adicionar(new Setor("Emergencia", 8));
        hospital.adicionar(urgencia);
        hospital.adicionar(new Setor("Clinica", 20));
        assertEquals(38, hospital.totalLeitos());
    }

    @Test
    void devePermitirAdicionarERemoverElementos() {
        Departamento departamento = new Departamento("Ala A");
        Setor enfermaria = new Setor("Enfermaria", 6);
        departamento.adicionar(enfermaria);
        assertEquals(6, departamento.totalLeitos());
        assertTrue(departamento.remover(enfermaria));
        assertEquals(0, departamento.totalLeitos());
        assertFalse(departamento.remover(enfermaria));
    }

    @Test
    void deveDescreverHierarquiaCompleta() {
        Departamento hospital = new Departamento("Hospital");
        Departamento ala = new Departamento("Ala B");
        ala.adicionar(new Setor("UTI", 4));
        hospital.adicionar(ala);
        String descricao = hospital.descrever("");
        assertTrue(descricao.contains("+ Departamento: Hospital"));
        assertTrue(descricao.contains("  + Departamento: Ala B"));
        assertTrue(descricao.contains("    - Setor: UTI (4 leitos)"));
    }

    @Test
    void deveImpedirCiclosNaArvore() {
        Departamento hospital = new Departamento("Hospital");
        Departamento ala = new Departamento("Ala");
        hospital.adicionar(ala);
        assertThrows(IllegalArgumentException.class, () -> ala.adicionar(hospital));
        assertThrows(IllegalArgumentException.class, () -> hospital.adicionar(hospital));
    }

    @Test
    void naoDevePermitirLeitosNegativos() {
        assertThrows(IllegalArgumentException.class, () -> new Setor("UTI", -1));
    }

    @Test
    void listaDeFilhosNaoDeveSerModificavelExternamente() {
        Departamento departamento = new Departamento("Hospital");
        assertThrows(UnsupportedOperationException.class,
                () -> departamento.getFilhos().add(new Setor("UTI", 3)));
    }
}
