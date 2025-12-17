package br.com.command.command_admin;

import br.com.Fachada;
import br.com.utilitarios.Hidrometro;

public class CadastrarHidrometroCommand implements ComandoAdmin {
    private Fachada fachada;
    private int id;
    private char tipo;

    public CadastrarHidrometroCommand(Fachada fachada, int id, char tipo) {
        this.fachada = fachada;
        this.id = id;
        this.tipo = tipo;
    }

    @Override
    public void executar() {
        try {
            Hidrometro hidrometro = fachada.criarHidro(id);
            fachada.cadastrarHidro(hidrometro, tipo);
        } catch (RuntimeException e) {
            String message = e.getMessage();
            if (e.getCause() != null && e.getCause().getMessage().contains("Duplicate entry")) {
                System.out.println("Erro: Já existe um hidrômetro cadastrado com o ID " + id);
            } else {
                System.out.println("Erro ao cadastrar hidrômetro: " + message);
            }
        }
    }
}
