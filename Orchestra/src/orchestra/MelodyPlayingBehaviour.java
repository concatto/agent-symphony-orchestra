/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import orchestra.MusicianAgent;

/**
 *
 * @author concatto
 */
public class MelodyPlayingBehaviour extends CyclicBehaviour {
    private final List<Melody> melodies;
    private Melody currentMelody;
    private float bpm;
    private int noteIndex = 0;
    private long remainingSleep = 0;
    private long totalSleep = 0;
    private long startTime;
    private int beatsToWait = 0;
    private int nextMelody = -1;
    private double cumulativeDuration = 0;
    
    public MelodyPlayingBehaviour(Agent myAgent, List<Melody> melodies, float bpm) {
        super(myAgent);
        this.melodies = melodies;
        this.bpm = bpm;
        
        finishMelody();
    }

    public void setBpm(float bpm) {
        this.bpm = bpm;
    }
    
    @Override
    public void action() {
        MusicianAgent agent = (MusicianAgent) myAgent;
        
        // The agent has been told to slept in the past
        if (totalSleep != 0) {
            remainingSleep = totalSleep - (System.currentTimeMillis() - startTime);
            // Checking to see if the agent woke up in the middle of its sleep
            if (remainingSleep > 0) {
                block(remainingSleep);
                return;
            }
        }
        
        if (noteIndex > 0) {
            agent.stop(currentMelody.getNotes().get(noteIndex - 1));
            //System.out.println("Stopping " + melody.getNotes().get(noteIndex - 1).getTone());

            if (noteIndex >= currentMelody.getNotes().size()) {
                finishMelody();
            }
        }
        
        Note note = currentMelody.getNotes().get(noteIndex);
        
        if (!note.isRest()) {
            agent.play(note);
            //System.out.println("Playing " + note.getTone());
        }
        
        cumulativeDuration += note.getDuration();
        totalSleep = Math.round((60.0 / bpm) * 1000 * note.getDuration());
        totalSleep += remainingSleep; // The agent overslept!
        //System.out.println("Will sleep for " + totalSleep);
        
        block(totalSleep);
        
        noteIndex++;
        startTime = System.currentTimeMillis();
    }
    
    public void synchronize(int beatsToWait, int melodyIndex) {
        this.beatsToWait = beatsToWait;
        this.nextMelody = melodyIndex;
    }

    private void finishMelody() {
        cumulativeDuration = 0;
        noteIndex = 0;
        
        if (beatsToWait > 0) {
            System.out.println("will rest for " + beatsToWait);
            currentMelody = Melody.empty(beatsToWait);
            beatsToWait = 0;
        } else {
            int melodyIndex = getRandomMelodyIndex();
            
            if (nextMelody >= 0) {
                melodyIndex = nextMelody;
                nextMelody = -1;
            }
            
            currentMelody = melodies.get(melodyIndex);
        }
        
        ((MusicianAgent) myAgent).notifyMelodyCompletion();
    }

    public Melody getCurrentMelody() {
        return currentMelody;
    }

    public int getRandomMelodyIndex() {
        return new Random().nextInt(melodies.size());
    }
}
