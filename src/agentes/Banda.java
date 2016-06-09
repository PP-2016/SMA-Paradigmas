package agentes;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
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

		addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {
				ACLMessage msgJurado = receive(MessageTemplate.MatchPerformative(ACLMessage.QUERY_REF));
				if(msgJurado != null){
					addBehaviour(new ComportamentoBanda(myAgent,msgJurado));
				}				
			}
		});
		sendMessage();
	}
	
	private void sendMessage(){
		
	}
	protected void takeDown(){
		System.out.println("Banda "+getName()+" saiu do palco!");
	}
	
}