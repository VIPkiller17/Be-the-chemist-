/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.solution;

import com.jme3.math.ColorRGBA;
import java.util.ArrayList;
import objects.containers.Container;
import objects.ion.Ion;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class Solution {
    
    private Container parentContainer;
    
    private double temperature;//updated every loop depending on tempeartures of substancecs
    private ColorRGBA liquidColor,solidColor,gasColor;
    
    private ArrayList<Ion> ions;//updated every loop depending on substances and their volumes
    private ArrayList<Double> ionCounts;//updated every loop depending on substances and their volumes
    private double PH;//modified every loop depending on ions
    private double volume;//modified every loop depending on volumes
    private ArrayList<Substance> substances;
    private ArrayList<Double> volumes;
    private ArrayList<Double> temperatures;
    
    private ArrayList<Substance> presentStateList;
    
    private Substance presentMergingSubstance;
    
    public Solution(Container container,Substance substance,double volume,double temperature){
        
        this.parentContainer=container;
        
        substances=new ArrayList<>();
        volumes=new ArrayList<>();
        temperatures=new ArrayList<>();
        
        if(substance!=null){
            substances.add(substance);
            volumes.add(volume);
            temperatures.add(temperature);
        }
        
        if(substances!=null&&!substances.isEmpty())
            updateSolutionState();
        
        //System.out.println("Making solution with substance: "+substances.get(0).getName()+" with state: "+substances.get(0).getStateInteger(298));
        if(!substances.isEmpty()){
        
            switch(substances.get(0).getStateInteger(298)){

                case 0:
                    gasColor=substances.get(0).getColor();
                    //System.out.println("Susbtance state is 0 and its gas color has been set to "+substances.get(0).getColor());
                    break;
                case 1:
                    liquidColor=substances.get(0).getColor();
                    //System.out.println("Susbtance state is 1 and its liquid color has been set to "+substances.get(0).getColor());
                    break;
                case 2:
                    solidColor=substances.get(0).getColor();
                    //System.out.println("Susbtance state is 2 and its solid color has been set to "+substances.get(0).getColor());
                    break;
                default:
                    //System.out.println("ERROR: invalid getStateInteger return value from substance: "+substance.getName());

            }
            
        }
        
    }
    
    public Solution(Container container,ArrayList<Substance> substances,ArrayList<Double> temperatures,ArrayList<Double> volumes){
        
        if(substances!=null)
            this.substances=substances;
        else
            this.substances=new ArrayList<>();
        
        this.parentContainer=container;
        
        if(temperatures!=null)
            this.temperatures=temperatures;
        else
            this.temperatures=new ArrayList<>();
        
        if(volumes!=null)
            this.volumes=volumes;
        else
            this.volumes=new ArrayList<>();
        
        if(substances!=null){
            this.substances=substances;
        }else{
            this.substances=new ArrayList<>();
        }
        
        if(substances!=null&&!substances.isEmpty())
            updateSolutionState();
        
        for(Substance s: substances){
            
            switch(s.getStateInteger(298)){
            
            case 0:
                if(gasColor==null)
                    gasColor=s.getColor();
                else
                    gasColor.add(s.getColor());
                break;
            case 1:
                if(liquidColor==null)
                    liquidColor=s.getColor();
                else
                    liquidColor.add(s.getColor());
                break;
            case 2:
                if(solidColor==null)
                    solidColor=s.getColor();
                else
                    solidColor.add(s.getColor());
                break;
            default:
                //System.out.println("ERROR: invalid getStateInteger return value from substance: "+s.getName());
            
            }
            
        }
        
    }
    
    public void removeSusbtance(int index){
        
        substances.remove(index);
        volumes.remove(index);
        temperatures.remove(index);
        
        for(int i=index+1;i<substances.size();i++){
        
            substances.set(i-1,substances.get(i));
            volumes.set(i-1,volumes.get(i));
            temperatures.set(i-1,temperatures.get(i));
        
        }
        
        substances.remove(substances.size()-1);
        volumes.remove(volumes.size()-1);
        temperatures.remove(temperatures.size()-1);
        
    }
    
    public Solution addSubstance(Substance substance,double volume,double temperature){
        
        System.out.println("is added substance null?: "+(substance==null));
        
        if(substances.isEmpty()){
            
            substances.add(substance);
            volumes.add(volume);
            temperatures.add(temperature);
            
        }else{
        
            //TO BE FIXED
            for(int j=0;j<substances.size();j++){

                if(substance.equals(substances.get(j))){

                    volumes.set(j,volumes.get(j)+volume);
                    
                    //temperature set is commented out because this simply replaces the temperature with the one from the added substance
                    //this will need to be modified for it to find the average also depending on the volume of each substances
                    //for now an easier alternative is just to leave the temperature as it is in the receiving solution
                    //temperatures.set(j,temperature);
                    
                    break;

                }else{

                    substances.add(substance);
                    volumes.add(volume);
                    temperatures.add(temperature);
                    
                    break;

                }

            }
            
        }
        
        System.out.println((substances==null)+", "+(substances.isEmpty()));
        
        updateSolutionState();
        
        return this;
        
    }
    
    public ColorRGBA getLiquidColor(){
        
        return liquidColor;
        
    }
    
    public void setLiquidColor(ColorRGBA color){
        
        liquidColor=color;
        //THE MODEL COLOR IS TO BE UPDATED IN THE LOOP
        
    }
    
    public ColorRGBA getSolidColor(){
        
        return solidColor;
        
    }
    
    public void setSolidColor(ColorRGBA color){
        
        solidColor=color;
        //THE MODEL COLOR IS TO BE UPDATED IN THE LOOP
        
    }
    
    public ColorRGBA getGasColor(){
        
        return gasColor;
        
    }
    
    public void setGasColor(ColorRGBA color){
        
        gasColor=color;
        //THE MODEL COLOR IS TO BE UPDATED IN THE LOOP
        
    }
    
    public int getRandomContainedState(int minState,int maxState){
        
        boolean containsGas=false;
        boolean containsLiquid=false;
        boolean containsSolid=false;
        
        for(Substance s: substances){
            
            switch(s.getStateInteger(temperature)){
                
                case 0:
                    containsGas=true;
                    break;
                case 1:
                    containsLiquid=true;
                    break;
                case 2:
                    containsSolid=true;
                    break;
                default:
                    //System.out.println("ERROR: @ getting random contained state, a substance in substance list: "+substances.toString()+" has an invalid integer state.");
                
            }
            
        }
        
        int result;
        
        while(true){
            
            result=((int)(Math.random()*(maxState+1)))+minState;
            
            switch(result){
                
                case 0:
                    if(containsGas)
                        return 0;
                    break;
                case 1:
                    if(containsLiquid)
                        return 1;
                    break;
                case 2:
                    if(containsSolid)
                        return 2;
                    break;
                default:
                    //System.out.println("ERROR: @ getting random contained state, result generated invalid.");
                
            }
            
        }
        
    }
    
    public boolean[] containsStates(){
        
        boolean containsGas=false;
        boolean containsLiquid=false;
        boolean containsSolid=false;
        
        for(Substance s: substances){
            
            //System.out.println("Checking substance "+s.getName()+"'s state at temp: "+temperature);
            
            switch(s.getStateInteger(temperature)){
                
                case 0:
                    containsGas=true;
                    //System.out.println(s.getName()+"'s state is gas");
                    break;
                case 1:
                    containsLiquid=true;
                    //System.out.println(s.getName()+"'s state is liquid");
                    break;
                case 2:
                    containsSolid=true;
                    //System.out.println(s.getName()+"'s state is solid");
                    break;
                default:
                    //System.out.println("ERROR: @ getting contained states, a substance in substance list: "+substances.toString()+" has an invalid integer state.");
                
            }
            
        }
        
        boolean[] containedStates=new boolean[3];
        containedStates[0]=containsGas;
        containedStates[1]=containsLiquid;
        containedStates[2]=containsSolid;
        
        return containedStates;
        
        
    }
    
    public double getTemperature(){
        
        return temperature;
        
    }
    
    public void setTemperature(double temperature){
        
        this.temperature=temperature;
        
    }
    
    public void merge(Solution solution){
        
        for(int i=0;i<solution.getSubstances().size();i++){
            
            presentMergingSubstance=solution.getSubstances().get(i);
            
            for(int j=0;j<substances.size();j++){
                
                if(presentMergingSubstance.equals(substances.get(j))){
                    
                    volumes.set(j,solution.getVolumes().get(j));
                    temperatures.set(j,solution.getTemperatures().get(j));
                    
                }else{
                    
                    substances.add(presentMergingSubstance);
                    volumes.add(solution.getVolumes().get(i));
                    temperatures.add(solution.getTemperatures().get(i));
                    
                }
                
            }
            
        }
        
    }
    
    public ArrayList<Substance> getStateList(int state){
        
        if(!presentStateList.isEmpty())
            
            presentStateList.clear();
        
        for(int i=0;i<substances.size();i++){
            
            if(substances.get(i).getStateInteger(temperatures.get(i))==state)
            
                presentStateList.add(substances.get(i));
            
        }
        
        return presentStateList;
        
    }
    
    public ArrayList<Double> getVolumes(){
        
        return volumes;
        
    }
    
    public ArrayList<Ion> getIons(){
        
        return ions;
        
    }
    
    public Ion getIon(int index){
        
        return ions.get(index);
        
    }
    
    public void setIons(ArrayList<Ion> ions){
        
        this.ions=ions;
        
    }
    
    public void setIon(int index,Ion ion){
        
        ions.set(index,ion);
        
    }
    
    public double getPH(){
        
        return PH;
        
    }
    
    public void setPH(double PH){
        
        this.PH=PH;
        
    }
    
    public double getVolume(){
        
        return volume;
        
    }
    
    public void setVolume(double volume){
        
        this.volume=volume;
        
    }
    
    public ArrayList<Substance> getSubstances(){
        
        return substances;
        
    }
    
    public Substance getSubstance(int index){
        
        return substances.get(index);
        
    }
    
    public void setSubstances(ArrayList<Substance> substances){
        
        this.substances=substances;
        
    }
    
    public void setSubstance(int index,Substance substance){
        
        substances.set(index,substance);
        
    }
    
    public Double getVolume(int index){
        
        return volumes.get(index);
        
    }
    
    public void setVolumes(ArrayList<Double> volumes){
        
        this.volumes=volumes;
        
    }
    
    public void setVolume(int index,Double volume){
        
        volumes.set(index,volume);
        
    }
    
    public ArrayList<Double> getTemperatures(){
        
        return temperatures;
        
    }
    
    public Double getTemperature(int index){
        
        return temperatures.get(index);
        
    }
    
    public void setTemperatures(ArrayList<Double> temperatures){
        
        this.temperatures=temperatures;
        
    }
    
    public void setTemperature(int index,Double temperature){
        
        temperatures.set(index,temperature);
        
    }
    
    @Override
    public boolean equals(Object otherSolution){
        
        if(otherSolution instanceof Solution){
            
            if(substances.size()==((Solution)otherSolution).getSubstances().size()){
                
                for(int i=0;i<substances.size();i++){

                    if(!substances.get(i).equals(((Solution)otherSolution).getSubstances().get(i))){
                        
                        return false;
                        
                    }

                }
                
                return true;
            
            }else
                
                return false;
            
        }else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        String t="Solution containing: ";
        
        for(Substance s: substances){
            
            t+=s.getName()+", ";
            
        }
        
        return t;
        
    }
    
    public void updateSolutionState(){
        
        temperature=0;
        volume=0;
        
        for(int i=0;i<substances.size();i++){
            
            System.out.println("Size of substances: "+substances.size()+", is it empty?: "+substances.isEmpty()+", index: "+i+", substance at 0: "+substances.get(0));
            
            System.out.println(substances.get(i).getName()+"'s temp: "+temperatures.get(i)+", volume: "+volumes.get(i));
            
            temperature+=temperatures.get(i);
            
            volume+=volumes.get(i);
            
            switch(substances.get(i).getStateInteger(298)){
            
                case 0:
                    
                    if(gasColor==null)
                        gasColor=substances.get(i).getColor();
                    else
                        gasColor=gasColor.add(substances.get(i).getColor());
                    //System.out.println("Susbtance state is 0 and its gas color has been set to "+substances.get(0).getColor());
                    break;
                case 1:
                    if(liquidColor==null)
                        liquidColor=substances.get(i).getColor();
                    else
                        liquidColor=liquidColor.add(substances.get(i).getColor());
                    //System.out.println("Susbtance state is 1 and its liquid color has been set to "+substances.get(0).getColor());
                    break;
                case 2:
                    if(solidColor==null)
                        solidColor=substances.get(i).getColor();
                    else
                        solidColor=solidColor.add(substances.get(i).getColor());
                    //System.out.println("Susbtance state is 2 and its solid color has been set to "+substances.get(0).getColor());
                    break;
                default:
                    System.out.println("ERROR: invalid getStateInteger return value from substance: "+substances.get(i).getName());
            
            }
            
        }
        
        //System.out.println("Temperature: "+temperature+", temperatures: "+temperatures);
        
        temperature=temperature/temperatures.size();
        
    }
    
    public int getMostCommonState(){
        
        double presentVolumeList[]=new double[3];
        presentVolumeList[0]=0;
        presentVolumeList[1]=0;
        presentVolumeList[2]=0;
        
        for(int i=0;i<substances.size();i++){
            
            presentVolumeList[substances.get(i).getStateInteger(temperatures.get(i))]+=volumes.get(i);
            
        }
        
        if(presentVolumeList[0]>presentVolumeList[1]&&presentVolumeList[0]>presentVolumeList[2]){
            
            return 0;
            
        }else if(presentVolumeList[1]>presentVolumeList[0]&&presentVolumeList[1]>presentVolumeList[2]){
            
            return 1;
            
        }else if(presentVolumeList[2]>presentVolumeList[1]&&presentVolumeList[2]>presentVolumeList[0]){
            
            return 2;
            
        }else{
            
            return 0;
            
        }
        
    }
    
    public ColorRGBA getStateColor(int state){
        
        if(state==0){
            
            return gasColor;
            
        }else if(state==1){
            
            return liquidColor;
            
        }else if(state==2){
            
            return solidColor;
            
        }else{
            
            return liquidColor;
            
        }
        
    }
    
}
