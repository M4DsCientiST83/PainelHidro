package br.com.command.command_admin;

import br.com.Fachada;

public class DesativarMonitoramentoCommand implements ComandoAdmin {
    private Fachada fachada;

    public DesativarMonitoramentoCommand(Fachada fachada) {
        this.fachada = fachada;
    }

    @Override
    public void executar() {
        fachada.desativarMonitoramentoAssincrono();
    }
}
