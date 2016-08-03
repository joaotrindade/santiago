package gameLogic;

import java.io.Serializable;
import java.util.ArrayList;

import misc.Pair;
@SuppressWarnings("serial")
public class Tabuleiro implements Serializable {
	public ArrayList<Terreno> listaTerrenos;
	public ArrayList<Canal> listaCanais;
	
	
	public Tabuleiro(Tabuleiro original)
	{
		listaTerrenos = new ArrayList<Terreno>(original.listaTerrenos);
		listaCanais = new ArrayList<Canal>(original.listaCanais);
	}
	
	public Tabuleiro()
	{
		
		listaTerrenos = new ArrayList<Terreno>();
		listaCanais = new ArrayList<Canal>(); 
		
		Terreno[] arrayTerreno = new Terreno[49] ;
		for(int i = 0 ; i < arrayTerreno.length ; i++)
		{
			arrayTerreno[i] = new Terreno();
			listaTerrenos.add(arrayTerreno[i]);
		}
		Canal[] arrayCanal = new Canal[32];
		for(int i = 0 ; i < arrayCanal.length ; i++)
		{
			arrayCanal[i] = new Canal();
			listaCanais.add(arrayCanal[i]);
		}
				
		/* NEW */
		arrayTerreno[1].addMultipleCanals(arrayCanal, new int[]{1,17});
		arrayTerreno[2].addMultipleCanals(arrayCanal, new int[]{1,18});
		arrayTerreno[3].addMultipleCanals(arrayCanal, new int[]{2,18});
		arrayTerreno[4].addMultipleCanals(arrayCanal, new int[]{2,19});
		
		arrayTerreno[5].addMultipleCanals(arrayCanal, new int[]{3,19});
		arrayTerreno[6].addMultipleCanals(arrayCanal, new int[]{3,20});
		arrayTerreno[7].addMultipleCanals(arrayCanal, new int[]{4,20});
		arrayTerreno[8].addMultipleCanals(arrayCanal, new int[]{4,21});
		
		arrayTerreno[9].addMultipleCanals(arrayCanal, new int[]{5,17});
		arrayTerreno[10].addMultipleCanals(arrayCanal, new int[]{5,18});
		arrayTerreno[11].addMultipleCanals(arrayCanal, new int[]{6,18});
		arrayTerreno[12].addMultipleCanals(arrayCanal, new int[]{6,19});
		
		arrayTerreno[13].addMultipleCanals(arrayCanal, new int[]{7,19});
		arrayTerreno[14].addMultipleCanals(arrayCanal, new int[]{7,20});
		arrayTerreno[15].addMultipleCanals(arrayCanal, new int[]{8,20});
		arrayTerreno[16].addMultipleCanals(arrayCanal, new int[]{8,21});
	
		arrayTerreno[17].addMultipleCanals(arrayCanal, new int[]{5,22});
		arrayTerreno[18].addMultipleCanals(arrayCanal, new int[]{5,23});
		arrayTerreno[19].addMultipleCanals(arrayCanal, new int[]{6,23});
		arrayTerreno[20].addMultipleCanals(arrayCanal, new int[]{6,24});
		
		arrayTerreno[21].addMultipleCanals(arrayCanal, new int[]{7,24});
		arrayTerreno[22].addMultipleCanals(arrayCanal, new int[]{7,25});
		arrayTerreno[23].addMultipleCanals(arrayCanal, new int[]{8,25});
		arrayTerreno[24].addMultipleCanals(arrayCanal, new int[]{8,26});
		
		arrayTerreno[25].addMultipleCanals(arrayCanal, new int[]{9,22});
		arrayTerreno[26].addMultipleCanals(arrayCanal, new int[]{9,23});
		arrayTerreno[27].addMultipleCanals(arrayCanal, new int[]{10,23});
		arrayTerreno[28].addMultipleCanals(arrayCanal, new int[]{10,24});
		
		arrayTerreno[29].addMultipleCanals(arrayCanal, new int[]{11,24});
		arrayTerreno[30].addMultipleCanals(arrayCanal, new int[]{11,25});
		arrayTerreno[31].addMultipleCanals(arrayCanal, new int[]{12,25});
		arrayTerreno[32].addMultipleCanals(arrayCanal, new int[]{12,26});
		
		arrayTerreno[33].addMultipleCanals(arrayCanal, new int[]{9,27});
		arrayTerreno[34].addMultipleCanals(arrayCanal, new int[]{9,28});
		arrayTerreno[35].addMultipleCanals(arrayCanal, new int[]{10,28});
		arrayTerreno[36].addMultipleCanals(arrayCanal, new int[]{10,29});
		
		arrayTerreno[37].addMultipleCanals(arrayCanal, new int[]{11,29});
		arrayTerreno[38].addMultipleCanals(arrayCanal, new int[]{11,30});
		arrayTerreno[39].addMultipleCanals(arrayCanal, new int[]{12,30});
		arrayTerreno[40].addMultipleCanals(arrayCanal, new int[]{12,31});
		
		arrayTerreno[41].addMultipleCanals(arrayCanal, new int[]{13,27});
		arrayTerreno[42].addMultipleCanals(arrayCanal, new int[]{13,28});
		arrayTerreno[43].addMultipleCanals(arrayCanal, new int[]{14,28});
		arrayTerreno[44].addMultipleCanals(arrayCanal, new int[]{14,29});
		
		arrayTerreno[45].addMultipleCanals(arrayCanal, new int[]{15,29});
		arrayTerreno[46].addMultipleCanals(arrayCanal, new int[]{15,30});
		arrayTerreno[47].addMultipleCanals(arrayCanal, new int[]{16,30});
		arrayTerreno[48].addMultipleCanals(arrayCanal, new int[]{16,31});
		
		for(int i = 1; i < 49; i++)
		{
			if(i % 8 != 0)
			{
				arrayTerreno[i].addTerrenoVizinho(arrayTerreno[i+1]);
				arrayTerreno[i+1].addTerrenoVizinho(arrayTerreno[i]);
			}
			if(i >= 9)
			{
				arrayTerreno[i].addTerrenoVizinho(arrayTerreno[i-8]);
				arrayTerreno[i-8].addTerrenoVizinho(arrayTerreno[i]);
			}
		}
		
		arrayCanal[1].addMultipleCanals(arrayCanal, new int[]{2,17,18});
		arrayCanal[2].addMultipleCanals(arrayCanal, new int[]{1,3,18,19});
		arrayCanal[3].addMultipleCanals(arrayCanal, new int[]{2,4,19,20});
		arrayCanal[4].addMultipleCanals(arrayCanal, new int[]{3,20,21});
		
		arrayCanal[5].addMultipleCanals(arrayCanal, new int[]{17,22,18,23,6});
		arrayCanal[6].addMultipleCanals(arrayCanal, new int[]{18,23,19,24,5,7});
		arrayCanal[7].addMultipleCanals(arrayCanal, new int[]{19,20,24,25,6,8});
		arrayCanal[8].addMultipleCanals(arrayCanal, new int[]{20,21,25,26,7});
		
		arrayCanal[9].addMultipleCanals(arrayCanal, new int[]{22,23,27,28,10});
		arrayCanal[10].addMultipleCanals(arrayCanal, new int[]{9,11,23,24,28,29});
		arrayCanal[11].addMultipleCanals(arrayCanal, new int[]{24,25,29,30,10,12});
		arrayCanal[12].addMultipleCanals(arrayCanal, new int[]{25,26,30,31,11});
		
		arrayCanal[13].addMultipleCanals(arrayCanal, new int[]{27,28,14});
		arrayCanal[14].addMultipleCanals(arrayCanal, new int[]{13,15,28,29});
		arrayCanal[15].addMultipleCanals(arrayCanal, new int[]{14,16,29,30});
		arrayCanal[16].addMultipleCanals(arrayCanal, new int[]{15,30,31});

	    arrayCanal[17].addMultipleCanals(arrayCanal, new int[]{1,5,22});
	    arrayCanal[18].addMultipleCanals(arrayCanal, new int[]{1,5,2,6,23});
	    arrayCanal[19].addMultipleCanals(arrayCanal, new int[]{2,6,3,7,24});
	    arrayCanal[20].addMultipleCanals(arrayCanal, new int[]{3,7,4,8,25});
	    arrayCanal[21].addMultipleCanals(arrayCanal, new int[]{4,8,26});
	     
	    arrayCanal[22].addMultipleCanals(arrayCanal, new int[]{5,9,17,27});
	    arrayCanal[23].addMultipleCanals(arrayCanal, new int[]{5,9,6,10,18,28});
	    arrayCanal[24].addMultipleCanals(arrayCanal, new int[]{6,10,7,11,19,29});
	    arrayCanal[25].addMultipleCanals(arrayCanal, new int[]{7,11,8,12,20,30});
	    arrayCanal[26].addMultipleCanals(arrayCanal, new int[]{8,12,21,31});
	     
	    arrayCanal[27].addMultipleCanals(arrayCanal, new int[]{9,13,22});
	    arrayCanal[28].addMultipleCanals(arrayCanal, new int[]{9,13,10,14,23});
	    arrayCanal[29].addMultipleCanals(arrayCanal, new int[]{10,14,11,15,24});
	    arrayCanal[30].addMultipleCanals(arrayCanal, new int[]{11,15,12,16,25});
	    arrayCanal[31].addMultipleCanals(arrayCanal, new int[]{12,16,26});

	}
	
