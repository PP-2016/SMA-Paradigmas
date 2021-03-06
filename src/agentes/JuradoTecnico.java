package agentes;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.lang.acl.ACLMessage;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.MessageTemplate;


public class JuradoTecnico extends Agent{
	
	private static final long serialVersionUID = 2L;
	private AID[] jurado;
	
	
	
	private int erros_banda = 0;// numero de erros que a banda cometeu.
	private int performance = 0;// numero de mensagens recebidas sem Erro. 
	private int erro_plateia = 0;
	private int palmas_plateia = 0;
	private static final float PORCENTAGEM_MAXIMA = 10f;
	
	
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
	    
	    
	    addBehaviour(new ComportamentoJuradoTecnico());
	  }
	  
	  
	  
	  private class ComportamentoJuradoTecnico extends Behaviour{
		  
			

		private static final long serialVersionUID = 2L;
		
		
		@Override
		public void action() {
			MessageTemplate message_t = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
			ACLMessage message_inform = myAgent.receive(message_t);	
			
			if(message_inform != null){
				
				if(message_inform.getConversationId() == "Band_Performance_end"){
				
					disparaAvaliacao();
				
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
							performance++;
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

		private void disparaAvaliacao() {
			
			float erros = erros_banda;
			float ciclo = performance;
			
			System.out.println("****Decisão do jurado técnico: ****");
			
			float porcentagem_erros = (erros/ciclo) * 100f;
			
			if(porcentagem_erros > PORCENTAGEM_MAXIMA){
				//implementar prints aqui para mostrar a performance da banda aqui. 
				//erros; 
				System.out.println("Cantaram muito bem, mas erros pontuais nao deixaram voce passar hoje...");
				System.out.println("Porcentagem de erros: "+porcentagem_erros+"%");
			}else{
				System.out.println("Meus parabens, voces foram aprovados!!");
				System.out.println("Porcentagem de erros: "+porcentagem_erros+"%");
			}
			
			block();
		}

		@Override
		public boolean done() {
			if(performance == 20 || erros_banda == 10){
			
				disparaAvaliacao();
				
				return true;				
			}
			else
				return false;
		}
		  
	  }

}
