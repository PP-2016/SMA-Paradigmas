
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



	@Override
	 protected void setup(){ 
		System.out.println("Plateia"+getAID().getLocalName() + "  estamos aqui torcendo");	 
		addBehaviour(new ComportamentoPlateia());
	}

	 protected void takedown(){
	    	 System.out.println("foi bom estar com voces");
	    }
	
		
	private   class  ComportamentoPlateia extends Behaviour{
      
		//	o comportamento da plateia precisa ser repetido toda vez que a banda 
			//mandar uma mensagem
		/**
		 * 
		 */
		
		/**
		 * 
		 */
	     	
	
	    
		
          private   MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST); 
          private ACLMessage  resposta,msg;
          @Override
 	      public void action(){
 			   resposta = myAgent.receive(mt);
 		    if (resposta != null){
 		    	    String a=resposta.getContent();
 		    		Integer meu_numero = new Integer(a);
 		            if(meu_numero==1){
 		    		     msg = new ACLMessage(ACLMessage.REQUEST);
 		    		     msg.setContent("AHHHHHHH EEEEE");
 		    		     msg.addReceiver(resposta.getSender());
 		    		     myAgent.send(msg);
 		            }    
 		    		   System.out.println("UUUUUUUUH PRECISAM TREINAR MAIS!");
 		    	    }
 		    }
          
          }
	    private void enviaPlateia(ACLMessage resposta,String b){
      	    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
    	    msg.addReceiver(resposta.getSender());
    	    msg.setContent(b);
    	    myAgent.send(msg);
    	}
          
          
 		    /** Verificará o tipo da resposta, se a resposta da banda for =1 a plateia
 		   
 		    	
 		    respoderá "AEEEEEEE! UHHUUUUUU"
 		      se nao for 1, a plateia responderá "UUUUUUUUH PRECISAM TREINAR MAIS!"
 		    }else{
 		      se a mensagem da banda for nulo, a plateia simplesmente nao faz nada.
 	        }
 	     }*/

 	      
 	   
 	    @Override  
 	     public boolean done() {
 	    	/** consulta df para ver se existe jurado se existir  false, senao true
 	    	 *  
 	    	 */
 	    /**	a platéia só morrerá se os jurados morrerem.
 	    	Mesmo se a banda morrer, a platéia não morrerá. */
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
			
	 	      
	 	    
 		
		
 	     /**private void enviaMSg(ACLMessage resposta, String message){
			 ACLMessage msg= new ACLMessage(ACLMessage.REQUEST);
			 msg.addReceiver(resposta.getSender());// esta parte esta sem sentido e so para testar , depois implementarei o cfp  ou modelarei com o inform.
			 msg.setContent(myAgent.getLocalName()+message);
			 myAgent.send(msg);
		 }*/
	
	
	
	

		
        
	


