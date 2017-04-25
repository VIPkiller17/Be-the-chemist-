/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 *
 * @author VIPkiller17
 */
public interface Grabbable {
    
    void highlightVisible(boolean highlightVisible);
    
    Vector3f getGrabbablePosition();
    
    boolean isHighlightVisible();
    
    Spatial getSpatial();
    
}
