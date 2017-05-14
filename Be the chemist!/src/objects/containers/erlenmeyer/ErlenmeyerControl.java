/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.erlenmeyer;

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
public class ErlenmeyerControl extends AbstractControl{
    
    private float testTPF;

    private Erlenmeyer erlenmeyer;
    
    public ErlenmeyerControl(Erlenmeyer erlenmeyer){
        
        this.erlenmeyer=erlenmeyer;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        //SET THE STATE OF THE CONTAINER
        if(erlenmeyer.getPosition().getX()<4.72f&&erlenmeyer.getPosition().getX()>4.28f&&erlenmeyer.getPosition().getY()<0.61f&&erlenmeyer.getPosition().getY()>0&&erlenmeyer.getPosition().getZ()<3.67f&&erlenmeyer.getPosition().getZ()>4.33f){
            
            erlenmeyer.destroy();
            
        }
        
        if(!erlenmeyer.isDestroyed()){
        
            if(erlenmeyer.getSolution()!=null&&erlenmeyer.getSolution().containsStates()[1]){

                erlenmeyer.setLiquidVisible(true,erlenmeyer.getSolution().getLiquidColor());

            }else{

                erlenmeyer.setLiquidVisible(false,ColorRGBA.White);

            }

            if(erlenmeyer.getSolution()!=null&&erlenmeyer.getSolution().containsInsolubleSolid()){

                erlenmeyer.setSolidVisible(true,erlenmeyer.getSolution().getSolidColor());

            }else{

                erlenmeyer.setSolidVisible(false,ColorRGBA.White);

            }

            erlenmeyer.updateNodeState();

            if(spatial.getLocalRotation().getRotationColumn(1).getY()<0){

                erlenmeyer.getPourParticleEmitter().setVolume((((FastMath.RAD_TO_DEG*FastMath.asin(Math.abs(spatial.getLocalRotation().getRotationColumn(1).getY())))+90)/1000)*erlenmeyer.getPourParticleEmitter().getDelay());

            }else{
                
                erlenmeyer.getPourParticleEmitter().setVolume((FastMath.RAD_TO_DEG*Math.acos(spatial.getLocalRotation().getRotationColumn(1).getY())/1000)*erlenmeyer.getPourParticleEmitter().getDelay());

            }
            
            if(!erlenmeyer.isClosed()&&spatial.getLocalRotation().getRotationColumn(1).getY()<=0.707f&&!erlenmeyer.getPourParticleEmitter().isEmitting()){

                erlenmeyer.startPouring();

            }else if(erlenmeyer.isClosed()||(spatial.getLocalRotation().getRotationColumn(1).getY()>0.707f&&erlenmeyer.getPourParticleEmitter().isEmitting())){

                erlenmeyer.stopPouring();

            }

            if(!erlenmeyer.isClosed()&&!erlenmeyer.getEvaporationParticleEmitter().isEmitting()&&erlenmeyer.getSolution().containsLowDensityGas()){

                erlenmeyer.startEvaporating();

            }else if(erlenmeyer.isClosed()||(erlenmeyer.getEvaporationParticleEmitter().isEmitting()&&!erlenmeyer.getSolution().containsLowDensityGas())){

                erlenmeyer.stopEvaporating();

            }
        
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
