/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package worldObjects.player;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Describable;
import java.util.ArrayList;
import jmevr.input.VRAPI;

/**
 *
 * @author VIPkiller17
 */
public class Player {
    
    private AssetManager assetManager;
    private Node rootNode;
    private VRAPI VRHardware;
    private CollisionResults collisionResults;
    private ArrayList<Describable> describables;
    
    private Spatial observer;
    private Hand rightHand,leftHand;
    
    private Node playerNode;
    
    public Player(AssetManager assetManager,Node rootNode,VRAPI VRHardware,CollisionResults collisionResults,ArrayList<Describable> describables,Spatial observer){
        
        this.assetManager=assetManager;
        this.rootNode=rootNode;
        this.VRHardware=VRHardware;
        this.collisionResults=collisionResults;
        this.describables=describables;
        this.observer=observer;
        
        playerNode=new Node();
        
        playerNode.attachChild(observer);
        
        rootNode.attachChild(playerNode);
        
    }
    
    public void createHand(int side){
        
        if(side==0){
            
            rightHand=new Hand(assetManager,rootNode,VRHardware,collisionResults,describables,0,observer,playerNode,this);
        
        }else if(side==1){
            
            leftHand=new Hand(assetManager,rootNode,VRHardware,collisionResults,describables,1,observer,playerNode,this);
        
        }else
            
            System.out.println("ERROR: Case for creation of hand at side: "+side+", does not exist");
        
    }
    
    public void teleportArea(Vector3f newPlayerPos){
        
        playerNode.setLocalTranslation(newPlayerPos);
        
    }
    
    public Vector3f getAreaPosition(){
        
        return observer.getLocalTranslation();
        
    }
    
}
