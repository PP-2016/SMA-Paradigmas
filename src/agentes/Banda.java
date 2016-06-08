package agentes;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import comportamentos.ComportamentoBanda;
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
	
	//agente initializer
	@Override
	protected void setup(){
		System.out.println("Banda "+getAID().getLocalName()+" no palco!");
		addBehaviour(new CyclicBehaviour(this) {
			
			@Override
			public void action() {
				ACLMessage msgJuiz = receive(MessageTemplate.MatchPerformative(ACLMessage.QUERY_REF));
				if(msgJuiz != null)
					addBehaviour(new ComportamentoBanda(myAgent,msgJuiz));
				block();
//				if(countB == 0){
//					/*Criar classes que conversem com Banda para que 
//					 * "EnviarMensagem" passe a funcinonar
//					 */
//					//enviaMensagem("Boa noite, galera! Pink floyd na �rea");
//					System.out.println("Boa noite, galera! PInk floyd na �rea!!");
//					
//					
//					
//				 System.out.println(countB);
//				}else if(msgAgente != null) {
//					enviaMensagem("Boa noite!!");
//					System.out.println(msgAgente.getContent());
//				}else{
//					this.block();
//				}
				
			}
		});
	}
	
	protected void takeDown(){
		System.out.println("Banda "+getName()+" saiu do palco!");
	}
	

}