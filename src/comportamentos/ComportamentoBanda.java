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


public class ComportamentoBanda extends SimpleBehaviour{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	ACLMessage resposta, msgJuiz;
	
	Integer countB = new Integer(0);
	
	Random rand = new Random();
	

	public ComportamentoBanda(Agent banda) {
		super(banda);
	}
	
	@Override
	public void action(){
		resposta = myAgent.receive(mt);
	    if (resposta != null) {
	      System.out.println(resposta.getContent());
	      enviaMsg();
	    } else {
	      this.block();
	    }
	}
	
	private void enviaMsg(){
	    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	    msg.addReceiver(resposta.getSender());
	    msg.setContent(myAgent.getLocalName() + " diz: Nós vamos cantar Highway to Hell!!");
	    myAgent.send(msg);
	}
	
	@Override
	public boolean done() {
	  if (countB == 3)
	    myAgent.doDelete();
	  else
	    countB++;
	
	  return false;
	}
	
	
//	@Override
//	public void onStart() {
//		enviaPrimeiraMensagem();
//		if(msgJuiz != null){
//			int erro = rand.nextInt(10);
//			
//			if(erro == 1){
//				resposta = 	msgJuiz.createReply();
//				enviaMensagem("~Erro da banda~");
//			}
//		}
//	}
//	
//	private void enviaMensagem(String mensagem){
//		ACLMessage mensage_received = new ACLMessage(ACLMessage.REQUEST);
//		//mensage_received.addReceiver(msgAgente.getSender());
//		mensage_received.setContent(mensagem);
//		myAgent.send(mensage_received);
//	}
//	
//	private void enviaPrimeiraMensagem(){
//		
//		AID recebedor = new AID("jurado", AID.ISLOCALNAME);
//		ACLMessage mensagem = new ACLMessage(ACLMessage.REQUEST);
//		
//		mensagem.addReceiver(recebedor);
//		mensagem.setContent("Hora do Show Porra!");
//		System.out.println(mensagem);
//		myAgent.send(mensagem);
//		System.out.println(mensagem);
//	}
	
}
