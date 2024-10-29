package br.com.valueprojects.mock_spring.interfaces;

import br.com.valueprojects.mock_spring.models.Alerta;

public interface IAlertSender {
    void envia(Alerta alerta);
}
