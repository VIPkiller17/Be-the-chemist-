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
    
    public SolutionControl(Solution solution){
        
        this.solution=solution;
        
    }
    
    @Override
    protected void controlUpdate(float f) {
        
        for(int i=0;i<solution.getSubstances().size();i++){
            
            solution.addQuantity(solution.getSubstances().get(i).getQuantity());
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
}
