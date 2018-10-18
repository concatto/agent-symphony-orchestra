/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package orchestra;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 5928036
 */
public class Melody {
    private String name;
    private List<Note> notes;

    public Melody(String name, List<Note> notes) {
        this.name = name;
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
