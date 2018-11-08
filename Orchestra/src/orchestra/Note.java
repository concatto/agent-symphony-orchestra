/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

/**
 *
 * @author 5928036
 */
public class Note {
    private int pitch;
    private boolean sharp;
    private double duration;

    public Note(int tone, boolean sharp, double duration) {
        this.pitch = tone;
        this.sharp = sharp;
        this.duration = duration;
    }
    
    public Note(int tone, double duration) {
        this(tone, false, duration);
    }

    public double getDuration() {
        return duration;
    }

    public int getPitch() {
        return pitch;
    }

    public boolean isSharp() {
        return sharp;
    }

    @Override
    public String toString() {
        return (sharp ? "#" : "") + pitch + "; " + duration + " beats";
    }

    public boolean isRest() {
        return pitch == 0;
    }
    
    
}