	public void printTabuleiroTerrenos()
	{
		int x = 1;
		for(int i=1; i <=48 ; i++)
		{
			System.out.print(listaTerrenos.get(i).getTipoPlantacao() + "\t");
			if (x == 8)
			{
				System.out.println();
				x = 0 ;
			}
			x++;
		}
	}

	public ArrayList<Terreno> getCluster(int cluster_id)
	{
		ArrayList<Terreno> result = new ArrayList<Terreno>();
		for(int i = 1 ; i <=48 ; i++ )
		{
			if (listaTerrenos.get(i).getCluster() == cluster_id) result.add(listaTerrenos.get(i));
		}
		if(result.size() != 0) return result;
		else return null;
	}
	
	public ArrayList< ArrayList<Terreno> > getAllClusters()
	{
		ArrayList<ArrayList<Terreno>> result = new ArrayList<ArrayList<Terreno>>();
		for(int i = 1 ; i <= Terreno.getLast_cluster_id(); i++)
		{
			ArrayList<Terreno> clusterI = new ArrayList<Terreno>();
			clusterI = getCluster(i);
			if(clusterI != null) result.add(clusterI);
		}
		return result;
	}
	
	// PAR: CLUSTER ID - TAMANHO
	public ArrayList <Pair<Integer,Integer>> getClusterIdAndSize()
	{
		ArrayList<ArrayList<Terreno>> listaClusters = new ArrayList<ArrayList<Terreno>>();
		ArrayList <Pair<Integer,Integer>> result = new ArrayList <Pair<Integer,Integer>>();
		listaClusters = this.getAllClusters();
		
		for (ArrayList<Terreno> k : listaClusters)
		{
			Pair<Integer,Integer> temp = new Pair<Integer, Integer>(k.get(0).getCluster(),k.size());
			result.add(temp);
		}
		
		return result;
	}

	public int getNumeroCanaisUsados()
	{
		int count = 0;
		for(Canal c : listaCanais)
		{
			if (c.hasAgua() == true) count++;
		}
		return count;
	}
	
	public int getNumeroTerrenosLivres()
	{
		int count = 0;
		for(int i = 1; i < listaTerrenos.size(); i++)
		{
			if (listaTerrenos.get(i).getTipoPlantacao() == 0) count++;
		}
		return count;
	}
}

