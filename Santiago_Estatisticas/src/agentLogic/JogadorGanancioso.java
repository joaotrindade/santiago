package agentLogic;

import java.io.IOException;
import java.util.Random;

import gameLogic.*;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import misc.Pair;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;



@SuppressWarnings("serial")
public class JogadorGanancioso extends Agent {
	boolean acorrer = false;
	
	static final float noWaterPenalization =(float) 0.5 ;
	
	static final float bidClass1UpperBound = Float.MAX_VALUE;
	static final float bidClass1LowerBound = 10;
	static final float bidClass2UpperBound = (float) 9.9;
	static final float bidClass2LowerBound = 5;
	static final float bidClass3UpperBound = (float) 4.99;
	static final float bidClass3LowerBound = 0;

	
	static final float bribeClass1UpperBound = Float.MAX_VALUE;
	static final float bribeClass1LowerBound = 10;
	static final float bribeClass2UpperBound = (float) 9.9;
	static final float bribeClass2LowerBound = 5;
	static final float bribeClass3UpperBound = (float) 4.99;
	static final float bribeClass3LowerBound = 0;
	class MessageBehaviour extends SimpleBehaviour {

	      // construtor do behaviour
	      public MessageBehaviour(Agent a) {
	         super(a);
	      }

	      // método action
	      public void action() {
				ACLMessage msg = blockingReceive();
				if (msg.getPerformative() == ACLMessage.INFORM || msg.getPerformative() == ACLMessage.QUERY_IF) 
				{
					//System.out.println(getLocalName() + " - " + msg.getConversationId());
					
					if (msg.getContent().contains(getName())) 
					{ 
						// cria resposta
						ACLMessage reply = msg.createReply();
						// preenche conteúdo da mensagem
						reply.setConversationId("FIRST-TO-PLAY");
						reply.setContent("Sou eu o primeiro a jogar " + getLocalName());
						// envia mensagem
						send(reply);
					} 
					else if(msg.getConversationId().equals("BID"))
					{
						//System.out.println(getLocalName() + "RECEBI BID");
						try {
							if (msg.getContentObject() != null) 
							{
								GameState gs1 = (GameState) msg.getContentObject();
								Jogador j1 = gs1.getJogador(getLocalName());
								
								// EXTRACTED 1
								makeBid(gs1, j1);
								
								ACLMessage reply = msg.createReply();
								reply.setContentObject(gs1);
								reply.setConversationId("FINISH BID");
								//System.out.println(getLocalName() + ": EFECTUA BID DE "+ bidValue);
								send(reply);
								
							}
						} catch (UnreadableException | IOException e) {
							e.printStackTrace();
						}
					}
					else if(msg.getConversationId().equals("PLANT"))
					{
						//System.out.println(getLocalName() + "RECEBI PLANT");
						try {
							if (msg.getContentObject() != null) 
							{
								GameState gs1 = (GameState) msg.getContentObject();
								Jogador j1 = gs1.getJogador(getLocalName());
								//System.out.println("RECEBI GAMESTATE ALTERADO: ");
								//gs1.tabuleiro.printTabuleiroTerrenos();
								
								//EXTRACTED 2
								makePlant(gs1, j1);
							    
							    ACLMessage reply = msg.createReply();
								reply.setContentObject(gs1);
								reply.setConversationId("FINISH PLANT");
								//System.out.println(getLocalName() + ": EFECTUA PLANT NA CELULA "+ plantedPos);
								send(reply);
							}
						} catch (UnreadableException | IOException e) {
							e.printStackTrace();
						}
					}
					else if(msg.getConversationId().equals("WANTEDPOS"))
					{
						//System.out.println(getLocalName() + "RECEBI WANTEDPOS");
						try {
							if (msg.getContentObject() != null) 
							{
								GameState gs1 = (GameState) msg.getContentObject();
								Jogador j1 = gs1.getJogador(getLocalName());
								setWantedPos(gs1,j1);
							    
							    ACLMessage reply = msg.createReply();
								reply.setContentObject(gs1);
								reply.setConversationId("FINISH WANTEDPOS");
								//System.out.println(getLocalName() + ": EFECTUA SETCHANNEL EM POS : "+ channelPos);
								send(reply);
							}
						} catch (UnreadableException | IOException e) {
							e.printStackTrace();
						}
					}
					else if(msg.getConversationId().equals("BRIBE"))
					{
						//System.out.println(getLocalName() + "RECEBI BRIBE");
						try {
							if (msg.getContentObject() != null) 
							{
								GameState gs1 = (GameState) msg.getContentObject();
								Jogador j1 = gs1.getJogador(getLocalName());
								
								//EXTRACTED 3
								makeBribe(gs1, j1);
							    
							    
							    ACLMessage reply = msg.createReply();
								reply.setContentObject(gs1);
								reply.setConversationId("FINISH BRIBE");
								//System.out.println(getLocalName() + ": EFECTUA BRIBE COM VALOR "+ bribePos);
								send(reply);
							}
							
						} catch (UnreadableException | IOException e) {
							e.printStackTrace();
						}
					}
					else if(msg.getConversationId().equals("MAXBRIBE"))
					{
						//System.out.println(getLocalName() + "RECEBI MAXBRIBE");
						try {
							if (msg.getContentObject() != null) 
							{
								GameState gs1 = (GameState) msg.getContentObject();
								Jogador j1 = gs1.getJogador(getLocalName());
								
								//EXTRACTED 4
								makeMaxBribe(gs1, j1);
							    
							    ACLMessage reply = msg.createReply();
								reply.setContentObject(gs1);
								reply.setConversationId("FINISH MAXBRIBE");
								//System.out.println(getLocalName() + ": EFECTUA MAXBRIBE COM VALOR "+ bribeValue);
								send(reply);
							}
							
						} catch (UnreadableException | IOException e) {
							e.printStackTrace();
						}
					}
					else if(msg.getConversationId().equals("SETCHANNEL"))
					{
						//System.out.println(getLocalName() + "RECEBI SETCHANNEL");
						try {
							if (msg.getContentObject() != null) 
							{
								GameState gs1 = (GameState) msg.getContentObject();
								Jogador j1 = gs1.getJogador(getLocalName());
								makeSetChannel(gs1,j1);
							    
							    ACLMessage reply = msg.createReply();
								reply.setContentObject(gs1);
								reply.setConversationId("FINISH SETCHANNEL");
								//System.out.println(getLocalName() + ": EFECTUA SETCHANNEL EM POS : "+ channelPos);
								send(reply);
							}
						} catch (UnreadableException | IOException e) {
							e.printStackTrace();
						}
					}
					else if(msg.getConversationId().equals("ASKCHANNEL"))
					{
						//System.out.println(getLocalName() + "RECEBI ASKCHANNEL");
						//System.out.println(this.getAgent().getName());
						//System.out.println("RECEBEU ASKCHANNEL");
						try {
							if (msg.getContentObject() != null) 
							{
								GameState gs1 = (GameState) msg.getContentObject();
								Jogador j1 = gs1.getJogador(getLocalName());
								
								gs1 = makeAskChannel(gs1, j1); 
							    
							    ACLMessage reply = msg.createReply();
								reply.setContentObject(gs1);
								reply.setConversationId("FINISH ASKCHANNEL");
								send(reply);
							}
						} catch (UnreadableException | IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
	      // método done
	      public boolean done() {
	         return acorrer;
	      }

	   } 
	
	
	protected void setup() 
    { 
		 // regista agente no DF
	    DFAgentDescription dfd = new DFAgentDescription();
	    dfd.setName(getAID());
	    ServiceDescription sd = new ServiceDescription();
	    sd.setName("Aleatorio");
	    //Define Agente do tipo + "tipo"
	    sd.setType("Agente " + "Jogador");
	    dfd.addServices(sd);
	    try {
	       DFService.register(this, dfd);
	    } catch(FIPAException e) {
	       e.printStackTrace();
	    }
	    
		 // cria behaviour
		 MessageBehaviour b = new MessageBehaviour(this);
		 addBehaviour(b);
    }
	public int setWantedPos(GameState gs1, Jogador j1) {
		GameState stateK = SerializationUtils.clone(gs1);
		ArrayList<Terreno> terrenosAbilitados = new ArrayList<Terreno>();
		ArrayList<PossibleCanalPlay> canaisPossiveis = new ArrayList<PossibleCanalPlay>();
		boolean enterCase2 = false;
		// VER TERRENOS HABILITADOS
		// TENHO UM TERRENO MEU SEM AGUA
		for (Terreno t : stateK.tabuleiro.listaTerrenos)
		{
			if (t.getJogadorProprietario().equals(j1.getNome()) && !t.hasAnyWater() && t.getTipoPlantacao() != -1)
			{
				terrenosAbilitados.add(t);
			}
		}
		
		if (terrenosAbilitados.size() > 0)
		{
			//System.out.println("CASO 1");
			// GERAR ESTADOS COM AGUA A VOLTA DE CADA TERRENO
			//System.out.println("size de canais abilitados : " + terrenosAbilitados.size());
			for(Terreno t : terrenosAbilitados)
			{
				for(Canal c : t.canaisVizinhos)
				{
					if (c.hasAgua() == false && c.hasVizinhoComAgua())
					{
						GameState estadoNovo = SerializationUtils.clone(stateK);
						PossibleCanalPlay temp = new PossibleCanalPlay(estadoNovo,c.getId());
						temp.calculateEstadoFinal(j1.getNome());
						canaisPossiveis.add(temp);
					}
				}
			}
			
			if (canaisPossiveis.size() > 0)
			{
				//System.out.println("size de canais possiveis : " + canaisPossiveis.size());
				Collections.sort(canaisPossiveis);
				//for (PossibleCanalPlay t : canaisPossiveis)
				//{
				//	System.out.print(t.valor + " ");
				//}
				//System.out.println("Valor escolhido : " + canaisPossiveis.get(0).valor);
				j1.setWantedPos(canaisPossiveis.get(0).canalPosition);
				gs1.updateJogador(j1);
				return canaisPossiveis.get(0).canalPosition;
			}
			else enterCase2 = true;
		}
		else enterCase2 = true;
		
		if(enterCase2)
		{
			//System.out.println("CASO 2");
			// VER SE EXISTE UM TERRENO VIZINHO DE UM TERRENO MEU (EM CLUSTER) QUE NAO TENHA AGUA
			ArrayList <Pair<Integer,Integer>> parClusters = new ArrayList <Pair<Integer,Integer>>();
			parClusters = stateK.tabuleiro.getClusterIdAndSize();
			canaisPossiveis.clear();
			for (Terreno t : stateK.tabuleiro.listaTerrenos)
			{
				if (t.getTipoPlantacao() == 0)
				{
					GameState estadoNovo = SerializationUtils.clone(stateK);
					int pos = estadoNovo.tabuleiro.listaTerrenos.get(t.getId()).addWaterToCanalVizinho();
					if (pos != -1)
					{
						int value = estadoNovo.tabuleiro.listaTerrenos.get(t.getId()).get_clustersize_vizinhos(j1.getNome(), parClusters);
						PossibleCanalPlay temp = new PossibleCanalPlay(estadoNovo,pos);
						canaisPossiveis.add(temp);
						temp.valor = value;
					}
				}
			}
			
			if(canaisPossiveis.size() > 0)
			{
				//System.out.println("size de canais possiveis : " + canaisPossiveis.size());
				Collections.sort(canaisPossiveis);
				//for (PossibleCanalPlay t : canaisPossiveis)
				//{
				//	System.out.print(t.valor + " ");
				//}
				j1.setWantedPos(canaisPossiveis.get(0).canalPosition);
				gs1.updateJogador(j1);
				return canaisPossiveis.get(0).canalPosition;
			}
		}
		
		//System.out.println("CASO 3 - RANDOM");
		Random rand = new Random();
		
		int randomPos = rand.nextInt((31 - 1) + 1) + 1;
		while(gs1.isCanalAvailable(randomPos) == false)
		{
			randomPos = rand.nextInt((31 - 1) + 1) + 1;
		}
		
		j1.setWantedPos(randomPos);
		gs1.updateJogador(j1);
		return randomPos;
		}
	
	public int makeBid(GameState gs1, Jogador j1) {
		GameState stateK = SerializationUtils.clone(gs1);
		int actual_value = stateK.getValorTotalJogador(j1.getNome());
		Vector<Tile> tilesDisponiveis = stateK.tiles;
		ArrayList<Terreno> terrenosPossiveis = new ArrayList<Terreno>(); 
		ArrayList<PossibleTilePlay> jogadasTotais = new ArrayList<PossibleTilePlay>();
		ArrayList<PossibleTilePlay> jogadasSelecionadas = new ArrayList<PossibleTilePlay>();
		
		//stateK.tabuleiro.printTabuleiroTerrenos();

		for(Terreno t : stateK.tabuleiro.listaTerrenos )
		{
			if (t.hasVizinhoComPlantancao() && t.getJogadorProprietario().equals(""))
			{
				terrenosPossiveis.add(t);
			}
		}
		
		if (terrenosPossiveis.size() != 0)
		{
			for(Terreno t : terrenosPossiveis)
			{
				for(int i = 0; i < tilesDisponiveis.size(); i++)
				{
					GameState estadoNovo = SerializationUtils.clone(stateK);
					estadoNovo.setPlantacaoFromTile(t.getId(), i, j1.getNome());
					int value = estadoNovo.getValorTotalJogador(j1.getNome());
					PossibleTilePlay p1 = new PossibleTilePlay(estadoNovo,value,i,t.getId());
					jogadasTotais.add(p1);
				}
			}
			// ORDENA TODAS AS JOGADAS POR VALOR
			Collections.sort(jogadasTotais);

			// SELECIONA AS 4 MELHORES JOGADAS QUE NAO TENHAM OVERLAPS de TILE / TERRENO
			boolean found ;
			for(PossibleTilePlay p : jogadasTotais )
			{
				found = false;
				
				for(PossibleTilePlay selected : jogadasSelecionadas)
				{
					if (p.equals(selected)) found = true;
				}
				
				if (found == false)
				{
					jogadasSelecionadas.add(p);
				}
				
				if (jogadasSelecionadas.size() == 4) break;
			}
			
			//System.out.println("Actual Value: " + actual_value);
			float somatorio = 0 ;
			for(PossibleTilePlay p : jogadasSelecionadas)
			{
				//System.out.print(p.estado.getValorTotalJogador(j1.getNome()) + " ");
				somatorio += (p.estado.getValorTotalJogador(j1.getNome()) - actual_value );
			}
			somatorio = somatorio/jogadasSelecionadas.size();
			//System.out.println("Media do Ganancioso: " + somatorio);
			int valorBid= 0;
			int minValue= 0;
			int maxValue= 0;
			
			if (somatorio >= bidClass1LowerBound && somatorio < bidClass1UpperBound)
			{
				//System.out.println("bid class 1");
				minValue = (int) (0.6 * j1.getDinheiro());
				maxValue = (int) (0.8 * j1.getDinheiro());
			}
			else if (somatorio >= bidClass2LowerBound && somatorio < bidClass2UpperBound)
			{
				//System.out.println("bid class 2");
				minValue = (int) (0.4 * j1.getDinheiro());
				maxValue = (int) (0.6 * j1.getDinheiro());
			}
			else if (somatorio >= bidClass3LowerBound && somatorio < bidClass3UpperBound)
			{
				//System.out.println("bid class 3");
				minValue = 0;
				maxValue = (int) (0.4 * j1.getDinheiro());
			}
			
			Random rand = new Random();
			int timesLooped = 0 ;
			valorBid = rand.nextInt((maxValue - minValue) + 1) + minValue;
			
			while(gs1.isLicitacaoLivre(valorBid)==false)
			{
				valorBid = rand.nextInt((maxValue - minValue) + 1) + minValue;
				timesLooped++;
				if (timesLooped > 100)
				{
					//System.out.println("CASO DE LOOP : TODOS OS VALORES OCUPADOS, VAI BID 0");
					valorBid = 0;
					break;
				}
			}
			
			j1.setDinheiro(j1.getDinheiro()-valorBid);
			j1.setLicitacao(valorBid);
			gs1.updateJogador(j1);
			return valorBid;
		}
		else
		{
			Random rand = new Random();
			int randomNum = rand.nextInt((j1.getDinheiro() - 2) + 1) + 0;
			if(randomNum != 0)
			{
				while(gs1.isLicitacaoLivre(randomNum)==false)
				{
					randomNum = rand.nextInt((j1.getDinheiro() - 2) + 1) + 0;
					if(randomNum == 0) break;
				}
			}
			j1.setDinheiro(j1.getDinheiro()-randomNum);
			j1.setLicitacao(randomNum);
			gs1.updateJogador(j1);
			return randomNum;
		}

	}

	public int makePlant(GameState gs1, Jogador j1) {
		//System.out.println("IMPRIMIR TILES...");
		GameState stateK = SerializationUtils.clone(gs1);
		Vector<Tile> tilesDisponiveis = stateK.tiles;
		ArrayList<Terreno> terrenosPossiveis = new ArrayList<Terreno>(); 
		ArrayList<PossibleTilePlay> jogadasTotais = new ArrayList<PossibleTilePlay>();
		
		//stateK.tabuleiro.printTabuleiroTerrenos();

		for(Terreno t : stateK.tabuleiro.listaTerrenos )
		{
			if (t.hasVizinhoComPlantancao() && t.getJogadorProprietario().equals(""))
			{
				terrenosPossiveis.add(t);
			}
		}
		
		if (terrenosPossiveis.size() != 0)
		{
			for(Terreno t : terrenosPossiveis)
			{
				for(int i = 0; i < tilesDisponiveis.size(); i++)
				{
					GameState estadoNovo = SerializationUtils.clone(stateK);
					estadoNovo.setPlantacaoFromTile(t.getId(), i, j1.getNome());
					int value = estadoNovo.getValorTotalJogador(j1.getNome());
					if (!estadoNovo.tabuleiro.listaTerrenos.get(t.getId()).hasAnyWater())
					{
						value = (int) (value * noWaterPenalization) ;
					}
					PossibleTilePlay p1 = new PossibleTilePlay(estadoNovo,value,i,t.getId());
					jogadasTotais.add(p1);
				}
			}
			// ORDENA TODAS AS JOGADAS POR VALOR
			Collections.sort(jogadasTotais);
			
			gs1.setPlantacaoFromTile(jogadasTotais.get(0).tabuleiroPosition, jogadasTotais.get(0).tileNumber, j1.getNome());
			j1.setPlantou(1);
			j1.setPlantouPos(jogadasTotais.get(0).tabuleiroPosition);
			gs1.updateJogador(j1);
			return jogadasTotais.get(0).tabuleiroPosition;
		}
		else
		{
			Random rand = new Random();
			int randomPos = rand.nextInt((24 - 1) + 1) + 1;
			while(gs1.tabuleiro.listaTerrenos.get(randomPos).getTipoPlantacao()!=0) 
			{
				randomPos = rand.nextInt((24 - 1) + 1) + 1;
			}
			
			int randomTile = rand.nextInt((gs1.tiles.size() - 1 - 0) + 1) + 0;
			
			
			gs1.setPlantacaoFromTile(randomPos,randomTile,j1.getNome());
			j1.setPlantou(1);
			j1.setPlantouPos(randomPos);
			gs1.updateJogador(j1);
			return randomPos;
		}
	}

	public int makeBribe(GameState gs1, Jogador j1) {
		
		int bribeValue = 0 ;
		int improvementValue = 0 ;
		int minValue = 0;
		int maxValue = 0;
		// SE TIVER AGUA bribeValue = 0
		
		GameState stateK = SerializationUtils.clone(gs1);
		PossibleCanalPlay jogada = new PossibleCanalPlay(stateK,j1.getWantedPos());
		jogada.calculateEstadoFinal(j1.getNome());
		improvementValue = jogada.diferenca ;


		if (improvementValue >= bribeClass1LowerBound && improvementValue < bribeClass1UpperBound)
		{
			//System.out.println("bribe class 1");
			minValue = (int) (0.6 * j1.getDinheiro());
			maxValue = (int) (0.8 * j1.getDinheiro());
		}
		else if (improvementValue >= bribeClass2LowerBound && improvementValue < bribeClass2UpperBound)
		{
			//System.out.println("bribe class 2");
			minValue = (int) (0.4 * j1.getDinheiro());
			maxValue = (int) (0.6 * j1.getDinheiro());
		}
		else if (improvementValue >= bribeClass3LowerBound && improvementValue < bribeClass3UpperBound)
		{
			//System.out.println("bribe class 3");
			minValue = 0;
			maxValue = (int) (0.4 * j1.getDinheiro());
		}
			
		Random rand = new Random();
		bribeValue = rand.nextInt((maxValue - minValue) + 1) + minValue;
		j1.setBribe(bribeValue);
		gs1.updateJogador(j1);
		return bribeValue;
	}
	
	public int makeMaxBribe(GameState gs1, Jogador j1) {
		
		int wantedPos = 0 ;
		
		//CALCULA WANTED POS USANDO A MESMA MERDA DE CIMA ^
		
				GameState stateK = SerializationUtils.clone(gs1);
				ArrayList<Terreno> terrenosAbilitados = new ArrayList<Terreno>();
				ArrayList<PossibleCanalPlay> canaisPossiveis = new ArrayList<PossibleCanalPlay>();
				boolean enterCase2 = false;
				// VER TERRENOS HABILITADOS
				// TENHO UM TERRENO MEU SEM AGUA
				for (Terreno t : stateK.tabuleiro.listaTerrenos)
				{
					if (t.getJogadorProprietario().equals(j1.getNome()) && !t.hasAnyWater())
					{
						terrenosAbilitados.add(t);
					}
				}
				
				if (terrenosAbilitados.size() > 0)
				{
					//System.out.println("CASO 1");
					// GERAR ESTADOS COM AGUA A VOLTA DE CADA TERRENO
					//System.out.println("size de canais abilitados : " + terrenosAbilitados.size());
					for(Terreno t : terrenosAbilitados)
					{
						for(Canal c : t.canaisVizinhos)
						{
							if (c.hasAgua() == false && c.hasVizinhoComAgua())
							{
								GameState estadoNovo = SerializationUtils.clone(stateK);
								PossibleCanalPlay temp = new PossibleCanalPlay(estadoNovo,c.getId());
								temp.calculateEstadoFinal(j1.getNome());
								canaisPossiveis.add(temp);
							}
						}
					}
					
					if (canaisPossiveis.size() > 0)
					{
						//System.out.println("size de canais possiveis : " + canaisPossiveis.size());
						Collections.sort(canaisPossiveis);
						//for (PossibleCanalPlay t : canaisPossiveis)
						//{
						//	System.out.print(t.valor + " ");
						//}
						//System.out.println("Valor escolhido : " + canaisPossiveis.get(0).valor);

						wantedPos = canaisPossiveis.get(0).canalPosition;
					}
					else enterCase2 = true;
				}
				else enterCase2 = true;
				
				if(enterCase2)
				{
					//System.out.println("CASO 2");
					// VER SE EXISTE UM TERRENO VIZINHO DE UM TERRENO MEU (EM CLUSTER) QUE NAO TENHA AGUA
					ArrayList <Pair<Integer,Integer>> parClusters = new ArrayList <Pair<Integer,Integer>>();
					parClusters = stateK.tabuleiro.getClusterIdAndSize();
					canaisPossiveis.clear();
					for (Terreno t : stateK.tabuleiro.listaTerrenos)
					{
						if (t.getTipoPlantacao() == 0)
						{
							GameState estadoNovo = SerializationUtils.clone(stateK);
							int pos = estadoNovo.tabuleiro.listaTerrenos.get(t.getId()).addWaterToCanalVizinho();
							if (pos != -1)
							{
								int value = estadoNovo.tabuleiro.listaTerrenos.get(t.getId()).get_clustersize_vizinhos(j1.getNome(), parClusters);
								PossibleCanalPlay temp = new PossibleCanalPlay(estadoNovo,pos);
								canaisPossiveis.add(temp);
								temp.valor = value;
							}
						}
					}
					
					if(canaisPossiveis.size() > 0)
					{
						//System.out.println("size de canais possiveis : " + canaisPossiveis.size());
						Collections.sort(canaisPossiveis);
						//for (PossibleCanalPlay t : canaisPossiveis)
						//{
						//	System.out.print(t.valor + " ");
						//}
						wantedPos = canaisPossiveis.get(0).canalPosition;
					}
				}
				
		// MERDAS DO MAX BRIBE COMEÇAM AQUI 
		
		
		
		int improvementValue = 0 ;
		
		GameState stateK2 = SerializationUtils.clone(gs1);
		PossibleCanalPlay jogada = new PossibleCanalPlay(stateK2,wantedPos);
		jogada.calculateEstadoFinal(j1.getNome());
		improvementValue = jogada.diferenca ;

		
		if (improvementValue + gs1.getValueBribeOnPosition(wantedPos) > gs1.getTopBribe())
		{
			int topBribe = gs1.getTopBribe() ;
			int aumentaRandom = 0 ;
			if (j1.getDinheiro() >= topBribe + 1) // SE COMPENSAR FAZER RAISE
			{
				Random rand = new Random();
				aumentaRandom = rand.nextInt((10 - 0) + 1) + 0;
				if (aumentaRandom >= 5)
				{
					// RAISE
					//System.out.println("*********** OC FAZ RAISE ************");
					j1.setBribe(gs1.getTopBribe() + 1 );
					j1.setWantedPos(wantedPos);
					gs1.BribeWinner = j1 ;
					return gs1.getTopBribe() + 1 ;
				}
				else
				{
					// TOP BRIBE
					//System.out.println("*********** TOP BRIBE ************");
					gs1.BribeWinner = gs1.getJogador(gs1.getMaxBribePlayer().getNome());
					return gs1.getTopBribe() ;
				}
			}
			else
			{
				//System.out.println("*********** FIRST ELSE ************");
				gs1.BribeWinner = gs1.getJogador(gs1.getMaxBribePlayer().getNome());
				return gs1.getTopBribe() ;
			}
		}
		else
		{
			//System.out.println("*********** SECOND ELSE ************");
			gs1.BribeWinner = gs1.getJogador(gs1.getMaxBribePlayer().getNome());
			return gs1.getTopBribe() ;
		}
	}

	public int makeSetChannel(GameState gs1, Jogador j1) {
		int posicaoFinalDoCanal = 0;
		//System.out.println("ENTROU NO SET CHANNEL DO GANANCIAS");
		posicaoFinalDoCanal = j1.getWantedPos();
		gs1.setCanalFromPosition(posicaoFinalDoCanal);
		//System.out.println("SAIU NO SET CHANNEL DO GANANCIAS");
		return posicaoFinalDoCanal;
	}

	public GameState makeAskChannel(GameState gs1, Jogador j1) {
		Random rand = new Random();
		//System.out.println("CANAL EXTRA GANANCIOSO");
		//ESCOLHE POS NO TABULEIRO
		int randomXtraChannel = rand.nextInt((1 - 0) + 1) + 0;
		int loopcounter = 0 ;
		if(randomXtraChannel==1 && j1.hasCanalExtra()==true) 
		{
			//QUER ACTIVAR CANAL EXTRA
			j1.setCanalExtra(false);
		
		    //ESCOLHE POS NO TABULEIRO
		    int randomPos = rand.nextInt((16 - 1) + 1) + 1;
		    while(gs1.isCanalAvailable(randomPos) == false)
		    {
		    	loopcounter++;
		    	randomPos = rand.nextInt((16 - 1) + 1) + 1;
		    	if (loopcounter == 500)
	    		{
		    		//System.out.println("LOOP BREAKER EXTRA CHANNEL");
		    		return gs1;
	    		}
		    }
			
		    gs1.setCanalFromPosition(randomPos);
		    gs1.updateJogador(j1);
		    gs1.houveActivacaoCanalExtra=1;
		    //System.out.println(getLocalName() + ": EFECTUA EXTRA CHANNEL EM POS : "+ randomPos);
		}
		return gs1;
	}
	
}
