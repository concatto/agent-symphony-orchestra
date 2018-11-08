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
        int index = degree % 7;
        int octaveDifference = degree / 7;
        
        if (degree < 0) {
            index = index + 7;
            octaveDifference -= 1;
        }
        
        int semitones = type.getSemitonesFromTonic()[index] + (sharp ? 1 : 0);
        
        return semitones + (12 * octaveDifference);
    }
}
