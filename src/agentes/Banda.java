package agentes;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
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
		addBehaviour(new TickerBehaviour(this, 2000) {

			@Override
			protected void onTick() {
					System.out.println("Cantando..");
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					
					sd.setType("Cantar-Musica");
					template.addServices(sd);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template);
					} catch (FIPAException e) {
						e.printStackTrace();
					}
					
					
				
			}
		});
		
	}
	
	protected void takeDown(){
		System.out.println("Banda "+getName()+" saiu do palco!");
	}
	

}