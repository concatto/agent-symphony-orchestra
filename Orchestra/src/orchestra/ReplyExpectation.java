/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 *
 * @author concatto
 */
public class ReplyExpectation {
    private Map<AID, String> replies;
    private Set<AID> agents;
    private Pattern messagePattern;
    
    public ReplyExpectation(Collection<AID> agents, Pattern messagePattern) {
        this.replies = new HashMap<>();
        this.agents = new HashSet<>(agents);
        this.messagePattern = messagePattern;
        
        System.out.println("Expecting " + agents.size() + " replies: " + agents.stream().map(x -> x.getLocalName()).collect(Collectors.joining(",")));
    }
    
    public void test(ACLMessage message) {
        if (message != null && agents.contains(message.getSender())) {
            Matcher matcher = messagePattern.matcher(message.getContent());
            
            System.out.println("Testing " + message.getContent());
            if (matcher.matches()) {
                boolean removed = agents.remove(message.getSender());
                replies.put(message.getSender(), message.getContent());
                System.out.println("OK " + agents.size() + " remaining. Removed = " + removed);
            } else {
                System.out.println("NOK");
            }
        }
    }
    
    public boolean isFulfilled() {
        return agents.isEmpty();
    }

    public Map<AID, String> getReplies() {
        return replies;
    }
}
