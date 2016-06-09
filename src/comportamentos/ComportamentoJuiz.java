package comportamentos;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ComportamentoJuiz extends SimpleBehaviour{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	ACLMessage msg;
	
	public ComportamentoJuiz(Agent juiz){
		super(juiz);
	}
	
	public void action() {

		if (msg != null) {
			// ACCEPT_PROPOSAL Message received. Process it
			ACLMessage msg = myAgent.receive(mt);
			
			System.out.println(msg.getContent());
			


			if (msg != null) {
//				if(performance.equalsIgnoreCase("positivo")){
//					reply.setPerformative(ACLMessage.INFORM);
//					System.out.println("O show foi um Sucesso! Meu voto é SIM!");
//				}else if(performance.equalsIgnoreCase("negativo")){
//					reply.setPerformative(ACLMessage.INFORM);
//					System.out.println("O show foi um Sucesso! Mas hoje é não!");
//				}
				
			}
//			else {
//				// The requested book has been sold to another buyer in the meanwhile .
//				reply.setPerformative(ACLMessage.FAILURE);
//				reply.setContent("not-available");
//			}				

		}
		else {
			block();
		}
	}
	
	
	public float contaErros(int erros, int total){
		float erros_percent = (erros/total)*10; 
		return erros_percent;
	}
	
	private void enviaMsg(){
		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	    msg.addReceiver(msgAgente.getSender());
	    msg.setContent(myAgent.getLocalName() + " diz: Bem e você?");
	    myAgent.send(msg);
	}

	
	

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}
}  // End of inner class OfferRequestsServer
