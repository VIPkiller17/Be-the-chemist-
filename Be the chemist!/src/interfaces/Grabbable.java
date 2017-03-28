/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.jme3.math.Vector3f;

/**
 *
 * @author VIPkiller17
 */
public interface Grabbable {
    
    void highlightVisible(boolean highlightVisible);
    
    Vector3f getGrabbablePosition();
    
    boolean isHighlightVisible();
    
}
