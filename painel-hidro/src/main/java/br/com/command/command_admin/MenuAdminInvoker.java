package br.com.command.command_admin;

public class MenuAdminInvoker 
{
    private ComandoAdmin comando;

    public void setComando(ComandoAdmin comando) 
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
