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

## Operações em cascata


 
 
