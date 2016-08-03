package agentLogic;

import java.io.IOException;
import java.util.Random;

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
public class JogadorAleatorio extends Agent {
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
		//ESCOLHE POS NO TABULEIRO
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
		Random rand = new Random();
		int randomNum = rand.nextInt((j1.getDinheiro() - 0) + 1) + 0;
		if(randomNum != 0)
		{
			while(gs1.isLicitacaoLivre(randomNum)==false)
			{
				randomNum = rand.nextInt((j1.getDinheiro() - 0) + 1) + 0;
				if(randomNum == 0) break;
			}
		}
		j1.setDinheiro(j1.getDinheiro()-randomNum);
		j1.setLicitacao(randomNum);
		gs1.updateJogador(j1);
		return randomNum;
	}


	public int makePlant(GameState gs1, Jogador j1) {
		//System.out.println("IMPRIMIR TILES...");
		for(int x=0;x<gs1.tiles.size();x++)
		{
			//System.out.println("TIPO -> " + gs1.tiles.get(x).getTipo());
			//System.out.println("NUMWORKERS -> " + gs1.tiles.get(x).getNumWorkers());
		}
		Random rand = new Random();
		//ESCOLHE TILE
		int randomTile=-1;
		if(gs1.tiles.size()-1>1)
		{
			randomTile = rand.nextInt((gs1.tiles.size()-1 - 0) + 1) + 0;
		}
		else
			randomTile = 0;
		
		//ESCOLHE POS NO TABULEIRO
		int randomPos = rand.nextInt((48 - 1) + 1) + 1;
		while(gs1.tabuleiro.listaTerrenos.get(randomPos).getTipoPlantacao()!=0) 
		{
			randomPos = rand.nextInt((48 - 1) + 1) + 1;
		}
		
		//gs1.tabuleiro.listaTerrenos[randomPos].setTipoPlantacao(gs1.tiles.get(randomTile).getTipo());
		if(randomTile!=-1)
		{
			gs1.setPlantacaoFromTile(randomPos,randomTile,j1.getNome());
			j1.setPlantou(1);
			j1.setPlantouPos(randomPos);
		}
		gs1.updateJogador(j1);
		return randomPos;
	}

	public int makeBribe(GameState gs1, Jogador j1) {
		//System.out.println(j1.getNome() + "RECEBEU UMA PUTA DUM BRIBE");
		Random rand = new Random();
		//ESCOLHE BRIBE
		int randomBribe = rand.nextInt((j1.getDinheiro() - 0) + 1) + 0;
		if(randomBribe != 0)
		{
			while(gs1.isBribeLivre(randomBribe)==false)
			{
				randomBribe = rand.nextInt((j1.getDinheiro() - 0) + 1) + 0;
				if(randomBribe == 0) break;
			}
		}
		j1.setBribe(randomBribe);
		gs1.updateJogador(j1);
		return randomBribe;
	}
	
	public int makeMaxBribe(GameState gs1, Jogador j1) {
		Random rand = new Random();
		//ESCOLHE BRIBE
		int randomPlayer = rand.nextInt((gs1.jogadores.size()-1 - 0) + 1) + 0;
		int randomBribe = rand.nextInt((j1.getDinheiro() - 0) + 1) + 0;
		//DECIDIU APOSTAR
		if(gs1.jogadores.get(randomPlayer).getNome().equals(j1.getNome()))
		{
			if(randomBribe != 0)
			{
				while(randomBribe<=gs1.getMaxBribe())
				{
					randomBribe = rand.nextInt((j1.getDinheiro() - 0) + 1) + 0;
					if(randomBribe == 0) break;
				}
			}
			//CANALOVERSEER GANHOU MAXBRIBE
			if(randomBribe>gs1.getMaxBribe())
			{
				//System.out.println("CANALOVERSEER GANHOU MAXBRIBE");
				j1.setBribe(randomBribe);
				gs1.BribeWinner = j1;
			}
			//CANALOVERSEER PASSOU, GANHA QUEM TEM MAIOR BRIBE
			else
			{
				//System.out.println("GANHA MAX BRIBE: " + gs1.getMaxBribePlayer().getNome());
				j1.setBribe(randomBribe);
				gs1.BribeWinner = gs1.getMaxBribePlayer();
			}
		}
		//DECIDIU DEIXAR O JOGADOR NA POS randomPlayer DECIDIR O SETCHANNEL
		else
		{
			//System.out.println("DEIXOU O JOGADOR DECIDIR: " + gs1.jogadores.get(randomPlayer).getNome());
			gs1.BribeWinner = gs1.jogadores.get(randomPlayer);
			j1.setBribe(0);
		}
		gs1.updateJogador(j1);
		return randomBribe;
	}
	public int makeSetChannel(GameState gs1,Jogador j1) {
		//ESCOLHE POS NO TABULEIRO
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
			//System.out.println("Entrou no pedido para colocar o canal na posicao defenida: " + j1.getNome());
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
