import jade.core.Agent;
import comportamentos.ComportamentoBanda;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;


public class Banda extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//lista de variaveis do agente Banda. 
	private String name = "Pink Floyd";
	AID id = new AID(name, AID.ISLOCALNAME);
	ACLMessage msg;

	//agente initializer
	@Override
	protected void setup(){
		System.out.println("Banda "+getAID().getLocalName()+" no palco!");
		addBehaviour(new ComportamentoBanda(this));
	}
	
	protected void takeDown(){
		System.out.println("Banda "+getName()+" saiu do palco!");
	}
	

}
