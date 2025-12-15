package br.com.servicos_fachada;

import br.com.utilitarios.LeituraContasJSON;

public class Autenticacao
{
    public boolean autenticar(String username, String senha)
    {
        if (LeituraContasJSON.carregarUsuarios().stream().anyMatch(u -> u.username.equals(username) && u.passwordHash.equals(senha)))
        {
            System.out.print("Login realizado com sucesso! Seja bem vindo(a) " + username);
            return true;
        }
        System.out.print("Usuário ou senha incorretos, por favor tente novamente.");
        return false;
    }
}
