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
import interfaces.Heatable;

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
       
        bunsenBurner.setRayCoords(new Vector3f(bunsenBurner.getLocalTranslation().x, bunsenBurner.getLocalTranslation().y+0.1f, bunsenBurner.getLocalTranslation().z), new Vector3f(bunsenBurner.getLocalTranslation().x, bunsenBurner.getLocalTranslation().y + 10f, bunsenBurner.getLocalTranslation().z));
        
        //Clearing collision list
        if(collisionResults.size()>0)

            //if it does clear it because there could be left-overs from last loop
            collisionResults.clear();
        
        //Find the collisions between the ray and other geoms
        rootNode.collideWith(bunsenBurner.getRay(),collisionResults);
            
        if(collisionResults.getClosestCollision().getGeometry().getUserData("correspondingObject")!=null&&collisionResults.getClosestCollision().getGeometry().getUserData("correspondingObject") instanceof Heatable){

            ((Heatable)collisionResults.getClosestCollision().getGeometry().getUserData("correspondingObject")).addKelvin(f);

        }else if(collisionResults.getClosestCollision().getGeometry().getParent().getUserData("correspondingObject")!=null&&collisionResults.getClosestCollision().getGeometry().getParent().getUserData("correspondingObject") instanceof Heatable){

            ((Heatable)collisionResults.getClosestCollision().getGeometry().getParent().getUserData("correspondingObject")).addKelvin(f);

        }
        
    }
    
     @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
       
    }
    
}
