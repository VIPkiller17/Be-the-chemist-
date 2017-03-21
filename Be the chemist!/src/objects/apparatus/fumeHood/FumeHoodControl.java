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
        
        timer+=tpf;
        
        //Every 0.5 seconds, this will check through every items in the world. If the item is within the bounds of the fume hood, its insideFumeHood boolean is set to true
        if(timer>0.5f){
        
            for(PhysicalObject p: main.getItemsList()){

                presentObjectPosition=p.getPos();

                if(presentObjectPosition.getX()<-11.14&&presentObjectPosition.getX()>-12.2&&presentObjectPosition.getY()<2.03&&presentObjectPosition.getY()>0.98&&presentObjectPosition.getZ()<7.71&&presentObjectPosition.getZ()>5.73)

                    p.setInsideFumeHood(true);

                else
                    
                    p.setInsideFumeHood(false);

            }
            
            timer=0;
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
