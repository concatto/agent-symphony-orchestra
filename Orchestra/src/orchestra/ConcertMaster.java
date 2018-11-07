package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.introspection.AddedBehaviour;
import jade.gui.AgentTree.SuperContainer;
import jade.lang.acl.ACLMessage;

public class ConcertMaster extends Agent{

   
	public ConcertMaster() {

	}
	
	
	protected void setup() {

        addBehaviour(new CyclicBehaviour(this) {
			@Override
			public void action() {				
				ACLMessage msg2 = receive();
				if(msg2 != null) {
					System.out.println(myAgent.getLocalName() + ": " + msg2.getContent());				
					block();
				}
					
			}
		});	
		
	}
	
	
}
