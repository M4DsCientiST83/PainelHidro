package br.com.servicos_fachada;

import br.com.Usuario;
import br.com.utilitarios.LeituraContasJSON;

public class Autenticacao
{
    public Usuario autenticar(String username, String senha)
    {
        return LeituraContasJSON.carregarUsuarios().stream()
                .filter(u ->
                        u.getUsername().equals(username) &&
                        u.getPasswordHash().equals(senha)
                )
                .findFirst()
                .orElse(null);
    }
}
