package agentLogic;


import gameDesign.GameFrame;

/*import gameLogic.Canal;
import gameLogic.GameState;
import gameLogic.Jogador;
import gameLogic.Tabuleiro;
import gameLogic.Terreno;
import jade.core.Agent;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;*/

public class Main {
	public static GameFrame mainFrame;
	public static void main(String[] args)
	{
		/*
		
		int i = 0 ;
		while(i < 50)
		{
			System.out.println(i);
			Runtime myRuntime = Runtime.instance();
			Profile myProfile = new ProfileImpl();
			myProfile.setParameter(Profile.PLATFORM_ID, "myplat");
			myProfile.setParameter(Profile.MAIN_HOST, "localhost");
			myProfile.setParameter(Profile.MAIN_PORT, "1099");
			ContainerController myContainer = myRuntime.createMainContainer(myProfile);
			Agent temp = new Manager();
			Object [] argos = new Object[2];
			argos[0] = "3";
			try {
				myContainer.createNewAgent("manager", "agentLogic.Manager", argos);
				myContainer.createNewAgent("ganancioso_1", "agentLogic.JogadorGanancioso", argos);
				myContainer.createNewAgent("aleatorio_1", "agentLogic.JogadorAleatorio", argos);
				myContainer.createNewAgent("gastador_1", "agentLogic.JogadorGastador", argos);
				myContainer.getAgent("manager").start();
				myContainer.getAgent("ganancioso_1").start();
				myContainer.getAgent("aleatorio_1").start();
				myContainer.getAgent("gastador_1").start();
			} catch (StaleProxyException e) {
				e.printStackTrace();
			} catch (ControllerException e) {
				e.printStackTrace();
			}
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("                            VAI LIMPAR");

			Canal.last_id = 0;
			Jogador.last_player_image_id = 0 ;
			Terreno.last_id = 0 ;
			Terreno.last_cluster_id = 0;
			
			i++;
		}
		
		System.out.println("ACABOU - Consulte Ficheiro para os resultados");
		
		*/
	}
}                                                                                                 