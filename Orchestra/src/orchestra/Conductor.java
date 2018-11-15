package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class Conductor extends Agent {
    private int beatIndex = 1;
    
    public Conductor() {

    }

    protected void setup() {
        addBehaviour(new TickerBehaviour(this, 500) {
            @Override
            protected void onTick() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("spalla", AID.ISLOCALNAME));
                msg.addReceiver(new AID("heifetz", AID.ISLOCALNAME));
                msg.addReceiver(new AID("heifetz2", AID.ISLOCALNAME));
                msg.addReceiver(new AID("heifetz3", AID.ISLOCALNAME));
                msg.setContent(String.valueOf(beatIndex++));
                send(msg);
                System.out.println(msg.getSender().getLocalName() + ": " + msg.getContent());
                
                if (beatIndex > 4) {
                    beatIndex = 1;
                }
            }
        });
    }

}
