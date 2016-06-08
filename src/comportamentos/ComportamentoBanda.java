package comportamentos;

import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;


public class ComportamentoBanda extends SimpleBehaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	ACLMessage msgAgente;
	Integer countB = new Integer(0);

	public ComportamentoBanda(Agent agente) {
		super(agente);
	}
	
	@Override
	public void action() {
		msgAgente = myAgent.receive(mt);
		if(countB == 0){
			/*Criar classes que conversem com Banda para que 
			 * "EnviarMensagem" passe a funcinonar
			 */
			//enviaMensagem("Boa noite, galera! Pink floyd na área");
			System.out.println("Boa noite, galera! PInk floyd na área!!");
		 System.out.println(countB);
		}else if(msgAgente != null) {
			enviaMensagem("Boa noite!!");
			System.out.println(msgAgente.getContent());
		}else{
			this.block();
		}
	}
	
	private void enviaMensagem(String mensagem){
		ACLMessage mensage_received = new ACLMessage(ACLMessage.REQUEST);
		//mensage_received.addReceiver(msgAgente.getSender());
		mensage_received.setContent(mensagem);
		myAgent.send(mensage_received);
	}

	@Override
	public boolean done() {
		if(countB == 1)
			myAgent.doDelete();
		else
			countB++;
		
		
		return false;
	}
	
	

}
