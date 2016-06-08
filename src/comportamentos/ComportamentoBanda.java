package comportamentos;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.introspection.AddedBehaviour;

import java.util.Random;

import jade.core.AID;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;


public class ComportamentoBanda extends SequentialBehaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	ACLMessage resposta, msgJuiz;
	
	
	Integer countB = new Integer(0);
	
	Random rand = new Random();
	

	public ComportamentoBanda(Agent agente, ACLMessage msgJuiz) {
		super(agente);
		this.msgJuiz = msgJuiz;
	}
	
	@Override
	public void onStart() {
		if(msgJuiz != null){
			int erro = rand.nextInt(10);
			
			if(erro == 1){
				resposta = 	msgJuiz.createReply();
				enviaMensagem("~Erro da banda~");
			}
		}
	}
	
	private void enviaMensagem(String mensagem){
		ACLMessage mensage_received = new ACLMessage(ACLMessage.REQUEST);
		//mensage_received.addReceiver(msgAgente.getSender());
		mensage_received.setContent(mensagem);
		myAgent.send(mensage_received);
	}
}
