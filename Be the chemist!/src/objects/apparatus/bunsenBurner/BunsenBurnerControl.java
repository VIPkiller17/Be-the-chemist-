/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.bunsenBurner;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class BunsenBurnerControl extends AbstractControl {
    
    private CollisionResults collisionResults;
    private BunsenBurner bunsenBurner;
    private Node rootNode;
    
    public BunsenBurnerControl(Node rootNode, BunsenBurner bunsenBurner) {
        
        this.bunsenBurner = bunsenBurner;
        this.rootNode = rootNode;
    }
    
    @Override
    protected void controlUpdate(float f) {
       
        bunsenBurner.setRayCoords(new Vector3f(bunsenBurner.getLocalTranslation().x, bunsenBurner.getLocalTranslation().y, bunsenBurner.getLocalTranslation().z), new Vector3f(bunsenBurner.getLocalTranslation().x, bunsenBurner.getLocalTranslation().y + 10f, bunsenBurner.getLocalTranslation().z));
        
        //Clearing collision list
        if(collisionResults.size()>0)

            //if it does clear it because there could be left-overs from last loop
            collisionResults.clear();
        
        //Find the collisions between the ray and other geoms
        rootNode.collideWith(bunsenBurner.getRay(),collisionResults);
        
        
        
    }
    
     @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
       
    }
    
}
