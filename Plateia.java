package agentes;
import jade.core.Agent;


import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class Plateia  extends Agent{
	/**
	 * 
	 */
	private static final long serialversionUID=1L;
	private String name= "Arquibancada";
	private AID id = new AID(name, AID.ISLOCALNAME);


	@Override
	 protected void setup(){// parte  ja testada e funcionando 
		System.out.println("Plateia"+getAID().getLocalName() + "  estamos aqui torcendo");	// parte ja testada e funcionando 
		addBehaviour(new ComportamentoPlateia());// parte ja testada e funcionando. 
	}

	 protected void takedown(){
	    	 System.out.println("foi bom estar com voces");
	    }
	
		
	private   class  ComportamentoPlateia extends Behaviour{
      

		/**
		 * 
		 */
		
		/**
		 * 
		 */
	     	
	
		ACLMessage resposta;
		
       private    MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);// ja foi aprovado   no telegram. 
          
          @Override
 	      public void action(){
 			   resposta = myAgent.receive(mt);
 		    if (resposta != null) {
 		      System.out.println(resposta.getContent());
 		      ACLMessage msg= new ACLMessage(ACLMessage.REQUEST);
 		      msg.addReceiver(resposta.getSender());
 		      msg.setContent(myAgent.getLocalName()+ " ih");
 		      myAgent.send(msg);
 		    }else{
 		      ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
			  msg.addReceiver(resposta.getSender());// esta parte esta sem sentido e so para testar , depois implementarei o cfp  ou modelarei com o inform.
			  msg.setContent(myAgent.getLocalName() + " uhu");
			  myAgent.send(msg);
 	        }
 	     }

 	      
 	   
 	    @Override  
 	     public boolean done() {
 	    	return false;
 		}
 	     
		
 	      
 	    
 		
 		
		
 	     /**private void enviaMSg(ACLMessage resposta, String message){
			 ACLMessage msg= new ACLMessage(ACLMessage.REQUEST);
			 msg.addReceiver(resposta.getSender());// esta parte esta sem sentido e so para testar , depois implementarei o cfp  ou modelarei com o inform.
			 msg.setContent(myAgent.getLocalName()+message);
			 myAgent.send(msg);
		 }*/
	
	
	
	

		
        
	}
}

