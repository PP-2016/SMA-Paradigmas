package agentes;
import comportamentos.ComportamentoJurado;

import sun.java2d.pipe.SpanShapeRenderer.Simple;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.MessageTemplate;


public class Jurado extends Agent{
	
	private static final long serialVersionUID = 2L;
	private AID[] jurado;
	
	  protected void setup(){
	    //linha de apresentação
	    System.out.println(this.getLocalName() + " diz: Sejam bem vindos e é Hora do Show!!");
	    
	    DFAgentDescription df_jurado= new DFAgentDescription();
	    df_jurado.setName(getAID());
	    
	    ServiceDescription jurado_service = new ServiceDescription();
	    
	    jurado_service.setType("Ato de Jugar");
	    jurado_service.setName("Agente Jurado julgar banda");
	    
	    df_jurado.addServices(jurado_service);
	    
	    try {
			DFService.register(this, df_jurado);
		} catch (FIPAException e) {
			e.printStackTrace();
		}   
	    
	    
	    addBehaviour(new ComportamentoJulgar());
	  }
	  
	  
	  private class ComportamentoJulgar extends Behaviour{
		  
		MessageTemplate message_t = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
		ACLMessage message_inform = myAgent.receive(message_t);		

		private static final long serialVersionUID = 2L;
		
		
		@Override
		public void action() {
		
			if(message_t != null){
				String string_value = message_inform.getContent();
				Integer value = new Integer(string_value);
				ACLMessage response = message_inform.createReply();

				if(value != null){
					response.setPerformative(ACLMessage.INFORM);
					System.out.println("Banda: "+ message_inform.getSender().getLocalName()+"Valor: "+ value);
				}
				
			}
			
		}

		@Override
		public boolean done() {
			return false;
		}
		  
	  }

}
