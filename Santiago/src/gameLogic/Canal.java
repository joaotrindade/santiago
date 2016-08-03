package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Canal implements Serializable {
	private int id;
	private boolean agua;
	private int suborno;
	public static int last_id = 0;
	public ArrayList<Terreno> terrenosVizinhos ;
	public ArrayList<Canal> canaisVizinhos ;
	
	public Canal()
	{
		id = last_id++;
		agua = false;
		suborno = 0;
		terrenosVizinhos = new ArrayList<Terreno>();
		canaisVizinhos = new ArrayList<Canal>();
		
	}
	
	public void addCanalVizinho(Canal c)
	{
		canaisVizinhos.add(c);
	}
	
	public boolean hasVizinhoComAgua()
	{
		for(Canal c : canaisVizinhos)
		{
			if (c.hasAgua()) return true;
		}
		return false;
	}
	
	public void addMultipleCanals(Canal[] input, int[] indices)
	{
		for(int i = 0; i < indices.length; i++){
			this.addCanalVizinho(input[indices[i]]);
		}
	}

	public boolean hasAgua() {
		return agua;
	}

	public void setAgua(boolean agua) {
		this.agua = agua;
	}

	public int getSuborno() {
		return suborno;
	}

	public void setSuborno(int suborno) {
		this.suborno = suborno;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


}
