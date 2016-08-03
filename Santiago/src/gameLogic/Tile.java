package gameLogic;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Tile implements Serializable {
	int tipotile=0;
	int numWorkers=0;
	
	public int getTipo() {
		return tipotile;
	}
	
	public void setTipo(int tipo) {
		tipotile = tipo;
	}
	
	public int getNumWorkers() {
		return numWorkers;
	}
	
	public void setNumWorkers(int n) {
		numWorkers = n;
	}

}
