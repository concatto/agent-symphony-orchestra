package orchestra;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.FIPAException;
import jade.domain.introspection.AddedBehaviour;
import jade.gui.AgentTree.SuperContainer;
import jade.lang.acl.ACLMessage;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ConcertMaster extends MusicianAgent {
    private ReplyExpectation expectation;
   
    public ConcertMaster() {
        
    }

    @Override
    protected void setup() {
        super.setup(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void handleMessage(ACLMessage msg) {
        super.handleMessage(msg);
        
        if (msg.getPerformative() == ACLMessage.PROPAGATE) {
            if (msg.getContent().equalsIgnoreCase("tutti")) {
                System.out.println("Tutti received");
                handleTuttiRequest();
            }
        }
        
        if (expectation != null) {
            expectation.test(msg);
            
            if (expectation.isFulfilled()) {
                handleTuttiResponse(expectation.getReplies());
                
                expectation = null;
            }
        }
    }
    
    public void handleTuttiRequest() {
        try {
            List<AID> musicians = DirectoryUtils.queryService(this, "treble");
            
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                
            for (AID aid : musicians) {
                msg.addReceiver(aid);
            }
            
            msg.setContent("remainingBeats");
            send(msg);
            
            expectReply(musicians, "\\d+ remaining");
        } catch (FIPAException ex) {
            Logger.getLogger(ConcertMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void expectReply(List<AID> musicians, String regex) {
        expectation = new ReplyExpectation(musicians, Pattern.compile(regex));
    }

    private void handleTuttiResponse(Map<AID, String> replies) {
        int melodyIndex = chooseMelody();
        
        int max = replies.values().stream()
                .map(content -> parseRemaining(content))
                .reduce(Integer::max)
                .get();
        
        replies.forEach((aid, content) -> {
            int remaining = parseRemaining(content);
            int toWait = max - remaining;
            
            ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
            message.addReceiver(aid);
            message.setContent("synchronize " + melodyIndex + " wait " + toWait);
            send(message);
        });
    }

    private int chooseMelody() {
        return getMelodyBehaviour().getRandomMelodyIndex(); // TODO improve
    }
    
    private int parseRemaining(String content) {
        String value = content.split(" ")[0];
        return Integer.parseInt(value);
    }
}
