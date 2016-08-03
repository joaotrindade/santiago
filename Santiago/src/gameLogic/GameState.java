package gameLogic;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
 
@SuppressWarnings("serial")
public class GameState implements Serializable {
        public Tabuleiro tabuleiro;
        public ArrayList<Jogador> jogadores;
        public String canalOverseerName;
        public Vector<Tile> tiles;
        public int houveActivacaoCanalExtra;
        public int firstWater;
        public Jogador BribeWinner;
        
                
        public GameState()
        {
                tabuleiro = new Tabuleiro();
                jogadores = new ArrayList<Jogador>();
                canalOverseerName = "";
                tiles = new Vector<Tile>();
                houveActivacaoCanalExtra=0;
                BribeWinner = new Jogador("");
        }
        
        public GameState(GameState original)
        {
                tabuleiro = new Tabuleiro(original.tabuleiro);
                jogadores = new ArrayList<Jogador>(original.jogadores);
                canalOverseerName = "";
                tiles = new Vector<Tile>(original.tiles);
                houveActivacaoCanalExtra=0;
        }
        
        public int getValueBribeOnPosition(int canalId)
        {
        	int count = 0 ;
        	for(Jogador k : jogadores)
        	{
        		if (k.getWantedPos() == canalId) count += k.getBribe() ;
        	}
        	return count;
        }
        
        public int getTopBribe()
        {
        	int max = Integer.MIN_VALUE;
        	for(Jogador k : jogadores)
        	{
        		if (k.getBribe() > max) max = k.getBribe();
        	}
        	return max;
        }
        
        
        public ArrayList<Integer> getBribePositions()
        {
        	ArrayList<Integer> result = new ArrayList<Integer>();
        	for(Jogador k : jogadores)
        	{
        		result.add(k.getWantedPos());
        	}
        	return result;
        }
        
       
       public boolean isCanalAvailable(int canalPos)
        {
        	
        	if(tabuleiro.getNumeroCanaisUsados() == 0)
        	{
        		//System.out.println("here here");
        		// USE FIRST WATER VARIABLE
        		switch (firstWater)
        		{
        			case 1 : if (canalPos == 1 || canalPos == 17) return true; else return false; 
        			case 2 : if (canalPos == 1 || canalPos == 2 || canalPos == 18) return true; else return false; 
        			case 3 : if (canalPos == 5 || canalPos == 17 || canalPos == 22) return true; else return false; 
        			case 4 : if (canalPos == 5 || canalPos == 18 || canalPos == 23 || canalPos == 6) return true; else return false; 
        			default: return false;
        		}
        		
        		
        	}
        	else
        	{
        		//System.out.println("there there");
        		if (tabuleiro.listaCanais.get(canalPos).hasAgua() == true) return false;
        		else
        		{
        			for(Canal c: tabuleiro.listaCanais.get(canalPos).canaisVizinhos)
        		
	        		{
	        			if (c.hasAgua() == true) return true;
	        		}
	        		
	        		return false;
        		}
        	}
        }
        
       public void updateWorkers()
       {
    	   for(Terreno t: tabuleiro.listaTerrenos)
    	   {
    		   if (!t.getJogadorProprietario().equals(""))
    		   {
    			   if(!t.hasAnyWater())
    			   {
    				   if(t.getNumMarkers() >= 0)
					   {
    					   t.setNumMarkers(t.getNumMarkers() - 1);
    					   if (t.getNumMarkers() == -1)
    					   {
    						   int presentCluster = t.getCluster();
    						   t.setTipoPlantacao(-1);
    						   t.setCluster(-1);
    						   for(Terreno k : t.terrenosVizinhos)
    						   {
    							   if (k.getCluster() == presentCluster && k.getCluster() != 0 && k.getCluster() != -1 &&   k.getJogadorProprietario() != "" && k.getTipoPlantacao() != -1 )
    								   k.updateCluster(true);
    						   }
    					   }
					   } 
    			   }
    		   }
    	   }
       }
       
