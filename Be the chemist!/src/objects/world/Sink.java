/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
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
            
            node.setLocalTranslation(2.75f,0.92f,4.85f);
            particleEmitter=new ParticleEmitter(main,this,new Vector3f(0.02f, 0.02f, 0.02f),new Vector3f(0,-1,0),new Quaternion(0,0,0,0),0,0,new Vector3f(0,0,0),new Vector3f(0,0,0),0.1,0.01,new Vector3f(0,-9.806f,0),new Vector3f(0,0,0));
        
            //Position testCube
            Box testCube = new Box(0.02f, 0.02f, 0.02f); 
            Geometry testCubeGeom = new Geometry("Analytical Balance Display", testCube);
            Material testCubeMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            testCubeMat.setColor("Color", ColorRGBA.Red);
            testCubeGeom.setMaterial(testCubeMat);
            testCubeMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            testCubeGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
            testCubeGeom.rotate(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*270, Vector3f.UNIT_Y));
            testCubeGeom.rotate(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*-60, Vector3f.UNIT_X));
            testCubeGeom.setUserData("correctCollision",true);
            testCubeGeom.setUserData("correspondingObject", this);
            testCubeGeom.setLocalTranslation(new Vector3f(0.12f,0.21f,0));
            node.attachChild(testCubeGeom);
            
        }else if(index==1){
            
            node.setLocalTranslation(3.339f,0.92f,4.84f);
            particleEmitter=new ParticleEmitter(main,this,new Vector3f(-0.5f,0.21f,0.01f),new Vector3f(0,-1,0),new Quaternion(0,0,0,0),0,0,new Vector3f(0,0,0),new Vector3f(0,0,0),0.1,0.01,new Vector3f(0,-9.806f,0),new Vector3f(0,0,0));
            
            //Position testCube
            Box testCube = new Box(0.02f, 0.02f, 0.02f); 
            Geometry testCubeGeom = new Geometry("Analytical Balance Display", testCube);
            Material testCubeMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
            testCubeMat.setColor("Color", ColorRGBA.Yellow);
            testCubeGeom.setMaterial(testCubeMat);
            testCubeMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            testCubeGeom.setQueueBucket(RenderQueue.Bucket.Transparent);
            testCubeGeom.rotate(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*270, Vector3f.UNIT_Y));
            testCubeGeom.rotate(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*-60, Vector3f.UNIT_X));
            testCubeGeom.setUserData("correctCollision",true);
            testCubeGeom.setUserData("correspondingObject", this);
            testCubeGeom.setLocalTranslation(new Vector3f(-0.5f,0.21f,0.01f));
            node.attachChild(testCubeGeom);

            node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.DEG_TO_RAD*180, Vector3f.UNIT_Y));
                
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
    
    @Override
    public void setPos(Vector3f position) {
        
        node.setLocalTranslation(position);
        
    }
    
    @Override
    public String getName() {
        
        return "Sink";
        
    }
    
}
