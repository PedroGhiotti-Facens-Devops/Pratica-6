
package br.com.valueprojects.mock_spring.models;


import java.util.Comparator;

public class Juiz {

	private Resultado melhorResultado;
	private Resultado piorResultado;

	public void julga(Jogo jogo) {

		if(jogo.getResultados().isEmpty()){
			throw new RuntimeException("Sem resultados não há julgamento!");
		}

		this.melhorResultado = jogo.getResultados().stream().max(Comparator.comparing(Resultado::getMetrica)).get();
		this.piorResultado = jogo.getResultados().stream().min(Comparator.comparing(Resultado::getMetrica)).get();

	}
		
	public double getMelhorResultado() { return this.melhorResultado.getMetrica(); }
    public double getPiorResultado()   { return this.piorResultado.getMetrica()  ; }

	public Participante getVencedor() { return this.melhorResultado.getParticipante(); }
	public Participante getPerdedor() { return this.piorResultado.getParticipante()  ; }

}