        public void updateJogador(Jogador _jogador)
        {
                for (Jogador j : jogadores)
                {
                        if (j.getNome().equals(_jogador.getNome()))
                        {
                                j.setCanalExtra(_jogador.hasCanalExtra());
                                j.setDinheiro(_jogador.getDinheiro());
                                j.setLicitacao(_jogador.getLicitacao());
                        }
                }
        }
       
        public void addJogador(String n_nome)
        {
                Jogador novo = new Jogador(n_nome);
                jogadores.add(novo);
        }
       
        public Jogador getJogador(String nome)
        {
                for(Jogador j : jogadores)
                {
                        if (j.getNome().equals(nome)) 
                        {
                        	return j;
                        }
                }
                return null;
        }
       
        public int getValorTotalJogador(String playerId)
        {
                ArrayList< ArrayList<Terreno> > clusters = new ArrayList< ArrayList<Terreno> >();
                clusters = tabuleiro.getAllClusters();
                //System.out.println("Clusters size: " + clusters.size());
                int valor = 0;
                for(int i = 0; i < clusters.size(); i++ )
                {
                        //System.out.println("number of terrs in cluster: " + clusters.get(i).size());
                        int sumProducao = 0;
                        for(Terreno t : clusters.get(i))
                        {
                                if (t.getJogadorProprietario().equals(playerId))
                                {
                                		if (t.getNumMarkers() != -1 )
                                			sumProducao+= t.getNumMarkers();
                                        //System.out.println("sum: " + sumProducao);
                                }
                        }
                        valor += sumProducao*clusters.get(i).size();
                }
               
                return valor;
        }
 
        public boolean isLicitacaoLivre(int valor)
        {
                for(Jogador j : jogadores)
                {
                        if (j.getLicitacao() == valor) return false;
                }
                return true;
        }
        
        public boolean isBribeLivre(int valor)
        {
        		if (valor == 0) return true;
                for(Jogador j : jogadores)
                {
                    if (j.getBribe() == valor) return false;
                }
                return true;
        }
        
        public int getMaxBribe()
        {
			int max = -999;
			for (Jogador j : jogadores) 
			{
				if (j.getBribe() > max)
					max = j.getBribe();
			}
			if(max == 999) return 0;
			else return max;
        }
        
        public Jogador getMaxBribePlayer()
        {
        	int max= Integer.MIN_VALUE;
        	String nomeJogador = "";
			for (Jogador j : jogadores) 
			{
				if (j.getBribe() > max)
				{
					max = j.getBribe();
					nomeJogador = j.getNome();
				}
			}
			
			return this.getJogador(nomeJogador);

        }
        
        public void setDinheiro()
        {
			for (Jogador j : jogadores) 
			{
				j.setDinheiro(j.getDinheiro()+3);
			}
        }
        
        public void resetJogadores()
        {
			for (Jogador j : jogadores) 
			{
				j.setLicitacao(-1);
                j.setPlantou(0);
                j.setBribe(-1);
                j.setWantedPos(-1);
                j.setPlantouPos(0);
			}
        }
        
        public void resetTiles()
        {
        	tiles = new Vector<Tile>();
        }
        
        public void resetHouveActivacaoCanalExtra()
        {
        	houveActivacaoCanalExtra=-1;
        }
        
        public void resetCanalOverSeerName()
        {
        	canalOverseerName = "";
        }

        public void setPlantacaoFromTile(int tabPosition, int tilePos, String playerName)
        {
        	Terreno t1 = tabuleiro.listaTerrenos.get(tabPosition);
        	t1.setTipoPlantacao(tiles.get(tilePos).getTipo());
        	t1.setNumMarkers(tiles.get(tilePos).getNumWorkers());
        	t1.setJogadorProprietario(playerName);
        	tabuleiro.listaTerrenos.set(tabPosition, t1);
        	
        	tiles.remove(tilePos);
        	
        }
        
        public void setCanalFromPosition(int canalPositionOnTabuleiro)
        {
        	Canal c1 = tabuleiro.listaCanais.get(canalPositionOnTabuleiro);
        	c1.setAgua(true);
        	tabuleiro.listaCanais.set(canalPositionOnTabuleiro, c1);
        	
        }
}