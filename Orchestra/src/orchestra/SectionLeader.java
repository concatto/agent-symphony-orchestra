/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author concatto
 */
public class SectionLeader extends MusicianAgent {

    @Override
    protected void setup() {
        super.setup(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void beat(int index) {
        super.beat(index); //To change body of generated methods, choose Tools | Templates.
        
        MelodyPlayingBehaviour behaviour = getMelodyBehaviour();
        
        boolean isAlmostStarting = index == 3 && !isPlaying();
        boolean isMelodyEnding = getRemainingBeats() == 1;
        boolean isSynchronized = behaviour.getNextMelody() >= 0;
        
        if ((!isSynchronized && isMelodyEnding) || isAlmostStarting) {
            int nextMelody = behaviour.getRandomMelodyIndex();
            
            String content = String.format("synchronize %d wait 0", nextMelody);
            
            try {
                DirectoryUtils.broadcast(this, ACLMessage.INFORM, content, getSection());
            } catch (FIPAException ex) {
                Logger.getLogger(SectionLeader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
