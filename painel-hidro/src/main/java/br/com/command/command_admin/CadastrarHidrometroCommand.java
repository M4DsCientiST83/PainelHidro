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
        Hidrometro hidrometro = fachada.criarHidro(id);
        fachada.cadastrarHidro(hidrometro, tipo);
    }
}
