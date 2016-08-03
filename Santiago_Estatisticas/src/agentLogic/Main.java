package agentLogic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import gameDesign.GameFrame;

import gameLogic.Canal;
import gameLogic.Jogador;
import gameLogic.Terreno;
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;
import jade.wrapper.StaleProxyException;

public class Main {
	public static GameFrame mainFrame;
	public static void main(String[] args)
	{
		
		
		int i = 0 ;
		Vector<String> lines = new Vector<String>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("createAgents.txt"));
			String line;
			while ((line = br.readLine()) != null) {
			   lines.add(line);
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String maxplayers = "0";
		int ntestes = 0;
		String plat = "";
		String host = "";
		String port = "";
		String manname = "";
		String[] splited = lines.get(0).split("\\s+");
		if(splited[0].equals("container"))
		{
			plat = splited[1];
			host = splited[2];
			port = splited[3];
			String splitedman[] = lines.get(1).split("\\s+");
			if(splitedman[1].equals("agentLogic.Manager"))
			{
				manname = splitedman[0];
				maxplayers = splitedman[2];
			}
		}
		
		Vector<String> jogadores = new Vector<String>(Integer.parseInt(maxplayers));
		for(int x=0;x<Integer.parseInt(maxplayers);x++)
		{
			jogadores.add(lines.get(x+2));
		}
		ntestes = Integer.parseInt(lines.get(lines.size()-1));
		
		while(i < ntestes)
		{
			System.out.println(i);
			Runtime myRuntime = Runtime.instance();
			Profile myProfile = new ProfileImpl();
			myProfile.setParameter(Profile.PLATFORM_ID, plat);
			myProfile.setParameter(Profile.MAIN_HOST, host);
			myProfile.setParameter(Profile.MAIN_PORT, port);
			ContainerController myContainer = myRuntime.createMainContainer(myProfile);
			Object [] argos = new Object[2];
			argos[0] = maxplayers;
			try {
				myContainer.createNewAgent(manname, "agentLogic.Manager", argos);
				for(int x=0;x<jogadores.size();x++)
				{
					String split[] = jogadores.get(x).split("\\s+");
					
					myContainer.createNewAgent(split[0], split[1], argos);
				}
				myContainer.getAgent(manname).start();
				for(int x=0;x<jogadores.size();x++)
				{
					String split[] = jogadores.get(x).split("\\s+");
					myContainer.getAgent(split[0]).start();
				}
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
	}
}                                                                                                 