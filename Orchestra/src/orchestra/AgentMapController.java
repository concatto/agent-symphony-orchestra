package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentMapController extends Agent{

    Map map = new Map();
    
    int i = 100;
    
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                
            }
        });
        
        addBehaviour(new TickerBehaviour(this, 1000) {
            @Override
            protected void onTick() {
                if (map.checkChangeBpm()) {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    msg.addReceiver(new AID("orchestador", AID.ISLOCALNAME));
                    msg.setContent(Integer.toString(map.getBpmCount()));
                    send(msg);
                }
                //System.out.println(msg.getSender().getLocalName() + ": " + msg.getContent());
            }
        });
    }
    
    
    
}
