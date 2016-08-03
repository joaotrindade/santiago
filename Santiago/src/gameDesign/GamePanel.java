package gameDesign;


import gameLogic.GameState;
import gameLogic.Tabuleiro;
import gameLogic.Jogador;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	int CanalX;
	int CanalY;
	int NumPlayers = 0;
	int tempExtra = 0;
	String printTop ="";
	public Tabuleiro tabuleiro1;
	public GameState gamestate1 = new GameState();
	public ArrayList<Jogador> jogadoresOrdenados = new ArrayList<Jogador>();
	
	private String jogador1,jogador2,jogador3,jogador4,jogador5,jogador6;
	/**
	 * Create the panel.
	 */
	public GamePanel() {
		
		setLayout(null);
		jogador1=""; jogador2=""; jogador3="";
		jogador4=""; jogador5=""; jogador6="";
		
		NumPlayers = 0;
	}
	
	public void setup(GameState _game)
	{
		tabuleiro1 = _game.tabuleiro;
		gamestate1 = _game;
		
		jogadoresOrdenados = new ArrayList<Jogador>();
		
		for(int i=0;i<gamestate1.jogadores.size();i++)
		{
			for(int j=0;j<gamestate1.jogadores.size();j++)
			{
				if(i==0 && gamestate1.jogadores.get(j).getNome().equals(jogador1))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==1 && gamestate1.jogadores.get(j).getNome().equals(jogador2))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==2 && gamestate1.jogadores.get(j).getNome().equals(jogador3))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==3 && gamestate1.jogadores.get(j).getNome().equals(jogador4))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==4 && gamestate1.jogadores.get(j).getNome().equals(jogador5))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==5 && gamestate1.jogadores.get(j).getNome().equals(jogador6))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
			}
		}
		
		NumPlayers = jogadoresOrdenados.size();
	
		this.repaint();
	}
	
	public void print_Winner(GameState _game,String _str)
	{
		printTop = _str;
		
		jogadoresOrdenados = new ArrayList<Jogador>();

		for(int i=0;i<gamestate1.jogadores.size();i++)
		{
			for(int j=0;j<gamestate1.jogadores.size();j++)
			{
				if(i==0 && gamestate1.jogadores.get(j).getNome().equals(jogador1))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==1 && gamestate1.jogadores.get(j).getNome().equals(jogador2))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==2 && gamestate1.jogadores.get(j).getNome().equals(jogador3))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==3 && gamestate1.jogadores.get(j).getNome().equals(jogador4))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==4 && gamestate1.jogadores.get(j).getNome().equals(jogador5))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
				else if(i==5 && gamestate1.jogadores.get(j).getNome().equals(jogador6))
				{
					jogadoresOrdenados.add(gamestate1.jogadores.get(j));
				}
			}
		}
		
		NumPlayers = jogadoresOrdenados.size();
		
		this.repaint();
	}
	
	public void setPlayerAlias(ArrayList<Jogador> jogadores)
	{
		jogador1=""; jogador2=""; jogador3="";
		jogador4=""; jogador5=""; jogador6="";
		
		for(int i=0;i<jogadores.size();i++)
		{
			if(i==0)
				jogador1 = jogadores.get(i).getNome();
			else if(i==1)
				jogador2 = jogadores.get(i).getNome();
			else if(i==2)
				jogador3 = jogadores.get(i).getNome();
			else if(i==3)
				jogador4 = jogadores.get(i).getNome();
			else if(i==4)
				jogador5 = jogadores.get(i).getNome();
			else if(i==5)
				jogador6 = jogadores.get(i).getNome();
		}
		
		for(int i=0;i<jogadores.size();i++)
		{
			for(int j=0;j<jogadores.size();j++)
			{
				if(i==0 && jogadores.get(j).getNome().equals(jogador1))
				{
					jogadoresOrdenados.add(jogadores.get(j));
				}
				else if(i==1 && jogadores.get(j).getNome().equals(jogador2))
				{
					jogadoresOrdenados.add(jogadores.get(j));
				}
				else if(i==2 && jogadores.get(j).getNome().equals(jogador3))
				{
					jogadoresOrdenados.add(jogadores.get(j));
				}
				else if(i==3 && jogadores.get(j).getNome().equals(jogador4))
				{
					jogadoresOrdenados.add(jogadores.get(j));
				}
				else if(i==4 && jogadores.get(j).getNome().equals(jogador5))
				{
					jogadoresOrdenados.add(jogadores.get(j));
				}
				else if(i==5 && jogadores.get(j).getNome().equals(jogador6))
				{
					jogadoresOrdenados.add(jogadores.get(j));
				}
			}
		}
		
		NumPlayers = jogadoresOrdenados.size();
		this.repaint();
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		//PAINT HUD PLAYERS
		int playerhudy = 10;
		int playerhudx = 865;
		for(int i=0;i<NumPlayers;i++)
		{
			if(i==0 && !jogador1.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador1.png")).getImage(), 845,playerhudy,null);
				g.drawString("- " + jogador1, 865, playerhudy +20);
				playerhudy += 25;
			}
			else if(i==1 && !jogador2.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador2.png")).getImage(), 845,playerhudy,null);
				g.drawString("- " + jogador2, 865, playerhudy +20);
				playerhudy += 25;
			}
			else if(i==2 && !jogador3.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador3.png")).getImage(), 845,playerhudy,null);
				g.drawString("- " + jogador3, 865, playerhudy +20);
				playerhudy += 25;
			}
			else if(i==3 && !jogador4.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador4.png")).getImage(), 845,playerhudy,null);
				g.drawString("- " + jogador4, 865, playerhudy +20);
				playerhudy += 25;
			}
			else if(i==4 && !jogador5.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador5.png")).getImage(), 845,playerhudy,null);
				g.drawString("- " + jogador5, 865, playerhudy+20);
				playerhudy += 25;
			}
			else if(i==5 && !jogador6.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador6.png")).getImage(), 845,playerhudy,null);
				g.drawString("- " + jogador6, 865, playerhudy+20);
				playerhudy += 25;
			}
		}
		
		if(!gamestate1.canalOverseerName.equals(""))
		{
			g.drawString("Channel OverSeer - " + gamestate1.canalOverseerName, 845, playerhudy + 30);
			playerhudy += 45;
		}
		else
		{
			g.drawString("Channel OverSeer - No one.", 845, playerhudy + 30);
			playerhudy += 45;
		}
		
		playerhudy += 20;
		g.drawString("Dinheiro:", playerhudx-20, playerhudy - 5);
		
		String moneytemp = "";
		playerhudx = 820;
		for(int i=0;i<NumPlayers;i++)
		{
			if(i==0 && !jogador1.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador1.png")).getImage(), playerhudx + 25,playerhudy,null);
				moneytemp = new Integer(jogadoresOrdenados.get(i).getDinheiro()).toString();
				if(jogadoresOrdenados.get(i).getDinheiro()<10)
					moneytemp=" "+moneytemp;
				g.drawString(moneytemp, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==1 && !jogador2.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador2.png")).getImage(), playerhudx + 25,playerhudy,null);
				moneytemp = new Integer(jogadoresOrdenados.get(i).getDinheiro()).toString();
				if(jogadoresOrdenados.get(i).getDinheiro()<10)
					moneytemp=" "+moneytemp;
				g.drawString(moneytemp, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==2 && !jogador3.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador3.png")).getImage(), playerhudx + 25,playerhudy,null);
				moneytemp = new Integer(jogadoresOrdenados.get(i).getDinheiro()).toString();
				if(jogadoresOrdenados.get(i).getDinheiro()<10)
					moneytemp=" "+moneytemp;
				g.drawString(moneytemp, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==3 && !jogador4.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador4.png")).getImage(), playerhudx + 25,playerhudy,null);
				moneytemp = new Integer(jogadoresOrdenados.get(i).getDinheiro()).toString();
				if(jogadoresOrdenados.get(i).getDinheiro()<10)
					moneytemp=" "+moneytemp;
				g.drawString(moneytemp, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==4 && !jogador5.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador5.png")).getImage(), playerhudx + 25,playerhudy,null);
				moneytemp = new Integer(jogadoresOrdenados.get(i).getDinheiro()).toString();
				if(jogadoresOrdenados.get(i).getDinheiro()<10)
					moneytemp=" "+moneytemp;
				g.drawString(moneytemp, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==5 && !jogador6.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador6.png")).getImage(), playerhudx + 25,playerhudy,null);
				moneytemp = new Integer(jogadoresOrdenados.get(i).getDinheiro()).toString();
				if(jogadoresOrdenados.get(i).getDinheiro()<10)
					moneytemp=" "+moneytemp;
				g.drawString(moneytemp, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
		}
		
		playerhudy += 70;
		playerhudx = 865;
		
		g.drawString("Bids:", playerhudx-20, playerhudy - 5);
		
		String bids = "";
		playerhudx = 820;
		for(int i=0;i<NumPlayers;i++)
		{
			if(i==0 && !jogador1.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador1.png")).getImage(), playerhudx + 25,playerhudy,null);
				bids = new Integer(jogadoresOrdenados.get(i).getLicitacao()).toString();
				if(jogadoresOrdenados.get(i).getLicitacao()<10)
					bids=" "+bids;
				g.drawString(bids, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==1 && !jogador2.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador2.png")).getImage(), playerhudx + 25,playerhudy,null);
				bids = new Integer(jogadoresOrdenados.get(i).getLicitacao()).toString();
				if(jogadoresOrdenados.get(i).getLicitacao()<10)
					bids=" "+bids;
				g.drawString(bids, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==2 && !jogador3.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador3.png")).getImage(), playerhudx + 25,playerhudy,null);
				bids = new Integer(jogadoresOrdenados.get(i).getLicitacao()).toString();
				if(jogadoresOrdenados.get(i).getLicitacao()<10)
					bids=" "+bids;
				g.drawString(bids, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==3 && !jogador4.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador4.png")).getImage(), playerhudx + 25,playerhudy,null);
				bids = new Integer(jogadoresOrdenados.get(i).getLicitacao()).toString();
				if(jogadoresOrdenados.get(i).getLicitacao()<10)
					bids=" "+bids;
				g.drawString(bids, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==4 && !jogador5.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador5.png")).getImage(), playerhudx + 25,playerhudy,null);
				bids = new Integer(jogadoresOrdenados.get(i).getLicitacao()).toString();
				if(jogadoresOrdenados.get(i).getLicitacao()<10)
					bids=" "+bids;
				g.drawString(bids, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==5 && !jogador6.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador6.png")).getImage(), playerhudx + 25,playerhudy,null);
				bids = new Integer(jogadoresOrdenados.get(i).getLicitacao()).toString();
				if(jogadoresOrdenados.get(i).getLicitacao()<10)
					bids=" "+bids;
				g.drawString(bids, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
		}
		
		playerhudy += 70;
		playerhudx = 865;
		
		g.drawString("Bribes:", playerhudx-20, playerhudy - 5);
		
		String bribes = "";
		playerhudx = 820;
		for(int i=0;i<NumPlayers;i++)
		{
			if(i==0 && !jogador1.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador1.png")).getImage(), playerhudx + 25,playerhudy,null);
				bribes = new Integer(jogadoresOrdenados.get(i).getBribe()).toString();
				if(jogadoresOrdenados.get(i).getBribe()<10)
					bribes=" "+bribes;
				g.drawString(bribes, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==1 && !jogador2.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador2.png")).getImage(), playerhudx + 25,playerhudy,null);
				bribes = new Integer(jogadoresOrdenados.get(i).getBribe()).toString();
				if(jogadoresOrdenados.get(i).getBribe()<10)
					bribes=" "+bribes;
				g.drawString(bribes, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==2 && !jogador3.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador3.png")).getImage(), playerhudx + 25,playerhudy,null);
				bribes = new Integer(jogadoresOrdenados.get(i).getBribe()).toString();
				if(jogadoresOrdenados.get(i).getBribe()<10)
					bribes=" "+bribes;
				g.drawString(bribes, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==3 && !jogador4.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador4.png")).getImage(), playerhudx + 25,playerhudy,null);
				bribes = new Integer(jogadoresOrdenados.get(i).getBribe()).toString();
				if(jogadoresOrdenados.get(i).getBribe()<10)
					bribes=" "+bribes;
				g.drawString(bribes, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==4 && !jogador5.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador5.png")).getImage(), playerhudx + 25,playerhudy,null);
				bribes = new Integer(jogadoresOrdenados.get(i).getBribe()).toString();
				if(jogadoresOrdenados.get(i).getBribe()<10)
					bribes=" "+bribes;
				g.drawString(bribes, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==5 && !jogador6.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador6.png")).getImage(), playerhudx + 25,playerhudy,null);
				bribes = new Integer(jogadoresOrdenados.get(i).getBribe()).toString();
				if(jogadoresOrdenados.get(i).getBribe()<10)
					bribes=" "+bribes;
				g.drawString(bribes, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
		}
		
		playerhudy += 70;
		playerhudx = 865;
		
		g.drawString("Extra Channel:", playerhudx-20, playerhudy - 5);
		
		
		String extra = "";
		playerhudx = 820;
		for(int i=0;i<NumPlayers;i++)
		{
			if(i==0 && !jogador1.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador1.png")).getImage(), playerhudx + 25,playerhudy,null);
				if(jogadoresOrdenados.get(i).hasCanalExtra())
					extra=" Y";
				else
					extra=" N";
				g.drawString(extra, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==1 && !jogador2.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador2.png")).getImage(), playerhudx + 25,playerhudy,null);
				if(jogadoresOrdenados.get(i).hasCanalExtra())
					extra=" Y";
				else
					extra=" N";
				g.drawString(extra, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==2 && !jogador3.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador3.png")).getImage(), playerhudx + 25,playerhudy,null);
				if(jogadoresOrdenados.get(i).hasCanalExtra())
					extra=" Y";
				else
					extra=" N";
				g.drawString(extra, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==3 && !jogador4.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador4.png")).getImage(), playerhudx + 25,playerhudy,null);
				if(jogadoresOrdenados.get(i).hasCanalExtra())
					extra=" Y";
				else
					extra=" N";
				g.drawString(extra, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==4 && !jogador5.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador5.png")).getImage(), playerhudx + 25,playerhudy,null);
				if(jogadoresOrdenados.get(i).hasCanalExtra())
					extra=" Y";
				else
					extra=" N";
				g.drawString(extra, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			else if(i==5 && !jogador6.equals(""))
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador6.png")).getImage(), playerhudx + 25,playerhudy,null);
				if(jogadoresOrdenados.get(i).hasCanalExtra())
					extra=" Y";
				else
					extra=" N";
				g.drawString(extra, playerhudx + 27, playerhudy +40);
				playerhudx += 25;
			}
			extra = "";
		}
		
		//PRINT WINNER
		
		if(!printTop.equals(""))
		{
			g.drawString("Winner - " + printTop, 840, 600);
			playerhudy += 70;
			playerhudx = 865;
			
			g.drawString("Pontos Finais:", playerhudx-20, playerhudy - 5);
			
			String pontos = "";
			int pontinhos = 0;
			playerhudx = 820;
			for(int i=0;i<NumPlayers;i++)
			{
				if(i==0 && !jogador1.equals(""))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador1.png")).getImage(), playerhudx + 25,playerhudy,null);
					pontinhos = gamestate1.getValorTotalJogador(jogadoresOrdenados.get(i).getNome());
					pontos = new Integer(pontinhos).toString();
					if(pontinhos < 10)
						pontos=" "+pontos;
					g.drawString(pontos, playerhudx + 27, playerhudy +40);
					playerhudx += 25;
				}
				else if(i==1 && !jogador2.equals(""))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador2.png")).getImage(), playerhudx + 25,playerhudy,null);
					pontinhos = gamestate1.getValorTotalJogador(jogadoresOrdenados.get(i).getNome());
					pontos = new Integer(pontinhos).toString();
					if(pontinhos < 10)
						pontos=" "+pontos;
					g.drawString(pontos, playerhudx + 27, playerhudy +40);
					playerhudx += 25;
				}
				else if(i==2 && !jogador3.equals(""))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador3.png")).getImage(), playerhudx + 25,playerhudy,null);
					pontinhos = gamestate1.getValorTotalJogador(jogadoresOrdenados.get(i).getNome());
					pontos = new Integer(pontinhos).toString();
					if(pontinhos < 10)
						pontos=" "+pontos;
					g.drawString(pontos, playerhudx + 27, playerhudy +40);
					playerhudx += 25;
				}
				else if(i==3 && !jogador4.equals(""))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador4.png")).getImage(), playerhudx + 25,playerhudy,null);
					pontinhos = gamestate1.getValorTotalJogador(jogadoresOrdenados.get(i).getNome());
					pontos = new Integer(pontinhos).toString();
					if(pontinhos < 10)
						pontos=" "+pontos;
					g.drawString(pontos, playerhudx + 27, playerhudy +40);
					playerhudx += 25;
				}
				else if(i==4 && !jogador5.equals(""))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador5.png")).getImage(), playerhudx + 25,playerhudy,null);
					pontinhos = gamestate1.getValorTotalJogador(jogadoresOrdenados.get(i).getNome());
					pontos = new Integer(pontinhos).toString();
					if(pontinhos < 10)
						pontos=" "+pontos;
					g.drawString(pontos, playerhudx + 27, playerhudy +40);
					playerhudx += 25;
				}
				else if(i==5 && !jogador6.equals(""))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador6.png")).getImage(), playerhudx + 25,playerhudy,null);
					pontinhos = gamestate1.getValorTotalJogador(jogadoresOrdenados.get(i).getNome());
					pontos = new Integer(pontinhos).toString();
					if(pontinhos < 10)
						pontos=" "+pontos;
					g.drawString(pontos, playerhudx + 27, playerhudy +40);
					playerhudx += 25;
				}
			}
		}
		
		
		
		//DESENHO CANAIS HORIZONTAIS
		CanalX=64;
		CanalY=0;
		for(int i=1; i<17; i++)
		{
			if((i-1)%4 == 0 && i>1)
			{
				CanalY+=192;
				CanalX=64;
			 }
			if(tabuleiro1.listaCanais.get(i).hasAgua())
			{
				g.setColor(Color.BLUE);
				//g.fillRect(CanalX, CanalY, 32, 16);
				g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), CanalX,CanalY,null);
				g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), CanalX+64,CanalY,null);
			}
			else
			{
				g.setColor(Color.CYAN);
				//g.fillRect(CanalX, CanalY, 32, 16);
				g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), CanalX,CanalY,null);
				g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), CanalX+64,CanalY,null);
			}
			
			CanalX+=192;
		}
		
		//DESENHO CANAIS VERTICAIS
		CanalX=0;
		CanalY=64;
		int Counter=0;
		for(int i=17; i<32; i++)
		{
			Counter++;
			if(Counter == 6)
			{
				Counter=1;
				CanalY+=192;
				CanalX=0;
			}
			
			if(tabuleiro1.listaCanais.get(i).hasAgua())
			{
				g.setColor(Color.BLUE);
				//g.fillRect(CanalX, CanalY, 16, 32);
				g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), CanalX,CanalY,null);
				g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), CanalX,CanalY+64,null);
			}
			else
			{
				g.setColor(Color.CYAN);
				//g.fillRect(CanalX, CanalY, 16, 32);
				g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), CanalX,CanalY,null);
				g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), CanalX,CanalY+64,null);
			}
			
			CanalX+=192;
		}
		
		int ajudax;
		for(int i=1; i<5; i++)
		{
			ajudax=0;
			for(int j=1; j<6; j++)
			{
				if(j==1)
				{
					if(i==1)
					{
						if(tabuleiro1.listaCanais.get(1).hasAgua() || tabuleiro1.listaCanais.get(17).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,0,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,0,null);
					}
					else if(i==2)
					{
						if(tabuleiro1.listaCanais.get(j+16).hasAgua() || tabuleiro1.listaCanais.get(j+21).hasAgua() || tabuleiro1.listaCanais.get(j+4).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,192,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,192,null);
					}
					else if(i==3)
					{
						if(tabuleiro1.listaCanais.get(j+21).hasAgua() || tabuleiro1.listaCanais.get(j+26).hasAgua() || tabuleiro1.listaCanais.get(j+8).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,384,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,384,null);
					}
					else if(i==4)
					{
						if(tabuleiro1.listaCanais.get(13).hasAgua() || tabuleiro1.listaCanais.get(27).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,576,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,576,null);
					}
					
				}
				else if(j==5)
				{
					if(i==1)
					{
						if(tabuleiro1.listaCanais.get(4).hasAgua() || tabuleiro1.listaCanais.get(21).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,0,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,0,null);
					}
					else if(i==2)
					{
						if(tabuleiro1.listaCanais.get(j+3).hasAgua() || tabuleiro1.listaCanais.get(j+16).hasAgua() || tabuleiro1.listaCanais.get(j+21).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,192,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,192,null);
					}
					else if(i==3)
					{
						if(tabuleiro1.listaCanais.get(j+7).hasAgua() || tabuleiro1.listaCanais.get(j+21).hasAgua() || tabuleiro1.listaCanais.get(j+26).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,384,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,384,null);
					}
					else if(i==4)
					{
						if(tabuleiro1.listaCanais.get(16).hasAgua() || tabuleiro1.listaCanais.get(31).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,576,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,576,null);
					}
				}
				else
				{
				
					if(i==1)
					{
						if(tabuleiro1.listaCanais.get(j-1).hasAgua() || tabuleiro1.listaCanais.get(j-1+1).hasAgua() || tabuleiro1.listaCanais.get(j-1+17).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,0,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,0,null);
					}
					else if(i==2)
					{
						if(tabuleiro1.listaCanais.get(j-1+4).hasAgua() || tabuleiro1.listaCanais.get(j-1+5).hasAgua() || tabuleiro1.listaCanais.get(j-1+17).hasAgua() || tabuleiro1.listaCanais.get(j-1+22).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,192,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,192,null);
					}
					else if(i==3)
					{
						if(tabuleiro1.listaCanais.get(j-1+8).hasAgua() || tabuleiro1.listaCanais.get(j-1+9).hasAgua() || tabuleiro1.listaCanais.get(j-1+22).hasAgua() || tabuleiro1.listaCanais.get(j-1+27).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,384,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,384,null);
					}
					else if(i==4)
					{
						if(tabuleiro1.listaCanais.get(j-1+12).hasAgua() || tabuleiro1.listaCanais.get(j-1+13).hasAgua() || tabuleiro1.listaCanais.get(j-1+27).hasAgua())
							g.drawImage(new ImageIcon(getClass().getResource("/resources/water.png")).getImage(), ajudax,576,null);
						else
							g.drawImage(new ImageIcon(getClass().getResource("/resources/possible_water.png")).getImage(), ajudax,576,null);
					}
				}
				
				ajudax+=192;
			}
		}
		
		//DESENHO Terrenos HORIZONTAIS
		CanalX=64;
		CanalY=64;
		int CounterX=0;
		int CounterY=0;
		int square=0;
		for(int i=1; i<49; i++)
		{
			CounterX++;
			CounterY++;
			
			if(CounterX == 3)
			{
				CounterX=1;
				CanalX+=64;
			}
			
			if(CounterY == 9)
			{
				square++;
				if(square==2)
				{
					CanalY+=128;
					square=0;
				}
				else
					CanalY+=64;
					
				CanalX=64;
				CounterY=1;
			}
			
			if(tabuleiro1.listaTerrenos.get(i).getTipoPlantacao() == 0)
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/vazio.png")).getImage(), CanalX,CanalY,null);
			}
			else if(tabuleiro1.listaTerrenos.get(i).getTipoPlantacao() == 1)
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/pattern1.png")).getImage(), CanalX,CanalY,null);
			}
			else if(tabuleiro1.listaTerrenos.get(i).getTipoPlantacao() == 2)
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/pattern2.png")).getImage(), CanalX,CanalY,null);
			}
			else if(tabuleiro1.listaTerrenos.get(i).getTipoPlantacao() == 3)
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/pattern3.png")).getImage(), CanalX,CanalY,null);
			}
			else if(tabuleiro1.listaTerrenos.get(i).getTipoPlantacao() == 4)
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/pattern4.png")).getImage(), CanalX,CanalY,null);
			}
			else if(tabuleiro1.listaTerrenos.get(i).getTipoPlantacao() == -1)
			{
				g.drawImage(new ImageIcon(getClass().getResource("/resources/patternM1.png")).getImage(), CanalX,CanalY,null);
			}
			
			if(tabuleiro1.listaTerrenos.get(i).getNumMarkers()>0)
			{
				if(tabuleiro1.listaTerrenos.get(i).getJogadorProprietario().equals(jogador1))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador1.png")).getImage(), CanalX+32,CanalY,null);
					if(tabuleiro1.listaTerrenos.get(i).getNumMarkers() == 2)
						g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador1.png")).getImage(), CanalX+48,CanalY,null);
				}
				else if(tabuleiro1.listaTerrenos.get(i).getJogadorProprietario().equals(jogador2))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador2.png")).getImage(), CanalX+32,CanalY,null);
					if(tabuleiro1.listaTerrenos.get(i).getNumMarkers() == 2)
						g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador2.png")).getImage(), CanalX+48,CanalY,null);
				}
				else if(tabuleiro1.listaTerrenos.get(i).getJogadorProprietario().equals(jogador3))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador3.png")).getImage(), CanalX+32,CanalY,null);
					if(tabuleiro1.listaTerrenos.get(i).getNumMarkers() == 2)
						g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador3.png")).getImage(), CanalX+48,CanalY,null);
				}
				else if(tabuleiro1.listaTerrenos.get(i).getJogadorProprietario().equals(jogador4))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador4.png")).getImage(), CanalX+32,CanalY,null);
					if(tabuleiro1.listaTerrenos.get(i).getNumMarkers() == 2)
						g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador4.png")).getImage(), CanalX+48,CanalY,null);
				}
				else if(tabuleiro1.listaTerrenos.get(i).getJogadorProprietario().equals(jogador5))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador5.png")).getImage(), CanalX+32,CanalY,null);
					if(tabuleiro1.listaTerrenos.get(i).getNumMarkers() == 2)
						g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador5.png")).getImage(), CanalX+48,CanalY,null);
				}
				else if(tabuleiro1.listaTerrenos.get(i).getJogadorProprietario().equals(jogador6))
				{
					g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador6.png")).getImage(), CanalX+32,CanalY,null);
					if(tabuleiro1.listaTerrenos.get(i).getNumMarkers() == 2)
						g.drawImage(new ImageIcon(getClass().getResource("/resources/jogador6.png")).getImage(), CanalX+48,CanalY,null);
				}
				
			}
			
			CanalX+=64;
		}
		
		//
		
		
	}
	

}