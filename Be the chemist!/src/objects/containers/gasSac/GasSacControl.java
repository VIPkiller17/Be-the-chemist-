/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.gasSac;

import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import jmevr.app.VRApplication;

/**
 *
 * @author VIPkiller17
 */
public class GasSacControl extends AbstractControl{

    private GasSac gasSac;
    
    public GasSacControl(GasSac gasSac){
        
        this.gasSac=gasSac;
        
    }
    
    @Override
    protected void controlUpdate(float f) {
        
        //SET THE STATE OF THE CONTAINER
        
        //gasSac.setTemperature(beaker.getSolution().getTemperature());
        
        if(gasSac.getPosition().getX()<4.72f&&gasSac.getPosition().getX()>4.28f&&gasSac.getPosition().getY()<0.61f&&gasSac.getPosition().getY()>0&&gasSac.getPosition().getZ()<3.67f&&gasSac.getPosition().getZ()>4.33f){
            
            gasSac.destroy();
            
        }
        
        if(!gasSac.isDestroyed()){
        
            gasSac.updateNodeState();

            if(spatial.getLocalRotation().getRotationColumn(1).getY()<0){

                gasSac.getPourParticleEmitter().setVolume((((FastMath.RAD_TO_DEG*FastMath.asin(Math.abs(spatial.getLocalRotation().getRotationColumn(1).getY())))+90)/1000)*gasSac.getPourParticleEmitter().getDelay());

            }else{

                gasSac.getPourParticleEmitter().setVolume((FastMath.RAD_TO_DEG*Math.acos(spatial.getLocalRotation().getRotationColumn(1).getY())/1000)*gasSac.getPourParticleEmitter().getDelay());

            }

            if(gasSac.isOpenned()){

                //System.out.println("Gas sac is openned, evap emitting: "+gasSac.getEvaporationParticleEmitter().isEmitting()+", solution contains low density gas: "+gasSac.getSolution().containsLowDensityGas());
                //System.out.println("pourParticleEMitter emitting: "+gasSac.getPourParticleEmitter().isEmitting()+", its volume: "+gasSac.getPourParticleEmitter().getVolume()+", its angle: "+spatial.getLocalRotation().getRotationColumn(1).getY()+", and is that lower than 0.707: "+(spatial.getLocalRotation().getRotationColumn(1).getY()<=0.707f));

                if(!gasSac.getEvaporationParticleEmitter().isEmitting()&&gasSac.getSolution().containsLowDensityGas()){

                    gasSac.startEvaporation();

                }

                if(spatial.getLocalRotation().getRotationColumn(1).getY()<=0.707f&&!gasSac.getPourParticleEmitter().isEmitting()){

                    gasSac.startPouring();

                }else if(spatial.getLocalRotation().getRotationColumn(1).getY()>0.707f&&gasSac.getPourParticleEmitter().isEmitting()){

                    gasSac.stopPouring();

                }

            }else{

                gasSac.stopEvaporation();
                gasSac.stopPouring();

            }

            //ACT BASED ON THE STATE OF THE CONTAINER

            //if the temperature of the container is too high
            if(gasSac.getTemperature()>gasSac.getMaxTemperature()){

                gasSac.meltDown();

            }
        
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
