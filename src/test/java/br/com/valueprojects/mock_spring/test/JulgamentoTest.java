package br.com.valueprojects.mock_spring.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.valueprojects.mock_spring.builders.CriadorDeJogo;
import br.com.valueprojects.mock_spring.models.Jogo;
import br.com.valueprojects.mock_spring.models.Juiz;
import br.com.valueprojects.mock_spring.models.Participante;
import br.com.valueprojects.mock_spring.models.Resultado;

public class JulgamentoTest {
   
	
	private Juiz juiz;
	private Participante joao;
	private Participante pedro;
	private Participante katia;
	private Participante maria;
	
	@BeforeEach
	public void criaJuiz(){

		this.juiz = new Juiz();
		this.joao = new Participante("Joao");
		this.pedro = new Participante("Pedro");
		this.katia = new Participante("Katia");
		this.maria =new Participante("Maria");
			
		}
	
	@Test
	public void deveJulgarPrimeiroEultimoColocado () {
		
		
		Jogo jogo = new Jogo("Derruba barreiras");

		jogo.anota(new Resultado(joao, 90.0));
		jogo.anota(new Resultado(pedro, 91.0));
		jogo.anota(new Resultado(katia, 93.0));
		jogo.anota(new Resultado(maria, 94.0));
		
		
		juiz.julga(jogo);
		
		double vencedorJogo = 94;
		double ultimoColocadoJogo = 90;
		
		assertEquals(vencedorJogo, juiz.getMelhorResultado(),0.00001);
		assertEquals(ultimoColocadoJogo, juiz.getPiorResultado(),0.00001);
	}
	
	public class JogoTest {

	    @Test
	    public void naoDeveJulgarSemResultado() {
	        Jogo jogo = new CriadorDeJogo()
	            .para("Caça peças")
	            .constroi();

	        assertThrows(RuntimeException.class, () -> juiz.julga(jogo));
	    }
	}
}
