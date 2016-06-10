package agentes;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
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
import comportamentos.ComportamentoBanda;

public class Banda extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//lista de variaveis do agente Banda. 
	private String name = "Pink Floyd";
	AID id = new AID(name, AID.ISLOCALNAME);
	ACLMessage msg;
	private AID[] juizes;
	
	//agente initializer
	@Override
	protected void setup(){

		//agente se apresenta
		System.out.println("Banda "+getAID().getLocalName() + " entrou no palco.");
	    System.out.println(this.getLocalName() + " diz: Boa noite galeraaaa!!!");
	    //definição do comportamento que a agente Maria irá executar
	    addBehaviour(new ComportamentoBanda(this));

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
						for (int i = 0; i < result.length; i++) {
							juizes[i] = result[i].getName();
							System.out.println(juizes[i].getName());
						}
					} catch (FIPAException e) {
						e.printStackTrace();
					}
					
					addBehaviour(new Performance());
					
					
				
			}
		});
	}
	
	private void sendMessage(){
		
	}
	protected void takeDown(){
		System.out.println("Banda "+this.getLocalName()+" saiu do palco!");
	}
	
	private class Performance extends Behaviour{

		@Override
		public void action() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}

		
		
	}


}