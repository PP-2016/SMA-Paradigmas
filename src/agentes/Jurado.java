package agentes;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.MessageTemplate;


public class Jurado extends Agent{
	
	@Override
	protected void setup(){
		
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Judge");
		sd.setName("JADE-Judge");
		dfd.addServices(sd);
		try {
			DFService.register(this, dfd);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}	
	}
	
	private class ComportamentoJuiz extends Behaviour {
		
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
		ACLMessage msg = myAgent.receive(mt);	
		
		public ComportamentoJuiz(Agent banda){
			super(banda);
		}
		
		public void action() {
	
			if (msg != null) {
				// ACCEPT_PROPOSAL Message received. Process it
				String performance = msg.getContent();
				ACLMessage reply = msg.createReply();

				if (performance != null) {
					if(performance.equalsIgnoreCase("positivo")){
						reply.setPerformative(ACLMessage.INFORM);
						System.out.println("O show foi um Sucesso! Meu voto é SIM!");
					}else if(performance.equalsIgnoreCase("negativo")){
						reply.setPerformative(ACLMessage.INFORM);
						System.out.println("O show foi um Sucesso! Mas hoje é não!");
					}
				}
				else {
					// The requested book has been sold to another buyer in the meanwhile .
					reply.setPerformative(ACLMessage.FAILURE);
					reply.setContent("not-available");
				}
				myAgent.send(reply);
			}
			else {
				block();
			}
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}
	}  // End of inner class OfferRequestsServer

}
