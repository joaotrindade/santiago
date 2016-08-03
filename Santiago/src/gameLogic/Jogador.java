package gameLogic;

import java.io.Serializable;
 
@SuppressWarnings("serial")
public class Jogador implements Serializable,Comparable<Jogador> {
        private String nome;
        private boolean canalExtra;
        private int dinheiro;
        private int licitacao;
        private int plantou;
        private int bribe;
        private int player_image_id;
        public static int last_player_image_id = 0 ;
        
        private int wantedPos;
        private int plantouPos;
        
        public Jogador(String n_nome)
        {
                nome = n_nome;
                licitacao = -1;
                dinheiro = 10;
                canalExtra = true;
                plantou = 0;
                bribe = -1;
                wantedPos = -1;
                plantouPos = 0;
                setPlayer_image_id(++last_player_image_id);
        }
       
        public String getNome() {
                return nome;
        }
 
        public void setNome(String nome) {
                this.nome = nome;
        }
 
        public boolean hasCanalExtra() {
                return canalExtra;
        }
 
        public void setCanalExtra(boolean canalExtra) {
                this.canalExtra = canalExtra;
        }
 
        public int getDinheiro() {
                return dinheiro;
        }
 
        public void setDinheiro(int dinheiro) {
                this.dinheiro = dinheiro;
        }
 
        public int getLicitacao() {
                return licitacao;
        }
 
        public void setLicitacao(int licitacao) {
                this.licitacao = licitacao;
        }
        
        public int getPlantou() {
            return plantou;
	    }
	
	    public void setPlantou(int plant) {
	            this.plantou = plant;
	    }
	    
	    public int getBribe() {
            return bribe;
	    }
	
	    public void setBribe(int brb) {
	            this.bribe = brb;
	    }
	    
	    public int getWantedPos() {
            return wantedPos;
	    }
	
	    public void setWantedPos(int pos) {
	            this.wantedPos = pos;
	    }
	    
	    public int getPlantouPos() {
            return plantouPos;
	    }
	
	    public void setPlantouPos(int plantP) {
	            this.plantouPos = plantP;
	    }
        
        @Override
        public int compareTo(Jogador input) {
                return (this.getLicitacao()-input.getLicitacao());
        }

		public int getPlayer_image_id() {
			return player_image_id;
		}

		public void setPlayer_image_id(int player_image_id) {
			this.player_image_id = player_image_id;
		}
       
}