/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.solution;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import java.util.ArrayList;
import java.util.Collection;
import main.Main;
import objects.ion.Ion;

/**
 *
 * @author VIPkiller17
 */
public class SolutionControl extends AbstractControl{
    
    private Solution solution;
    
    private double presentVolume;
    private double percentageOfPartialVolume;
    private double presentTemperature;
    
    private Main main;
    
    private ArrayList<Ion> presentIonList=new ArrayList<>();
    private int[] presentIonicInfo0=new int[3];
    private int[] presentIonicInfo1=new int[3];
    private int presentCorrectIndex;
    private boolean foundInReactivitySeries;
    
    public SolutionControl(Main main,Solution solution){
        
        this.main=main;
        this.solution=solution;
        
    }
    
    @Override
    protected void controlUpdate(float tpf) {
        
        //account for H2O2 decomposition
        //account for H2CO3 decomposition
        
        if(solution.getTemperature()>298&&solution.getTemperature()-tpf/2>=298){
            
            solution.setTemperature(solution.getTemperature()-tpf/2);
            
        }else if(solution.getTemperature()>298&&solution.getTemperature()-tpf/2<298){
            
            solution.setTemperature(298);
            
        }else if(solution.getTemperature()<298&&solution.getTemperature()+tpf/2<=298){
            
            solution.setTemperature(solution.getTemperature()+tpf/2);
            
        }else if(solution.getTemperature()<298&&solution.getTemperature()+tpf/2>298){
            
            solution.setTemperature(298);
            
        }
        
        //System.out.println(solution.toString());
        
        //System.out.println("TOTAL VOLUME BEFORE CHEMISTRY PROCESS: "+solution.getVolume());
        
        //CHEMISTRY
        for(int i=0;i<solution.getSubstances().size();i++){
            
            for(int j=i+1;j<solution.getSubstances().size();j++){
                
                if(solution.getVolume(i)>0.001&&solution.getVolume(j)>0.001){
                
                    //System.out.println("Checking substances "+solution.getSubstance(i).getName()+" which is "+solution.getSubstances().get(i).getType()+" and "+solution.getSubstance(j).getName()+" which is "+solution.getSubstances().get(j).getType());

                    //HALOGEN DISPLACEMENT
                    if(solution.getSubstances().get(i).getTypeInteger()==3&&solution.getSubstances().get(j).getTypeInteger()==4){//if 0 is halogen and 1 is salt

                        //System.out.println("Solution 1 is halogen ");

                        //in a salt, the second ion in the ion list is the halide
                        if(solution.getSubstances().get(i).getIons().get(0).getElements().get(0).getNumber()<solution.getSubstances().get(j).getIons().get(1).getElements().get(0).getNumber()){

                            solution.setVolume(i,solution.getVolume(i)-0.001);
                            solution.setVolume(j,solution.getVolume(j)-0.001);

                            //the process to add the new salt to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of the new salt
                            //the first ion in the salt, which is first in new salt
                            presentIonList.add(solution.getSubstances().get(j).getIons().get(0));
                            //the first ion in the halogen, which is ssecond in the new salt
                            presentIonList.add(solution.getSubstances().get(i).getIons().get(0));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                            //the process to add the new halogen to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of the new halogen
                            presentIonList.add(solution.getSubstances().get(j).getIons().get(1));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                        }

                    }else if(solution.getSubstances().get(i).getTypeInteger()==4&&solution.getSubstances().get(j).getTypeInteger()==3){//if 0 is salt and 1 is halogen

                        //System.out.println("Solution 1 is halogen ");

                        //in a salt, the second ion in the ion list is the halide
                        //if salt's second ion is a halide with higher number than the halogen
                        if(solution.getSubstances().get(j).getIons().get(0).getElements().get(0).getNumber()<solution.getSubstances().get(i).getIons().get(1).getElements().get(0).getNumber()){

                            solution.setVolume(i,solution.getVolume(i)-0.001);
                            solution.setVolume(j,solution.getVolume(j)-0.001);

                            //the process to add the new salt to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of the new salt
                            //the first ion in the salt, also the first ion in the new salt
                            presentIonList.add(solution.getSubstances().get(i).getIons().get(0));
                            //first ion in the halogen, second ion in new salt
                            presentIonList.add(solution.getSubstances().get(j).getIons().get(0));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                            //the process to add the new halogen to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of the new halogen
                            //second ion in salt, first ion in new halogen
                            presentIonList.add(solution.getSubstances().get(i).getIons().get(1));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                        }

                    }
                    //END OF HALOGEN DISPLACEMENT

                    //System.out.println("TOTAL VOLUME AFTER HALOGEN DISPLACEMENT CHEMISTRY PROCESS: "+solution.getVolume());

                    //HYDROGEN AND METAL DISPLACEMENT AND PRECIPITATION
                    presentIonicInfo0=solution.getSubstances().get(i).containsReactiveIon();
                    presentIonicInfo1=solution.getSubstances().get(j).containsReactiveIon();

                    //System.out.println("Hydrogen and metal displacement start on sustances 1.: "+solution.getSubstances().get(i).getName()+" with ionic info: "+presentIonicInfo0[0]+", "+presentIonicInfo0[1]+", "+presentIonicInfo0[2]+" and 2.: "+solution.getSubstances().get(j)+" with ionic info: "+presentIonicInfo1[0]+", "+presentIonicInfo1[1]+", "+presentIonicInfo1[2]);

                    if((solution.getSubstances().get(i).getTypeInteger()==5||solution.getSubstances().get(i).equals(main.getSubstances().get(54)))&&presentIonicInfo1[0]!=-1&&presentIonicInfo1[1]!=-1&&presentIonicInfo1[2]!=-1&&solution.getSubstances().get(j).getIons().size()>1){
                        //if first is metal and second has an ion present in the reactivity series and second has more than one ion

                        if(presentIonicInfo0[1]<presentIonicInfo1[1]){
                            //if first has a more reactive ion than second

                            solution.setVolume(i,solution.getVolume(i)-0.001);
                            solution.setVolume(j,solution.getVolume(j)-0.001);

                            //the process to add the new poly ionic substance to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of the new substance
                            //the only ion in the metal/hydrogen, which is first in the new substance
                            presentIonList.add(solution.getSubstances().get(i).getIons().get(0));
                            //the non-reactive ion in the substance, which is second in new substance
                            presentIonList.add(solution.getSubstances().get(j).getIons().get(presentIonicInfo1[2]));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                            //the process to add the new halogen to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of the new metal/hydrogen
                            //the reactive ion in the second substance, which is the only ion in the new metal/hydrogen
                            presentIonList.add(solution.getSubstances().get(j).getIons().get(presentIonicInfo1[0]));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                        }

                    }else if((solution.getSubstances().get(j).getTypeInteger()==5||solution.getSubstances().get(j).equals(main.getSubstances().get(54)))&&presentIonicInfo0[0]!=-1&&presentIonicInfo0[1]!=-1&&presentIonicInfo0[2]!=-1&&solution.getSubstances().get(i).getIons().size()>1){
                        //if first is metal and second has an ion present in the reactivity series and second has more than one ion

                        if(presentIonicInfo1[1]<presentIonicInfo0[1]){
                            //if first has a more reactive ion than second

                            solution.setVolume(i,solution.getVolume(i)-0.001);
                            solution.setVolume(j,solution.getVolume(j)-0.001);

                            //the process to add the new poly ionic substance to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of the new substance
                            //the only ion in the metal/hydrogen, which is first in the new substance
                            presentIonList.add(solution.getSubstances().get(j).getIons().get(0));
                            //the non-reactive ion in the substance, which is second in new substance
                            presentIonList.add(solution.getSubstances().get(i).getIons().get(presentIonicInfo0[2]));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                            //the process to add the new halogen to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of the new metal/hydrogen
                            //the reactive ion in the second substance, which is the only ion in the new metal/hydrogen
                            presentIonList.add(solution.getSubstances().get(i).getIons().get(presentIonicInfo0[0]));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                        }

                    }else if(presentIonicInfo0[0]!=-1&&presentIonicInfo0[1]!=-1&&presentIonicInfo0[2]!=-1&&solution.getSubstances().get(i).getIons().size()>1&&presentIonicInfo1[0]!=-1&&presentIonicInfo1[1]!=-1&&presentIonicInfo1[2]!=-1&&solution.getSubstances().get(j).getIons().size()>1){
                        //if first has ion present in reactivity series and first has more than 1 ion and second has an ion present in the reactivity series and second has more than one ion

                        if(presentIonicInfo0[1]<presentIonicInfo1[1]&&presentIonicInfo0[1]!=presentIonicInfo1[1]){
                            //if first has a more reactive ion than second

                            solution.setVolume(i,solution.getVolume(i)-0.001);
                            solution.setVolume(j,solution.getVolume(j)-0.001);

                            //the process to add the new poly ionic substance to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of a new substance
                            //the reactive ion in first substance is first in one of the new substances
                            presentIonList.add(solution.getSubstances().get(i).getIons().get(presentIonicInfo0[0]));
                            //the non-reactgive ion in second substance is second in one of the new substances
                            presentIonList.add(solution.getSubstances().get(j).getIons().get(presentIonicInfo1[2]));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                            //the process to add the new halogen to the solution
                            if(!presentIonList.isEmpty())

                                presentIonList.clear();

                            //the ions of a new substance
                            //the reactgive ion in second substance is first in one of the new substances
                            presentIonList.add(solution.getSubstances().get(j).getIons().get(presentIonicInfo1[0]));
                            //the non-reactive ion in first substance is second in one of the new substances
                            presentIonList.add(solution.getSubstances().get(i).getIons().get(presentIonicInfo0[2]));

                            presentCorrectIndex=-1;

                            for(int a=0;a<main.getSubstances().size();a++){

                                if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                    presentCorrectIndex=a;

                                    break;

                                }

                            }

                            if(presentCorrectIndex==-1){

                                System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                            }else{

                                solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                            }

                        }

                    }
                    //END HYDROGEN AND METAL DISPLACEMENT AND PRECIPITATION

                    //System.out.println("TOTAL VOLUME AFTER HYDROGEN AND METAL DISPLACEMENT CHEMISTRY PROCESS: "+solution.getVolume());

                    //NEUTRALIZATION
                    if(solution.getSubstances().get(i).getTypeInteger()==1&&solution.getSubstances().get(j).getTypeInteger()==2){//if 0 is acidic and 1 is basic

                        solution.setVolume(i,solution.getVolume(i)-0.001);
                        solution.setVolume(j,solution.getVolume(j)-0.001);

                        //the process to add the new salt to the solution
                        if(!presentIonList.isEmpty())

                            presentIonList.clear();

                        //the ions of the new salt
                        //the first ion in the base, which is first in the new salt
                        presentIonList.add(solution.getSubstances().get(j).getIons().get(0));
                        //the second ion in the acid, which is second in new salt
                        presentIonList.add(solution.getSubstances().get(i).getIons().get(1));

                        presentCorrectIndex=-1;

                        for(int a=0;a<main.getSubstances().size();a++){

                            if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                presentCorrectIndex=a;

                                break;

                            }

                        }

                        if(presentCorrectIndex==-1){

                            System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                        }else{

                            solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                        }

                        //the process to add the water to the solution
                        solution.addSubstance(main.getSubstances().get(44),0.001,solution.getTemperature());


                    }else if(solution.getSubstances().get(i).getTypeInteger()==2&&solution.getSubstances().get(j).getTypeInteger()==1){//if 0 is salt and 1 is halogen

                        solution.setVolume(i,solution.getVolume(i)-0.001);
                        solution.setVolume(j,solution.getVolume(j)-0.001);

                        //the process to add the new salt to the solution
                        if(!presentIonList.isEmpty())

                            presentIonList.clear();

                        //the ions of the new salt
                        //the first ion in the base, which is first in the new salt
                        presentIonList.add(solution.getSubstances().get(i).getIons().get(0));
                        //the second ion in the acid, which is second in new salt
                        presentIonList.add(solution.getSubstances().get(j).getIons().get(1));

                        presentCorrectIndex=-1;

                        for(int a=0;a<main.getSubstances().size();a++){

                            if(main.getSubstances().get(a).getIons().equals(presentIonList)){

                                presentCorrectIndex=a;

                                break;

                            }

                        }

                        if(presentCorrectIndex==-1){

                            System.out.println("Susbtance with ions: "+presentIonList+", does not exist.");

                        }else{

                            solution.addSubstance(main.getSubstances().get(presentCorrectIndex),0.001,solution.getTemperature());

                        }

                        //the process to add the water to the solution
                        solution.addSubstance(main.getSubstances().get(44),0.001,solution.getTemperature());

                    }
                    //END OF NEUTRALIZATION

                }
                
            }
            
        }
        
        //System.out.println("TOTAL VOLUME AFTER CHEMISTRY PROCESS: "+solution.getVolume());
        
        solution.removeInvalidSubstances();
        
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        
        
        
    }
    
    public void updateVolume(){
        
        presentVolume=0;
        
        for(int i=0;i<solution.getVolumes().size();i++){
            
            presentVolume+=solution.getVolumes().get(i);
            
        }
        
        if(presentVolume>solution.getVolume()){
            
            solution.setVolume(presentVolume);
            
        }else if(presentVolume<solution.getVolume()){
            
            percentageOfPartialVolume=presentVolume*100;
            
            for(int i=0;i<solution.getVolumes().size();i++){
                
                solution.setVolume(i,(solution.getVolume(i)/percentageOfPartialVolume)*100);
                
            }
            
        }
        
    }
    
}
