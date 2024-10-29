package infra;

import br.com.valueprojects.mock_spring.models.Alerta;

import java.util.ArrayList;
import java.util.List;

public class AlertaDaoFalso {
    private static List<Alerta> Alertas = new ArrayList<Alerta>();

    public void salva(Alerta alerta) {
        Alertas.add(alerta);
    }

    public List<Alerta> getAlertas() {
        return AlertaDaoFalso.Alertas;
    }
}
