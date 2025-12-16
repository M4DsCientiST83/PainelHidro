package br.com.servicos_fachada;

import java.util.List;

import br.com.dao.HidrometroDAO;
import br.com.utilitarios.Hidrometro;
import br.com.utilitarios.RelacaoHidroUser;

public class CrudHidro 
{
    private HidrometroDAO hidrodao;

    public CrudHidro()
    {
        hidrodao = new HidrometroDAO();
    }

    public void criarHidro(Hidrometro h)
    {
        hidrodao.salvar(h);
    }

    public void associarUsuarioHidro(int id_h, int id_u)
    {
        hidrodao.associarUsuario(id_h, id_u);
    }

    public void exibirHidroUsers()
    {
        List<RelacaoHidroUser> lista = hidrodao.buscarHidrometrosComUsuario();

        System.out.println("Usuários do sistema:\n");

        for (RelacaoHidroUser r : lista) 
        {
            System.out.println(
                "ID do Hidrômetro: " + r.getCodigoHidrometro() +
                " | Nome do Usuário: " + r.getNomeUsuario()
            );
        }

        System.out.println("\n");
    }

    public void removerHidrometro(int id)
    {
        hidrodao.removerHidrometro(id);
    }
}
