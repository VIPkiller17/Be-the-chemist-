/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter;

import com.jme3.asset.AssetManager;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import main.Main;
import objects.PhysicalObject;
import objects.apparatus.distilledWaterContainer.DistilledWaterContainer;
import objects.containers.Container;
import objects.containers.beaker.Beaker;
import objects.containers.erlenmeyer.Erlenmeyer;
import objects.containers.gasSac.GasSac;
import objects.containers.testTube.TestTube;
import objects.particleEmitter.particle.Particle;
import objects.solution.Solution;
import objects.substance.Substance;
import objects.world.Sink;

/**
 *
 * @author VIPkiller17
 */
public class ParticleEmitter {
    
    private PhysicalObject parentObject;
    
    private Vector3f position;
    private Vector3f outDirection;
    private Quaternion randomOutDirectionOffset;
    private double randomHorizontalOutputOffset;
    private double randomVerticalOutputOffset;
    private Vector3f initialVelocity;
    private Vector3f randomInitialVelocityOffset;
    private double delay;
    private double randomDelayOffset;
    private Vector3f acceleration;
    private Vector3f randomAccelerationOffset;
    private String gasParticleModelPath,liquidParticleModelPath,solidParticleModelPath;
    
    private boolean emitting;
    
    private ParticleEmitterControl control;
    
    private Node node;
    
    private Main main;
    
    private double volume;
    
    private String name;
    
    public ParticleEmitter(Main main,PhysicalObject parentObject,Vector3f position,Vector3f outDirection,Quaternion randomOutDirectionOffset,double randomHorizontalOutputOffset,double randomVerticalOutputOffset,Vector3f initialVelocity,Vector3f randomInitialVelocityOffset,double delay,double randomDelayOffset,Vector3f acceleration,Vector3f randomAccelerationOffset,String name){
        
        this.main=main;
        this.parentObject=parentObject;
        this.position=position;
        this.outDirection=outDirection;
        this.randomOutDirectionOffset=randomOutDirectionOffset;
        this.randomHorizontalOutputOffset=randomHorizontalOutputOffset;
        this.randomVerticalOutputOffset=randomVerticalOutputOffset;
        this.initialVelocity=initialVelocity;
        this.randomInitialVelocityOffset=randomInitialVelocityOffset;
        this.delay=delay;
        this.randomDelayOffset=randomDelayOffset;
        this.acceleration=acceleration;
        this.randomAccelerationOffset=randomAccelerationOffset;
        this.name=name;
        
        gasParticleModelPath="Models/Particles/Gas/Gas.j3o";
        liquidParticleModelPath="Models/Particles/Liquid/Liquid.j3o";
        solidParticleModelPath="Models/Particles/Solid/Solid.j3o";
        
        node=new Node();
        
        control=new ParticleEmitterControl(this);
        
        node.addControl(control);
        
        parentObject.getNode().attachChild(node);
        
        node.setLocalTranslation(position);
        
    }
    
