package agentLogic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Random;
import java.util.Vector;
import gameLogic.GameState;
import gameLogic.Jogador;
import gameLogic.Tile;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

@SuppressWarnings("serial")
public class Manager extends Agent {
	public GameState gs1;
	//public GameFrame mainFrame;
	int maxplayers;
	int bidn=0;
	int plantn=0;
	int askwantedn=0;
	int briben=0;
	int pos=0;
	int askchan=0;
	int extraActivated=0;
	int plantsecondtime=0;
	int nrounds = 1;
	boolean sleeps;
	Vector<String> asked = new Vector<String>();
	DFAgentDescription[] result = null;
	File file = new File("stats.txt");
	
	
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
				if (msg.getContent().contains("Sou eu o primeiro a jogar")) 
				{
					setPlantacoes();
					ACLMessage reply = msg.createReply();
					// preenche conteúdo da mensagem
					try {
						reply.setConversationId("BID");
						reply.setContentObject(gs1);
					} catch (IOException e) {
						e.printStackTrace();
					}
					// envia mensagem
					send(reply);
				}
				else if(msg.getConversationId().equals("FINISH BID")) 
				{
					//Fase de BID completa -> Escolher Canal Overseer e Pedir Plantação ao jogador com maior BID
					bidn++;
					if(bidn == maxplayers)
					{
						GUIredraw();
						if(sleeps)
						{
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						askForFirstPlant(msg);
					}
					else
					{
						waitForAllBids(msg);
					}
				}
				else if(msg.getConversationId().equals("FINISH PLANT"))
				{
					plantn++;
					try {
						gs1 = (GameState) msg.getContentObject();
						//GUIredraw();
						//gs1.tabuleiro.printTabuleiroTerrenos();
					} catch (UnreadableException e1) {
						e1.printStackTrace();
					}
					if(plantn==maxplayers && maxplayers<5)
					{
						if(maxplayers==3 && plantsecondtime==0)
						{
							plantn--;
							try {
								plantsecondtime = 1;
								DFAgentDescription template = new DFAgentDescription();
						        ServiceDescription sd1 = new ServiceDescription();
						        sd1.setType("Agente Jogador");
						        template.addServices(sd1);
						        DFAgentDescription[] result = null;
								result = DFService.search(myAgent, template);
								for(int x=0;x<result.length;x++)
								{
									if(result[x].getName().getLocalName().equals(gs1.jogadores.get(gs1.jogadores.size()-1).getNome()))
									{
										try {
											
											//Primeiro a plantar
											ACLMessage msgBID = new ACLMessage(ACLMessage.INFORM);
											msgBID.addReceiver(result[x].getName());
							    			msgBID.setConversationId("PLANT");
											msgBID.setContentObject(gs1);
											send(msgBID);
											
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
								}
							} catch(FIPAException e) {
								e.printStackTrace();
							}
						}
						else
						{
							GUIredraw();
							if(sleeps)
							{
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
							askWantedPos(msg);
						}
						
					}
					else if(plantn==maxplayers-1 && maxplayers==5)
					{
						GUIredraw();
						if(sleeps)
						{
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						askWantedPos(msg);
					}
					else
					{
						waitForAllPlants(msg);
					}
				}
				else if(msg.getConversationId().equals("FINISH WANTEDPOS"))
				{
					askwantedn++;
					try {
						gs1 = (GameState) msg.getContentObject();
						//GUIredraw();
						//gs1.tabuleiro.printTabuleiroTerrenos();
					} catch (UnreadableException e1) {
						e1.printStackTrace();
					}
					for(int x=0;x<gs1.jogadores.size();x++)
					{
						//System.out.println("JOGADOR : " + gs1.jogadores.get(x).getNome() + " QUER CHANNEL EM : " + gs1.jogadores.get(x).getWantedPos());
					}
					if(askwantedn == maxplayers-1)
					{
						startBribe(msg);
					}
					else
					{
						waitForAllAsksWantedPos(msg);
					}
				}
				else if(msg.getConversationId().equals("FINISH BRIBE"))
				{
					briben++;
					//MAXPLAYERS - 1 CanalOverSeer Apenas faz no fim
					if(briben==maxplayers-1)
					{
						GUIredraw();
						if(sleeps)
						{
							try {
								Thread.sleep(300);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						askCanalOverSeerAboutBribe(msg);
					}
					else
					{
						waitForAllBribes(msg);
					}
				}
				else if(msg.getConversationId().equals("FINISH MAXBRIBE"))
				{
					askForSetChannel(msg);
				}
				else if(msg.getConversationId().equals("FINISH SETCHANNEL"))
				{
					
					//System.out.println("MANAGER RECEBEU FINISH SETCHANNEL");
					GUIredraw();
					if(sleeps)
					{
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					askForExtraChannel(msg);				
				}
				else if(msg.getConversationId().equals("FINISH ASKCHANNEL"))
				{
					askchan++;
					//System.out.println("ASKCHANNELS : " + askchan);
					try {
						gs1 = (GameState) msg.getContentObject();
						if(gs1.houveActivacaoCanalExtra==1)
						{
							extraActivated=1;
							//System.out.println("HOUVE UTILIZACAO DE EXTRA CHANNEL");
						}
					} catch (UnreadableException e1) {
						e1.printStackTrace();
					}
					if(extraActivated==0)
					{
						//System.out.println("sem activacao");
						if(askchan==maxplayers)
						{
							//System.out.println("chegou ao fim da pergunta");
							if(gs1.tabuleiro.getNumeroTerrenosLivres()!=0)
							{
								//System.out.println("NUMERO TERRENOS LIVRES : " + gs1.tabuleiro.getNumeroTerrenosLivres());
								try {
									//System.in.read();
									GUIredraw();
									if (sleeps) Thread.sleep(3000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								startNewRound();
							}
							else
							{
								try {
									gameOver();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
						else
						{
							//System.out.println("a peguntar askchannel");
							waitForAsksExtraChannel();
						}
					}
					else
					{
						if(gs1.tabuleiro.getNumeroTerrenosLivres()!=0)
						{
							try {
								GUIredraw();
								if(sleeps)Thread.sleep(3000);
								//System.in.read();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							startNewRound();
						}
						else
						{
							try {
								gameOver();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
				////System.out.println(getLocalName() + ": recebi " + msg.getConversationId());
			}
		}

	      // método done
	      public boolean done() {
	         return false;
	      }

	   } 
	
	
	protected void setup() 
    { 
		Object[] args = getArguments();
		maxplayers = Integer.parseInt((String) args[0]);
		sleeps = false;
		//System.out.println("MAXPLAYERS : " + maxplayers);
		gs1 = new GameState();
		//mainFrame = new GameFrame();
		//GUIredraw();
		
		gs1.firstWater = 4 ;
		
		//mainFrame.main();
		 // regista agente no DF
	    DFAgentDescription dfd = new DFAgentDescription();
	    dfd.setName(getAID());
	    ServiceDescription sd = new ServiceDescription();
	    sd.setName("Manager");
	    sd.setType("Agente " + "Manager");
	    dfd.addServices(sd);
	    try {
	       DFService.register(this, dfd);
	    } catch(FIPAException e) {
	       e.printStackTrace();
	    }
	    DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd1 = new ServiceDescription();
        sd1.setType("Agente Jogador");
        template.addServices(sd1);
        
        // cria behaviour
     	MessageBehaviour b = new MessageBehaviour(this);
     	addBehaviour(b);
     		 
     		 
        try {
		    int nplayers = 0;
		    while(nplayers == 0) {
		    	result = DFService.search(this, template);
		    	for(int x=0;x<result.length;x++)
		    	{
		    		//System.out.println("NOME: " + result[x].getName());
		    	}
				if (result.length == maxplayers) 
				{
					for (int x = 0; x < result.length; x++) {
						gs1.addJogador(result[x].getName().getLocalName());
					}
					nplayers = 1;
				}
		    }
        } catch(FIPAException e) { e.printStackTrace(); }
        //System.out.println(gs1.jogadores.size());
        
        
        //mainFrame.mainpanel.setPlayerAlias(gs1.jogadores);
        
        //Random rnd = new Random();
    	//int num = rnd.nextInt(10 - 0 + 1) + 0;
    	//String num2 = Integer.toString(num);
    	ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    	Collections.shuffle(Arrays.asList(result));
        for(int i=0; i<result.length; ++i)
        {
    		msg.addReceiver(result[i].getName());
        }
        //SET FIRST CANAL
        Random rnd = new Random();
    	int PosCanal = rnd.nextInt((4 - 1) + 1) + 1;
    	gs1.firstWater=PosCanal;
        msg.setConversationId("FIRST-TO-PLAY");
        msg.setContent("E o jogador " + result[0].getName() + "a jogar");
        //System.out.println("content: " + msg.getContent());
        send(msg);
    }
	
	public void waitForAllAsksWantedPos(ACLMessage msg) {
		//Ainda precisa de pedir BRIBE aos outros agentes
		//System.out.println("MANAGER: RECEBE UM WANTEDPOS");
		try {
			gs1 = (GameState) msg.getContentObject();
			int encontrou=0;
			for(int i=pos; i<result.length; ++i)
			{
				//System.out.println("Procurando jogadores que ainda nao disseram wantedPos...");
				if(gs1.getJogador(result[i].getName().getLocalName()).getWantedPos()==-1 && !gs1.getJogador(result[i].getName().getLocalName()).equals(gs1.canalOverseerName))
				{
					//System.out.println("Jogador - " + result[i].getName().getLocalName() + " -> Nao fez wantedpos");
					try {
						ACLMessage msgWANTED = new ACLMessage(ACLMessage.QUERY_IF);
						msgWANTED.addReceiver(result[i].getName());
						msgWANTED.setConversationId("WANTEDPOS");
						msgWANTED.setContentObject(gs1);
						send(msgWANTED);
						encontrou=1;
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			//Podem existir jogadores nas posicoes antes (no vector) do que esta a esquerda do CanalOverSeer
			if(encontrou==0) 
			{
				for(int x=0;x<pos-1;x++)
				{
					//System.out.println("Procurando jogadores que ainda nao disseram wantedPos...");
					if(gs1.getJogador(result[x].getName().getLocalName()).getWantedPos()==-1 && !gs1.getJogador(result[x].getName().getLocalName()).equals(gs1.canalOverseerName))
					{
						//System.out.println("Jogador - " + result[x].getName().getLocalName() + " -> Nao fez wantedpos");
						try {
							ACLMessage msgWANTED = new ACLMessage(ACLMessage.QUERY_IF);
							msgWANTED.addReceiver(result[x].getName());
			    			msgWANTED.setConversationId("WANTEDPOS");
							msgWANTED.setContentObject(gs1);
							send(msgWANTED);
							break;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
		
	}

	public void askWantedPos(ACLMessage msg) {
		try {
			for(int x=0;x<gs1.jogadores.size();x++)
			{
				//System.out.println("JOGADOR " + gs1.jogadores.get(x).getNome() + "    -    PLANTOU EM : " + gs1.jogadores.get(x).getPlantouPos());
			}
			//System.out.println("FINISHED PLANTS PHASE");
			//Comeco Suborno do CanalOverSeer
			pos=0;
			for(int x=0;x<result.length;x++)
			{
				if(result[x].getName().getLocalName().equals(gs1.canalOverseerName))
				{
					if(x==result.length-1)
					{
						pos = 0;
					}
					else
						pos = x+1;
				}
			}
			
			//Comeca a perguntar no jogador a esquerda do canalOverSeer
			ACLMessage msgWANTED = new ACLMessage(ACLMessage.QUERY_IF);
			msgWANTED.addReceiver(result[pos].getName());
			msgWANTED.setConversationId("WANTEDPOS");
			msgWANTED.setContentObject(gs1);
			send(msgWANTED);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void GUIredraw()
	{
		//mainFrame.mainpanel.setup(gs1);
	}

	private void setPlantacoes() {
		//Gerar Plantacoes
		for(int i=0;i<4;i++)
		{
			Random rnd = new Random();
			int num = rnd.nextInt(4 - 1 + 1) + 1;
			Tile t1 = new Tile();
			if(num==1)
				t1.setTipo(1);
			else if(num==2)
				t1.setTipo(2);
			else if(num==3)
				t1.setTipo(3);
			else
				t1.setTipo(4);
			if(i==2)
				t1.setNumWorkers(1);
			else if(i==3)
				t1.setNumWorkers(0);
			else
				t1.setNumWorkers(2);
			gs1.tiles.add(t1);
		}
	}

	private void startBribe(ACLMessage msg) {
		for(int x=0;x<gs1.jogadores.size();x++)
		{
			//System.out.println("JOGADOR " + gs1.jogadores.get(x).getNome() + "    -    QUER CANAL EM : " + gs1.jogadores.get(x).getWantedPos());
		}
		try {
			//System.out.println("FINISHED PLANTS PHASE");
			//Comeco Suborno do CanalOverSeer
			pos=0;
			for(int x=0;x<result.length;x++)
			{
				if(result[x].getName().getLocalName().equals(gs1.canalOverseerName))
				{
					if(x==result.length-1)
					{
						pos = 0;
					}
					else
						pos = x+1;
				}
			}
			
			//Comeca bribe no jogador a esquerda do canalOverSeer
			ACLMessage msgBRIBE = new ACLMessage(ACLMessage.QUERY_IF);
			msgBRIBE.addReceiver(result[pos].getName());
			msgBRIBE.setConversationId("BRIBE");
			msgBRIBE.setContentObject(gs1);
			send(msgBRIBE);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void waitForAllBids(ACLMessage msg) {
		//Ainda precisa de pedir BID aos outros agentes
		//System.out.println("MANAGER: RECEBE UMA FINISHED BID");
		try {
			gs1 = (GameState) msg.getContentObject();
			for(int i=0; i<result.length; ++i)
			{
				//System.out.println("Procurando jogadores que ainda nao licitaram...");
				if(gs1.getJogador(result[i].getName().getLocalName()).getLicitacao()==-1)
				{
					//System.out.println("Jogador - " + result[i].getName().getLocalName() + " -> Nao licitou");
					try {
						ACLMessage msgBID = new ACLMessage(ACLMessage.QUERY_IF);
						msgBID.addReceiver(result[i].getName());
						msgBID.setConversationId("BID");
						msgBID.setContentObject(gs1);
						send(msgBID);
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}

	private void askForFirstPlant(ACLMessage msg) {
		//System.out.println("FINISHED BID's PHASE");
		try {
			gs1 = (GameState) msg.getContentObject();
			int min=999;
			for(int x=0;x<gs1.jogadores.size();x++)
			{
				if(gs1.jogadores.get(x).getLicitacao()==0)
				{
					//Primeiro com 0 (a passar) logo CanalOverSeer
					min=0;
					gs1.canalOverseerName = gs1.jogadores.get(x).getNome();
				}
			}
			Collections.sort(gs1.jogadores);
			for(int x=0;x<gs1.jogadores.size();x++)
			{
				//System.out.println("JOGADOR " + gs1.jogadores.get(x).getNome() + "    -    Dinheiro : " + gs1.jogadores.get(x).getDinheiro());
				//System.out.println("JOGADOR " + gs1.jogadores.get(x).getNome() + "    -    Licitacao : " + gs1.jogadores.get(x).getLicitacao());
			}
			//System.out.println("JOGADORES ORDENADOS POR BID");
			for(int x=0;x<gs1.jogadores.size();x++)
			{
				//System.out.println("JOGADOR " + gs1.jogadores.get(x).getNome() + "    -    Dinheiro : " + gs1.jogadores.get(x).getDinheiro());
			}
			if(min==999)
			{
				gs1.canalOverseerName = gs1.jogadores.get(0).getNome();
			}
			//Pedir Plantacao a FIRST TO PLANT
			for(int x=0;x<result.length;x++)
			{
				if(result[x].getName().getLocalName().equals(gs1.jogadores.get(gs1.jogadores.size()-1).getNome()))
				{
					try {
						
						//Primeiro a plantar
						ACLMessage msgBID = new ACLMessage(ACLMessage.QUERY_IF);
						msgBID.addReceiver(result[x].getName());
						msgBID.setConversationId("PLANT");
						msgBID.setContentObject(gs1);
						send(msgBID);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			
			
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}
	
	private void askCanalOverSeerAboutBribe(ACLMessage msg) {
		for(int x=0;x<gs1.jogadores.size();x++)
		{
			//System.out.println("JOGADOR " + gs1.jogadores.get(x).getNome() + "    -    BRIBE DE : " + gs1.jogadores.get(x).getBribe());
		}
		try {
			if(sleeps)Thread.sleep(300);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//Verificar se CanalOverSeer quer pagar mais que o max BRIBE ou tem que aceitá-lo.
		try {
			gs1 = (GameState) msg.getContentObject();
			//System.out.println("FINISHED BRIBES PHASE");
			for(int x=0;x<gs1.jogadores.size();x++)
			{
				//System.out.println("JOGADOR " + gs1.jogadores.get(x).getNome() + "FEZ BRIBE DE : " + gs1.jogadores.get(x).getBribe());
			}
			//System.in.read();
			
			ACLMessage msgBRIBE = new ACLMessage(ACLMessage.QUERY_IF);
			//NEEDS TESTS
			if(pos>0)
				msgBRIBE.addReceiver(result[pos-1].getName());
			else
				msgBRIBE.addReceiver(result[result.length-1].getName());
			msgBRIBE.setConversationId("MAXBRIBE");
			msgBRIBE.setContentObject(gs1);
			send(msgBRIBE);
			
		} catch (UnreadableException | IOException e) {
			e.printStackTrace();
		}
	}

	private void waitForAllPlants(ACLMessage msg) {
		//Collections.sort(gs1.jogadores);
			for(int i=0; i<result.length; ++i)
			{
				//System.out.println("Procurando jogadores que ainda nao plantaram...");
					if(gs1.jogadores.get(gs1.jogadores.size()-(1+plantn)).getPlantou()==0 && result[i].getName().getLocalName().equals(gs1.jogadores.get(gs1.jogadores.size()-(1+plantn)).getNome()))
					{
						//System.out.println("Jogador - " + result[i].getName().getLocalName() + " -> Nao Plantou");
						try {
							ACLMessage msgPLANT = new ACLMessage(ACLMessage.QUERY_IF);
							msgPLANT.addReceiver(result[i].getName());
			    			msgPLANT.setConversationId("PLANT");
							msgPLANT.setContentObject(gs1);
							send(msgPLANT);
							break;
						} catch (IOException e) {
							e.printStackTrace();
						}
						break;
					}
			}
	}
	
	private void waitForAllBribes(ACLMessage msg) {
		//Ainda precisa de pedir BRIBE aos outros agentes
		//System.out.println("MANAGER: RECEBE UM BRIBE");
		try {
			gs1 = (GameState) msg.getContentObject();
			int encontrou=0;
			for(int i=pos; i<result.length; ++i)
			{
				//System.out.println("Procurando jogadores que ainda nao foram manhosos...");
				if(gs1.getJogador(result[i].getName().getLocalName()).getBribe()==-1 && !gs1.getJogador(result[i].getName().getLocalName()).equals(gs1.canalOverseerName))
				{
					//System.out.println("Jogador - " + result[i].getName().getLocalName() + " -> Nao foi Manhoso");
					try {
						ACLMessage msgBRIBE = new ACLMessage(ACLMessage.QUERY_IF);
						msgBRIBE.addReceiver(result[i].getName());
						msgBRIBE.setConversationId("BRIBE");
						msgBRIBE.setContentObject(gs1);
						send(msgBRIBE);
						encontrou=1;
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
			//Podem existir jogadores nas posicoes antes (no vector) do que esta a esquerda do CanalOverSeer
			if(encontrou==0) 
			{
				for(int x=0;x<pos-1;x++)
				{
					//System.out.println("Procurando jogadores que ainda nao foram manhosos...");
					if(gs1.getJogador(result[x].getName().getLocalName()).getBribe()==-1 && !gs1.getJogador(result[x].getName().getLocalName()).equals(gs1.canalOverseerName))
					{
						//System.out.println("Jogador - " + result[x].getName().getLocalName() + " -> Nao foi Manhoso");
						try {
							ACLMessage msgBRIBE = new ACLMessage(ACLMessage.QUERY_IF);
							msgBRIBE.addReceiver(result[x].getName());
			    			msgBRIBE.setConversationId("BRIBE");
							msgBRIBE.setContentObject(gs1);
							send(msgBRIBE);
							break;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (UnreadableException e) {
			e.printStackTrace();
		}
	}

	private void askForSetChannel(ACLMessage msg) {
		for(int x=0;x<gs1.jogadores.size();x++)
		{
			//System.out.println("ASKSETCHANNEL JOGADOR " + gs1.jogadores.get(x).getNome() + "    -    BRIBE DE : " + gs1.jogadores.get(x).getBribe());
		}
		try {
			gs1 = (GameState) msg.getContentObject();
			for(int x=0;x<gs1.jogadores.size();x++)
			{
				//if(gs1.jogadores.get(x).getNome().equals(gs1.canalOverseerName))
					//System.out.println("CANAL OVERSEER JOGADOR : " + gs1.jogadores.get(x).getNome() + " EFECTUA BRIBE DE : " + gs1.jogadores.get(x).getBribe());
				//else
					//System.out.println("JOGADOR : " + gs1.jogadores.get(x).getNome() + " EFECTUA BRIBE DE : " + gs1.jogadores.get(x).getBribe());
			}
			//System.out.println("MAIOR BRIBE DE: " + gs1.BribeWinner.getNome() + " COM VALOR DE : " + gs1.BribeWinner.getBribe());
			Jogador j1 = gs1.BribeWinner;
			j1.setDinheiro(j1.getDinheiro()-j1.getBribe());
			gs1.updateJogador(j1);
			
			Jogador j2 = gs1.getJogador(gs1.canalOverseerName);
			
			if(j1!=j2)
			{
				for(int x=0;x<gs1.jogadores.size();x++)
				{
					if(gs1.jogadores.get(x)!=j1)
					{
						if(gs1.jogadores.get(x).getWantedPos()==j1.getWantedPos())
						{
							gs1.jogadores.get(x).setDinheiro(gs1.jogadores.get(x).getDinheiro()-gs1.jogadores.get(x).getBribe());
							j2.setDinheiro(j2.getDinheiro()+gs1.jogadores.get(x).getBribe());
						}
					}
				}
				//Nao foi o CanalOverSeer que ganhou bribe logo adiciona-se dinheiro a conta dele
				j2.setDinheiro(j2.getDinheiro()+gs1.BribeWinner.getBribe());
				gs1.updateJogador(j2);
			}
			//Decidir onde fica o Canal de Água
			
			for(int x=0;x<result.length;x++)
			{
				if(result[x].getName().getLocalName().equals(gs1.BribeWinner.getNome()))
				{
					ACLMessage msgSETCHANNEL = new ACLMessage(ACLMessage.QUERY_IF);
					msgSETCHANNEL.addReceiver(result[x].getName());
					msgSETCHANNEL.setConversationId("SETCHANNEL");
					msgSETCHANNEL.setContentObject(gs1);
					send(msgSETCHANNEL);
					break;
				}
			}
			
			
		} catch (UnreadableException | IOException e) {
			e.printStackTrace();
		}
	}

	private void askForExtraChannel(ACLMessage msg) {
		try {
			gs1 = (GameState) msg.getContentObject();
			//System.out.println("FINISHED SETCHANNEL PHASE");
			//GUIredraw();
			//System.in.read();
			//Perguntar Canal Extra
			
			ACLMessage msgASKCHANNEL = new ACLMessage(ACLMessage.QUERY_IF);
			msgASKCHANNEL.addReceiver(result[0].getName());
			msgASKCHANNEL.setConversationId("ASKCHANNEL");
			msgASKCHANNEL.setContentObject(gs1);
			send(msgASKCHANNEL);
			//System.out.println("ENVIOU ASKCHANNEL - CANAL EXTRA");
			asked.add(result[0].getName().getLocalName());
			
		} catch (UnreadableException | IOException e) {
			e.printStackTrace();
		}
	}

	private void startNewRound() {
		nrounds++;
		//Ninguem usou canal extra e ja se perguntou a todos -> Dar dinheiro e passar à próxima ronda
		gs1.setDinheiro();
		bidn=0;
		plantn=0;
		briben=0;
		pos=0;
		askchan=0;
		askwantedn=0;
		extraActivated=0;
		plantsecondtime=0;
		asked.clear();
		gs1.resetJogadores();
		gs1.resetTiles();
		gs1.resetHouveActivacaoCanalExtra();
		gs1.resetCanalOverSeerName();
		gs1.BribeWinner = new Jogador("");
		
		
		//System.out.println("NUMERO DE RONDAS -> " + nrounds);
		try {
			if(sleeps)Thread.sleep(0);
			gs1.updateWorkers();
			
			ACLMessage msgROUND = new ACLMessage(ACLMessage.INFORM);
			Collections.shuffle(Arrays.asList(result));
		    for(int i=0; i<result.length; ++i)
		    {
				msgROUND.addReceiver(result[i].getName());
		    }
		    Collections.shuffle(gs1.jogadores);
		    msgROUND.setConversationId("FIRST-TO-PLAY");
		    msgROUND.setContent("E o jogador " + result[0].getName() + "a jogar");
		    //System.out.println("content: " + msgROUND.getContent());
		    send(msgROUND);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void waitForAsksExtraChannel() {
		try {
			//Falta perguntar a agentes se querem usar canal extra
			
			//System.out.println("SIZE RESULTS : " + result.length);
			for(int x=0;x<result.length;x++)
			{
				//System.out.println("a procurar players para perguntar...");
				if(asked.contains(result[x].getName().getLocalName())==false)
				{
					//System.out.println("encontrou jogador : " + result[x].getName().getLocalName());
					asked.add(result[x].getName().getLocalName());
					ACLMessage msgASKCHANNEL = new ACLMessage(ACLMessage.QUERY_IF);
					msgASKCHANNEL.addReceiver(result[x].getName());
					msgASKCHANNEL.setConversationId("ASKCHANNEL");
					msgASKCHANNEL.setContentObject(gs1);
					send(msgASKCHANNEL);
					break;
				}	
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void gameOver() throws IOException
	{
		Date date = new Date() ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
		//File file = new File("santiago-stats" + dateFormat.format(date) + ".txt") ;
		File file = new File("santiago-stats.txt") ;
		PrintWriter out = new PrintWriter(new FileOutputStream( file,true /* append = true */)); 
		System.out.println("Game over");
		
		ArrayList<Jogador> stats = gs1.jogadores ;
		Collections.sort(stats,new Comparator<Jogador>(){
            public int compare(Jogador s1,Jogador s2){
                  return s1.getNome().compareTo(s2.getNome());
            }});

		for (Jogador x : stats)
		{
			out.print("agente,");
			out.print(x.getNome());
			out.print(",pontos,");
			out.println(gs1.getValorTotalJogador(x.getNome()));
		}
		out.println();
		out.flush();
		out.close();
	}
	
}
