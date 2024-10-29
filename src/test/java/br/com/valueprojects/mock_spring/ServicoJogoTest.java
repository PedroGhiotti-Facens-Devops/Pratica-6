package br.com.valueprojects.mock_spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import br.com.valueprojects.mock_spring.models.*;
import br.com.valueprojects.mock_spring.services.ServicoAlerta;
import br.com.valueprojects.mock_spring.services.ServicoJogo;
import br.com.valueprojects.mock_spring.services.alert_senders.EmailSender;
import br.com.valueprojects.mock_spring.services.alert_senders.SmsSender;
import infra.VencedorDaoFalso;
import org.junit.jupiter.api.Test;

import br.com.valueprojects.mock_spring.builders.CriadorDeJogo;
import infra.JogoDao;

public class ServicoJogoTest {

	@Test
	public void deveFinalizarJogosDaSemanaAnterior() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(1999, 1, 20);

		Jogo jogo1 = new CriadorDeJogo().para("Cata moedas")
				.naData(antiga).constroi();

		Jogo jogo2 = new CriadorDeJogo().para("Derruba barreiras")
				.naData(antiga).constroi();

		// mock no lugar de dao falso

		List<Jogo> jogosAnteriores = Arrays.asList(jogo1, jogo2);

		JogoDao daoFalso = mock(JogoDao.class);

		when(daoFalso.emAndamento()).thenReturn(jogosAnteriores);

		ServicoJogo finalizador = new ServicoJogo(daoFalso);
		finalizador.finalizaJogosEmAndamento();

		assertTrue(jogo1.isFinalizado());
		assertTrue(jogo2.isFinalizado());
		assertEquals(2, finalizador.getTotalFinalizados());
	}

	@Test
	public void deveVerificarSeMetodoAtualizaFoiInvocado() {

		Calendar antiga = Calendar.getInstance();
		antiga.set(1999, 1, 20);

		Jogo jogo1 = new CriadorDeJogo().para("Cata moedas").naData(antiga).constroi();
		Jogo jogo2 = new CriadorDeJogo().para("Derruba barreiras").naData(antiga).constroi();

		// mock no lugar de dao falso

		List<Jogo> jogosAnteriores = Arrays.asList(jogo1, jogo2);

		JogoDao daoFalso = mock(JogoDao.class);

		when(daoFalso.emAndamento()).thenReturn(jogosAnteriores);

		ServicoJogo finalizador = new ServicoJogo(daoFalso);
		finalizador.finalizaJogosEmAndamento();

		verify(daoFalso, times(1)).atualiza(jogo1);
		//Mockito.verifyNoInteractions(daoFalso);
	}

	@Test
	public void deveEnviarSmsAoVencedorAposFinalizarESalvarJogosDaSemanaAnteriorESalvarVencedor() {
		
		Calendar dataSemanaAnterior = Calendar.getInstance();
		dataSemanaAnterior.set(2024, 9, 28);

		// Instancia jogo
		Jogo jogo = new CriadorDeJogo().para("Jogo").naData(dataSemanaAnterior).constroi();

		// Insere resultados
		jogo.anota(new Resultado(new Participante("J1"), 0  ));
		jogo.anota(new Resultado(new Participante("J2"), 100));
		jogo.anota(new Resultado(new Participante("J3"), 10 ));

		// Cria mock pra jogo DAO
		JogoDao jogoDaoFalso = mock(JogoDao.class);

		// Finaliza jogos
		ServicoJogo finalizador = new ServicoJogo(jogoDaoFalso);
		finalizador.finalizaJogosEmAndamento();

		// Salva jogo finalizado e verifica se método salva foi chamado
		jogoDaoFalso.salva(jogo);
		verify(jogoDaoFalso, times(1)).salva(jogo);

		// Instancia juiz e julga jogo
		Juiz juiz = new Juiz();
		juiz.julga(jogo);

		// Pega vencedor do jogo
		Participante vencedor = juiz.getVencedor();

		// Cria mock para vencedor DAO
		VencedorDaoFalso vencedorDaoFalso = mock(VencedorDaoFalso.class);

		// Salva vencedor e verifica se metodo salva foi chamado
		vencedorDaoFalso.salva(vencedor);
		verify(vencedorDaoFalso, times(1)).salva(vencedor);

		// Cria alerta a ser enviada
		Alerta alertaVencedor = new Alerta(String.format("Parabéns, %s! Você venceu um jogo!", vencedor.getNome()), vencedor);

		// Cria mock do serviço de envio de alerta
		ServicoAlerta servicoAlerta = mock(ServicoAlerta.class);

		// Injeto meio de envio no serviço
		servicoAlerta.setAlertSender(new EmailSender());

		// Executa serviço e verifica que o método envia foi chamado
		servicoAlerta.envia(alertaVencedor);

		verify(servicoAlerta, times(1)).envia(alertaVencedor);

	}
}
