/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import main.Main;
import objects.PhysicalObject;
import objects.particleEmitter.ParticleEmitter;

/**
 *
 * @author VIPkiller17
 */
public class Sink extends PhysicalObject{
    
    private Main main;
    private Node rootNode;
    private AssetManager assetManager;
    
    private ParticleEmitter particleEmitter;
    
    private SinkHandle hotHandle,coldHandle;
    private int index;
    
    private Node node;

    public Sink(Main main,AssetManager assetManager,Node rootNode,int index){
        
        super(main,new Vector3f());
        
        this.main=main;
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        this.index=index;
        
        node=new Node();
        node.setName("Sink");
        
        rootNode.attachChild(node);
        
        if(index==0){
            
            node.setLocalTranslation(4.155f,0.92f,5.14f);
            particleEmitter=new ParticleEmitter(assetManager,this,node.getLocalTranslation().add(new Vector3f(0.12f,0.21f,0)),new Vector3f(0,-1,0),new Quaternion(0,0,0,0),0,0,new Vector3f(0,0,0),new Vector3f(0,0,0),0.1,0.01,new Vector3f(0,-9.806f,0),new Vector3f(0,0,0));
        
        }else if(index==1){
            
            node.setLocalTranslation(5.345f,0.92f,5.14f);
            particleEmitter=new ParticleEmitter(assetManager,this,node.getLocalTranslation().add(new Vector3f(1.07f,0.21f,0)),new Vector3f(0,-1,0),new Quaternion(0,0,0,0),0,0,new Vector3f(0,0,0),new Vector3f(0,0,0),0.1,0.01,new Vector3f(0,-9.806f,0),new Vector3f(0,0,0));
        
        }
        
        hotHandle=new SinkHandle(main,assetManager,0,this);
        coldHandle=new SinkHandle(main,assetManager,1,this);
        
        rootNode.attachChild(node);
    }
    
    public int getIndex(){
        
        return index;
        
    }
    
    @Override
    public Node getNode(){
        
        return node;
        
    }

    @Override
    public String getDescription() {
        
        return "";
        
    }
    
}
