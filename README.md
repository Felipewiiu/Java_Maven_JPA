# Java: persistência de dados e consultas com Spring Data JPA

Projeto desenvolvido no segundo curso da formação Avançando com Java da Alura

## 🔨 Objetivos do projeto

- Evoluir no projeto Screenmatch, iniciado no primeiro curso da formação, criando um menu com várias opções;
- Modelar as abstrações da aplicação através de classes, enums, atributos e métodos;
- Consumir a API do ChatGPT;
- Utilizar o Spring Data JPA para persistir dados no banco;
- Conhecer vários tipos de banco de dados e utilizar o PostgreSQL;
- Trabalhar com vários tipos de consultas ao banco de dados;
- Aprofundar na interface JPARepository

## Links de documentações

+ https://docs.spring.io/spring-data/jpa/reference/#repositories

## Enun o que é isso?

Enum é uma abreviação de "enumerados" e é um tipo especial de classe em Java que tem um número fixo de constantes.
Empregar um Enum pode ser muito útil na programação para armazenar valores que sabemos que nunca vão mudar, como os dias
da semana ou as fases da lua.

## Tratando Dados Categóricos

Um dos usos mais práticos de Enums é para tratar dados categóricos. Dados categóricos são tipos de dados que podem ser
divididos em vários grupos ou categorias. A vantagem de usar Enums é que eles podem ajudar a garantir que só vamos usar
os valores pré-definidos, prevenindo a ocorrência de erros.

## Trabalhando com métodos estáticos

Em Java, um método estático é um método que pertence à classe e não a um objeto específico da classe. Traduzindo para
uma linguagem mais simples: um método estático é aquele que pode ser usado mesmo que você não tenha criado nenhum objeto
da classe.

Se você precisar de um método que não precisa interagir com os atributos ou outros métodos de um objeto, criar um método
estático pode ser uma solução elegante e eficiente. Por exemplo, você poderia criar um método estático para calcular a
média de um conjunto de números.

Além disso, os métodos estáticos são amplamente utilizados em bibliotecas auxiliares, como a classe Math e a classe
Arrays do Java. Em vez de requerer que o usuário crie um objeto para usar os métodos, os métodos são estáticos, e assim
podem ser acessados diretamente a partir da classe.

## Diretório resources

O diretório "resources" é utilizado para armazenar arquivos e recursos estáticos que são necessários para o
funcionamento da aplicação. Esses recursos podem incluir arquivos de configuração, arquivos de propriedades, arquivos de
template, imagens, arquivos de estilo, entre outros. O diretório "resources" é uma convenção do Spring Boot e é
automaticamente incluído no classpath da aplicação.

No aplication.properties é o lugar onde configuramos a nossa string de conexão com o banco, usuário para a conexão e a
senha do banco por exemplo

## JPA o que é?

Dentro do ecossistema Java, a ferramenta ORM mais conhecida é a JPA, ou Java Persistance API, que é uma especificação
criada com o intuito de simplificar a conexão de banco de dados em aplicações Java, a partir da definição de uma
interface comum a ser utilizada para persistência de dados na linguagem.

O JPA surge, justamente com o objetivo de oferecer uma maneira mais fácil e padronizada e mapear objetos e tabelas de um
banco de dados relacional, e fornecer uma API para realizar as operações CRUD, utilizando o Java.

Para entender melhor seu funcionamento, é importante familiarizar-se com alguns conceitos-chave da especificação, como:

+ Entidade: é uma classe Java criada para representar um objeto armazenado em um banco de dados. Cada entidade é mapeada
  para uma tabela do banco.

+ EntityManager: é a interface central do JPA, que é usada para realizar as operações de persistência, ou seja, o CRUD.
  É a EntityManager que gerencia o ciclo de vida das entidades dentro de uma aplicação Java.

+ JPQL: é a linguagem de consulta da JPA, que permite escrever consultas customizadas se aproveitando da orientação a
  objetos, ou seja, é como se fosse uma mistura de Java e SQL, ou uma linguagem SQL orientada a objetos. A utilização da
  JPQL é interessante pois ela encapsula a chamada real para o banco de dados, e permite inclusive a troca do banco de
  dados da aplicação sem a necessidade de alteração no código-fonte.

