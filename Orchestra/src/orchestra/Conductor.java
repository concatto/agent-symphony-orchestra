package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class Conductor extends Agent{

	public Conductor() {
		
	}
	
	
	protected void setup() {	
        
		addBehaviour(new TickerBehaviour(this, 1000) {
            @Override
            protected void onTick() {
            	ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				//msg.addReceiver(new AID("concertMaster", AID.ISLOCALNAME));
				msg.addReceiver(new AID("heifetz", AID.ISLOCALNAME));
				msg.setContent("TOCA");
				send(msg);
				System.out.println("ENVIEI A MENSAGEM POURRA");               
            }
        });
	}
	
}
