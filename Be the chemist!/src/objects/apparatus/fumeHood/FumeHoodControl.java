/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.fumeHood;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import main.Main;
import objects.PhysicalObject;

/**
 *
 * @author VIPkiller17
 */
public class FumeHoodControl extends AbstractControl{

    private FumeHood fumeHood;
    
    private Main main;
    
    private float timer;
    
    private Vector3f presentObjectPosition;
    
    public FumeHoodControl(Main main,FumeHood fumeHood){
        
        this.main=main;
        
        this.fumeHood=fumeHood;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
}
