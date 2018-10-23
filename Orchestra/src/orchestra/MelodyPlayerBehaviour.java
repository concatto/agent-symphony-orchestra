/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import jade.core.behaviours.CyclicBehaviour;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author concatto
 */
public class MelodyPlayerBehaviour extends CyclicBehaviour {
    private Melody melody;
    private float bpm;
    private int noteIndex = 0;
    
    public MelodyPlayerBehaviour(Melody melody, float bpm) {
        this.melody = melody;
        this.bpm = bpm;
    }

    public void setBpm(float bpm) {
        this.bpm = bpm;
    }
    
    @Override
    public void action() {
        if (noteIndex > 0) {
            System.out.println("Stopping " + melody.getNotes().get(noteIndex - 1).getTone());

            if (noteIndex >= melody.getNotes().size()) {
                noteIndex = 0;
            }
        }
        
        Note note = melody.getNotes().get(noteIndex);
        
        System.out.println("Playing " + note.getTone());
        
        long ms = Math.round((60.0 / bpm) * 1000 * note.getDuration());
        System.out.println("Will sleep for " + ms);
        blockForcibly(ms);
        
        noteIndex++;
    }
    
    private void blockForcibly(long ms) {
        block(ms);
    }
}
