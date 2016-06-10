package comportamentos;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ComportamentoJurado extends SimpleBehaviour{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
	ACLMessage msgJurado;
	Integer countA = new Integer(0);
	
	public ComportamentoJurado(Agent jurado){
		super(jurado);
	}
	
	@Override
	public void action() {
	  if (countA == 0){ //para a primeira mensagem da conversa entre os agentes
	    enviaPrimeiraMsg();
	  } else {
	    msgJurado = myAgent.receive(mt);
	    if (msgJurado!= null) {
	      System.out.println(msgJurado.getContent());
	      enviaMsg();
	    } else {
	      this.block();
	    }
	  }
	}
	
	private void enviaPrimeiraMsg(){
	    //definição do agente que irá conversar com o João
	    AID agenteRecebedor = new AID ("banda", AID.ISLOCALNAME);
	    ACLMessage priMsgJurado = new ACLMessage(ACLMessage.REQUEST);
	    priMsgJurado.addReceiver(agenteRecebedor);
	    priMsgJurado.setContent(myAgent.getLocalName() + " diz: E o que vocês vão cantar hoje?");
	    myAgent.send(priMsgJurado);
	  }
	
	private void enviaMsg(){
	  ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
	  msg.addReceiver(msgJurado.getSender());
	  msg.setContent(myAgent.getLocalName() +" diz: Mandaram muito bem! Mas hoje é não... :(");
	  myAgent.send(msg);
	}
	
	@Override
	public boolean done() {
	  if (countA == 1)
	    myAgent.doDelete();
	  else
	    countA++;
   
	  return false;
   }

	
//	public float contaErros(int erros, int total){
//		float erros_percent = (erros/total)*10; 
//		return erros_percent;
//	}
//	
//	private void enviaMsg(){
//		ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
//	    msg.addReceiver(msgAgente.getSender());
//	    msg.setContent(myAgent.getLocalName() + " diz: Bem e você?");
//	    myAgent.send(msg);
//	}

	
}  // End of inner class OfferRequestsServer
