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
	    System.out.println(this.getLocalName() + " diz: Sejam bem vindos e eh Hora do Show!!");
	    
	    DFAgentDescription df_jurado= new DFAgentDescription();
	    df_jurado.setName(getAID());
	    
	    ServiceDescription sd = new ServiceDescription();
	    
	    sd.setType("Ato de Jugar");
	    sd.setName("Agente Jurado julgar banda");
	    
	    df_jurado.addServices(sd);
	    
	    try {
			DFService.register(this, df_jurado);
		} catch (FIPAException e) {
			e.printStackTrace();
		}   
	    
	    
	    addBehaviour(new ComportamentoJulgar());
	  }
	  
	  
	  
	  private class ComportamentoJulgar extends Behaviour{
		  
			

		private static final long serialVersionUID = 2L;
		
		
		@Override
		public void action() {
			MessageTemplate message_t = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage message_inform = myAgent.receive(message_t);	
		
			if(message_t != null){
				
				String string_value = message_inform.getContent();
				System.out.println("**********");
				Integer value = new Integer(string_value);
				ACLMessage response = message_inform.createReply();

				if(value != null){
					response.setPerformative(ACLMessage.INFORM);
					System.out.println("Banda: "+ message_inform.getSender().getLocalName()+"Valor: "+ value);
				}
				
			}else{
				block();
			}
			
		}

		@Override
		public boolean done() {
			return false;
		}
		  
	  }

}