## Estratégias de geração do ID

Temos várias formas de gerar IDs no banco de dados, e a JPA também nos ajuda com isso. Basta utilizar a anotação
GeneratedValue e escolher a estratégia de geração do ID.

## Tipos de geração de IDs

+ GenerationType.IDENTITY
+ GenerationType.SEQUENCE
+ GenerationType.TABLE
+ GenerationType.AUTO
+ GenerationType.UUID

## Injeção de dependência no spring

Injeção de dependência no Spring é um padrão de projeto que permite que as dependências de uma classe sejam fornecidas
por outra classe, em vez de serem criadas pela própria classe. No contexto do Spring, a injeção de dependência é
realizada por meio da anotação ``@Autowired``, que permite que o Spring seja responsável por instanciar e gerenciar as
dependências das classes. Isso facilita a manutenção, testabilidade e flexibilidade do código.

## Buscando dados no banco

O construtor padrão é necessário para que a JPA consiga recuperar os dados do banco e representá-los como objetos do
tipo Serie. Ele permite que a JPA crie uma instância da classe Serie sem a necessidade de passar argumentos para o
construtor. Dessa forma, a JPA consegue mapear os dados do banco para os atributos da classe corretamente.

+ OBS: Sem esse recurso a stack trace mostrará um erro chamado ``InstantiationException``

## Relacionamentos Uni e Bidirecionais com JPA

Ao trabalharmos com banco de dados, existem relacionamentos com diferentes tipos de direção. Existem relacionamentos
unidirecionais e bidirecionais. Os unidirecionais deixam a relação visível apenas em um lado, enquanto relacionamentos
bidirecionais permitem que os objetos de ambos os lados acessem e/ou alterem o objeto do outro lado. Isso é muito útil
quando você quer ter um controle maior sobre seus objetos e as operações que você pode executar neles.

### Exemplo:

````

  public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));// estabelecimento de relação bidirecional
        this.episodios = episodios;
    }

````

## Operações em cascata

Uma operação em cascata em um banco de dados refere-se a uma ação automática que ocorre em uma tabela quando uma ação
relacionada é executada em outra tabela e existe uma relação de cascata entre elas. Essas operações geralmente estão
associadas a restrições de chave estrangeira (FK - Foreign Key).

Imagine que temos duas entidades: Post e Comment. Um Post pode ter muitos Comments. Se excluirmos um Post, o que
acontecerá com os Comments relacionados a ele? Os tipos de cascata têm o objetivo de responder a este tipo de questões.

### Os diferentes tipos de cascata são:

+ PERSIST : se você persistir a entidade Post, os Comments relacionados também serão persistidos.
+ MERGE : se você mesclar os detalhes de um Post, os Comments relacionados também serão mesclados.
+ REMOVE : se você remover um Post, os Comments relacionados também serão removidos.
+ REFRESH : se você atualizar o Post, também atualizará os Comments relacionados.
+ DETACH : se um Post foi desanexado, todos os Comments relacionados serão desanexados também.
+ ALL : se você executar qualquer uma das operações acima em um Post, essa operação será propagada para todos os
  Comments relacionados.

## Tipos de busca ("eager" ou "lazy")

Geralmente encontramos dois tipos de carregamento de dados: "lazy" e "eager". Esses dois conceitos são
essencialmente sobre quando e como os dados são recuperados do banco de dados para serem usados em nossas aplicações.

## O que é Fetch Type?

De modo bem simples, Fetch Type define qual a estratégia será utilizada para carregar os dados do banco para sua
aplicação.
Podemos usar a analogia do café da manhã. Imagine que você tem uma mesa de café da
manhã e pode haver vários itens nela, como pão, café, leite, frutas, etc.
A forma como você vai pegar esses itens, quando e quantos de uma vez, é basicamente o conceito por trás do fetch type,
que veremos a seguir.

## Lazy Fetch Type

