/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.testing;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import main.Main;
import objects.PhysicalObject;
import objects.particleEmitter.ParticleEmitter;

/**
 *
 * @author VIPkiller17
 */
public class SimpleEmitter extends PhysicalObject{
    
    private Geometry boxGeo;
    
    private ParticleEmitter particleEmitter;
    
    private Node node;
    
    public SimpleEmitter(Main main,Vector3f position){
        
        super(main,position);
        
        node=new Node();
        
        Box boxMesh = new Box(0.01f,0.01f,0.01f); 
        boxGeo = new Geometry("A Textured Box", boxMesh); 
        Material boxMat = new Material(main.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"); 
        boxMat.setColor("Color",ColorRGBA.Blue);
        boxGeo.setMaterial(boxMat); 
        
        node.attachChild(boxGeo);
        main.getRootNode().attachChild(node);
        
        particleEmitter=new ParticleEmitter(main,this);
        
        node.setLocalTranslation(position);
        
        //particleEmitter.updatePosition();
        
        //System.out.println("Simple emitter created");

    }

    @Override
    public void setPos(Vector3f position) {
        
        boxGeo.setLocalTranslation(position);
        
    }

    @Override
    public Node getNode() {
        
        return node;
        
    }

    @Override
    public String getDescription() {
        
        return "Test particle emitter";
        
    }

    @Override
    public String getName() {
        
        return "Test particle emitter";
        
    }

    public Spatial getSpatial() {
        
        return boxGeo;
        
    }
    
}
