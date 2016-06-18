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


public class JuradoEmotivo extends Agent{
	
	private static final long serialVersionUID = 2L;
	private AID[] jurado;
	
	private int performance = 0;// numero de mensagens recebidas sem Erro. 
	private float erro_plateia = 0f;
	private float palmas_plateia = 0f;
	
	
	  protected void setup(){
	    //linha de apresentação
	    System.out.println(this.getLocalName() + " diz: Sejam bem vindos!! Me impressionem!!");
	    
	    DFAgentDescription df_jurado= new DFAgentDescription();
	    df_jurado.setName(getAID());
	    
	    ServiceDescription sd = new ServiceDescription();
	    
	    sd.setType("Ato de acompanhar o show");
	    sd.setName("Agente Jurado julgar emocao");
	    
	    df_jurado.addServices(sd);
	    
	    try {
			DFService.register(this, df_jurado);
		} catch (FIPAException e) {
			e.printStackTrace();
		}   
	    
	    addBehaviour(new ComportamentoJuradoEmotivo());
	  }
	  
	  private class ComportamentoJuradoEmotivo extends Behaviour{

		/**
		 * 
		 */
		private static final long serialVersionUID = 3L;

		@Override
		public void action() {
			
			
			MessageTemplate message_t = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
			// setar uma performativa diferente, pois o INFORM  ja est� sendo utilizado para comunica�oes com a Banda.
			// Este jurado Recebe mensagens da plateia e decide como agir a partir daqui.
			
			ACLMessage message_inform = myAgent.receive(message_t);
						
			if(message_inform != null){
				
				String string_value = message_inform.getContent();

				if(string_value != null){
					
					if(string_value.equalsIgnoreCase("uhhh")){
						erro_plateia = (float) (erro_plateia + 4);
						performance++;
					}else if(string_value.equalsIgnoreCase("uhuu")){
						palmas_plateia++;
						performance++;
					}
				
				}else{
					block();
				}
			}
			
		}

		@Override
		public boolean done() {
			if(performance == 20){
				disparaAvaliacao();
				return true;
			}
				
			else
				return false;
		}

		private void disparaAvaliacao() {
			System.out.println("****Decisão do jurado emotivo: ****");
			
			if(erro_plateia > palmas_plateia){
				System.out.println("Cantaram muito bem, mas faltou emoção na sua musica para conseguir passar hoje...");
			}else if(erro_plateia == palmas_plateia){
				System.out.println("Sua performance foi morna, mas para te dar uma chance, hoje voto sim!");
			}else{
				System.out.println("Excelente!! Vocês tocaram meu coração!");
			}
			System.out.println("Quantidade de vaias: "+ erro_plateia);
			System.out.println("Quantidade de apoios: "+palmas_plateia);
		}
	  
	  }
	  
	  
	  
	  
	  

}
