package agentes;
import javax.xml.ws.Response;

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
	
	
	int erros_banda =0;// numero de erros que a banda cometeu.
	int performance =0;// numero de mensagens recebidas sem Erro. 
	
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
			
			if(message_inform != null){
				
				if(message_inform.getConversationId() == "Band_Performance_end"){
				
					disparaAvaliacao(erros_banda);
				
				}else{
				
					String string_value = message_inform.getContent();
					Integer value = new Integer(string_value);
					
					ACLMessage response = message_inform.createReply();
	
					if(value != null){
					
						if(value == 1 ){
							erros_banda++;
							
							response.setPerformative(ACLMessage.INFORM);
							response.setContent("ERRO");
							
							
							System.out.println("****************erros da banda:"+erros_banda+"\n\n");
							
							System.out.println("*****Banda cometeu um erro, contagem: "+erros_banda+"*******");
						
						}else{
							performance++;
							
							System.out.println("****************Performance:"+performance+"\n\n");
	
							response.setPerformative(ACLMessage.INFORM);
							response.setContent("OK");
						}							
					}
					myAgent.send(response);
				}
				
			}else{
				block();
			}
		}

		private void disparaAvaliacao(int erros_banda) {
			if(erros_banda <=3){
				System.out.println("Meus parabens, voces foram aprovados!!");
				block();
			}else{
				System.out.println("Cantaram muito bem, mas erros pontuais nao deixaram voce passar hoje...");
				block();
			}
			
		}

		@Override
		public boolean done() {
			return false;
		}
		  
	  }

}
