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
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class ConcertMaster extends MusicianAgent {
    private ReplyExpectation expectation;
    private ReplyExpectation expectationDegree;
   
    public ConcertMaster() {
        
    }

    @Override
    protected void setup() {
        super.setup(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void handleMessage(ACLMessage msg) {
        super.handleMessage(msg);
        
        //if (msg.getPerformative()== ACLMessage.PROPAGATE) {
            if (msg.getContent().equalsIgnoreCase("tutti")) {
                System.out.println("Tutti received");
                handleTuttiRequest();
            }else if(msg.getContent().equalsIgnoreCase("willChangeDegree")){
                System.out.println("DegreeShift warning received");
                handleDegreeRequest();
            }
        //}
        
        testExpectation(expectation, msg, this::handleTuttiResponse);
        testExpectation(expectationDegree, msg, this::handleDegreeResponse);
    }
    
    private void testExpectation(ReplyExpectation expectation, ACLMessage msg, Consumer<Map<AID, String>> handler) {
        if (expectation != null && !expectation.isFulfilled()) {
            expectation.test(msg);
            
            if (expectation.isFulfilled()) {
                handler.accept(expectation.getReplies());
            }
        }
    }

    public void handleDegreeRequest() {
        try {
            List<AID> musicians = DirectoryUtils.queryService(this, "musician");
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                
            for (AID aid : musicians) {
                msg.addReceiver(aid);
            }
            
            msg.setContent("changeDegree");
            send(msg);
            
            expectationDegree = expectReply(musicians, "\\d+ actualDegree");
        } catch (FIPAException ex) {
            Logger.getLogger(ConcertMaster.class.getName()).log(Level.SEVERE, null, ex);
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
            
            expectation = expectReply(musicians, "\\d+ remaining");
        } catch (FIPAException ex) {
            Logger.getLogger(ConcertMaster.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    private ReplyExpectation expectReply(List<AID> musicians, String regex) {
        return new ReplyExpectation(musicians, Pattern.compile(regex));
    }

    private void handleDegreeResponse(Map<AID, String> replies){
        
        int parimpar = (int)(Math.random() * ((10 - 0) + 1));
        
        replies.forEach((aid, content) -> {
            String[] aux = content.split("\\s+");
            
            //se o apalla decidir que o novo degree shift é impar ou par...
            if(parimpar%2 == 0){
                if(Integer.parseInt(aux[0])%2 == 0){
                    ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                    message.addReceiver(aid);
                    message.setContent(aux[0] + " newDegreeShift");
                    send(message);    
                }else{
                    ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                    message.addReceiver(aid);
                    String[] aux2 = content.split("\\s+");
                    content = Integer.toString(Integer.parseInt(aux2[0]) + 1);
                    message.setContent(content + " newDegreeShift");
                    send(message);                    
                }                
            }else{
                if(Integer.parseInt(aux[0])%2 == 0){
                    ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                    message.addReceiver(aid);
                    String[] aux2 = content.split("\\s+");
                    content = Integer.toString(Integer.parseInt(aux2[0]) + 1);
                    message.setContent(content + " newDegreeShift");
                    send(message);    
                }else{
                    ACLMessage message = new ACLMessage(ACLMessage.PROPOSE);
                    message.addReceiver(aid);
                    message.setContent(aux[0] + " newDegreeShift");
                    send(message);                    
                }                                
            }
            

            
        });        
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
    
    @Override
    public void beat(int index) {
        super.beat(index); //To change body of generated methods, choose Tools | Templates.
        if(this.cumulativeBeats%12 == 0){
            System.out.println("entrou");
            addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("spalla", AID.ISLOCALNAME));                
                int randomNumber = (int)(Math.random() * ((6 - 0) + 1));
                msg.setContent("willChangeDegree");
                send(msg);
            }
        });   
        }
    }


    
}


