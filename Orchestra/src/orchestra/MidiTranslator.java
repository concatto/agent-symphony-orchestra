/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author concatto
 */
public class MidiTranslator {
    private static Map<String, Integer> noteMap;
    
    static {
        Map<String, Integer> notes = new HashMap<>();
        notes.put("C", 0);
        notes.put("C#", 1);
        notes.put("Db", 1);
        notes.put("D", 2);
        notes.put("D#", 3);
        notes.put("Eb", 3);
        notes.put("E", 4);
        notes.put("Fb", 4);
        notes.put("F", 5);
        notes.put("F#", 6);
        notes.put("Gb", 6);
        notes.put("G", 7);
        notes.put("G#", 8);
        notes.put("Ab", 8);
        notes.put("A", 9);
        notes.put("A#", 10);
        notes.put("Bb", 10);
        notes.put("B", 11);
        
        noteMap = new HashMap<>(notes);
    }
    
    public static int translate(String note, int octave) {
       return noteMap.get(note) + (octave * 12);
    }
}
