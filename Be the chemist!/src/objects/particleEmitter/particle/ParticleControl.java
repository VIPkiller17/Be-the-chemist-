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
import objects.PhysicalObject;
import objects.apparatus.chemichalWasteDisposalContainer.ChemicalWasteDisposalContainer;
import objects.containers.Container;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class ParticleControl extends AbstractControl{

    private Particle particle;
    
    private CollisionResults collisionResults;
    
    private PhysicalObject presentClosestValidReceiver;
    
    public ParticleControl(Particle particle){
        
        this.particle=particle;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        if(!particle.isDestroyed()){
        
            //set particle state
            
            //set its velocity depending on acceleration
            particle.setVelocity(new Vector3f(particle.getVelocity().getX()+particle.getParticleEmitter().getAcceleration().getX()*tpf,particle.getVelocity().getY()+particle.getParticleEmitter().getAcceleration().getY()*tpf,particle.getVelocity().getZ()+particle.getParticleEmitter().getAcceleration().getZ()*tpf));

            //set its position depending on velocity
            particle.getSpatial().move(particle.getVelocity().getX()*tpf,particle.getVelocity().getY()*tpf,particle.getVelocity().getZ()*tpf);

            if(particle.getSpatial().getWorldTranslation().getY()<0||particle.getSpatial().getWorldTranslation().getY()>3){
                //if a particle hits the floor or goes through the roof, destroy it
                particle.destroy();

            }else if(particle.getState()==0&&particle.getSpatial().getLocalTranslation().getX()>1.98&&particle.getSpatial().getLocalTranslation().getX()<3.96&&particle.getSpatial().getLocalTranslation().getY()>0.98&&particle.getSpatial().getLocalTranslation().getY()<2.03&&particle.getSpatial().getLocalTranslation().getZ()>5&&particle.getSpatial().getLocalTranslation().getZ()<5.75){
                //if a gas particle is inside the fume hood, destroy it
                particle.destroy();
                
            }
            
            //This would be the correct way of implementing it, but we get a nullpointerexception on collidewith
            //even when using the bounding volume of the particle spatial instead of the spatial itself
            //The remaining possibility of the error could be that somewhere there is a spatial without mesh which could not be read by collidewith
            /*
            particle.getMain().getRootNode().collideWith(particle.getSpatial().getWorldBound(),collisionResults);
            
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
            */
            
            if(!particle.isDestroyed()&&particle.getSpatial()!=null&&!particle.getMain().getParticleReceivers().isEmpty()){
            
                presentClosestValidReceiver=particle.getMain().getParticleReceivers().get(0);

                for(int i=1;i<particle.getMain().getParticleReceivers().size();i++){
                    
                    if(particle.getParticleEmitter().getParentObject()!=particle.getMain().getParticleReceivers().get(i)){
                    
                        if(particle.getMain().getParticleReceivers().get(i) instanceof Container){

                            if(particle.getSpatial().getWorldTranslation().distance(((Container)particle.getMain().getParticleReceivers().get(i)).getSpatial().getLocalTranslation())<0.03f){

                                for(Substance s: particle.getSubstances()){

                                    System.out.println(((Container)particle.getMain().getParticleReceivers().get(i)).getSolution());
                                    
                                    ((Container)particle.getMain().getParticleReceivers().get(i)).getSolution().addSubstance(s,particle.getVolume(),particle.getTemperature());
                                }

                                particle.destroy();

                            }

                        }else if(particle.getMain().getParticleReceivers().get(i) instanceof ChemicalWasteDisposalContainer){

                            if(particle.getSpatial().getWorldTranslation().distance(((ChemicalWasteDisposalContainer)particle.getMain().getParticleReceivers().get(i)).getOpenningPosition())<0.03f){

                                particle.destroy();

                            }

                        }
                    
                    }

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
