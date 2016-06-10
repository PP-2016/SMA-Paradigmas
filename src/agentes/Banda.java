package agentes;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import comportamentos.ComportamentoBanda;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;
import comportamentos.ComportamentoBanda;

public class Banda extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void setup(){
	    //agente se apresenta
		System.out.println("Banda "+ this.getLocalName() + " entrou no palco.");
	    System.out.println(this.getLocalName() + " diz: Boa noite galeraaaa!!!");
	    //definição do comportamento que a agente Maria irá executar
	    addBehaviour(new ComportamentoBanda(this));
	  }
	
	private void sendMessage(){
		
	}
	protected void takeDown(){
		System.out.println("Banda "+this.getLocalName()+" saiu do palco!");
	}
	
}