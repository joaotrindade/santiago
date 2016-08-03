package gameLogic;

public class PossibleTilePlay implements Comparable<PossibleTilePlay>{
	
	public GameState estado;
	public int valor;
	public int tileNumber;
	public int tabuleiroPosition;
	
	public PossibleTilePlay(GameState gs, int value, int tile, int tab)
	{
		estado = gs;
		tileNumber = tile;
		tabuleiroPosition = tab;
		valor = value;
	}
	
    public boolean equals(PossibleTilePlay play1) {
       if ( (tileNumber == play1.tileNumber) || (tabuleiroPosition == play1.tabuleiroPosition) )
    	   	return true;
       else return false;
    }

	@Override
	public int compareTo(PossibleTilePlay arg0) {
		return (arg0.valor - valor);
	}
}
