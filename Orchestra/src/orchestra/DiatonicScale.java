/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

/**
 *
 * @author concatto
 */
public class DiatonicScale {
    private char tonic;
    private ScaleType type;

    public DiatonicScale(char tonic, ScaleType type) {
        this.tonic = Character.toUpperCase(tonic);
        this.type = type;
    }

    public char getTonic() {
        return tonic;
    }
    
    public int semitonesFromTonic(int degree, boolean sharp) {
        int index = degree - 1;
        
        if (degree < 0) {
            index = degree + 7;
        }
        
        int octaveDifference = index / 7; // TODO fix for negative degrees
        int semitones = type.getSemitonesFromTonic()[index % 7] + (sharp ? 1 : 0);
        semitones = degree >= 0 ? semitones : semitones - 12;
        
        return semitones + (12 * octaveDifference); // TODO fix for negative degrees
    }
}
