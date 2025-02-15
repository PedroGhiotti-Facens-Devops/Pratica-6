package br.com.valueprojects.mock_spring.services;

import java.util.Calendar;
import java.util.List;

import br.com.valueprojects.mock_spring.models.Jogo;
import infra.JogoDao;



public class ServicoJogo {

	private int total = 0;
	private final JogoDao dao;

	public ServicoJogo(JogoDao dao) {
		this.dao = dao;
	}

	public void finalizaJogosEmAndamento() {
		List<Jogo> todosJogosEmAndamento = dao.emAndamento();

		for (Jogo jogo : todosJogosEmAndamento) {
			if (!iniciouSemanaAnterior(jogo)) { continue; }

			jogo.finaliza();
			total++;
			dao.atualiza(jogo);
		}
	}

	private boolean iniciouSemanaAnterior(Jogo jogo) {
		return (diasEntre(jogo.getData(), Calendar.getInstance()) >= 7);
	}

	private int diasEntre(Calendar inicio, Calendar fim) {
		Calendar data = (Calendar) inicio.clone();
		int diasNoIntervalo = 0;
		while (data.before(fim)) {
			data.add(Calendar.DAY_OF_MONTH, 1);
			diasNoIntervalo++;
		}

		return diasNoIntervalo;
	}

	public int getTotalFinalizados() {
		return total;
	}
}
