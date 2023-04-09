package com.trybe.consultafilmes;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Consultas {

  private final Collection<Filme> filmes;

  public Consultas(Collection<Filme> filmes) {
    this.filmes = filmes;
  }

  /**
   * Consulta 1: a partir da coleção de filmes desta classe, este método retorna o conjunto de
   * atores que interpretaram a si próprios em pelo menos um desses filmes.
   *
   * <p>
   * Considera-se "atores que interpretaram a si próprios" aqueles que têm o seu nome como uma das
   * chaves do Map `atoresPorPersonagem` e também como um dos itens pertencentes ao conjunto
   * associado a esta mesma chave.
   * </p>
   */
  public Set<String> atoresQueInterpretaramSiProprios() {
    Set<String> atores =
        filmes.stream().flatMap(filme -> filme.atoresPorPersonagem.entrySet().stream())
            .filter(ator -> ator.getValue().contains(ator.getKey())).map(ator -> ator.getKey())
            .collect(Collectors.toSet());
    return atores;
  }

  /**
   * Consulta 2: a partir da coleção de filmes desta classe, este método retorna a lista de atores
   * que atuaram em pelo menos um filme de um determinado diretor. A lista retornada está disposta
   * em ordem alfabética.
   *
   * <p>
   * Considera-se que um ator tenha atuado em um filme de um determinado diretor se ele tem o seu
   * nome como um dos itens do campo `atores`, ao mesmo tempo em que o diretor em questão tem o seu
   * nome como um dos itens do campo `diretores` do mesmo filme.
   * </p>
   */
  public List<String> atoresQueAtuaramEmFilmesDoDiretorEmOrdemAlfabetica(String diretor) {
    List<String> atores = filmes.stream().filter(filme -> filme.diretores.contains(diretor))
        .map(filme -> filme.atores.toArray()).flatMap(e -> Stream.of(e)).distinct().sorted()
        .map(e -> e.toString()).collect(Collectors.toList());
    return atores;
  }

  /**
   * Consulta 3: a partir da coleção de filmes desta classe, este método retorna a lista de filmes
   * em que pelo menos um dos diretores tenha atuado. A lista retornada está disposta em ordem de
   * lançamento, com os filmes mais recentes no início.
   *
   * <p>
   * Considera-se "filmes em que pelo menos um dos diretores tenha atuado" aqueles em que pelo menos
   * um dos itens do campo `diretores` também é um item do campo `atores`.
   * </p>
   */
  public List<Filme> filmesEmQuePeloMenosUmDiretorAtuouMaisRecentesPrimeiro() {
    List<Filme> filmesComDiretorAtuando = filmes.stream()
        .filter(filme -> filme.diretores.stream().anyMatch(filme.atores::contains)).distinct()
        .sorted((a, b) -> b.anoDeLancamento - a.anoDeLancamento).collect(Collectors.toList());
    return filmesComDiretorAtuando;
  }

  /**
   * Consulta 4: a partir da coleção de filmes desta classe, este método retorna um Map contendo
   * todos os filmes lançados em um determinado ano agrupados por categoria.
   *
   * <p>
   * Cada chave do Map representa uma categoria, enquanto cada valor representa o conjunto de filmes
   * que se encaixam na categoria da chave correspondente.
   * </p>
   */
  public Map<String, Set<Filme>> filmesLancadosNoAnoAgrupadosPorCategoria(int ano) {
    Set<Filme> filmesLancados =
        filmes.stream().filter(filme -> filme.anoDeLancamento == ano).collect(Collectors.toSet());

    Map<String, Set<Filme>> filmesPorCategoria =
        filmesLancados.stream().flatMap(filme -> filme.categorias.stream()).distinct().sorted()
            .collect(Collectors.toMap(categoria -> categoria,
                categoria -> filmesLancados.stream()
                    .filter(filme -> filme.categorias.contains(categoria))
                    .collect(Collectors.toSet())));
    return filmesPorCategoria;
  }
}
