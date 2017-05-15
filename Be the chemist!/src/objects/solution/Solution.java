/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.solution;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import java.util.ArrayList;
import main.Main;
import objects.containers.Container;
import objects.ion.Ion;
import objects.substance.Substance;

/**
 *
 * @author VIPkiller17
 */
public class Solution {
    
    private Container parentContainer;
    
    private ColorRGBA liquidColor=new ColorRGBA(0,0,0,0),solidColor=new ColorRGBA(0,0,0,0),gasColor=new ColorRGBA(0,0,0,0);
    private ColorRGBA presentLiquidColor=new ColorRGBA(0,0,0,0),presentSolidColor=new ColorRGBA(0,0,0,0),presentGasColor=new ColorRGBA(0,0,0,0);
    private boolean liquidColorInit,solidColorInit,gasColorInit;
    
    private ArrayList<Ion> ions;//updated every loop depending on substances and their volumes
    private ArrayList<Double> ionCounts;//updated every loop depending on substances and their volumes
    private double PH;//modified every loop depending on ions
    private double presentVolume;
    private int presentVolumeCount;
    private double presentTemperature;
    private ArrayList<Substance> substances;
    private ArrayList<Double> volumes;
    private ArrayList<Double> temperatures;
    
    private ArrayList<Substance> presentStateList;
    
    private Substance presentMergingSubstance;
    
    private boolean substanceFound;
    
    private Node logicNode;
    
    private SolutionControl control;
    
    private boolean hasFoundSubstance;
    
    private int presentVolumeDotPosition;
    private int presentTempDotPosition;
    private String presentFormattedVolume;
    private String presentFormattedTemp;
    
