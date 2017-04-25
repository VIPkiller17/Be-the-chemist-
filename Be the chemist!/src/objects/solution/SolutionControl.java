/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.solution;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class SolutionControl extends AbstractControl{

    private Solution solution;
    
    private double presentVolume;
    private double presentTemperature;
    
    public SolutionControl(Solution solution){
        
        this.solution=solution;
        
    }
    
    @Override
    protected void controlUpdate(float f) {
        
        
        
        //account for H2O2 decomposition
        
        /*
        presentVolume=0;
        
        for(int i=0;i<solution.getVolumes().size();i++){
            
            presentVolume+=solution.getVolumes().get(i);
            
        }
        
        solution.setVolume(presentVolume);
        */
        
        presentTemperature=0;
        
        for(int i=0;i<solution.getTemperatures().size();i++){
            
            //System.out.println("Present temperature: "+solution.getTemperatures().get(i));
            
            presentTemperature+=solution.getTemperatures().get(i);
            
            //System.out.println("Present temperature now: "+presentTemperature);
            
        }
        
        solution.setTemperature(presentTemperature/solution.getTemperatures().size());
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
}
