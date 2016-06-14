
package agentes;
import jade.core.Agent;




import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

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
        private AID [] jurado;
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
 		    		     enviaPlateia(resposta,"AHHHHHHH EEEEE");
 		            }else{
 		    		   enviaPlateia(resposta,"UUUUUUUUH PRECISAM TREINAR MAIS!");
 		    	     }
 		     }  
 		    }
          
	    private void enviaPlateia(ACLMessage resposta,String b){
      	    ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
      	    msg=setReceiverJurado(msg);
      	   
      	    msg.setContent("aeiou");
      	    if(msg.getContent()!="para"){
      	    msg.addReceiver(resposta.getSender());
    	       msg.setContent(b);
    	       myAgent.send(msg);
      	    }
    	   
    	}
          
          /**  Banda "Ato de Jugar" 
                                     */
	    
 		    /** Verificará o tipo da resposta, se a resposta da banda for =1 a plateia
 		   
 		    	
 		    respoderá "AEEEEEEE! UHHUUUUUU"
 		      se nao for 1, a plateia responderá "UUUUUUUUH PRECISAM TREINAR MAIS!"
 		    }else{
 		      se a mensagem da banda for nulo, a plateia simplesmente nao faz nada.
 	        }
 	     }*/

 	      
 	   
 	    @Override  
 	     public boolean done() {
 	    	//DFAGENT
 	    	/** consulta df para ver se existe jurado se existir  false, senao true
 	    	 *  
 	    	 */
 	    /**	a platéia só morrerá se os jurados morrerem.
 	    	Mesmo se a banda morrer, a platéia não morrerá. */
 	    	return false;
 		}
 	        
 ACLMessage	  setReceiverJurado(ACLMessage msg){	
 	    int conterro=0;
 	    DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("Ato de Jugar");
		template.addServices(sd);
		while(conterro<4||msg.getContent()!="bemsucedido" ){
		 try{
			DFAgentDescription[] result = DFService.search(myAgent, template);
			jurado = new AID[result.length];
			if(result.length!=0){
			   for (int i = 0; i < result.length; ++i) {
				  jurado[i] = result[i].getName(); 
				  msg.addReceiver(jurado[i]); 
				  System.out.println(jurado[i].getName());
				  msg.setContent("bemsucedido");
			    }
			   }else{
			     msg.setContent("para");
			   }
		   }catch(FIPAException e) {
			  System.out.println("*****************Erro");
			  e.printStackTrace();
              conterro++;
            }
		  if(conterro==4){
		      msg.setContent("nao foi possivel verificar o numero de juizes");	  
		  } 		
		return msg;
		}	
 }
 	      
 	    
    ACLMessage	  setReceiverGrupo(ACLMessage msg, String Servico){	
	 	    int conterro=0;
	 	    DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType(Servico);
			template.addServices(sd);
			while(conterro<4||msg.getContent()!="bemsucedido" ){
			 try{
				DFAgentDescription[] result = DFService.search(myAgent, template);
				jurado = new AID[result.length];
				if(result.length!=0){
				   for (int i = 0; i < result.length; ++i) {
					  jurado[i] = result[i].getName(); 
					  msg.addReceiver(jurado[i]); 
					  System.out.println(jurado[i].getName());
					  msg.setContent("bemsucedido");
				    }
				   }else{
				     msg.setContent("para");
				   }
			   }catch(FIPAException e) {
				  System.out.println("*****************Erro");
				  e.printStackTrace();
	              conterro++;
	            }
			  if(conterro==4){
			      msg.setContent("nao foi possivel verificar o numero de "+Servico+"s");	  
			  } 		
			return msg;
			}	
 		
    }		
		
 	     /**private void enviaMSg(ACLMessage resposta, String message){
			 ACLMessage msg= new ACLMessage(ACLMessage.REQUEST);
			 msg.addReceiver(resposta.getSender());// esta parte esta sem sentido e so para testar , depois implementarei o cfp  ou modelarei com o inform.
			 msg.setContent(myAgent.getLocalName()+message);
			 myAgent.send(msg);
		 }*/
	
	/**
 	   DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		
		sd.setType("Ato de Jugar");
		template.addServices(sd);
		try {
			DFAgentDescription[] result = DFService.search(myAgent, template);
			jurado = new AID[result.length];
			if(result.length!=0){
			   for (int i = 0; i < result.length; ++i) {
				  jurado[i] = result[i].getName(); mudar o getName  pode-se adicionar o name direto na variavel Receiver
//				  System.out.println(jurado[i].getName());
 *                conterro=0;
			   }else{
			     conterro=4;
			   }
		   } catch (FIPAException e) {
			  System.out.println("*****************Erro");
			  e.printStackTrace();
}             conterro++  
 done 
	*/

		
 	   "Ato de Jugar"
 	   /** done(){ 
 	    *     Boolean retorno; 
 	    *     if(conterro==4)
 	    *       reterno=true;
 	    *     else
 	    *        retorno=false;  
 	    *      return retorno;
 	    *  }      
 	         
 	    */
	}
}
			
	 	      
	 	    
 		
		
 	     /**private void enviaMSg(ACLMessage resposta, String message){
			 ACLMessage msg= new ACLMessage(ACLMessage.REQUEST);
			 msg.addReceiver(resposta.getSender());// esta parte esta sem sentido e so para testar , depois implementarei o cfp  ou modelarei com o inform.
			 msg.setContent(myAgent.getLocalName()+message);
			 myAgent.send(msg);
		 }*/
	
	
	
	

		
        
	


