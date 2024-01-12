package br.com.alura.screenmatch.model;

public enum Categoria {// enuns são declaradas com letras maiusculas
    ACAO("Action"),
    ROMANCE("Romance"),
    COMEDIA("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime");

    private String categoriaOmdb; // isso aqui é um campo de uma classe

    Categoria(String categoriaOmdb){// declarar um construtor
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {
        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }



}

// Aqui nos limitamos os possíveis valores a uma constantes
