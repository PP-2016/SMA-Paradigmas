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
	private AID[] jurado;
	
	//agente initializer
	@Override
	protected void setup(){

		//agente se apresenta
		System.out.println("Banda "+getAID().getLocalName() + " entrou no palco.");
	    System.out.println(this.getLocalName() + " diz: Boa noite galeraaaa!!!");
	    //definição do comportamento que a agente Maria irá executar

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
							jurado[i] = result[i].getName();
							System.out.println(jurado[i].getName());
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
		
		private static final long serialVersionUID = 1L;
		private MessageTemplate message_template;
		
		@Override
		public void action() {
			ACLMessage message_to_jugdes = new ACLMessage(ACLMessage.INFORM);
		
			for (int i = 0; i < jurado.length; i++) {
				message_to_jugdes.addReceiver(jurado[i]);
			} 
			
			// MENSAGEM DE TESTE
			message_to_jugdes.setContent("10");
			message_to_jugdes.setConversationId("Band_Performance_value");
			myAgent.send(message_to_jugdes);
			
			message_template = MessageTemplate.and(MessageTemplate.MatchConversationId("Band_performance_value"),
					MessageTemplate.MatchInReplyTo(message_to_jugdes.getReplyWith()));
			
		}

		@Override
		public boolean done() {
			return false;
		}

		
		
	}


}