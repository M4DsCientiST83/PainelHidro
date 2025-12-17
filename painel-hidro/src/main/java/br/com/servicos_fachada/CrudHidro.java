package br.com.servicos_fachada;

import java.util.List;

import br.com.dao.HidrometroDAO;
import br.com.utilitarios.Hidrometro;
import br.com.utilitarios.RelacaoHidroUser;

public class CrudHidro {
    private HidrometroDAO hidrodao;

    public CrudHidro() {
        hidrodao = new HidrometroDAO();
    }

    public void criarHidro(Hidrometro h) {
        criarHidro(h, 'A'); // Tipo padrão
    }

    public void criarHidro(Hidrometro h, char tipo) {
        hidrodao.salvar(h, tipo);
    }

    public void associarUsuarioHidro(int id_h, int id_u) {
        hidrodao.associarUsuario(id_h, id_u);
    }

    public void exibirHidroUsers() {
        List<RelacaoHidroUser> lista = hidrodao.exibirHidrometrosUsuarios();

        System.out.println("Usuários do sistema:\n");

        for (RelacaoHidroUser r : lista) {
            System.out.println(
                    "ID do Hidrômetro: " + r.getCodigoHidrometro() +
                            " | Nome do Usuário: " + r.getNomeUsuario());
        }

        System.out.println("\n");
    }

    public void removerHidrometro(int id) {
        hidrodao.removerHidrometro(id);
    }

    public void exibirHidrometrosDoUsuario(int idUsuario) {
        List<Hidrometro> hidrometros = hidrodao.buscarPorUsuario(idUsuario);

        System.out.println("\n--- Meus Hidrômetros ---\n");

        if (hidrometros.isEmpty()) {
            System.out.println("Você não possui hidrômetros cadastrados.\n");
        } else {
            for (Hidrometro h : hidrometros) {
                System.out.println("ID do Hidrômetro: " + h.getId());
            }
            System.out.println("\nTotal: " + hidrometros.size() + " hidrômetro(s)\n");
        }
    }
}