Lazy, em inglês, significa preguiçoso. Em termos de programação, Lazy Fetch Type é quando você pega apenas o que
precisa, no momento em que precisa.

Se voltarmos para a nossa analogia do café da manhã, seria como pegar somente o café primeiro. Quando se sentir pronto
para comer algo, você então vai e pega uma fruta ou um pão. Ou seja, você só busca os outros itens quando realmente vai
utilizá-los.

## Eager Fetch Type

Eager, em inglês, pode ser traduzido como ansioso. Em programação, Eager Fetch Type é mais rápido, pois vai pegar todos
os dados relacionados ao mesmo tempo.

## Impacto no desempenho da aplicação

A estratégia de carregamento afeta diretamente o desempenho da aplicação. Um carregamento Eager pode parecer eficiente,
pois tudo já está carregado de uma vez. No entanto, se a relação envolver muitos dados, isso pode causar problemas de
desempenho, além de consumir muito mais memória, uma vez que estamos carregando mais dados do que realmente precisamos.

Por outro lado, Lazy Fetch Type pode parecer uma estratégia mais eficaz, pois carrega os dados sob demanda. No entanto,
se não administrado cuidadosamente, pode acabar resultando em múltiplas chamadas ao banco de dados, aumentando o tempo
de resposta.

Escolher entre Lazy e Eager não é uma decisão trivial e deve ser baseada na necessidade da aplicação. Um bom ponto de
partida é começar com Lazy Fetch Type e optar por Eager onde o carregamento completo é muitas vezes necessário.

O escopo da aplicação, a quantidade de dados, a frequência de acesso e muitos outros fatores serão decisivos para essa
escolha. É importante sempre analisar o contexto e testar o desempenho para alcançar a melhor estratégia.

## Consultas derivadas ("derived queries")

A JPA tem diversos recursos, e um dos mais legais que podemos utilizar são as derived queries, em que trabalhamos com
métodos específicos que consultam o banco de forma personalizada. Esses métodos são criados na interface que herda de
JpaRepository. Neles, utilizaremos palavras-chave (em inglês) para indicar qual a busca que queremos fazer.

### A estrutura básica de uma derived query na JPA consiste em:

````
verbo introdutório + palavra-chave “By” + critérios de busca

````

+ Como verbos introdutórios, temos find, read, query, count e get. Já os critérios são variados.

### Palavras relativas à igualdade:

+ **Is**, para ver igualdades
+ **Equals**, para ver igualdades (essa palavra-chave e a anterior têm os mesmos princípios, e são mais utilizadas para
  a legibilidade do método).
+ **IsNot**, para checar desigualdades
+ **IsNull**, para verificar se um parâmetro é nulo

### Palavras relativas à similaridade:

+ **Containing**, para palavras que contenham um trecho
+ **StartingWith**, para palavras que comecem com um trecho
+ **EndingWith**, para palavras que terminem com um trechoEssas palavras podem ser concatenadas com outras condições,
  como o ContainingIgnoreCase, para não termos problemas de Case Sensitive.

### Palavras relacionadas à comparação:

+ **LessThan**, para buscar registros menores que um valor
+ **LessThanEqual**, para buscar registros menores ou iguais a um valor
+ **GreaterThan**, para identificar registros maiores que um valor
+ **GreaterThanEqual**, para identificar registros maiores ou iguais a um valor
+ **Between**, para saber quais registros estão entre dois valores

### Ordenação

+ **Desc**, Para ordenar os dados de forma decrescente

## Ordenações e outras palavras chaves das consultas derivadas

Para trabalhar com a ordenação de registros pesquisados, também existem algumas palavras-chave. Podemos utilizar o
**OrderBy** para ordenar os registros por algum atributo deles, como a série pela avaliação. Também podemos encadear
atributos. Se uma Série tem um Ator e queremos ordenar pelo nome do ator, podemos utilizar **OrderByAtorNome**, por exemplo.

Além do **OrderBy**, ainda existem alguns outros recursos de filtros que podem ser utilizados:

+ **Distinct**, para remover dados duplicados
+ **First**, para pegar o primeiro registro
+ **Top**, para limitar o número de dados