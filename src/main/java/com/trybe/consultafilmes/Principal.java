package com.trybe.consultafilmes;

import java.util.List;

public class Principal {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   */
  public static void main(String[] args) {
    Consultas consultas = new Consultas(Filmes.todos());
    List<Filme> resultados = consultas.filmesEmQuePeloMenosUmDiretorAtuouMaisRecentesPrimeiro();
    System.out.println(resultados);
  }
}
