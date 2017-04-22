/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.particleEmitter.particle;

import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import objects.containers.Container;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class ParticleControl extends AbstractControl{

    private Particle particle;
    
    private CollisionResults collisionResults;
    
    public ParticleControl(Particle particle){
        
        this.particle=particle;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        if(!particle.isDestroyed()){
        
            //set particle state
            particle.setVelocity(new Vector3f(particle.getVelocity().getX()+particle.getParticleEmitter().getAcceleration().getX()*tpf,particle.getVelocity().getY()+particle.getParticleEmitter().getAcceleration().getY()*tpf,particle.getVelocity().getZ()+particle.getParticleEmitter().getAcceleration().getZ()*tpf));

            particle.getSpatial().move(particle.getVelocity().getX()*tpf,particle.getVelocity().getY()*tpf,particle.getVelocity().getZ()*tpf);

            if(particle.getSpatial().getWorldTranslation().getY()<0){

                particle.destroy();

            }
            
            particle.getMain().getRootNode().collideWith(particle.getSpatial(),collisionResults);
            
            for(int i=0;i<collisionResults.size();i++){
                
                if(collisionResults.getCollision(i).getGeometry().getName().contains("openning surface")&&collisionResults.getCollision(i).getGeometry().getUserData("CorrespondingObject")!=null&&collisionResults.getCollision(i).getGeometry().getUserData("CorrespondingObject") instanceof Container){
                    
                    for(Substance s: particle.getSubstances()){
                    
                        ((Container)collisionResults.getCollision(i).getGeometry().getUserData("CorrespondingObject")).getSolution().addSubstance(s,particle.getVolume(),particle.getTemperature());
                    }
                    
                    particle.destroy();
                    
                    break;
                    
                }else if(collisionResults.getCollision(i).getGeometry().getParent().getName().contains("openning surface")){
                    
                    for(Substance s: particle.getSubstances()){
                    
                        ((Container)collisionResults.getCollision(i).getGeometry().getParent().getUserData("CorrespondingObject")).getSolution().addSubstance(s,particle.getVolume(),particle.getTemperature());
                    }
                    
                    particle.destroy();
                    
                    break;
                    
                }
                
            }
            
            
        }else{
            
            particle=null;
            
            this.setEnabled(false);
            
        }
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
}
