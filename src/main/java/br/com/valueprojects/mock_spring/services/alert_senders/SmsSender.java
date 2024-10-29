package br.com.valueprojects.mock_spring.services.alert_senders;

import br.com.valueprojects.mock_spring.interfaces.IAlertSender;
import br.com.valueprojects.mock_spring.models.Alerta;

public class SmsSender implements IAlertSender {

    public SmsSender() {}

    @Override
    public void envia(Alerta alerta) {
        // Envia alerta ao destinatário através de SMS
    }

}
