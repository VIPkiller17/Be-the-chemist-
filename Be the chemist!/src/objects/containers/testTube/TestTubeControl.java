/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.containers.testTube;

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
public class TestTubeControl extends AbstractControl{
    
    private float testTPF;

    private TestTube testTube;
    
    public TestTubeControl(TestTube testTube){
        
        this.testTube=testTube;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        //SET THE STATE OF THE CONTAINER
        if(testTube.getPosition().getX()<4.72f&&testTube.getPosition().getX()>4.28f&&testTube.getPosition().getY()<0.61f&&testTube.getPosition().getY()>0&&testTube.getPosition().getZ()<3.67f&&testTube.getPosition().getZ()>4.33f){
            
            testTube.destroy();
            
        }
        
        if(!testTube.isDestroyed()){
        
            if(testTube.getSolution()!=null&&testTube.getSolution().containsStates()[1]){

                testTube.setLiquidVisible(true,testTube.getSolution().getLiquidColor());

            }else{

                testTube.setLiquidVisible(false,ColorRGBA.White);

            }

            if(testTube.getSolution()!=null&&testTube.getSolution().containsInsolubleSolid()){

                testTube.setSolidVisible(true,testTube.getSolution().getSolidColor());

            }else{

                testTube.setSolidVisible(false,ColorRGBA.White);

            }

            testTube.updateNodeState();

            if(spatial.getLocalRotation().getRotationColumn(1).getY()<0){

                testTube.getPourParticleEmitter().setVolume((((FastMath.RAD_TO_DEG*FastMath.asin(Math.abs(spatial.getLocalRotation().getRotationColumn(1).getY())))+90)/1000)*testTube.getPourParticleEmitter().getDelay());

            }else{
                
                testTube.getPourParticleEmitter().setVolume((FastMath.RAD_TO_DEG*Math.acos(spatial.getLocalRotation().getRotationColumn(1).getY())/1000)*testTube.getPourParticleEmitter().getDelay());

            }
            
            if(!testTube.isClosed()&&spatial.getLocalRotation().getRotationColumn(1).getY()<=0.707f&&!testTube.getPourParticleEmitter().isEmitting()){

                testTube.startPouring();

            }else if(testTube.isClosed()||(spatial.getLocalRotation().getRotationColumn(1).getY()>0.707f&&testTube.getPourParticleEmitter().isEmitting())){

                testTube.stopPouring();

            }

            if(!testTube.isClosed()&&!testTube.getEvaporationParticleEmitter().isEmitting()&&testTube.getSolution().containsLowDensityGas()){

                testTube.startEvaporating();

            }else if(testTube.isClosed()||(testTube.getEvaporationParticleEmitter().isEmitting()&&!testTube.getSolution().containsLowDensityGas())){

                testTube.stopEvaporating();

            }
        
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    
    
}
