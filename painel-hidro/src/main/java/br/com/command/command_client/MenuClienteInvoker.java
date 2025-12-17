package br.com.command.command_client;

public class MenuClienteInvoker 
{
    private ComandoCliente comando;

    public void setComando(ComandoCliente comando) 
    {
        this.comando = comando;
    }

    public void executar() 
    {
        if (comando != null) 
        {
            comando.executar();
        }
    }
}
