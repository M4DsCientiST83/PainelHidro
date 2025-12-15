package br.com.leitura_imagens;

public interface Sujeito 
{
    void cadastrar(Observador o);
    void remover(Observador o);
    void notificar();
}

