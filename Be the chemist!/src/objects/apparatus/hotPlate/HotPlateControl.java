/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.hotPlate;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import interfaces.Heatable;
import main.Main;
import objects.PhysicalObject;

/**
 *
 * @author VIPkiller17
 */
public class HotPlateControl extends AbstractControl{
    
    private HotPlate hotPlate;
    private Node rootNode;
    private CollisionResults collisionResults;
    private PhysicalObject objectToMeasureMass;
    private double mass;
    
    private Main main;
    
    private Heatable presentHeatable;

    public HotPlateControl(Main main,Node rootNode, CollisionResults collisionResults, HotPlate hotPlate) {
        
        this.main=main;
        this.rootNode = rootNode;
        this.hotPlate= hotPlate;
        this.collisionResults = collisionResults;
        
    }
   
    @Override
    public String toString() {
        
        return "HotPlate: " + hotPlate;
        
    }
    
    @Override
    public boolean equals(Object object) {
        
        return (object instanceof HotPlateControl);
           
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        for(Heatable h:main.getHeatables()){
            
            if(hotPlate.canHeat(h.getPosition())){
                
                if(h.getTemperature()<hotPlate.getTemperature()+tpf){
                    
                    h.addKelvin(tpf);
                    
                }else if(h.getTemperature()>=hotPlate.getTemperature()+tpf){
                    
                    h.setTemperature(hotPlate.getTemperature());
                    
                }
                
            }
            
        }
        
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
       
    }
    
    
}
