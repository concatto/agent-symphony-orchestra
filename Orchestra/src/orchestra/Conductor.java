package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class Conductor extends Agent {
    private int timer = 500;
    private int beatIndex = 1;
    
    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg2 = receive();
                if (msg2 != null) {
                    timer = Integer.parseInt(msg2.getContent());
                    System.out.println("Changed time to: " + timer);
                    block();
                }
            }
        });
        
        addBehaviour(new TickerBehaviour(this, this.timer) {
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
                
                reset(timer);
            }
        });
    }

}
