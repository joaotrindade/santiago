package agentLogic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import gameLogic.Canal;
import gameLogic.GameState;
import gameLogic.Jogador;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class JogadorGastador extends Agent {
	boolean acorrer = false;
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


		
		//SE TIVER TERRENO SEM AGUA E COM WORKERS REGAR ESSE TREINO
		boolean enterCase2 = false;
		//TENTAR SEMPRE POR AGUA NO ULTIMO QUE PLANTOU
		if(gs1.tabuleiro.listaTerrenos.get(j1.getPlantouPos()).hasAnyWater()==false)
		{
			ArrayList<Canal> cvizinhos = gs1.tabuleiro.listaTerrenos.get(j1.getPlantouPos()).canaisVizinhos ;
			ArrayList<Canal> canaisCandidatos = new ArrayList<Canal>();
			for (Canal c : cvizinhos)
			{
				if (gs1.isCanalAvailable(c.getId())) canaisCandidatos.add(c);
			}

			if (canaisCandidatos.size() != 0)
			{
				j1.setWantedPos(canaisCandidatos.get(0).getId());
				return(canaisCandidatos.get(0).getId());
			}
			else
			{
				enterCase2 = true;
			}

		} else enterCase2 = true;
		//PROCURAR TERRENOS DELE SEM AGUA
		
		if(enterCase2)
		{
			for(int x=0;x<gs1.tabuleiro.listaTerrenos.size();x++)
			{
				//Terrenos dele
				if(gs1.tabuleiro.listaTerrenos.get(x).getJogadorProprietario().equals(j1.getNome()))
				{
					//Terreno sem agua e com workers
					if(gs1.tabuleiro.listaTerrenos.get(x).hasAnyWater()==false && gs1.tabuleiro.listaTerrenos.get(x).getNumMarkers()>0)
					{
						//Fazer random entre canais vizinhos
						ArrayList<Canal> cvizinhos = gs1.tabuleiro.listaTerrenos.get(x).canaisVizinhos;
						ArrayList<Canal> canaisCandidatos = new ArrayList<Canal>();
						
						for (Canal c : cvizinhos)
						{
							if (gs1.isCanalAvailable(c.getId())) canaisCandidatos.add(c);
						}
						if (canaisCandidatos.size() > 0)
						{
							j1.setWantedPos(canaisCandidatos.get(0).getId());
							return canaisCandidatos.get(0).getId();
						}
					}
					else if(gs1.tabuleiro.listaTerrenos.get(x).hasAnyWater()==false && gs1.tabuleiro.listaTerrenos.get(x).getNumMarkers()==0)
					{
						//Fazer random entre canais vizinhos
						ArrayList<Canal> cvizinhos = gs1.tabuleiro.listaTerrenos.get(x).canaisVizinhos;
						ArrayList<Canal> canaisCandidatos = new ArrayList<Canal>();
						
						for (Canal c : cvizinhos)
						{
							if (gs1.isCanalAvailable(c.getId())) canaisCandidatos.add(c);
						}
						if (canaisCandidatos.size() > 0)
						{
							j1.setWantedPos(canaisCandidatos.get(0).getId());
							return canaisCandidatos.get(0).getId();
						}
					}
				}
			}
		}

		Random rand = new Random();
		int randomChannel = rand.nextInt((31 - 1) + 1) + 1;
		while(!gs1.isCanalAvailable(randomChannel))
		{
			randomChannel = rand.nextInt((31 - 1) + 1) + 1;
		}
		j1.setWantedPos(randomChannel);
		return randomChannel;
	}

	public int makeBid(GameState gs1, Jogador j1) {
		
		int nplayersplant = 0;
		int maxbid = -999;
		for(int x=0;x<gs1.jogadores.size();x++)
		{
			if(gs1.jogadores.get(x).getLicitacao()!=-1)
			{
				nplayersplant++;
				if(gs1.jogadores.get(x).getLicitacao()>maxbid)
					maxbid=gs1.jogadores.get(x).getLicitacao();
			}
		}
		
		double perc=0;
		int bid = 0;
		int minmoney = 0;
		minmoney = j1.getDinheiro();
		Random rand = new Random();
		int randomGame = rand.nextInt((100 - 0) + 1) + 0;
		//Nao vai a jogo com menos de 6,7 de $ a nao ser que calhe uma probabilidade de ir
		if(minmoney < 4 && randomGame<65)
		{
			//Tenta ser CanalOverSeer, ganhar dinheiro para arrasar na proxima ronda
			bid = 0;
		}
		else
		{
			if(randomGame<15 && j1.getDinheiro()<10)
			{
				//Probabilidade de ele nao ir a jogo a nao ser que tenha muito dinheiro para gastar
				bid = 0;
			}
			else if(nplayersplant>=2)
			{
				//Vai a jogo baseado nos outros players que fizeram bid
				if(maxbid <=3)
				{
					//Faz bid com 65% do dinheiro
					perc = 0.65;
					bid = (int) (j1.getDinheiro()*perc);
					//Se estiver ocupado paga acima.
					while(gs1.isLicitacaoLivre(bid)==false)
					{
						if(perc>1)
							break;
						perc = perc + 0.05;
						bid = (int) (j1.getDinheiro()*perc);
					}
				}
				else if(maxbid<=6)
				{
					//Faz bid com 80% do dinheiro
					perc = 0.80;
					bid = (int) (j1.getDinheiro()*perc);
					//Se estiver ocupado paga acima.
					while(gs1.isLicitacaoLivre(bid)==false)
					{
						if(perc>1)
							break;
						perc = perc + 0.05;
						bid = (int) (j1.getDinheiro()*perc);
					}
				}
				else if(maxbid<=9)
				{
					perc = 1;
					bid = (int) (j1.getDinheiro()*perc);
					//Vai ALL IN
					while(gs1.isLicitacaoLivre(bid)==false)
					{
						perc = perc - 0.05;
						bid = (int) (j1.getDinheiro()*perc);
					}
				}
			}
			else
			{
				//Baseia-se apenas no seu dinheiro
				if(j1.getDinheiro()<=3)
				{
					//Tenta ficar a CanalOverSeer para ganhar dinheiro
					bid = 0;
				}
				else if(j1.getDinheiro()<=6)
				{
					//Tenta apostar forte >=3
					perc = 0.80;
					bid = (int) (j1.getDinheiro()*perc);
					while(gs1.isLicitacaoLivre(bid)==false)
					{
						if(perc>1)
							break;
						perc = perc + 0.05;
						bid = (int) (j1.getDinheiro()*perc);
					}
				}
				else if(j1.getDinheiro()<=9)
				{
					//Aposta forte (nao quer dar hipoteses aos outros de ganhar plant)
					perc = 0.90;
					bid = (int) (j1.getDinheiro()*perc);
					while(gs1.isLicitacaoLivre(bid)==false)
					{
						if(perc>1)
							break;
						perc = perc + 0.05;
						bid = (int) (j1.getDinheiro()*perc);
					}
				}
				
			}
		}
		
		j1.setDinheiro(j1.getDinheiro()-bid);
		j1.setLicitacao(bid);
		gs1.updateJogador(j1);
		return bid;
	}


	public int makePlant(GameState gs1, Jogador j1) {
		
		//PERCORRER MATRIZ E VER OS TERRENOS QUE NAO TEM DONO E QUE TEM AGUA A VOLTA
		//SENAO POR POS RANDOM
		
		int maxWorkers = -999;
		int pos = -1;
		for(int i=0;i<gs1.tiles.size();i++)
		{
			if(gs1.tiles.get(i).getNumWorkers()>maxWorkers)
			{
				maxWorkers = gs1.tiles.get(i).getNumWorkers();
				pos = i;
			}
		}
		Random rand = new Random();
		if(pos == -1)
		{
			if(gs1.tiles.size()>1)
			{
				pos = rand.nextInt((gs1.tiles.size()-1 - 0) + 1) + 0;
			}
			else
				pos = 0;
		}
		
		int encontrou = 0;
		int posPlant = 0;
		for(int x=1;x<gs1.tabuleiro.listaTerrenos.size();x++)
		{
			if(gs1.tabuleiro.listaTerrenos.get(x).hasAnyWater())
			{
				if(gs1.tabuleiro.listaTerrenos.get(x).getJogadorProprietario().equals("") && gs1.tabuleiro.listaTerrenos.get(x).getTipoPlantacao()==0)
				{
					gs1.setPlantacaoFromTile(x, pos, j1.getNome());
					j1.setPlantou(1);
					encontrou = 1;
					posPlant = x;
					break;
				}
			}
		}
	
		
		//NAO ENCONTROU, RANDOM
		if(encontrou == 0)
		{
			posPlant = rand.nextInt((48 - 1) + 1) + 1;
			while(gs1.tabuleiro.listaTerrenos.get(posPlant).getTipoPlantacao()!=0) 
			{
				posPlant = rand.nextInt((48 - 1) + 1) + 1;
			}
			
			//gs1.tabuleiro.listaTerrenos[randomPos].setTipoPlantacao(gs1.tiles.get(randomTile).getTipo());
			gs1.setPlantacaoFromTile(posPlant,pos,j1.getNome());
			j1.setPlantou(1);
		}
		j1.setPlantouPos(posPlant);
		//System.out.println("PLANTOU NA POS " + posPlant);
		gs1.updateJogador(j1);
		return posPlant;
	}

	public int makeBribe(GameState gs1, Jogador j1) {
		
		//DEPENDENDO DO DINHEIRO DELE PODERA FAZER BRIBE SE TIVER DINHEIRO QUE O MAXBRIBE ATE AO MOMENTO
		//System.out.println(j1.getNome() + "RECEBEU UMA PUTA DUM BRIBE");
		int countjogadores = 0;
		for(int x=0;x<gs1.jogadores.size();x++)
		{
			if(gs1.jogadores.get(x).getBribe()!=-1)
			{
				countjogadores++;
			}
		}

	
		//SE TIVEREM FEITO 2 OU MAIS BRIBE
		int maxBribeAteMomento = gs1.getMaxBribe();
		if(countjogadores>=2 && j1.getDinheiro()>maxBribeAteMomento)
		{
			//Pouca probabilidade de alguem por muito maior visto que pelo menos 2 ja jogaram
			if(maxBribeAteMomento<=3 && j1.getDinheiro()>maxBribeAteMomento+3)
			{
				j1.setBribe(maxBribeAteMomento+3);
			}
			//Poe o que pode
			else if(maxBribeAteMomento<=3 && j1.getDinheiro()<maxBribeAteMomento+3)
			{
				j1.setBribe(j1.getDinheiro());
			}
			else if(maxBribeAteMomento<=6 && j1.getDinheiro()>maxBribeAteMomento+3)
			{
				j1.setBribe(maxBribeAteMomento+3);
			}
			else if(maxBribeAteMomento<=6 && j1.getDinheiro()<maxBribeAteMomento+3)
			{
				j1.setBribe(j1.getDinheiro());
			}
			//Quase impossivel alguem dar mais
			else if(maxBribeAteMomento<=9 && j1.getDinheiro()>maxBribeAteMomento)
			{
				j1.setBribe(maxBribeAteMomento+1);
			}
			//Da o que pode
			else
			{
				j1.setBribe(j1.getDinheiro());
			}
		}
		else if(countjogadores<2 && j1.getDinheiro()>maxBribeAteMomento)
		{
			if(gs1.getMaxBribe()>0)
			{
				if(j1.getDinheiro()>(maxBribeAteMomento+2))
				{
					if(gs1.getMaxBribe()<=3)
					{
						if(j1.getDinheiro()>=(maxBribeAteMomento*2.5))
						{
							//FAZ SUBORNO FORTE PARA GARANTIR QUE NAO EXISTE OUTRO A FRENTE
							j1.setBribe((int) (maxBribeAteMomento*2.5));
						}
						else
						{
							j1.setBribe(j1.getDinheiro());
						}
					}
					else if(maxBribeAteMomento<=6)
					{
						j1.setBribe(maxBribeAteMomento+2);
					}
					else if(gs1.getMaxBribe()<=9)
					{
						j1.setBribe(maxBribeAteMomento+2);
					}
					else
					{
						j1.setBribe(maxBribeAteMomento+2);
					}
				}
				else
				{
					int valor = j1.getDinheiro();
					while(!gs1.isBribeLivre(valor) )
					{
						valor -= 1; 
						if(valor==0)
							break;
					}
					j1.setBribe(valor);
				}
			}
			else
			{
				j1.setBribe((int) (j1.getDinheiro()*0.85));
			}
		}
		else
		{
			int valor = j1.getDinheiro();
			while(!gs1.isBribeLivre(valor))
			{
				valor -= 1; 
				if(valor==0)
					break;
			}
			j1.setBribe(valor);
		}
		gs1.updateJogador(j1);
		return j1.getBribe();
	}
	
	public int makeMaxBribe(GameState gs1, Jogador j1) {
		int bribe = 0;
		
		//Paga mais que o maior bribe se tiver dinheiro
		if(j1.getDinheiro()>gs1.getMaxBribe())
		{
			bribe = gs1.getMaxBribe()+1;
			j1.setBribe(bribe);
			gs1.BribeWinner = j1;
		}
		//Decide que vai jogar quem lhe der mais dinheiro para gasta-lo na proxima ronda
		else
		{
			bribe = gs1.getMaxBribe();
			gs1.BribeWinner = gs1.getMaxBribePlayer();
		}
		gs1.updateJogador(j1);
		return bribe;
	}

	public int makeSetChannel(GameState gs1,Jogador j1) {		
		int randomPos = 0;
		if(j1.getWantedPos()==-1)
		{
			//CANALOVERSEER
			Random rand = new Random();
			
			randomPos = rand.nextInt((31 - 1) + 1) + 1;
			while(gs1.isCanalAvailable(randomPos) == false)
			{
				randomPos = rand.nextInt((31 - 1) + 1) + 1;
			}
		}
		else
		{
			//NAO E CANAL OVERSEER LOGO IR BUSCAR O WANTEDPOS QUE ELE DEFINIU
			randomPos = j1.getWantedPos();
		}
		
		gs1.setCanalFromPosition(randomPos);
		return randomPos;
	}

	public GameState makeAskChannel(GameState gs1, Jogador j1) {
		Random rand = new Random();
		
		//ESCOLHE POS NO TABULEIRO
		int randomXtraChannel = rand.nextInt((1 - 0) + 1) + 0;
		
		if(randomXtraChannel==1 && j1.hasCanalExtra()==true) 
		{
			//QUER ACTIVAR CANAL EXTRA
			j1.setCanalExtra(false);
		
		    //ESCOLHE POS NO TABULEIRO
		    int randomPos = rand.nextInt((16 - 1) + 1) + 1;
		    while(gs1.isCanalAvailable(randomPos) == false)
		    {
		    	randomPos = rand.nextInt((16 - 1) + 1) + 1;
		    }
			
		    gs1.setCanalFromPosition(randomPos);
		    gs1.updateJogador(j1);
		    gs1.houveActivacaoCanalExtra=1;
		    //System.out.println(getLocalName() + ": EFECTUA EXTRA CHANNEL EM POS : "+ randomPos);
		}
		return gs1;
	}
	
}
