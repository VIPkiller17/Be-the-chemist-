/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.beaker;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 *
 * @author VIPkiller17
 */
public class BeakerControl extends AbstractControl{
    
    private float testTPF;

    private Beaker beaker;
    
    public BeakerControl(Beaker beaker){
        
        this.beaker=beaker;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        //TESTING
        
        //System.out.println("Beaker's description: "+beaker.getDescription()+"\n\n");
        
        //System.out.println("Height of angle of beakre rotation: "+spatial.getLocalRotation().getRotationColumn(1).getY()+" and angle is smaller than 0.707: "+(spatial.getLocalRotation().getRotationColumn(1).getY()<=0.707f)+" and beaker is emitting: "+beaker.isEmitting());
        
        //System.out.println("Beaker's position: "+beaker.getPosition()+"\nBeaker's velocity: "+beaker.getBeaker().getControl(RigidBodyControl.class).getLinearVelocity());
        
        //SET THE STATE OF THE CONTAINER
        if(beaker.getPosition().getX()<4.72f&&beaker.getPosition().getX()>4.28f&&beaker.getPosition().getY()<0.61f&&beaker.getPosition().getY()>0&&beaker.getPosition().getZ()<3.67f&&beaker.getPosition().getZ()>4.33f){
            
            beaker.destroy();
            
        }
        
        if(!beaker.isDestroyed()){
        
            if(beaker.getSolution()!=null&&beaker.getSolution().containsStates()[1]){

                //System.out.println("beaker contains a liquid, updating color to "+beaker.getSolution().getLiquidColor());

                beaker.setLiquidVisible(true,beaker.getSolution().getLiquidColor());

            }else{

                beaker.setLiquidVisible(false,ColorRGBA.White);

            }

            if(beaker.getSolution()!=null&&beaker.getSolution().containsInsolubleSolid()){

                //System.out.println("beaker contains a solid, updating color...");

                beaker.setSolidVisible(true,beaker.getSolution().getSolidColor());

            }else{

                beaker.setSolidVisible(false,ColorRGBA.White);

            }

            beaker.updateNodeState();

            //This should be done by the angle, the more tilted is the container the less it emits from evaporation
            //beaker.getEvaporationParticleEmitter().setVolume(0.001);

            if(spatial.getLocalRotation().getRotationColumn(1).getY()<0){

                //System.out.println("y angle height smaller than 0and angle in degrees is: "+((FastMath.RAD_TO_DEG*FastMath.asin(Math.abs(spatial.getLocalRotation().getRotationColumn(1).getY())))+90)+", setting volume to: "+(((FastMath.RAD_TO_DEG*FastMath.asin(Math.abs(spatial.getLocalRotation().getRotationColumn(1).getY())))+90)/100)*beaker.getPourParticleEmitter().getDelay());

                beaker.getPourParticleEmitter().setVolume((((FastMath.RAD_TO_DEG*FastMath.asin(Math.abs(spatial.getLocalRotation().getRotationColumn(1).getY())))+90)/1000)*beaker.getPourParticleEmitter().getDelay());

                //beaker.getEvaporationParticleEmitter().setVolume();

            }else{

                //System.out.println("y angle height greater than 0 and angle in degrees is: "+FastMath.RAD_TO_DEG*FastMath.acos(spatial.getLocalRotation().getRotationColumn(1).getY())+", setting volume to: "+(FastMath.RAD_TO_DEG*Math.acos(spatial.getLocalRotation().getRotationColumn(1).getY())/100)*beaker.getPourParticleEmitter().getDelay());

                beaker.getPourParticleEmitter().setVolume((FastMath.RAD_TO_DEG*Math.acos(spatial.getLocalRotation().getRotationColumn(1).getY())/1000)*beaker.getPourParticleEmitter().getDelay());

                //beaker.getEvaporationParticleEmitter().setVolume();

            }
            //beaker.setTemperature(beaker.getSolution().getTemperature());

            //ACT BASED ON THE STATE OF THE CONTAINER

            //if container is rotated 45 degrees to one side, start particle emission
            //if the y (height/sin) of the angle between the object's y axis and the world y axis is lower than 0.707 (angle higher than 45 degrees)
            //start emitting
            if(spatial.getLocalRotation().getRotationColumn(1).getY()<=0.707f&&!beaker.getPourParticleEmitter().isEmitting()){

                //System.out.println("*Beaker is now emitting particles*");

                beaker.startPouring();

            }else if(spatial.getLocalRotation().getRotationColumn(1).getY()>0.707f&&beaker.getPourParticleEmitter().isEmitting()){

                //System.out.println("*Beaker is not emitting particles*");

                beaker.stopPouring();

            }

            if(!beaker.getEvaporationParticleEmitter().isEmitting()&&beaker.getSolution().containsLowDensityGas()){

                System.out.println("            Beaker contains a gas with density lower than air's and the evaporation emitter is not already emitting, starting emission");

                beaker.startEvaporating();

            }else if(beaker.getEvaporationParticleEmitter().isEmitting()&&!beaker.getSolution().containsLowDensityGas()){

                beaker.stopEvaporating();

            }

            //if the temperature of the container is too high
            /*
            if(beaker.getTemperature()>beaker.getMaxTemperature()){

                beaker.meltDown();

            }
            */
        
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
}
