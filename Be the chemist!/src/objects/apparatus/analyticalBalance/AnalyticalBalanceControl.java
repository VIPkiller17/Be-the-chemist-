/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.analyticalBalance;

import com.jme3.collision.CollisionResults;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import objects.PhysicalObject;

/**
 *
 * @author VIPkiller17
 */
public class AnalyticalBalanceControl extends AbstractControl{
    
    private AnalyticalBalance analyticalBalance;
    private Node rootNode;
    private CollisionResults collisionResults;
    private PhysicalObject objectToMeasureMass;
    private double mass;

    public AnalyticalBalanceControl(Node rootNode, CollisionResults collisionResults, AnalyticalBalance analyticalBalance) {
        
        this.rootNode = rootNode;
        this.analyticalBalance= analyticalBalance;
        this.collisionResults = collisionResults;
        
    }
   
    @Override
    public String toString() {
        return "AnalyticalBalance: " + analyticalBalance;  
    }
    
    @Override
    public boolean equals(Object object) {
        return (object instanceof AnalyticalBalanceControl);
           
    }
    
    @Override
    protected void controlUpdate(float f) {
        
        /*
        mass = 0;
        
        rootNode.collideWith(analyticalBalance.getSurface(),collisionResults);
        
        //Display mass (with Display)
        for (int i = 0; i < collisionResults.size(); i++) {
            if (collisionResults.getCollision(i).getGeometry().getParent().getUserData("correspondingObject") instanceof PhysicalObject) {
                
                objectToMeasureMass = (PhysicalObject) collisionResults.getCollision(i).getGeometry().getParent().getUserData("correspondingObject");
                mass += objectToMeasureMass.getMass();
            } 
                
        }
        
        //Format values to be displayed by balance
        String massNumber = String.valueOf(4567.1234567678587658765765);
        int pointIndex = massNumber.indexOf(".");
        String massNumberFormatted = massNumber.substring(0, pointIndex + 4);
        analyticalBalance.getDisplayText().setText(massNumberFormatted + "");
        */
        
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
       
    }
    
    
}
