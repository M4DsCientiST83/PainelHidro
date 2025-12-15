package br.com.utilitarios;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.Usuario;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class LeituraContasJSON
{
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Usuario> carregarUsuarios() 
    {
        try 
        {
            InputStream is = LeituraContasJSON.class.getResourceAsStream("/usuarios.json");

            if (is == null) 
                {
                throw new RuntimeException("usuarios.json não encontrado em resources");
            }

            return Arrays.asList(
                    mapper.readValue(is, Usuario[].class)
            );

        } 
        catch (Exception e) 
        {
            throw new RuntimeException("Erro ao ler usuarios.json", e);
        }
    }
}