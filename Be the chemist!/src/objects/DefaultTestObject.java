/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import interfaces.Describable;
import java.io.IOException;
import java.util.ArrayList;
//by Tommy
public class DefaultTestObject implements Describable,Savable{
    
    private Spatial spatial;
    
    public DefaultTestObject(Node rootNode,AssetManager assetManager,ArrayList<Describable> describables){
        Box boxMesh = new Box(0.1f,0.1f,0.1f);
        Geometry boxGeo = new Geometry("DefaultTestObject", boxMesh); 
        boxGeo.setLocalTranslation(-1,1,0.5f);
        spatial=boxGeo;
        Material boxMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        boxMat.setColor("Color",ColorRGBA.Blue);
        boxGeo.setMaterial(boxMat); 
        spatial.setName("DefaultTestObject");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        describables.add(this);
        rootNode.attachChild(boxGeo);
        
        System.out.println("Created default test object with geometry name: "+boxGeo.getName()+" and parent name: "+boxGeo.getParent().getName());
        
    }

    public String getDescription() {
        
        return "A default test object";
        
    }

    public Spatial getSpatial() {
        
        return spatial;
        
    }

    public void write(JmeExporter je) throws IOException {
        
        
    }

    public void read(JmeImporter ji) throws IOException {
        
        
    }
    
}