    public Solution(Main main,Container container,Substance substance,double volume,double temperature){
        
        this.parentContainer=container;
        
        substances=new ArrayList<>();
        volumes=new ArrayList<>();
        temperatures=new ArrayList<>();
        
        if(substance!=null){
            substances.add(substance);
            
            if(substance.getStateInteger(temperature)==2){
                
                volumes.add(volume*0.001/substance.getDensity(substance.getStateInteger(temperature)));
                
            }else{
                
                volumes.add(volume);
                
            }
            
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
        
        logicNode=new Node();
        main.getRootNode().attachChild(logicNode);
        control=new SolutionControl(main,this);
        logicNode.addControl(control);
        
    }
    
    public Solution(Main main,Container container,ArrayList<Substance> substances,ArrayList<Double> temperatures,ArrayList<Double> volumes){
        
        if(substances!=null)
            this.substances=substances;
        else
            this.substances=new ArrayList<>();
        
        this.parentContainer=container;
        
        if(temperatures!=null)
            this.temperatures=temperatures;
        else
            this.temperatures=new ArrayList<>();
        
        if(volumes!=null){
            
            this.volumes=new ArrayList<>();
            
            for(int i=0;i<substances.size();i++){
                
                if(substances.get(i).getStateInteger(getTemperature())==2){
                
                    this.volumes.add(volumes.get(i)*0.001/substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i))));
                
                }
                
            }
            
        }else{
            
            this.volumes=new ArrayList<>();
            
        }
        
        if(substances!=null){
            this.substances=substances;
        }else{
            this.substances=new ArrayList<>();
        }
        
        if(substances!=null&&!substances.isEmpty())
            updateSolutionState();
        
        /*
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
        */
        
        logicNode=new Node();
        main.getRootNode().attachChild(logicNode);
        control=new SolutionControl(main,this);
        logicNode.addControl(control);
        
    }
    
    public void removeSubstance(int index){
        
        System.out.println("Lists before removal of substance, subs: "+substances+" vols: "+volumes+" and temps: "+temperatures);
        
        //System.out.println("RemoveSusbtance() called on index: "+index+" for substance: "+substances.get(index)+"\nlist before removal: "+substances);
        
        substances.remove(index);
        volumes.remove(index);
        temperatures.remove(index);
        
        System.out.println("Lists after removal of substance, subs: "+substances+" vols: "+volumes+" and temps: "+temperatures);
        
        //System.out.println("Litst after removal: "+substances);
        
        //commented out because the arraylist places them correctly on its own
        /*
        for(int i=index+1;i<substances.size();i++){
        
            substances.set(i-1,substances.get(i));
            volumes.set(i-1,volumes.get(i));
            temperatures.set(i-1,temperatures.get(i));
        
        }
        
        substances.remove(substances.size()-1);
        volumes.remove(volumes.size()-1);
        temperatures.remove(temperatures.size()-1);
        */
        
    }
    
    public Solution addSubstance(Substance substance,double volume,double temperature){
        
        //System.out.println("addSubstance() called with: "+substance+" with volume: "+volume+" and temp: "+temperature);
        
        if(substances.isEmpty()){
            
            //System.out.println("    the receiver solution substances list is apparently empty");
            
            substances.add(substance);
            volumes.add(volume);
            temperatures.add(temperature);
            
        }else{
            
            //System.out.println("    the receiver solution substances list is apparently NOT empty");
            
            substanceFound=false;
        
            //TO BE FIXED
            for(int j=0;j<substances.size();j++){
                
                //System.out.println("        Adding substance "+substances+" to receiver solution");

                if(substance==substances.get(j)){
                    
                    //System.out.println("            substance "+substance+" was already in the solution's substance list, adding volume and calculating temperature");

                    volumes.set(j,volumes.get(j)+volume);
                    
                    //temperature set is commented out because this simply replaces the temperature with the one from the added substance
                    //this will need to be modified for it to find the average also depending on the volume of each substances
                    //for now an easier alternative is just to leave the temperature as it is in the receiving solution
                    //temperatures.set(j,temperature);
                    
                    substanceFound=true;
                    
                    break;

                }

            }
            
            if(!substanceFound){
                
                //System.out.println("            substance "+substance+" was NOT already in the solution's substance list, adding substance, volume and temperature to corresponding lists");
                
                substances.add(substance);
                volumes.add(volume);
                temperatures.add(temperature);
                
            }
            
        }
        
        //System.out.println((substances==null)+", "+(substances.isEmpty()));
        
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
            
            switch(s.getStateInteger(getTemperature())){
                
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
    
    public boolean containsInsolubleSolid(){
    
        for(Substance s: substances){
            
            if(s.getStateInteger(298)==2&&!s.isSoluble()){
                
                return true;
                
            }
            
        }
        
        return false;
    
    }
    
    public boolean[] containsStates(){
        
        boolean containsGas=false;
        boolean containsLiquid=false;
        boolean containsSolid=false;
        
        if(!substances.isEmpty()){
        
            for(Substance s: substances){

                //System.out.println("Checking substance "+s.getName()+"'s state at temp: "+temperature);

                switch(s.getStateInteger(getTemperature())){

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
            
        }
        
        boolean[] containedStates=new boolean[3];
        containedStates[0]=containsGas;
        containedStates[1]=containsLiquid;
        containedStates[2]=containsSolid;
        
        return containedStates;
        
        
    }
    
    public double getTemperature(){
        
        presentTemperature=0;
        
        for(int i=0;i<temperatures.size();i++){
            
            presentTemperature+=temperatures.get(i);
            
        }
        
        return presentTemperature/temperatures.size();
        
    }
    
    public void setTemperature(double temperature){
        
        presentTemperature=getTemperature();
        
        for(int i=0;i<temperatures.size();i++){
            
            setTemperature(i,getTemperature(i)+(temperature-presentTemperature));

        }
        
    }
    
    public void merge(Solution solution){
        
        for(int i=0;i<solution.getSubstances().size();i++){
            
            hasFoundSubstance=false;
            
            presentMergingSubstance=solution.getSubstances().get(i);
            
            for(int j=0;j<substances.size();j++){
                
                if(presentMergingSubstance.equals(substances.get(j))){
                    
                    volumes.set(j,volumes.get(j)+solution.getVolumes().get(i));
                    temperatures.set(j,volumes.get(j)+solution.getTemperatures().get(i));
                    
                    hasFoundSubstance=true;
                    
                    break;
                    
                }
                
            }
            
            if(!hasFoundSubstance){
                    
                substances.add(presentMergingSubstance);
                volumes.add(solution.getVolumes().get(i));
                temperatures.add(solution.getTemperatures().get(i));

            }
            
        }
        
        updateSolutionState();
        
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
        
        presentVolume=0;
        
        for(int i=0;i<volumes.size();i++){
            
            presentVolume+=volumes.get(i);
            
        }
        
        return presentVolume;
        
    }
    
    public void setVolume(double volume){
        
        //System.out.println("setVolume() called, volume before: "+getVolume());
        
        presentVolume=getVolume();
        
        if(presentVolume>volume){
            
            presentVolume-=volume;
            
            presentVolume=presentVolume/volumes.size();
            
            for(int i=0;i<volumes.size();i++){
            
                setVolume(i,getVolume(i)-presentVolume);
            
            }
            
        }else if(presentVolume<volume){
            
            presentVolume=volume-presentVolume;
            
            presentVolume=presentVolume/volumes.size();
            
            for(int i=0;i<volumes.size();i++){
            
                setVolume(i,getVolume(i)+presentVolume);
            
            }
            
        }
        
        //System.out.println("setVolume() called, volume after: "+getVolume());
        
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
        
        //System.out.println("setVolumes(list) called, volume before: "+getVolume());
        
        this.volumes=volumes;
        
        //System.out.println("setVolumes(list) called, volume after: "+getVolume());
        
    }
    
    public void setVolume(int index,Double volume){
        
        //System.out.println("setVolume(index,volume) called, volume before: "+getVolume());
        
        volumes.set(index,volume);
        
        //System.out.println("setVolume(index,volume) called, volume after: "+getVolume());
        
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
        
        String t="Solution containing:\n";
        
        for(int i=0;i<substances.size();i++){
            
            presentFormattedVolume=""+getVolume(i);
            presentFormattedTemp=""+getTemperature(i);
            
            presentVolumeDotPosition=presentFormattedVolume.indexOf(".");
            presentTempDotPosition=presentFormattedTemp.indexOf(".");
            
            if(presentFormattedVolume.substring(presentVolumeDotPosition,presentFormattedVolume.length()-1).length()>4){
            
                presentFormattedVolume=presentFormattedVolume.substring(0,presentVolumeDotPosition+4);
            
            }else{
                
                presentFormattedVolume=""+getVolume(i);
            
            }
            
            if(presentFormattedTemp.substring(presentTempDotPosition,presentFormattedTemp.length()-1).length()>4){
            
                presentFormattedTemp=presentFormattedTemp.substring(0,presentTempDotPosition+4);
            
            }else{
                
                presentFormattedTemp=""+getTemperature(i);
            
            }
            
            if(i<substances.size()-1)
            
                t+="   "+substances.get(i).getName()+": "+getVolume(i)+" L\n    with temperature: "+presentFormattedTemp+" K;\n";
            
            else if(i==substances.size()-1)
                
                t+="   "+substances.get(i).getName()+": "+getVolume(i)+" L\n    with temperature: "+presentFormattedTemp+" K.\n";
            
        }
        
        return t;
        
    }
    
    public void updateSolutionState(){
        
        liquidColorInit=false;
        solidColorInit=false;
        gasColorInit=false;
        
        for(int i=0;i<substances.size();i++){
            
            //System.out.println("Size of substances: "+substances.size()+", is it empty?: "+substances.isEmpty()+", index: "+i+", substance at 0: "+substances.get(0));
            
            //System.out.println(substances.get(i).getName()+"'s temp: "+temperatures.get(i)+", volume: "+volumes.get(i)+"\n    state at 298: "+substances.get(i).getStateInteger(298));
            
            switch(substances.get(i).getStateInteger(getTemperature(i))){
            
                case 0:
                    
                    if(!gasColorInit){
                        presentGasColor.set(substances.get(i).getColor());
                        gasColorInit=true;
                    }else{
                        presentGasColor.set((float)Math.sqrt(((gasColor.getRed()*gasColor.getRed())+(substances.get(i).getColor().getRed()*substances.get(i).getColor().getRed()))/2),(float)Math.sqrt(((gasColor.getGreen()*gasColor.getGreen())+(substances.get(i).getColor().getGreen()*substances.get(i).getColor().getGreen()))/2),(float)Math.sqrt(((gasColor.getBlue()*gasColor.getBlue())+(substances.get(i).getColor().getBlue()*substances.get(i).getColor().getBlue()))/2),(gasColor.getAlpha()+substances.get(i).getColor().getAlpha())/2);
                    }
                    //System.out.println("Susbtance state is 0 and its gas color has been set to "+substances.get(0).getColor());
                    break;
                case 1:
                    if(!liquidColorInit){
                        presentLiquidColor.set(substances.get(i).getColor());
                        liquidColorInit=true;
                    }else{
                        presentLiquidColor.set((float)Math.sqrt(((liquidColor.getRed()*liquidColor.getRed())+(substances.get(i).getColor().getRed()*substances.get(i).getColor().getRed()))/2),(float)Math.sqrt(((liquidColor.getGreen()*liquidColor.getGreen())+(substances.get(i).getColor().getGreen()*substances.get(i).getColor().getGreen()))/2),(float)Math.sqrt(((liquidColor.getBlue()*liquidColor.getBlue())+(substances.get(i).getColor().getBlue()*substances.get(i).getColor().getBlue()))/2),(liquidColor.getAlpha()+substances.get(i).getColor().getAlpha())/2);
                    }
                    //System.out.println("Susbtance state is 1 and its liquid color has been set to "+substances.get(0).getColor());
                    break;
                case 2:
                    if(!solidColorInit){
                        presentSolidColor.set(substances.get(i).getColor());
                        solidColorInit=true;
                    }else{
                        presentSolidColor.set((float)Math.sqrt(((solidColor.getRed()*solidColor.getRed())+(substances.get(i).getColor().getRed()*substances.get(i).getColor().getRed()))/2),(float)Math.sqrt(((solidColor.getGreen()*solidColor.getGreen())+(substances.get(i).getColor().getGreen()*substances.get(i).getColor().getGreen()))/2),(float)Math.sqrt(((solidColor.getBlue()*solidColor.getBlue())+(substances.get(i).getColor().getBlue()*substances.get(i).getColor().getBlue()))/2),(solidColor.getAlpha()+substances.get(i).getColor().getAlpha())/2);
                    }
                    //System.out.println("Susbtance state is 2 and its solid color has been set to "+substances.get(0).getColor());
                    break;
                default:
                    System.out.println("ERROR: invalid getStateInteger return value from substance: "+substances.get(i).getName());
            
            }
            
        }
        
        liquidColor.set(presentLiquidColor);
        solidColor.set(presentSolidColor);
        gasColor.set(presentGasColor);
        
        //System.out.println("Temperature: "+temperature+", temperatures: "+temperatures);
        
    }
    
    public int getMostCommonState(){
        
        double presentVolumeList[]=new double[3];
        presentVolumeList[0]=0;
        presentVolumeList[1]=0;
        presentVolumeList[2]=0;
        
        for(int i=0;i<substances.size();i++){
            
            presentVolumeList[substances.get(i).getStateInteger(temperatures.get(i))]+=volumes.get(i);
            
            //System.out.println("presentvolumelist at index: "+substances.get(i).getStateInteger(temperatures.get(i))+" now at "+presentVolumeList[substances.get(i).getStateInteger(temperatures.get(i))]);
            
        }
        
        //System.out.println("0: "+presentVolumeList[0]+"\n1: "+presentVolumeList[1]+"\n2: "+presentVolumeList[2]);
        
        if(presentVolumeList[0]>=presentVolumeList[1]&&presentVolumeList[0]>=presentVolumeList[2]){
            
            return 0;
            
        }else if(presentVolumeList[1]>=presentVolumeList[0]&&presentVolumeList[1]>=presentVolumeList[2]){
            
            return 1;
            
        }else if(presentVolumeList[2]>=presentVolumeList[1]&&presentVolumeList[2]>=presentVolumeList[0]){
            
            return 2;
            
        }else{
            
            System.out.println("For some reason there is no most common state, returning 0");
            
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
    
    public void flush(){
        
        if(!substances.isEmpty()){
            
            substances.clear();
            volumes.clear();
            temperatures.clear();
            
        }
        
        PH=0;
        
    }
    
    public SolutionControl getControl(){
        
        return control;
        
    }
    
    public boolean containsLowDensityGas(){
        
        //System.out.println("containsLowDensityGas() called.");
        
        for(int i=0;i<substances.size();i++){
            
            //System.out.println("    Checking substance: "+s.getName()+"'s density: "+s.getDensity());
            
            if(substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))<0.00128){
                
                //System.out.println("        Substance deensity less than 0.00128, returning true...");
                
                return true;
                
            }
            
        }
        
        return false;
        
    }
    
    public void setPourableVolume(double volume){
        
        System.out.println("setPourableVolume() called, volume before: "+getVolume());
        
        presentVolume=0;
        presentVolumeCount=0;
        
        for(int i=0;i<substances.size();i++){
            
            if(substances.get(i).getStateInteger(getTemperature(i))!=0&&substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))>0.00128){
                
                presentVolume+=volumes.get(i);
                presentVolumeCount++;
                
            }
            
        }
        
        if(presentVolume>volume){
            
            presentVolume-=volume;
            
            presentVolume=presentVolume/presentVolumeCount;
            
            for(int i=0;i<volumes.size();i++){
                
                if(substances.get(i).getStateInteger(getTemperature(i))!=0&&substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))>0.00128){
                
                    setVolume(i,getVolume(i)-presentVolume);
                
                }
            
            }
            
        }else if(presentVolume<volume){
            
            presentVolume=volume-presentVolume;
            
            presentVolume=presentVolume/presentVolumeCount;
            
            for(int i=0;i<volumes.size();i++){
                
                if(substances.get(i).getStateInteger(getTemperature(i))!=0&&substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))>0.00128){
                
                    setVolume(i,getVolume(i)+presentVolume);
                
                }
            
            }
            
        }
        
        System.out.println("setPourableVolume() called, volume after: "+getVolume());
        
    }
    
    public double getPourableVolume(){
        
        presentVolume=0;
        
        for(int i=0;i<volumes.size();i++){
            
            System.out.println("        Checking if "+substances.get(i)+" has density: "+substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))+" heigher than 0.00128: "+(substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))>0.00128));
            
            if(substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))>0.00128){
                
                presentVolume+=volumes.get(i);

            }
            
        }
        
        return presentVolume;
        
    }
    
    public ArrayList<Substance> getPourables(){
        
        presentStateList=new ArrayList<>();
        
        for(int i=0;i<substances.size();i++){
            
            if(substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))>0.00128){
                
                presentStateList.add(substances.get(i));

            }
            
        }
        
        return presentStateList;
        
    }
    
    public void setEvaporatableVolume(double volume){
        
        //System.out.println("setEvaporatableVoume() called");
        
        presentVolume=0;
        presentVolumeCount=0;
        
        for(int i=0;i<substances.size();i++){
            
            //System.out.println("    Checking substance: "+substances.get(i).getName()+" with phase at 298: "+substances.get(i).getStateInteger(temperature)+" and if it has less density than air: "+(substances.get(i).getDensity()<0.00128));
            
            if(substances.get(i).getStateInteger(getTemperature(i))==0&&substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))<0.00128){
                
                //System.out.println("        Substance is evaporatable, adding volume to presentvolume");
                
                presentVolume+=volumes.get(i);
                presentVolumeCount++;
                
            }
            
        }
        
        //System.out.println("We want to set presentVolume: "+presentVolume+" to volume: "+volume);
        
        if(presentVolume>volume){
            
            //System.out.println("    The total volume of evaporatables is "+presentVolume+" which is more than the volume we want to set it too of "+volume);
            
            presentVolume-=volume;
            
            presentVolume=presentVolume/presentVolumeCount;
            
            for(int i=0;i<volumes.size();i++){
                
                //System.out.println("        Checking volume: "+volumes.get(i));
                
                if(substances.get(i).getStateInteger(getTemperature(i))==0&&substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))<0.00128){
                
                    //System.out.println("            Volume's corresponding substance is a gas at 298 and its density is less than air's, setting its volume...");
                    
                    setVolume(i,getVolume(i)-presentVolume);
                
                }
            
            }
            
        }else if(presentVolume<volume){
            
            //System.out.println("    Total volume of evaporatables is less than teh volume we want to set it at, so we want to add partial volumes to get an evaporatable total of the geiven volume");
            
            presentVolume=volume-presentVolume;
            
            presentVolume=presentVolume/presentVolumeCount;
            
            for(int i=0;i<volumes.size();i++){
                
                //System.out.println("        Checking volume "+volumes.get(i));
                
                if(substances.get(i).getStateInteger(getTemperature(i))==0&&substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))<0.00128){
                
                    //System.out.println("            Volume's corresponding substance is evaporatable, adding partial volume...");
                    
                    setVolume(i,getVolume(i)+presentVolume);
                
                }
            
            }
            
        }
        
    }
    
    public double getEvaporatableVolume(){
        
        presentVolume=0;
        
        for(int i=0;i<volumes.size();i++){
            
            if(substances.get(i).getStateInteger(getTemperature(i))==0&&substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))<0.00128){
                
                presentVolume+=volumes.get(i);

            }
            
        }
        
        return presentVolume;
        
    }
    
    public ArrayList<Substance> getEvaporatables(){
        
        presentStateList=new ArrayList<>();
        
        for(int i=0;i<substances.size();i++){
            
            if(substances.get(i).getStateInteger(getTemperature(i))==0&&substances.get(i).getDensity(substances.get(i).getStateInteger(getTemperature(i)))<0.00128){
                
                presentStateList.add(substances.get(i));

            }
            
        }
        
        return presentStateList;
        
    }
    
    public void removeInvalidSubstances(){
        
        //System.out.println("Substances list size: "+substances.size());
        
        //System.out.println("Lists before removal of all invalid substances, subs: "+substances+" vols: "+volumes+" and temps: "+temperatures);
        
        for(int i=0;i<substances.size();i++){
            
            //System.out.println("Checking volume validity of "+substances.get(i).getName()+" with volume: "+volumes.get(i)+" at index: "+i);
            
            if(volumes.get(i)<0.001){

                //System.out.println("    Lists before removal of substance, subs: "+substances+" vols: "+volumes+" and temps: "+temperatures);
                
                substances.remove(i);
                volumes.remove(i);
                temperatures.remove(i);
                
                i--;

                //System.out.println("    Lists after removal of substance, subs: "+substances+" vols: "+volumes+" and temps: "+temperatures);
                
            }
            
        }
        
        //System.out.println("        Lists after removal of all invalid substances, subs: "+substances+" vols: "+volumes+" and temps: "+temperatures);
        
    }
    
}