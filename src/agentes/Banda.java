package agentes;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.Random;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;


public class Banda extends Agent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//lista de variaveis do agente Banda. 
	private String name = "Pink Floyd";
	AID id = new AID(name, AID.ISLOCALNAME);
	ACLMessage msg;
	private AID[] jurado;
	private AID plateia;
	int contador = 0;
	public static final int CONDICAO = 20;
	
	//agente initializer
	@Override
	protected void setup(){

		//agente se apresenta
		System.out.println("Banda "+getAID().getLocalName() + " entrou no palco.");
	    System.out.println(this.getLocalName() + " diz: Boa noite galeraaaa!!!");
	    //definição do comportamento que a agente Maria irá executar

		addBehaviour(new TickerBehaviour(this, 500) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5652388196400737248L;

			@Override
			protected void onTick() {
					
				if(contador < CONDICAO){
					
					System.out.println("****************Cantando..");
					DFAgentDescription template = new DFAgentDescription();
					DFAgentDescription template2 = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					ServiceDescription sd2 = new ServiceDescription();
					
					sd.setType("Ato de Jugar");
					sd2.setType("Ato de acompanhar o show");
					template.addServices(sd);
					template2.addServices(sd2);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template);
						DFAgentDescription[] result2 = DFService.search(myAgent, template2);
						jurado = new AID[result.length];
						plateia = result2[0].getName();
						for (int i = 0; i < result.length; ++i) {
							jurado[i] = result[i].getName();
//							System.out.println(jurado[i].getName());
						}
					} catch (FIPAException e) {
						System.out.println("*****************Erro");
						e.printStackTrace();
					}
					
					//verificar o contador, se for para continuar acontece o Performance.
					
						System.out.println("****************Contador de interações: "+contador);
						
						int momento = 0;//variavel que controla o switch
						addBehaviour(new Performance(momento));
						
				}else if(contador>= CONDICAO){
						System.out.println("****************: " + getAID().getLocalName() + " diz: Obrigado pela oportunidade!!!");
						int momento =1;
						addBehaviour(new Performance(momento));
						stop();
				}else{
					block();
				}
					
					
				
			}
		});
	}
	
	protected void takeDown(){
		System.out.println("Banda "+this.getLocalName()+" saiu do palco!");
	}
	
	//inner class
	
	int range = 7; //variavel controladora da chance de erro
	private class Performance extends Behaviour{
		
		private static final long serialVersionUID = 1L;
		private MessageTemplate message_template;
		
		private int momento;
		
		public Performance(int momento) {
			this.momento = momento;
		}

		@Override
		public void action() {
			
			switch(momento){
				case 0:
					ACLMessage message_to_jugdes = new ACLMessage(ACLMessage.INFORM);
					ACLMessage message_to_plateia = new ACLMessage(ACLMessage.INFORM);
					for (int i = 0; i < jurado.length; i++) {
						message_to_jugdes.addReceiver(jurado[i]);
					} 
					message_to_plateia.addReceiver(plateia);
					
					// MENSAGEM DE TESTE
					Integer value = ErroRandomicoBanda(range);
				
					System.out.println("****************Valor random: "+value);
					
					message_to_jugdes.setContent(value.toString());
					message_to_jugdes.setConversationId("Band_Performance_value");
					message_to_plateia.setContent(value.toString());
					message_to_plateia.setConversationId("Band_Performance_value");
					
					myAgent.send(message_to_jugdes);
					myAgent.send(message_to_plateia);
					
					message_template = MessageTemplate.and(MessageTemplate.MatchConversationId("Band_performance_value"),
							MessageTemplate.MatchInReplyTo(message_to_jugdes.getReplyWith()));
					
					momento = 1; //ir para "receber" msg
					contador++;
					break;
				
				case 1:
					
					if(contador == CONDICAO){
					
						ACLMessage final_message_to_jugdes = new ACLMessage(ACLMessage.INFORM);
						for (int i = 0; i < jurado.length; i++) {
							final_message_to_jugdes.addReceiver(jurado[i]);
						} 
						
						final_message_to_jugdes.setContent("Fim da apresentacao.");
						final_message_to_jugdes.setConversationId("Band_Performance_end");
						myAgent.send(final_message_to_jugdes);
					
					}else{
					
						ACLMessage reply = myAgent.receive(message_template);
	
						if(reply != null){
							
							if(reply.getPerformative() == ACLMessage.INFORM){
								//recebe a "cara" do jurado. Se a cara for negativa, aumenta a chance de erro.
								
								
								if(reply.getContent().equalsIgnoreCase("erro")){
			
									System.out.println("****************Conteúdo da msg do Juiz: "+reply.getContent());//teste
									range--;
								
								}else{
									
								}
								//momento = 0; //voltar para o passo onde envia a msg de tocar;
							}
							
						}else{
							block();
						}
						block();
					}
					break;
				
		}
		
	 }

		@Override
		public boolean done() {
			return true;
		}

		public int ErroRandomicoBanda(int range){
			
			Random whell= new Random();
			int value = whell.nextInt(range);
				
			return value;
			
		}
		
	}


}