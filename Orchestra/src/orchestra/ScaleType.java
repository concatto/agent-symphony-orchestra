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
public enum ScaleType {
    MAJOR(0, 2, 4, 5, 7, 9, 11),
    MINOR(0, 2, 3, 5, 7, 8, 10);
    
    private int[] semitonesFromTonic;
    private ScaleType(int n1, int n2, int n3, int n4, int n5, int n6, int n7) {
        semitonesFromTonic = new int[] {n1, n2, n3, n4, n5, n6, n7};
    }

    public int[] getSemitonesFromTonic() {
        return semitonesFromTonic;
    }
}
