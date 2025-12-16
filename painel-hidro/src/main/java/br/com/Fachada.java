package br.com;

import br.com.servicos_fachada.Autenticacao;
import br.com.servicos_fachada.CrudUsuarios;
import br.com.servicos_fachada.MonitoramentoAssync;
import br.com.utilitarios.Role;
import br.com.utilitarios.Usuario;

public class Fachada 
{
    private CrudUsuarios cadastro;
    private MonitoramentoAssync monitor;

    public Fachada()
    {
        cadastro = new CrudUsuarios();
    }

    public Usuario login(String u, String s)
    {
        Autenticacao auth = new Autenticacao();
        return auth.autenticar(u, s);
    }

    public void menuAdmin() 
    {
        System.out.println("--- MENU ADMIN ---");
        System.out.println("1) Cadastrar novo usuário");
        System.out.println("2) Exibir usuários");
        System.out.println("3) Associar hidrômetro ao usuário");
        System.out.println("4) Ativar monitoramento do hidrômetro");
        System.out.println("5) Exibir monitoramento");
        System.out.println("6) Remover usuário");
        System.out.println("0) Sair");
    }

    public void menuCliente() 
    {
        System.out.println("--- MENU CLIENTE ---");
        System.out.println("1) Ver meus dados");
        System.out.println("2) Consultar leitura");
        System.out.println("0) Sair");    
    }

    public void ativarMonitoramentoAssincrono()
    {
        monitor.ativarMonitoramento();
    }

    public void cadastrarUsuario(Usuario u)
    {
        boolean check = false;
        check = cadastro.criarUsuario(u);

        if (check)
        {
            System.out.print("\nUsuário cadastrado com sucesso!\n");
        }
    }

    public void exibirUsuarios()
    {
        cadastro.exibirUsuarios();
    }

    public void atualizarDadosUsuario(String tipo, Usuario u, String dado)
    {
        cadastro.atualizarUsuario(tipo, u, dado);
    }

    public String obterDadosUsuario(String tipo, Usuario u)
    {
        return cadastro.obterDado(tipo, u);
    }
}