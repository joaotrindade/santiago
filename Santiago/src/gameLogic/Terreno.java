package gameLogic;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;

import misc.Pair;

@SuppressWarnings("serial")
public class Terreno implements Serializable {
	private int id ;
	private int tipoPlantacao; // -1 Inutilizável, 0 - Vazio , 1 - Batatas, 2 - Pimentos, 3 - Feijoes, 4 - Beringelas
	private String jogadorProprietario;
	private int numMarkers;
	public static int last_id = 0;
	public static int last_cluster_id = 1;
	public ArrayList<Canal> canaisVizinhos ;
	public ArrayList<Terreno> terrenosVizinhos ;
	private int cluster ;
	
	public Terreno()
	{
		id = last_id++;
		tipoPlantacao = 0 ;
		canaisVizinhos = new ArrayList<Canal>();
		terrenosVizinhos = new ArrayList<Terreno>();
		jogadorProprietario = "";
		
	}
	
	public int getTipoPlantacao() {
		return tipoPlantacao;
	}
	public void setTipoPlantacao(int tipoPlantacao) {
		this.tipoPlantacao = tipoPlantacao;
		updateCluster(false);
	}

	public int getNumMarkers() {
		return numMarkers;
	}
	public void setNumMarkers(int numMarkers) {
		this.numMarkers = numMarkers;
	}
	
	public void addCanal(Canal input)
	{
		input.terrenosVizinhos.add(this);
		canaisVizinhos.add(input);
	}
	
	public void addMultipleCanals(Canal[] input, int[] indices)
	{
		for(int i = 0; i < indices.length; i++){
			this.addCanal(input[indices[i]]);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCluster() {
		return cluster;
	}

	public void setCluster(int cluster) {
		this.cluster = cluster;
	}
	
	public void updateCluster(boolean forceUpdate)
	{
		ArrayDeque<Terreno> sameColor = new ArrayDeque<Terreno>();
		int newPlantacao = this.getTipoPlantacao();
		for(Terreno t : terrenosVizinhos)
		{
			if(newPlantacao == t.getTipoPlantacao())
			{
				sameColor.add(t);
			}
		}
		if(sameColor.size() == 0)
		{
			this.setCluster(setLast_cluster_id(getLast_cluster_id() + 1));
		}
		else if(sameColor.size() == 1)
		{
			if (!forceUpdate)
			{
				this.setCluster(sameColor.getFirst().getCluster());
			}
			else
			{
				this.setCluster(setLast_cluster_id(getLast_cluster_id() + 1));
				sameColor.getFirst().setCluster(this.getCluster());
			}
		}
		else if (sameColor.size() > 1)
		{
			final int chosenClusterID ;
			if (!forceUpdate)
				chosenClusterID = sameColor.getFirst().getCluster();
			else
			{
				chosenClusterID = setLast_cluster_id(getLast_cluster_id() + 1);
			}
			this.setCluster(chosenClusterID);
			//System.out.println("removed: " + sameColor.getFirst().getId());
			//System.out.println("c_cluster_id: " + chosenClusterID);
			sameColor.removeFirst();
			//System.out.println("c_cluster_id: " + chosenClusterID);
			while(sameColor.size() != 0)
			{
				for(Terreno k : sameColor.getFirst().terrenosVizinhos)
				{
					if (k.getTipoPlantacao() == newPlantacao)
					{
						if(k.getCluster() != chosenClusterID )
						{
							sameColor.addLast(k);
							//System.out.println("added: " + k.getId());
						}
						
					}
				}
				sameColor.getFirst().setCluster(chosenClusterID);
				//System.out.println("removed: " + sameColor.getFirst().getId());
				sameColor.removeFirst();
				
			}
			
		}
	}
	
	public int addWaterToCanalVizinho()
	{
		for(Canal c : canaisVizinhos)
		{
			if (c.hasVizinhoComAgua())
			{
				c.setAgua(true);
				return c.getId();
			}
		}
		return -1;
	}
	
	public void addTerrenoVizinho(Terreno t)
	{
		this.terrenosVizinhos.add(t);
	}

	public static int getLast_cluster_id() {
		return last_cluster_id;
	}

	public static int setLast_cluster_id(int _last_cluster_id) {
		Terreno.last_cluster_id = _last_cluster_id;
		return last_cluster_id;
	}

	public String getJogadorProprietario() {
		return jogadorProprietario;
	}

	public void setJogadorProprietario(String jogadorProprietario) {
		this.jogadorProprietario = jogadorProprietario;
	}
	
	public boolean hasAnyWater()
	{
		for(Canal c: canaisVizinhos)
		{
			if (c.hasAgua() == true) return true;
		}
		return false;
	}
	
	public boolean hasVizinhoComPlantancao()
	{
		for(Terreno t: terrenosVizinhos)
		{
			if(t.getTipoPlantacao() != 0) return true;
		}
		return false;
	}
	
	// DEVOLVE O TAMANHO DO MAIOR CLUSTER NOS VIZINHOS, QUE PERTENÇA A UM JOGADOR
	public int get_clustersize_vizinhos(String playerid, ArrayList <Pair<Integer,Integer>> parClusterIdTamanho)
	{
		int max = Integer.MIN_VALUE;
		for(Terreno t: this.terrenosVizinhos)
		{
			if (t.getJogadorProprietario().equals(playerid))
			{
				for(Pair<Integer,Integer> par : parClusterIdTamanho)
				{
					if (t.getCluster() == par.getLeft() && par.getRight() > max)
					{
						max = par.getRight();
						break;
					}
				}
			}
		}
		return 0;
	}
	
	
}
