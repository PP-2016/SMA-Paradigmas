package agentes;
import javax.xml.ws.Response;

import comportamentos.ComportamentoJurado;
import sun.java2d.pipe.SpanShapeRenderer.Simple;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.MessageTemplate;


public class Plateia extends Agent{
	
	private static final long serialVersionUID = 2L;
	private AID[] plateia;
	
	private int erro_plateia = 0;
	
	
	  protected void setup(){
	    //linha de apresentação
	    System.out.println(this.getLocalName() + " reage com: 'Palmas'!!");
	    
	    DFAgentDescription df_plateia= new DFAgentDescription();
	    df_plateia.setName(getAID());
	    
	    ServiceDescription sd = new ServiceDescription();
	    
	    sd.setType("Ato de acompanhar o show");
	    sd.setName("Plateia do programa The Voice");
	    
	    df_plateia.addServices(sd);
	    
	    try {
			DFService.register(this, df_plateia);
		} catch (FIPAException e) {
			e.printStackTrace();
		}   
	    
	    
	    addBehaviour(new ComportamentoPlateia());
	  }
	  
	  private class ComportamentoPlateia extends Behaviour{
		  
		private static final long serialVersionUID = 3L;
		
		@Override
		public void action() {
			MessageTemplate message_t = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage message_inform = myAgent.receive(message_t);	
			
			if(message_inform != null){
				
				String string_value = message_inform.getContent();
				Integer value = new Integer(string_value);
				
				ACLMessage response = message_inform.createReply();

				if(value != null){
				
					if(value == 1 ){
						erro_plateia++;
						
						response.setPerformative(ACLMessage.AGREE);
						response.setContent("uhhh");
						
						
						System.out.println("****************Plateia grita: Uhhh!!!!!\n\n");
											
					}else{
						
						System.out.println("****************Plateia grita: Uhuu!!!!!\n\n");

						response.setPerformative(ACLMessage.AGREE);
						response.setContent("uhuu");
					}							
				}
				myAgent.send(response);
				
			}
			
		}

		@Override
		public boolean done() {
			// TODO Auto-generated method stub
			return false;
		}
		  
		  
	  }
}