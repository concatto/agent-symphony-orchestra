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
    public static final int REST = Integer.MAX_VALUE;
    private int pitch;
    private Accident accident;
    private double duration;

    public Note(int tone, Accident accident, double duration) {
        this.pitch = tone;
        this.accident = accident;
        this.duration = duration;
    }
    
    public Note(int tone, double duration) {
        this(tone, null, duration);
    }

    public double getDuration() {
        return duration;
    }

    public int getPitch() {
        return pitch;
    }

    public boolean isSharp() {
        return accident == Accident.SHARP;
    }
    
    public boolean isFlat() {
        return accident == Accident.FLAT;
    }

    @Override
    public String toString() {
        return (isSharp() ? "#" : (isFlat() ? "b" : "")) + pitch + "; " + duration + " beats";
    }

    public boolean isRest() {
        return pitch == REST;
    }

    public Accident getAccident() {
        return accident;
    }
    
    
}
