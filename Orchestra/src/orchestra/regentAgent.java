package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
*
* @author jesus
*/

public class regentAgent extends Agent {

	private void conduct() {
		addBehaviour(new OneShotBehaviour() {
			@Override
			public void action() {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(new AID("heifetz", AID.ISLOCALNAME));
				msg.setContent("TOCA");
				send(msg);
				System.out.println("ENVIEI A MENSAGEM PONTO COM PONTO BR");
			}
		});
		
	}
	
	protected void setup() {
		
		addBehaviour(new TickerBehaviour(this, 1000) {
			@Override
			protected void onTick() {
				conduct();
			}
		});
		
	}
}
