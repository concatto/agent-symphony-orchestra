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
    private Melody melody;
    private float bpm;
    private int noteIndex = 0;
    private long remainingSleep = 0;
    private long totalSleep = 0;
    private long startTime;
    
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
            agent.stop(melody.getNotes().get(noteIndex - 1));
            //System.out.println("Stopping " + melody.getNotes().get(noteIndex - 1).getTone());

            if (noteIndex >= melody.getNotes().size()) {
                finishMelody();
            }
        }
        
        Note note = melody.getNotes().get(noteIndex);
        
        if (!note.isRest()) {
            agent.play(note);
            //System.out.println("Playing " + note.getTone());
        }
        
        
        totalSleep = Math.round((60.0 / bpm) * 1000 * note.getDuration());
        totalSleep += remainingSleep; // The agent overslept!
        //System.out.println("Will sleep for " + totalSleep);
        
        block(totalSleep);
        
        noteIndex++;
        startTime = System.currentTimeMillis();
    }

    private void finishMelody() {
        noteIndex = 0;
        
        int melodyIndex = new Random().nextInt(melodies.size());
        melody = melodies.get(melodyIndex);
    }
}
