package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.*;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConverteDados;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    private List<DadosSerie> dadosSeries = new ArrayList<>();

    private SerieRepository repositorio;
    private List<Serie> series = new ArrayList<>();
    private Optional<Serie> serieBusca;

    public Principal(SerieRepository repositorio) {
        this.repositorio = repositorio;
    }


    public void exibeMenu() {

        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar séries
                    2 - Buscar episódios
                    3 - Listar as séries buscadas
                    4 - Buscar série por titulo  
                    5 - Buscar séries por ator 
                    6 - Top 5 séries  
                    7 - Buscar série por categoria  
                    8 - Buscar série por temporada
                    9 - Buscar episódio por trecho
                    10- Buscar os top episódios da série
                    11- Buscar episodio a partir de uma data
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarSerieWeb();
                    break;
                case 2:
                    buscarEpisodioPorSerie();
                    break;
                case 3:
                    listarSeriesBuscadas();
                    break;
                case 4:
                    buscarSeriePorTitulo();
                    break;
                case 5:
                    buscarSeriesPorAtor();
                    break;
                case 6:
                    buscarTop5Series();
                    break;
                case 7:
                    buscarSeriePorCategoria();
                    break;
                case 8:
                    buscarSeriePorTemporada();
                    break;
                case 9:
                    buscarEpsodioPorTrecho();
                case 10:
                    topEpisodiosPorSerie();
                    break;
                case 11:
                    buscarEpisodioDepoisDeUmaData();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }



    private void buscarSerieWeb() {
        DadosSerie dados = getDadosSerie();
        Serie serie = new Serie(dados);
        //dadosSeries.add(dados);
        repositorio.save(serie);
        System.out.println(dados);
    }

    private DadosSerie getDadosSerie() {
        System.out.println("Digite o nome da série para busca");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
        System.out.println(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
        return dados;
    }

    private void buscarEpisodioPorSerie() {
        listarSeriesBuscadas();
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();

        Optional<Serie> serie = repositorio.findByTituloContainingIgnoreCase(nomeSerie);
        if (serie.isPresent()) {
            var serieEncontrada = serie.get();

            List<DadosTemporada> temporadas = new ArrayList<>();

            for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
                var json = consumo.obterDados(ENDERECO + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
                DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
                temporadas.add(dadosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d -> d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());

            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void listarSeriesBuscadas() {
        series = repositorio.findAll();

        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSeriePorTitulo() {
        System.out.println("Escolha uma série pelo nome: ");
        var nomeSerie = leitura.nextLine();

        serieBusca = repositorio.findByTituloContainingIgnoreCase(nomeSerie);

        if (serieBusca.isPresent()) {
            System.out.println("Dados da série" + serieBusca.get());
        } else {
            System.out.println("Série não encontrada");
        }
    }

    private void buscarSeriesPorAtor() {
        System.out.println("Qual o nome para a busca?");
        var nomeAtor = leitura.nextLine();
        System.out.println("Avaliações a partir de que valor?");
        var avaliacao = leitura.nextDouble();
        List<Serie> seriesEncontradas = repositorio.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeAtor, avaliacao);
        System.out.println("\nSerie em que o nome do ator " + nomeAtor + "trabalhou:\n");
        seriesEncontradas.forEach(s -> System.out.println(s.getTitulo() + " Avaliação: " + s.getAvaliacao()));
    }

    private void buscarTop5Series() {
        List<Serie> seriesTop = repositorio.findTop3ByOrderByAvaliacaoDesc(); //Consultas derivadas ("derived queries")
        seriesTop.forEach(s -> System.out.println(s.getTitulo() + " Avaliação: " + s.getAvaliacao()));
    }

    private void buscarSeriePorCategoria() {
        System.out.println("Deseja buscar séries por qual categoria/gênero");
        var nomeGenero = leitura.nextLine();
        Categoria categoria = Categoria.fromPortugues(nomeGenero);// não foi preciso instanciar pois o método é static

        List<Serie> serieCategoria = repositorio.findByGenero(categoria);

        System.out.println("Série da categoria " + nomeGenero);
        serieCategoria.forEach(s -> System.out.println(s.getTitulo()));
    }

    private void buscarSeriePorTemporada() {
        System.out.println("Digite o número de temporada que deseja buscar");
        var totalTemporadas = leitura.nextInt();

        System.out.println("Com avaliaçao a partir de qual valor?");
        var avaliacao = leitura.nextDouble();

        leitura.nextLine();
        List<Serie> filtroSeries = repositorio.seriesPorTemporadasEAvaliacao(totalTemporadas, avaliacao);
        System.out.println("*** Séries filtradas ***");
        filtroSeries.forEach(s ->
                System.out.println(s.getTitulo() + "  - avaliação: " + s.getAvaliacao()));

//        find - By - TotalTemporadas - LessThanEqual - And - Avaliacao - GreaterThanEqual

    }

    private void buscarEpsodioPorTrecho() {
        System.out.println("Qual o nome do epsódio para a busca?");
        var trechoEpsodio = leitura.nextLine();
        List<Episodio> episodiosEncontrados = repositorio.epsodiosPorTrecho(trechoEpsodio);
        episodiosEncontrados.forEach(e ->
                System.out.printf("Série: %s Temporada %s - Episódio %s - %s\n",
                        e.getSerie().getTitulo(), e.getTemporada(),
                        e.getNumeroEpisodio(), e.getTitulo()));
    }

    private void topEpisodiosPorSerie() {
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();
            List<Episodio> topEpsodios = repositorio.topEpisodioPorSerie(serie);
            topEpsodios.forEach(e ->
                    System.out.printf("Série: %s Temporada %s - Episódio %s - %s Avaliação %s\n",
                            e.getSerie().getTitulo(), e.getTemporada(),
                            e.getNumeroEpisodio(), e.getTitulo(), e.getAvaliacao()));
        }
    }

    private void buscarEpisodioDepoisDeUmaData() {
        buscarSeriePorTitulo();
        if(serieBusca.isPresent()){
            Serie serie = serieBusca.get();            System.out.println("Digite o ano limite de lançamento");
            var anoLancamento = leitura.nextInt();
            List<Episodio> episodiosAno = repositorio.episodiosPorSerieEPorano(serie, anoLancamento);
            episodiosAno.forEach(System.out::println);
        }

    }


}