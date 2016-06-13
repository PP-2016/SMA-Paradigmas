package agentes;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

import java.util.Random;

import comportamentos.ComportamentoBanda;
import jade.core.AID;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.ACLMessage;
import comportamentos.ComportamentoBanda;

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
	int contador = 0;
	public static final int CONDICAO = 4;
	
	//agente initializer
	@Override
	protected void setup(){

		//agente se apresenta
		System.out.println("Banda "+getAID().getLocalName() + " entrou no palco.");
	    System.out.println(this.getLocalName() + " diz: Boa noite galeraaaa!!!");
	    //definição do comportamento que a agente Maria irá executar

		addBehaviour(new TickerBehaviour(this, 1000) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5652388196400737248L;

			@Override
			protected void onTick() {
					
				if(contador != CONDICAO){
					
					System.out.println("Cantando..");
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					
					sd.setType("Ato de Jugar");
					template.addServices(sd);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template);
						jurado = new AID[result.length];
						for (int i = 0; i < result.length; ++i) {
							jurado[i] = result[i].getName();
							System.out.println(jurado[i].getName());
						}
					} catch (FIPAException e) {
						System.out.println("*****************Erro");
						e.printStackTrace();
					}
					
					//verificar o contador, se for para continuar acontece o Performance.
					
					
						System.out.println(contador+"**************************");
						addBehaviour(new Performance());
						
					}
					else{
						block();
					}
					
					
				
			}
		});
	}
	
	public boolean done(){
		
		return false;
	}
	
	protected void takeDown(){
		System.out.println("Banda "+this.getLocalName()+" saiu do palco!");
	}
	
	
	

	
	
	
	//inner class
	int momento = 0;//variavel que controla o switch
	int range = 100; //variavel controladora da chance de erro
	private class Performance extends Behaviour{
		
		private static final long serialVersionUID = 1L;
		private MessageTemplate message_template;
		
		@Override
		public void action() {
			
			switch(momento){
				case 0:
					ACLMessage message_to_jugdes = new ACLMessage(ACLMessage.INFORM);
					for (int i = 0; i < jurado.length; i++) {
						message_to_jugdes.addReceiver(jurado[i]);
					} 
					
					// MENSAGEM DE TESTE
					Integer value = ErroRandomicoBanda(range);
				
					System.out.println("***"+value+"***\n\n\n");
					message_to_jugdes.setContent(value.toString());
					message_to_jugdes.setConversationId("Band_Performance_value");
					myAgent.send(message_to_jugdes);
					
					message_template = MessageTemplate.and(MessageTemplate.MatchConversationId("Band_performance_value"),
							MessageTemplate.MatchInReplyTo(message_to_jugdes.getReplyWith()));
					
					momento = 1; //ir para "receber" msg
					contador++;
					break;
				
				case 1:
					ACLMessage reply = myAgent.receive(message_template);

					if(reply != null){
						
						if(reply.getPerformative() == ACLMessage.INFORM){
							//recebe a "cara" do jurado. Se a cara for negativa, aumenta a chance de erro.
							
							
							if(reply.getContent().equalsIgnoreCase("erro")){
		
								System.out.println(reply.getContent());//teste
								range--;
							
							}else{
								
							}
							momento = 0; //voltar para o passo onde envia a msg de tocar;
						}
						
					}else{
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