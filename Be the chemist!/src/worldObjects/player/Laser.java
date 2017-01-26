/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worldObjects.player;

import com.jme3.math.Vector3f;
import com.jme3.scene.shape.Line;
//by Tommy
public class Laser extends Line{
    
    private Vector3f start,end;
    
    public Laser(Vector3f start,Vector3f end){
        
        super(start,end);
        
        this.start=start;
        this.end=end;
        
    }
    
    @Override
    public void updatePoints(Vector3f start,Vector3f end){
        
        super.updatePoints(start, end);
        
    }
    
    @Override
    public void updateGeometry(Vector3f start,Vector3f end){
        
        super.updateGeometry(start, end);
        
    }
    
}
