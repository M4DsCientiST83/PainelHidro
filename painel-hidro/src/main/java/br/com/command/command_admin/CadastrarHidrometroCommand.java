package br.com.command.command_admin;

import br.com.Fachada;
import br.com.utilitarios.Hidrometro;

public class CadastrarHidrometroCommand implements ComandoAdmin 
{
    private Fachada fachada;
    private int id;

    public CadastrarHidrometroCommand(Fachada fachada, int id) 
    {
        this.fachada = fachada;
        this.id = id;
    }

    @Override
    public void executar() 
    {
        Hidrometro hidrometro = fachada.criarHidro(id);
        fachada.cadastrarHidro(hidrometro);
    }
}
