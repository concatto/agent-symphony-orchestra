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
    private static Map<Character, Integer> noteMap;
    
    static {
        Map<Character, Integer> notes = new HashMap<>();
        notes.put('C', 0);
        notes.put('D', 2);
        notes.put('E', 4);
        notes.put('F', 5);
        notes.put('G', 7);
        notes.put('A', 9);
        notes.put('B', 11);
        
        noteMap = new HashMap<>(notes);
    }
    
    public static int translate(char note, int octave) {
       return noteMap.get(Character.toUpperCase(note)) + (octave * 12);
    }
}
