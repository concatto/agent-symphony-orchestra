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
    private String tonic;
    private ScaleType type;

    public DiatonicScale(String tonic, ScaleType type) {
        this.tonic = tonic;
        this.type = type;
    }

    public String getTonic() {
        return tonic;
    }
    
    private static int getAccidentValue(Accident accident) {
        if (accident == null) {
            return 0;
        } else switch (accident) {
            case SHARP:
                return 1;
            case FLAT:
                return -1;
            default:
                return 0;
        }
    }
    
    public int semitonesFromTonic(int degree, Accident accident) {
        int index = degree % 7;
        int octaveDifference = degree / 7;
        
        if (degree < 0) {
            index = (index + 7) % 7;
            octaveDifference = ((degree + 1) / 7) - 1;
        }
        
        int x = type.getSemitonesFromTonic()[index];
        //System.out.println("From tonic = " + x);
        int semitones = x + getAccidentValue(accident);
        
        return semitones + (12 * octaveDifference);
    }
}
