/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import jmevr.input.VRAPI;

/**
 *
 * @author VIPkiller17
 */
public class DescriptionControl extends AbstractControl {
    
    public static Spatial observer;
    private VRAPI VRHardware;
    private int controllerIndex;
    private boolean movedOut=true,doneMovingOut;
    
    public DescriptionControl(VRAPI VRHardware,int controllerIndex,Spatial newObserver){
        
        this.VRHardware=VRHardware;
        this.controllerIndex=controllerIndex;
        observer=newObserver;
        
    }

    @Override
    protected void controlUpdate(float tpf) {
        
        if(!movedOut){
            
            spatial.setLocalTranslation(VRHardware.getVRinput().getPosition(controllerIndex).add(0,0.05f,0).add(observer.getWorldTranslation()));
            spatial.lookAt(VRHardware.getPosition().add(observer.getWorldTranslation()), Vector3f.UNIT_Y);
        
        }else if(movedOut&&!doneMovingOut){
            
            spatial.setLocalTranslation(0,-2,0);
            
            doneMovingOut=true;
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    public void moveOut(boolean moveOut){
        
        this.movedOut=moveOut;
        
        doneMovingOut=false;
        
    }
    
}
