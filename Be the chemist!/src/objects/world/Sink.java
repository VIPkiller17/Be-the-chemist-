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
import com.jme3.scene.Spatial;
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
    
    private float coldFlow,hotFlow;
    
    private double flowTemperature=298;
    
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
        
        particleEmitter=new ParticleEmitter(main,this);
        
        hotHandle=new SinkHandle(main,assetManager,0,this);
        coldHandle=new SinkHandle(main,assetManager,1,this);
        
        rootNode.attachChild(node);
        
        if(index==0){
            
            node.setLocalTranslation(-1f,0.92f,-0.29f);
        
        }else if(index==1){
            
            node.setLocalTranslation(0.19f,0.92f,-0.29f);
        }
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
    
    public Spatial getSpatial(){
        
        return null;
        
    }
    
    public void setFlow(double amount){
        
        particleEmitter.setVolume(amount);
        
    }
    
    public void addFlow(double amount){
        
        if(particleEmitter.getVolume()>0)
        
        particleEmitter.setVolume(particleEmitter.getVolume()+amount);
        
    }
    
    public void setColdFlow(float amount){
        
        //System.out.println("            Set cold flow called and temperature set to: "+((flowTemperature*((coldFlow+hotFlow)*1000))+(283.15*(amount*1000)))/((coldFlow+hotFlow)*1000));
        
        if(amount+hotFlow!=0){
        
            //System.out.println("Present flow temp: "+flowTemperature+";\nTotal flow volume in liters: "+(coldFlow+hotFlow)+";\n   In mL: "+((coldFlow+hotFlow)*1000)+";\nSet cold water volume in liters: "+amount+";\n   In mL: "+amount*1000+";\nAdded cold water volume in liters: "+(amount-coldFlow)+";\n   In mL: "+(amount-coldFlow)*1000+";\nTotal volume after modification, in liters: "+(amount+hotFlow)+";\n   In mL: "+(amount+hotFlow)*1000);
            
            flowTemperature=((flowTemperature*((coldFlow+hotFlow)*1000))+(283.15*((amount-coldFlow)*1000)))/((amount+hotFlow)*1000);
            
        }else{
            
            flowTemperature=283.15;
            
        }
        
        coldFlow=amount;
        
        particleEmitter.setVolume(coldFlow+hotFlow);
        
        if(!particleEmitter.isEmitting()&&particleEmitter.getVolume()>0){
            
            System.out.println("                Particle emitter is not emitting but its volume is higher than 0, starting emission");
            
            particleEmitter.begin();
            
        }else if(particleEmitter.isEmitting()&&particleEmitter.getVolume()<=0){
            
            System.out.println("                Particle emitter is emitting but its volume is lower or equal to 0, stopping emission");
            
            particleEmitter.stop();
            
        }
        
    }
    
    public void addToColdFlow(float amount){
        
        System.out.println("            Add to cold flow called");
        
        coldFlow+=amount;
        
        particleEmitter.setVolume(coldFlow+hotFlow);
        
        flowTemperature=((flowTemperature*(((coldFlow-amount)+hotFlow)*1000))+(283.15*(amount*1000)))/((coldFlow+hotFlow)*1000);
        
        if(!particleEmitter.isEmitting()&&particleEmitter.getVolume()>0){
            
            System.out.println("                Particle emitter is not emitting but its volume is higher than 0, starting emission");
            
            particleEmitter.begin();
            
        }else if(particleEmitter.isEmitting()&&particleEmitter.getVolume()<=0){
            
            System.out.println("                Particle emitter is emitting but its volume is lower or equal to 0, stopping emission");
            
            particleEmitter.stop();
            
        }
        
    }
    
    public void setHotFlow(float amount){
        
        if(amount+hotFlow!=0){
            
            flowTemperature=((flowTemperature*((coldFlow+hotFlow)*1000))+(328*((amount-hotFlow)*1000)))/((coldFlow+amount)*1000);
            
            System.out.println(flowTemperature);
            
        }else{
            
            flowTemperature=328;
            
        }
        
        hotFlow=amount;
        
        particleEmitter.setVolume(coldFlow+hotFlow);
        
        if(!particleEmitter.isEmitting()&&particleEmitter.getVolume()>0){
            
            particleEmitter.begin();
            
        }else if(particleEmitter.isEmitting()&&particleEmitter.getVolume()<=0){
            
            particleEmitter.stop();
            
        }
        
    }
    
    public void addToHotFlow(float amount){
        
        System.out.println("            Add to hot flow called");
        
        hotFlow+=amount;
        
        particleEmitter.setVolume(coldFlow+hotFlow);
        
        flowTemperature=((flowTemperature*(((hotFlow-amount)+coldFlow)*1000))+(283.15*(amount*1000)))/((coldFlow+hotFlow)*1000);
        
        if(!particleEmitter.isEmitting()&&particleEmitter.getVolume()>0){
            
            System.out.println("                Particle emitter is not emitting but its volume is higher than 0, starting emission");
            
            particleEmitter.begin();
            
        }else if(particleEmitter.isEmitting()&&particleEmitter.getVolume()<=0){
            
            System.out.println("                Particle emitter is emitting but its volume is lower or equal to 0, stopping emission");
            
            particleEmitter.stop();
            
        }
        
    }
    
    public double getFlowTemperature(){
        
        return flowTemperature;
        
    }
    
    @Override
    public void destroy() {
        
        main.getRootNode().detachChild(node);
        
    }
    
    @Override
    public boolean equals(Object otherSink){
        
        if(otherSink instanceof Sink)
            
            return ((Sink) otherSink).getIndex()==index;
        
        else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        return "Sink of index: "+index;
        
    }
    
}
