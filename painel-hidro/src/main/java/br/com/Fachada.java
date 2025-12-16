package br.com;

import br.com.servicos_fachada.Autenticacao;
import br.com.servicos_fachada.CrudHidro;
import br.com.servicos_fachada.CrudUsuarios;
import br.com.servicos_fachada.MonitoramentoAssync;
import br.com.utilitarios.Hidrometro;
import br.com.utilitarios.Usuario;

public class Fachada 
{
    private CrudUsuarios crud;
    private CrudHidro crudh;
    private MonitoramentoAssync monitor;

    public Fachada()
    {
        crud = new CrudUsuarios();
        crudh = new CrudHidro();
    }

    public Usuario login(String u, String s)
    {
        Autenticacao auth = new Autenticacao();
        return auth.autenticar(u, s);
    }

    public void menuAdmin() 
    {
        System.out.println("--- MENU ADMIN ---");
        System.out.println("1) Cadastrar novo usuário (Nome, Senha e Id_usuário)");
        System.out.println("2) Exibir usuários");
        System.out.println("3) Atualizar dados do usuário (Nome, Senha ou Cargo)");
        System.out.println("4) Remover Usuário (Id_usuário)");
        System.out.println("5) Cadastrar hidrômetro (Id_hidrômetro)");
        System.out.println("6) Associar hidrômetro ao usuário (Id_hidrômetro e Id_usuário)");
        System.out.println("7) Exibir hidrômetros associados a usuários");
        System.out.println("8) Remover Hidrômetro (Id_hidrômetro)");
        System.out.println("9) Ativar monitoramento do hidrômetro");
        System.out.println("10) Exibir monitoramento");
        System.out.println("0) Sair");
    }

    public void menuCliente() 
    {
        System.out.println("--- MENU CLIENTE ---");
        System.out.println("1) Ver meus dados");
        System.out.println("2) Consultar leitura");
        System.out.println("0) Sair");    
    }

    public Usuario criarUsuario(int id, String un, String ps)
    {
        Usuario u = new Usuario(id, un, ps);
        return u;
    }

    public Hidrometro criarHidro(int id)
    {
        Hidrometro h = new Hidrometro(id);
        return h;
    }

    public void ativarMonitoramentoAssincrono()
    {
        monitor.ativarMonitoramento();
    }

    public void cadastrarUsuario(Usuario u)
    {
        boolean check = false;
        check = crud.criarUsuario(u);

        if (check)
        {
            System.out.print("\nUsuário cadastrado com sucesso!\n");
        }
    }

    public void exibirUsuarios()
    {
        crud.exibirUsuarios();
    }

    public void atualizarDadosUsuario(String tipo, int id, String dado)
    {
        crud.atualizarUsuario(tipo, id , dado);
    }

    public void removerUsuario(int id)
    {
        crud.removerUsuario(id);
    }

    public String obterDadosUsuario(String tipo, int id)
    {
        return crud.obterDado(tipo, id);
    }
    
    public int obterId(Usuario u)
    {
        return u.getId();
    }

    public void cadastrarHidro(Hidrometro h)
    {
        crudh.criarHidro(h);
    }

    public void associarUsuarioHidro(int id_h, int id_u)
    {
        crudh.associarUsuarioHidro(id_h, id_u);
    }

    public void exibirHidrosUsers()
    {
        crudh.exibirHidroUsers();
    }

    public void removerHidrometro(int id)
    {
        crudh.removerHidrometro(id);
    }
}