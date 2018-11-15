/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 5928036
 */
public class MelodyReader {
    public static List<Melody> read(String path) throws IOException {
        List<Melody> melodies = new ArrayList<>();
        
        String name = null;
        List<Note> notes = new ArrayList<>();
        
        for (String line : Files.readAllLines(Paths.get(path))) {
            if (!line.isEmpty()) { 
                if (name == null) {
                    name = line;
                } else {
                    String[] pieces = line.split(" ");
                    int pitch = pieces[0].equals("R") ? Note.REST : Integer.parseInt(pieces[0]);
                    
                    boolean hasAccident = pieces.length > 2;
                    Accident accident = null;
                    
                    if (pieces[1].equals("#")) {
                        accident = Accident.SHARP;
                    } else if (pieces[1].equals("b")) {
                        accident = Accident.FLAT;
                    }
                    
                    float duration = Float.parseFloat(pieces[hasAccident ? 2 : 1]);
                    
                    notes.add(new Note(pitch, accident, duration));
                }
            } else {
                melodies.add(new Melody(name, notes));
                name = null;
                notes.clear();
            }
        }
        
        return melodies;
    }
}
