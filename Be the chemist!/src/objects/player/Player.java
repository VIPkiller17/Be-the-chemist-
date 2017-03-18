/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.player;

import com.jme3.asset.AssetManager;
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import interfaces.Describable;
import java.util.ArrayList;
import jmevr.input.VRAPI;
import main.Main;
//by Tommy
public class Player {
    
    private AssetManager assetManager;
    private Node rootNode;
    private VRAPI VRHardware;
    private CollisionResults collisionResults;
    private ArrayList<Describable> describables;
    
    private Spatial observer;
    private Hand rightHand,leftHand;
    
    private Node playerNode;
    
    private Main main;
    
    public Player(Main main,AssetManager assetManager,Node rootNode,VRAPI VRHardware,CollisionResults collisionResults,ArrayList<Describable> describables,Spatial observer){
        
        this.main=main;
        
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
            
            rightHand=new Hand(main,assetManager,rootNode,VRHardware,collisionResults,describables,0,observer,playerNode,this);
        
        }else if(side==1){
            
            leftHand=new Hand(main,assetManager,rootNode,VRHardware,collisionResults,describables,1,observer,playerNode,this);
        
        }else
            
            System.out.println("ERROR: Case for creation of hand at side: "+side+", does not exist");
        
    }
    
    public void teleportArea(Vector3f newPlayerPos){
        
        playerNode.setLocalTranslation(newPlayerPos);
        
    }
    
    public Vector3f getAreaPosition(){
        
        return observer.getLocalTranslation();
        
    }
    
    public void move(String direction,float distanceMeters){
        
        if(direction.equals("forward")){
        
            playerNode.setLocalTranslation(playerNode.getLocalTranslation().add(new Vector3f(0f,0f,distanceMeters)));
        
        }else if(direction.equals("backward")){
            
            playerNode.setLocalTranslation(playerNode.getLocalTranslation().add(new Vector3f(0f,0f,-distanceMeters)));

        }else if(direction.equals("right")){
            
            playerNode.setLocalTranslation(playerNode.getLocalTranslation().add(new Vector3f(-distanceMeters,0f,0f)));

        }else if(direction.equals("left")){
            
            playerNode.setLocalTranslation(playerNode.getLocalTranslation().add(new Vector3f(distanceMeters,0f,0f)));

        }else if(direction.equals("up")){
            
            playerNode.setLocalTranslation(playerNode.getLocalTranslation().add(new Vector3f(0f,distanceMeters,0f)));

        }else if(direction.equals("downControl")||direction.equals("downShift")){
            
            playerNode.setLocalTranslation(playerNode.getLocalTranslation().add(new Vector3f(0f,-distanceMeters,0f)));

        }else{
            
            System.out.println("Direction code \""+direction+"\" invalid.");
            
        }
        
    }
    
}
