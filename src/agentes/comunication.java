package agentes;
import jade.core.Agent;
public class comunication extends Agent{
	protected void setup() {
		System.out.println("Hallo World! my name is "+getAID().getName());
		//Object[] args = getArguments();
		//if (args != null) {
		//System.out.println("My arguments are: ");
		//for (int i = 0; i < args.length; ++i) {
		//System.out.println("- "+args[i]);
		//}
		//}
		doDelete();
		}
		protected void takeDown() {
		  System.out.println("Bye...");
		}
}
