package br.com;

import br.com.factory.Usuario;
import br.com.factory.UsuarioAdminCreator;
import br.com.factory.UsuarioClienteCreator;
import br.com.factory.UsuarioCreator;
import br.com.servicos_fachada.Autenticacao;
import br.com.servicos_fachada.CrudHidro;
import br.com.servicos_fachada.CrudUsuarios;
import br.com.servicos_fachada.MonitoramentoAssync;
import br.com.utilitarios.Hidrometro;
import br.com.utilitarios.Role;

public class Fachada {
    private CrudUsuarios crud;
    private CrudHidro crudh;
    private java.util.Map<Integer, MonitoramentoAssync> monitores; // Monitoramento por ID

    public Fachada() {
        crud = new CrudUsuarios();
        crudh = new CrudHidro();
        monitores = new java.util.HashMap<>();
    }

    public Usuario login(String u, String s) {
        Autenticacao auth = new Autenticacao();
        return auth.autenticar(u, s);
    }

    public void menuAdmin() {
        System.out.println("--- MENU ADMIN ---");
        System.out.println("1) Cadastrar novo usuário (Nome, Senha e Id_usuário)");
        System.out.println("2) Exibir usuários");
        System.out.println("3) Atualizar dados do usuário (Nome, Senha ou Cargo)");
        System.out.println("4) Remover Usuário (Id_usuário)");
        System.out.println("5) Cadastrar hidrômetro (Id_hidrômetro)");
        System.out.println("6) Associar hidrômetro ao usuário (Id_hidrômetro e Id_usuário)");
        System.out.println("7) Exibir hidrômetros associados a usuários");
        System.out.println("8) Remover Hidrômetro (Id_hidrômetro)");
        System.out.println("9) Ativar monitoramento do hidrômetro (Id_hidrômetro, caminho_arquivo)");
        System.out.println("10) Exibir monitoramento");
        System.out.println("11) Desativar monitoramento (Id_hidrômetro)");
        System.out.println("0) Sair");
    }

    public void menuCliente() {
        System.out.println("--- MENU CLIENTE ---");
        System.out.println("1) Exibir meus hidrômetros");
        System.out.println("2) Consultar leitura");
        System.out.println("3) Ativar monitoramento do hidrômetro (Id_hidrômetro, caminho_arquivo)");
        System.out.println("4) Exibir monitoramento");
        System.out.println("5) Desativar monitoramento (Id_hidrômetro)");
        System.out.println("0) Sair");
    }

    public Usuario criarUsuario(int id, String nome, String senha, Role role) {
        UsuarioCreator creator;

        if (role == Role.ADMIN) {
            creator = new UsuarioAdminCreator();
        } else {
            creator = new UsuarioClienteCreator();
        }

        return creator.criar(id, nome, senha);
    }

    public Hidrometro criarHidro(int id) {
        Hidrometro h = new Hidrometro(id);
        return h;
    }

    public void ativarMonitoramentoAssincrono(String caminhoPasta, int idHidrometro) {
        if (!monitores.containsKey(idHidrometro)) {
            MonitoramentoAssync monitor = new MonitoramentoAssync(caminhoPasta, idHidrometro);
            monitores.put(idHidrometro, monitor);
            monitor.ativarMonitoramento();
            System.out.println("Monitoramento ativado para o hidrômetro " + idHidrometro);
        } else {
            System.out.println("Já existe um monitoramento ativo para o hidrômetro " + idHidrometro);
            monitores.get(idHidrometro).ativarMonitoramento();
        }
    }

    public MonitoramentoAssync getMonitor(int idHidrometro) {
        return monitores.get(idHidrometro);
    }

    // Método auxiliar para pegar o único monitor se houver apenas um
    // (compatibilidade)
    // Se houver mais de um, retorna null para forçar o usuário a especificar
    public MonitoramentoAssync getMonitorUnico() {
        if (monitores.size() == 1) {
            return monitores.values().iterator().next();
        }
        return null;
    }

    public java.util.Set<Integer> getMonitoresAtivos() {
        return monitores.keySet();
    }

    public void desativarMonitoramentoAssincrono(int idHidrometro) {
        if (monitores.containsKey(idHidrometro)) {
            MonitoramentoAssync monitor = monitores.get(idHidrometro);
            monitor.desativarMonitoramento();
            monitores.remove(idHidrometro);
            System.out.println("Monitoramento desativado para o hidrômetro " + idHidrometro);
        } else {
            System.out.println("Não há monitoramento ativo para o hidrômetro " + idHidrometro);
        }
    }

    public void cadastrarUsuario(Usuario u) {
        boolean check = false;
        check = crud.criarUsuario(u);

        if (check) {
            System.out.print("\nUsuário cadastrado com sucesso!\n");
        }
    }

    public void exibirUsuarios() {
        crud.exibirUsuarios();
    }

    public void atualizarDadosUsuario(String tipo, int id, String dado) {
        crud.atualizarUsuario(tipo, id, dado);
    }

    public void removerUsuario(int id) {
        crud.removerUsuario(id);
    }

    public String obterDadosUsuario(String tipo, int id) {
        return crud.obterDado(tipo, id);
    }

    public int obterId(Usuario u) {
        return u.getId();
    }

    public void cadastrarHidro(Hidrometro h) {
        cadastrarHidro(h, 'A'); // Tipo padrão A
    }

    public void cadastrarHidro(Hidrometro h, char tipo) {
        crudh.criarHidro(h, tipo);
    }

    public void associarUsuarioHidro(int id_h, int id_u) {
        crudh.associarUsuarioHidro(id_h, id_u);
    }

    public void exibirHidrosUsers() {
        crudh.exibirHidroUsers();
    }

    public void removerHidrometro(int id) {
        crudh.removerHidrometro(id);
    }

    public void exibirHidrometrosDoUsuario(int idUsuario) {
        crudh.exibirHidrometrosDoUsuario(idUsuario);
    }

    public double consultarLeitura(int idHidrometro) {
        return crudh.consultarLeitura(idHidrometro);
    }
}
