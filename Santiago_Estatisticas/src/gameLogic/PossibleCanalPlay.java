package gameLogic;

import org.apache.commons.lang3.SerializationUtils;

public class PossibleCanalPlay implements Comparable<PossibleCanalPlay> {
	public GameState estadoInicial;
	public GameState estadoFinal;
	public int canalPosition;
	public int valor;
	public int diferenca;
	
	public PossibleCanalPlay(GameState ei, int pos)
	{
		estadoInicial = ei;
		canalPosition = pos;
		estadoFinal = null;
		valor = 0;
		diferenca = 0;
	}
	
	public void calculateEstadoFinal(String nomeJogador)
	{
		estadoFinal = SerializationUtils.clone(estadoInicial);
		estadoFinal.setCanalFromPosition(canalPosition);
		estadoFinal.updateWorkers();
		estadoFinal.updateWorkers();
		estadoFinal.updateWorkers();
		//System.out.println(">>>VALOR ESTADO INICIAL : " + estadoInicial.getValorTotalJogador(nomeJogador) );
		//System.out.println(">>>VALOR ESTADO FINAL : " + estadoFinal.getValorTotalJogador(nomeJogador) );
		valor = estadoFinal.getValorTotalJogador(nomeJogador);
		diferenca = valor - estadoInicial.getValorTotalJogador(nomeJogador);
	}

	@Override
	public int compareTo(PossibleCanalPlay o) {
		return (o.valor - valor);
	}
}