    public ParticleEmitter(Main main,PhysicalObject parentObject){
        
        this.main=main;
        this.parentObject=parentObject;
        
        if(parentObject instanceof DistilledWaterContainer){
                    
            this.position=new Vector3f(-0.37f,-0.39f,0f);
            this.outDirection=new Vector3f(0,0,0);
            this.randomOutDirectionOffset=new Quaternion().fromAngleAxis(0,Vector3f.UNIT_XYZ);
            this.randomHorizontalOutputOffset=0;
            this.randomVerticalOutputOffset=0;
            this.initialVelocity=new Vector3f(0,-0.5f,0);
            this.randomInitialVelocityOffset=new Vector3f(0,0,0);
            this.delay=0.1;
            this.randomDelayOffset=0;
            this.acceleration=new Vector3f(0,-9.806f,0);
            this.randomAccelerationOffset=new Vector3f(0,0,0);
            this.name="DistilledWaterContainer's particle emitter";
            
            System.out.println("DistilledWaterContainer's particle emitter created");
            
        }else if(parentObject instanceof Sink){
                    
            if(((Sink) parentObject).getIndex()==0){
                
                this.position=new Vector3f(0.12f,0.2f,0f);
                
            }else{
                
                this.position=new Vector3f(-0.12f,0.2f,0f);
                
            }
            
            this.outDirection=new Vector3f(0,0,0);
            this.randomOutDirectionOffset=new Quaternion().fromAngleAxis(0,Vector3f.UNIT_XYZ);
            this.randomHorizontalOutputOffset=0;
            this.randomVerticalOutputOffset=0;
            this.initialVelocity=new Vector3f(0,-0.5f,0);
            this.randomInitialVelocityOffset=new Vector3f(0,0,0);
            this.delay=0.1;
            this.randomDelayOffset=0;
            this.acceleration=new Vector3f(0,-9.806f,0);
            this.randomAccelerationOffset=new Vector3f(0,0,0);
            this.name="Sink'ss particle emitter";
            
            System.out.println("Sink #"+((Sink) parentObject).getIndex()+"'s particle emitter created");
            
        }
        
        node=new Node();
        
        control=new ParticleEmitterControl(this);
        
        node.addControl(control);
        
        parentObject.getNode().attachChild(node);
        
        node.setLocalTranslation(position);
        
    }
    
    public void setPosition(Vector3f position){
        
        this.position=position;
        
    }
    
    public Vector3f getPosition(){
        
        return position;
        
    }
    
    public void setOutDirection(Vector3f outDirection){
        
        this.outDirection=outDirection;
        
    }
    
    public Vector3f getOutDirection(){
        
        return outDirection;
        
    }
    
    public void setRandomOutDirectionOffset(Quaternion randomOutDirectionOffset){
        
        this.randomOutDirectionOffset=randomOutDirectionOffset;
        
    }
    
    public Quaternion getRandomOutDirectionOffset(){
        
        return randomOutDirectionOffset;
        
    }
    
    public void setRandomHorizontalOutputOffset(double randomHorizontalOutputOffset){
        
        this.randomHorizontalOutputOffset=randomHorizontalOutputOffset;
        
    }
    
    public double getRandomHorizontalOutputOffset(){
        
        return randomHorizontalOutputOffset;
        
    }
    
    public void setRandomVerticalOutputOffset(double randomVerticalOutputOffset){
        
        this.randomVerticalOutputOffset=randomVerticalOutputOffset;
        
    }
    
    public double getRandomVerticalOutputOffset(){
        
        return randomVerticalOutputOffset;
        
    }
    
    public void setInitialVelocity(Vector3f initialVelocity){
        
        this.initialVelocity=initialVelocity;
        
    }
    
    public Vector3f getInitialVelocity(){
        
        return initialVelocity;
        
    }
    
    public void setRandomInitialVelocityOffset(Vector3f randomInitialVelocityOffset){
        
        this.randomInitialVelocityOffset=randomInitialVelocityOffset;
        
    }
    
    public Vector3f getRandomInitialVelocityOffset(){
        
        return randomInitialVelocityOffset;
        
    }
    
    public void setDelay(double delay){
        
        this.delay=delay;
        
    }
    
    public double getDelay(){
        
        return delay;
        
    }
    
    public void setRandomDelayoffset(double randomDelayOffset){
        
        this.randomDelayOffset=randomDelayOffset;
        
    }
    
    public double getRandomDelayOffset(){
        
        return randomDelayOffset;
        
    }
    
    public void setAcceleration(Vector3f acceleration){
        
        this.acceleration=acceleration;
        
    }
    
    public Vector3f getAcceleration(){
        
        return acceleration;
        
    }
    
    public void setrandomAccelerationOffset(Vector3f randomAccelerationOffset){
        
        this.randomAccelerationOffset=randomAccelerationOffset;
        
    }
    
