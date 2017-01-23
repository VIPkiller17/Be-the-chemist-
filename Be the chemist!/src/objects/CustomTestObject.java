/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Describable;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author VIPkiller17
 */
public class CustomTestObject implements Describable,Savable{
    
    private static int index;
    
    private Spatial spatial;
    private int thisIndex;
    private String model;
    
    
    public CustomTestObject(Node rootNode,AssetManager assetManager,ArrayList<Describable> describables){
        
        spatial = assetManager.loadModel("Models/Player/MyRightHand/MyRightHand.j3o");
        model="Models/Player/MyRightHand/MyRightHand.j3o";
        //spatial.scale(0.1f,0.1f,0.1f);
        spatial.setLocalTranslation(0f,0f,0f);
        thisIndex=index++;
        spatial.setName("CustomTestObject #"+thisIndex);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        describables.add(this);
        rootNode.attachChild(spatial);
        System.out.println("Created custom test object with spatial name: "+spatial.getName()+" and parent name: "+spatial.getParent().getName());
        
    }
    
    public CustomTestObject(Node rootNode,AssetManager assetManager,ArrayList<Describable> describables,Float posX,Float posY,Float posZ,String model){
        
        spatial = assetManager.loadModel(model);
        this.model=model;
        //spatial.scale(0.1f,0.1f,0.1f);
        spatial.setLocalTranslation(posX,posY,posZ);
        thisIndex=index++;
        spatial.setName("CustomTestObject #"+thisIndex);
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        describables.add(this);
        rootNode.attachChild(spatial);
        System.out.println("Created custom test object with spatial name: "+spatial.getName()+" and parent name: "+spatial.getParent().getName());
        
    }

    public String getDescription() {
        
        return "Custom test object #"+thisIndex+"\nWith model path:\n"+model;
        
    }

    public Spatial getSpatial() {
        
        return spatial;
        
    }

    public void write(JmeExporter je) throws IOException {
        
        
    }

    public void read(JmeImporter ji) throws IOException {
        
        
    }
    
}
