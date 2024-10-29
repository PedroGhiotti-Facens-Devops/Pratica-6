package br.com.valueprojects.mock_spring.services;

import br.com.valueprojects.mock_spring.interfaces.IAlertSender;
import br.com.valueprojects.mock_spring.models.Alerta;
import br.com.valueprojects.mock_spring.models.Participante;
import infra.AlertaDaoFalso;

public class ServicoAlerta {

    private AlertaDaoFalso alertaDao;
    private IAlertSender alertSender;

    public ServicoAlerta(IAlertSender alertSender) { this.alertSender = alertSender; }

    public void setAlertSender(IAlertSender alertSender) {
        this.alertSender = alertSender;
    }

    public void envia(Alerta alerta) {
        alertaDao.salva(alerta);
        alertSender.envia(alerta);
    }
}
