/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus;

import com.jme3.math.Vector3f;
import main.Main;
import objects.PhysicalObject;

/**
 *
 * @author VIPkiller17
 */
public abstract class Apparatus extends PhysicalObject{
    
    public Apparatus(Main main,Vector3f position) {
        super(main,position);
    }
    
}