    public Vector3f getRandomAccelerationOffset(){
        
        return randomAccelerationOffset;
        
    }
    
    public void setGasParticleModelPath(String gasParticleModelPath){
        
        this.gasParticleModelPath=gasParticleModelPath;
        
    }
    
    public String getGasParticleModelPath(){
        
        return gasParticleModelPath;
        
    }
    
    public void setLiquidParticleModelPath(String liquidParticleModelPath){
        
        this.liquidParticleModelPath=liquidParticleModelPath;
        
    }
    
    public String getLiquidParticleModelPath(){
        
        return liquidParticleModelPath;
        
    }
    
    public void setSolidParticleModelPath(String solidParticleModelPath){
        
        this.solidParticleModelPath=solidParticleModelPath;
        
    }
    
    public String getSolidParticleModelPath(){
        
        return solidParticleModelPath;
        
    }
    
    public void begin(){
        
        //System.out.println("Beginning emission");
        
        emitting=true;
        
        //System.out.println("Emitting set to true");
        
    }
    
    public void stop(){
        
        emitting=false;
        
        //System.out.println(name+"'s emitting set to false");
        
    }
    
    public boolean isEmitting(){
        
        return emitting;
        
    }
    
    public void emit(){
        
        //System.out.println("Emit has been called");
        
        if(parentObject instanceof Container&&!((Container)parentObject).getSolution().getSubstances().isEmpty()){
            
            //System.out.println("    the parentobject is a container and the colution is not empty, its volume is: "+((Container)parentObject).getSolution().getVolume()+" and the volume of the particle would be: "+volume);
                
            if(parentObject instanceof Beaker){
            
                if(this.equals(((Beaker)parentObject).getPourParticleEmitter())){
                
                    if(((Container)parentObject).getSolution().getPourableVolume()>volume){

                        //System.out.println("        Creating new particle for a container");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Liquid/Liquid.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getPourables(),volume,((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setPourableVolume(((Container)parentObject).getSolution().getPourableVolume()-volume);

                    }else if(((Container)parentObject).getSolution().getPourableVolume()<volume&&((Container)parentObject).getSolution().getPourableVolume()!=0){

                        //System.out.println("        Creating new particle for a container");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Liquid/Liquid.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getPourables(),((Container)parentObject).getSolution().getPourableVolume(),((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setPourableVolume(0);

                    }
                
                }else if(this.equals(((Beaker)parentObject).getEvaporationParticleEmitter())){
                    
                    //System.out.println("Emitter is the beaker's evaporation emitter, the evaporatable volume is: "+((Container)parentObject).getSolution().getEvaporatableVolume()+" and volume is: "+volume);
                
                    if(((Container)parentObject).getSolution().getEvaporatableVolume()>volume){
                        
                        //System.out.println("There is more evaporatable volume in the beaker than there should be in the particle, making a particle of given volume...");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getEvaporatables(),volume,((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setEvaporatableVolume(((Container)parentObject).getSolution().getEvaporatableVolume()-volume);

                    }else if(((Container)parentObject).getSolution().getEvaporatableVolume()!=0&&((Container)parentObject).getSolution().getEvaporatableVolume()<volume){

                        //System.out.println("There is less evaporatable voume in the beaker than the particle volume should be, making particle with remaining evaporatable volume...");
                        
                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getEvaporatables(),((Container)parentObject).getSolution().getEvaporatableVolume(),((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setEvaporatableVolume(0);

                    }
                
                }
            
            }else if(parentObject instanceof GasSac){
                
                //System.out.println("The emitter's parentobject is a gassac, is this its pouremitter?: "+this.equals(((GasSac)parentObject).getPourParticleEmitter()));
            
                if(this.equals(((GasSac)parentObject).getPourParticleEmitter())){
                    
                    //System.out.println("    it is, is volume: "+volume+" higher than the pourable volume in the gas sac which is: "+((Container)parentObject).getSolution().getPourableVolume()+",?: "+(((Container)parentObject).getSolution().getPourableVolume()>volume));
                
                    if(((Container)parentObject).getSolution().getPourableVolume()>volume){

                        //System.out.println("        Creating new particle for a container");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getPourables(),volume,((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setPourableVolume(((Container)parentObject).getSolution().getPourableVolume()-volume);

                    }else if(((Container)parentObject).getSolution().getPourableVolume()<volume&&((Container)parentObject).getSolution().getPourableVolume()!=0){

                        //System.out.println("        Creating new particle for a container");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getPourables(),((Container)parentObject).getSolution().getPourableVolume(),((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setPourableVolume(0);

                    }
                
                }else if(this.equals(((GasSac)parentObject).getEvaporationParticleEmitter())){
                    
                    //System.out.println("Emitter is the beaker's evaporation emitter, the evaporatable volume is: "+((Container)parentObject).getSolution().getEvaporatableVolume()+" and volume is: "+volume);
                
                    if(((Container)parentObject).getSolution().getEvaporatableVolume()>volume){
                        
                        //System.out.println("There is more evaporatable volume in the beaker than there should be in the particle, making a particle of given volume...");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getEvaporatables(),volume,((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setEvaporatableVolume(((Container)parentObject).getSolution().getEvaporatableVolume()-volume);

                    }else if(((Container)parentObject).getSolution().getEvaporatableVolume()!=0&&((Container)parentObject).getSolution().getEvaporatableVolume()<volume){

                        //System.out.println("There is less evaporatable voume in the beaker than the particle volume should be, making particle with remaining evaporatable volume...");
                        
                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getEvaporatables(),((Container)parentObject).getSolution().getEvaporatableVolume(),((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setEvaporatableVolume(0);

                    }
                
                }
            
            }else if(parentObject instanceof TestTube){
            
                if(this.equals(((TestTube)parentObject).getPourParticleEmitter())){
                
                    if(((Container)parentObject).getSolution().getPourableVolume()>volume){

                        //System.out.println("        Creating new particle for a container");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Liquid/Liquid.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getPourables(),volume,((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setPourableVolume(((Container)parentObject).getSolution().getPourableVolume()-volume);

                    }else if(((Container)parentObject).getSolution().getPourableVolume()<volume&&((Container)parentObject).getSolution().getPourableVolume()!=0){

                        //System.out.println("        Creating new particle for a container");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Liquid/Liquid.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getPourables(),((Container)parentObject).getSolution().getPourableVolume(),((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setPourableVolume(0);

                    }
                
                }else if(this.equals(((TestTube)parentObject).getEvaporationParticleEmitter())){
                    
                    //System.out.println("Emitter is the beaker's evaporation emitter, the evaporatable volume is: "+((Container)parentObject).getSolution().getEvaporatableVolume()+" and volume is: "+volume);
                
                    if(((Container)parentObject).getSolution().getEvaporatableVolume()>volume){
                        
                        //System.out.println("There is more evaporatable volume in the beaker than there should be in the particle, making a particle of given volume...");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getEvaporatables(),volume,((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setEvaporatableVolume(((Container)parentObject).getSolution().getEvaporatableVolume()-volume);

                    }else if(((Container)parentObject).getSolution().getEvaporatableVolume()!=0&&((Container)parentObject).getSolution().getEvaporatableVolume()<volume){

                        //System.out.println("There is less evaporatable voume in the beaker than the particle volume should be, making particle with remaining evaporatable volume...");
                        
                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getEvaporatables(),((Container)parentObject).getSolution().getEvaporatableVolume(),((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setEvaporatableVolume(0);

                    }
                
                }
            
            }else if(parentObject instanceof Erlenmeyer){
            
                if(this.equals(((Erlenmeyer)parentObject).getPourParticleEmitter())){
                
                    if(((Container)parentObject).getSolution().getPourableVolume()>volume){

                        //System.out.println("        Creating new particle for a container");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Liquid/Liquid.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getPourables(),volume,((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setPourableVolume(((Container)parentObject).getSolution().getPourableVolume()-volume);

                    }else if(((Container)parentObject).getSolution().getPourableVolume()<volume&&((Container)parentObject).getSolution().getPourableVolume()!=0){

                        //System.out.println("        Creating new particle for a container");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Liquid/Liquid.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getPourables(),((Container)parentObject).getSolution().getPourableVolume(),((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setPourableVolume(0);

                    }
                
                }else if(this.equals(((Erlenmeyer)parentObject).getEvaporationParticleEmitter())){
                    
                    //System.out.println("Emitter is the beaker's evaporation emitter, the evaporatable volume is: "+((Container)parentObject).getSolution().getEvaporatableVolume()+" and volume is: "+volume);
                
                    if(((Container)parentObject).getSolution().getEvaporatableVolume()>volume){
                        
                        //System.out.println("There is more evaporatable volume in the beaker than there should be in the particle, making a particle of given volume...");

                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getEvaporatables(),volume,((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setEvaporatableVolume(((Container)parentObject).getSolution().getEvaporatableVolume()-volume);

                    }else if(((Container)parentObject).getSolution().getEvaporatableVolume()!=0&&((Container)parentObject).getSolution().getEvaporatableVolume()<volume){

                        //System.out.println("There is less evaporatable voume in the beaker than the particle volume should be, making particle with remaining evaporatable volume...");
                        
                        control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Gas/Gas.j3o",((Container)parentObject).getSolution().getMostCommonState(),((Container)parentObject).getSolution().getEvaporatables(),((Container)parentObject).getSolution().getEvaporatableVolume(),((Container)parentObject).getSolution().getTemperature()));

                        ((Container)parentObject).getSolution().setEvaporatableVolume(0);

                    }
                
                }
            
            }
            
        }else if(parentObject instanceof DistilledWaterContainer){
            
            //System.out.println("Creating new particle for DsitilledWaterContainer");
            
            control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Liquid/Liquid.j3o",1,main.getSubstances().get(44),0.001,298));
            
        }else if(parentObject instanceof Sink){
            
            //System.out.println("Creating new particle for Sink");
            
            //here water is added to the solution of the particle instead of just using the substance directly
            //this is used as a precursor on how the tap water will later on be composed of multiple substances instead of just one
            //but only water is added right now because the values arent exact and most of the substances are not yet implemented in the game
            control.getActiveParticles().add(new Particle(main,this,"Models/Particles/Liquid/Liquid.j3o",1,new Solution(main,null,null,0,0).addSubstance(main.getSubstances().get(44),0.001,((Sink)parentObject).getFlowTemperature()),volume));
            
        }
        
        
    }
    
    public PhysicalObject getParentObject(){
        
        return parentObject;
        
    }
    
    public Container ifParentObjectContainerGetIt(){
        
        if(parentObject instanceof Container)
            
            return ((Container)parentObject);
        
        else 
            
            return null;
        
    }
    
    public AssetManager getAssetManager(){
        
        return main.getAssetManager();
        
    }
    
    public ParticleEmitterControl getControl(){
        
        return control;
        
    }
    
    public Node getNode(){
        
        return node;
        
    }
    
    public String getName(){
        
        return name;
        
    }
    
    @Override
    public boolean equals(Object otherParticleEmitter){
        
        if(otherParticleEmitter instanceof ParticleEmitter)
            
            return ((ParticleEmitter) otherParticleEmitter).getName().equals(name);
            
        else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        return "ParticleEmitter of object: "+parentObject.getDescription();
        
    }
    
    public void setVolume(double volume){
        
        this.volume=volume;
        
    }
    
    public double getVolume(){
        
        return volume;
        
    }
    
}
