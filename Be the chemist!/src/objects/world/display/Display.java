/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.world.display;

import com.jme3.asset.AssetManager;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.Savable;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import interfaces.Pointable;
import java.io.IOException;
import java.util.ArrayList;
import main.Main;
import objects.player.Hand;

/**
 *
 * @author VIPkiller17
 */
public class Display implements Pointable,Savable{
    
    private AssetManager assetManager;
    
    private ArrayList<Button> buttons;
    private ArrayList<BitmapText> texts;
    private static ArrayList<SubstanceButton> substanceButtonList=new ArrayList<>();
    private static ArrayList<MaterialButton> materialButtonList=new ArrayList<>();
    private static ArrayList<SubstanceButton> filteredSubstanceButtonList=new ArrayList<>();
    private static ArrayList<MaterialButton> filteredMaterialButtonList=new ArrayList<>();
    private static ArrayList<SubstanceButton> initFilteredSubstanceButtonList=new ArrayList<>();
    private static ArrayList<MaterialButton> initFilteredMaterialButtonList=new ArrayList<>();
    
    private static boolean substanceFilterChanged;
    private static boolean materialFilterChanged;
    
    private int preset;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    
    private Node node;
    private Node rootNode;
    
    private Main main;
    
    public static final int MAIN_MENU=0,SUBSTANCE_LIST=1,KEYBOARD=2,MATERIAL_LIST=3,PERIODIC_TABLE=4,SETTINGS_MENU=5;
    
    private static boolean keyBoardIsInCaps;
    
    private static SubstanceButton selectedSubstanceButton;
    private static MaterialButton selectedMaterialButton;
    private static int indexOfFirstDisplayedSubstanceButton;
    private static int indexOfFirstDisplayedMaterialButton;
    private static int indexOfLastDisplayedSubstanceButton=4;
    private static int indexOfLastDisplayedMaterialButton;
    
    private static int typeFilter;
    private static int phaseFilter;
    private static int classFilter;
    private static int presentPositionIndex;
    
    public Display(Main main,int preset){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        this.rootNode=main.getRootNode();
        
        this.preset=preset;
        
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        
        texts=new ArrayList<>();
        buttons=new ArrayList<>();
        
        node=new Node();
        
        setup(preset);
        
    }
    
    public void addButton(Button button){
        
        buttons.add(button);
        
    }
    
    public void setup(int preset){
        
        texts.add(new BitmapText(font));
        node.attachChild(texts.get(texts.size()-1));
        
        //IN HERE SET THE POSITION AND ROTATION OF THE DISPLAY WITH THE NODE CONTAINING THE BUTTONS AND TEXTS
        switch(preset){
            
            case 0://Main menu
                
                createBackground(1,2);
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("Main menu");
                texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.8f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                texts.add(new BitmapText(font));
                node.attachChild(texts.get(texts.size()-1));
                texts.get(texts.size()-1).setSize(0.27f);
                texts.get(texts.size()-1).setText("Be the chemist!");
                texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),1.28f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                texts.add(new BitmapText(font));
                node.attachChild(texts.get(texts.size()-1));
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("       Made by:\n    Tommy Soucy\n Etienne Duchesne");
                texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.05f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                buttons.add(new Button(main,this,0));
                buttons.get(buttons.size()-1).setGrayedOut(true);
                buttons.add(new Button(main,this,1));
                buttons.add(new Button(main,this,2));
                node.setLocalTranslation(new Vector3f(-4.9f,1.2f,-4f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                break;
                
            case 1://Substance list
                
                createBackground(1,2);
                
                Quad substanceTextFieldQuad=new Quad(0.80f,0.15f);
                Geometry substanceTextField=new Geometry("Substance list text field",substanceTextFieldQuad);
                Material substanceTextFieldMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                substanceTextFieldMat.setColor("Color",ColorRGBA.BlackNoAlpha);
                substanceTextField.setMaterial(substanceTextFieldMat);
                node.attachChild(substanceTextField);
                substanceTextField.setLocalTranslation(-0.40f,0.60f,0.05f);
                
                Quad substanceListBackgroundQuad=new Quad(0.8f,1f);
                Geometry substanceListBackground=new Geometry("Substance list background",substanceListBackgroundQuad);
                Material substanceListBackgroundMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                substanceListBackgroundMat.setColor("Color",ColorRGBA.Gray);
                substanceListBackground.setMaterial(substanceListBackgroundMat);
                node.attachChild(substanceListBackground);
                substanceListBackground.setLocalTranslation(-0.40f,-0.5f,0.03f);
                
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("Substance list");
                texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.9f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                buttons.add(new Button(main,this,3));
                node.setLocalTranslation(new Vector3f(-4.9f,1.2f,-2.5f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                
                texts.add(new BitmapText(font));
                node.attachChild(texts.get(texts.size()-1));
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("");
                texts.get(texts.size()-1).setLocalTranslation(-0.40f,0.73f,0.055f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                
                addSubstances();
                
                break;
                
            case 2://Keyboard
                
                createBackground(1.15f,0.58f);
                for(int i=8;i<53;i++){
                    
                    if(i!=45)
                    
                        buttons.add(new Button(main,this,i));
                    
                }
                
                buttons.add(new Button(main,this,4));
                
                node.setLocalTranslation(new Vector3f(-4.9f,0.49f,-1.4f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                break;
                
            case 3://Material list
                
                createBackground(1,2);
                    
                Quad materialTextFieldQuad=new Quad(0.80f,0.15f);
                Geometry materialTextField=new Geometry("Material list text field",materialTextFieldQuad);
                Material materialTextFieldMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                materialTextFieldMat.setColor("Color",ColorRGBA.BlackNoAlpha);
                materialTextField.setMaterial(materialTextFieldMat);
                node.attachChild(materialTextField);
                materialTextField.setLocalTranslation(-0.40f,0.60f,0.05f);
                
                Quad materialListBackgroundQuad=new Quad(0.8f,1f);
                Geometry materialListBackground=new Geometry("Material list background",materialListBackgroundQuad);
                Material materialListBackgroundMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
                materialListBackgroundMat.setColor("Color",ColorRGBA.Gray);
                materialListBackground.setMaterial(materialListBackgroundMat);
                node.attachChild(materialListBackground);
                materialListBackground.setLocalTranslation(-0.40f,-0.5f,0.03f);
                
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("Material list");
                texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.9f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                buttons.add(new Button(main,this,53));
                node.setLocalTranslation(new Vector3f(-4.9f,1.2f,-0.3f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                
                texts.add(new BitmapText(font));
                node.attachChild(texts.get(texts.size()-1));
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("");
                texts.get(texts.size()-1).setLocalTranslation(-0.40f,0.73f,0.055f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                
                addMaterials();
                
                break;
                
            case 4://List filters
                
                createBackground(1.15f,1.4f);
                buttons.add(new Button(main,this,5));
                buttons.add(new Button(main,this,6));
                buttons.add(new Button(main,this,7));
                buttons.add(new Button(main,this,57));
                buttons.add(new Button(main,this,58));
                buttons.add(new Button(main,this,45));
                buttons.add(new Button(main,this,54));
                buttons.add(new Button(main,this,55));
                buttons.add(new Button(main,this,56));
                node.setLocalTranslation(new Vector3f(-4.9f,1.5f,-1.4f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                break;
                
                
            
        }
        
        rootNode.attachChild(node);
        
    }
    
    public void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Display background",quad);
        background.setUserData("correctCollision",true);
        background.setUserData("correspondingObject", this);
        backgroundMat=new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",new ColorRGBA(0,255,0,1f));
        backgroundMat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
        background.setQueueBucket(Bucket.Transparent);
        background.setMaterial(backgroundMat);
        
        node.attachChild(background);
        
        background.setLocalTranslation(-quad.getWidth()/2,-quad.getHeight()/2,0f);
        
    }
    
    public Node getNode(){
        
        return node;
        
    }
    
    public float getHeightDimension(){
        
        return quad.getHeight();
        
    }
    
    public float getWidthDimension(){
        
        return quad.getWidth();
        
    }
    
    public boolean addSubstance(){
        
        if(preset==1){
            
            //Add a button for the substance
            
            return true;
            
        }else
            
            return false;
        
    }
    
    public boolean addMaterial(){
        
        if(preset==3){
            
            //Add a button for the material
            
            return true;
            
        }else
            
            return false;
        
    }
    
    public int getPreset(){
        
        return preset;
        
    }
    
    public void addLetter(String letter){
        
        if(preset==1||preset==3){
            
            texts.get(texts.size()-1).setText(texts.get(texts.size()-1).getText()+letter);
        
            if(preset==1){
                
                setSubstanceFilterChanged(true);
                
                updateDisplayedSubstances();
                
            }else if(preset==3){
                
                setMaterialFilterChanged(true);
                
                updateDisplayedMaterials();
                
            }
            
        }
        
    }
    
    public void removeLetter(){
        
        if(preset==1||preset==3){
        
            texts.get(texts.size()-1).setText(texts.get(texts.size()-1).getText()+"\b");
            
            if(preset==1){
                
                setSubstanceFilterChanged(true);
                
                updateDisplayedSubstances();
                
            }else if(preset==3){
                
                setMaterialFilterChanged(true);
                
                updateDisplayedMaterials();
                
            }
            
        }
        
    }
    
    public void removeLastLetter(){
    
        setTextFieldText(getTextField().getText().substring(0,getTextField().getText().length()-1));
        
        if(preset==1){
                
                setSubstanceFilterChanged(true);
                
                updateDisplayedSubstances();
                
            }else if(preset==3){
                
                setMaterialFilterChanged(true);
                
                updateDisplayedMaterials();
                
            }
    
    }
    
    private void addSubstances(){
        
        for(int i=0;i<main.getSubstances().size();i++){
            
            if(i<5){
            
                substanceButtonList.add(new SubstanceButton(main,this,main.getSubstances().get(i),new Vector3f(-0.35f,0.5f-i*0.2f,0.05f)));
            
            }else{
                
                substanceButtonList.add(new SubstanceButton(main,this,main.getSubstances().get(i),new Vector3f(-0.35f,-700,0.05f)));
                
            }
            
            filteredSubstanceButtonList.add(substanceButtonList.get(substanceButtonList.size()-1));
            
        }
        
        //System.out.println(indexOfFirstDisplayedSubstanceButton);
        
    }
    
    public void updateDisplayedSubstances(){
        
        //System.out.println(substanceButtonList.size()-1+" and "+(indexOfFirstDisplayedSubstanceButton+4));
        
        if(getSubstanceFilterChanged()){
            
            indexOfFirstDisplayedSubstanceButton=0;
            
            for(SubstanceButton b: filteredSubstanceButtonList){
                
                b.setPosition(-1);
                
                //System.out.println("substance button: "+b.getSubstance()+" set to -1 because filter changed");
                
                setSubstanceFilterChanged(false);
                
            }
            
        }
        
        //clear the current filtered button list is case of a change
        if(filteredSubstanceButtonList.size()>0){
            
            filteredSubstanceButtonList.clear();
            
        }
        
        if(initFilteredSubstanceButtonList.size()>0){
            
            initFilteredSubstanceButtonList.clear();
            
        }
        
        indexOfLastDisplayedSubstanceButton=indexOfFirstDisplayedSubstanceButton+4;
        
        for(int i=0;i<substanceButtonList.size();i++){
            
            if(substanceButtonList.get(i).getSubstance().getName().contains(getTextField().getText())||substanceButtonList.get(i).getSubstance().getEquation().contains(getTextField().getText())){
                
                initFilteredSubstanceButtonList.add(substanceButtonList.get(i));
                
                //System.out.println(substanceButtonList.get(i).getSubstance().getName()+" or "+substanceButtonList.get(i).getSubstance().getEquation()+" contains "+getTextField().getText());
                
            }
            
        }
        
        for(int i=0;i<initFilteredSubstanceButtonList.size();i++){
            
            if(typeFilter==0&&phaseFilter==0){
                
                filteredSubstanceButtonList.add(initFilteredSubstanceButtonList.get(i));
                
            }else if(typeFilter!=0&&phaseFilter==0&&(typeFilter)==initFilteredSubstanceButtonList.get(i).getSubstance().getTypeInteger()){
                
                filteredSubstanceButtonList.add(initFilteredSubstanceButtonList.get(i));
                
            }else if(phaseFilter!=0&&typeFilter==0&&(phaseFilter-1)==initFilteredSubstanceButtonList.get(i).getSubstance().getStateInteger(298)){
                    
                filteredSubstanceButtonList.add(initFilteredSubstanceButtonList.get(i));
                    
            }else if(phaseFilter!=0&&typeFilter!=0&&(phaseFilter-1)==initFilteredSubstanceButtonList.get(i).getSubstance().getStateInteger(298)&&(typeFilter)==initFilteredSubstanceButtonList.get(i).getSubstance().getTypeInteger()){
                    
                filteredSubstanceButtonList.add(initFilteredSubstanceButtonList.get(i));
                    
            }
            
        }
        
        for(int i=indexOfFirstDisplayedSubstanceButton;i<filteredSubstanceButtonList.size();i++){
            
            filteredSubstanceButtonList.get(i).setPosition(i-indexOfFirstDisplayedSubstanceButton);
            
        }
        
        //if there is a button before the first one displayed, make it dissapear
        if(indexOfFirstDisplayedSubstanceButton!=0){
            
            filteredSubstanceButtonList.get(indexOfFirstDisplayedSubstanceButton-1).setPosition(-1);
            
            //System.out.println("substance button before first diaplyed: "+filteredSubstanceButtonList.get(indexOfFirstDisplayedSubstanceButton-1).getSubstance().getName()+"'s position set to -1");
            
        }
        
        //if there is a button after the last button displayed, make it dissapear
        if(indexOfFirstDisplayedSubstanceButton+5<filteredSubstanceButtonList.size()-1){
            
            filteredSubstanceButtonList.get(indexOfFirstDisplayedSubstanceButton+5).setPosition(-1);
            
            //System.out.println(filteredSubstanceButtonList.get(indexOfFirstDisplayedSubstanceButton+5).getSubstance().getName()+"'s position set to -1");
            
        }
        
        /*
        indexOfLastDisplayedSubstanceButton=indexOfFirstDisplayedSubstanceButton+4;
        
        presentPositionIndex=0;
        
        for(int i=indexOfFirstDisplayedSubstanceButton;i<substanceButtonList.size();i++){
            
            if(typeFilter==0&&phaseFilter==0){
                
                substanceButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedSubstanceButton);
                
                displayedSubstanceButtonList.add(substanceButtonList.get(i));
                
                presentPositionIndex++;
                
            }else if(typeFilter!=0&&(typeFilter-1)==substanceButtonList.get(i).getSubstance().getTypeInteger()){
                
                if(phaseFilter!=0&&(phaseFilter-1)==substanceButtonList.get(i).getSubstance().getStateInteger(298)){
                
                    substanceButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedSubstanceButton);
                    
                    displayedSubstanceButtonList.add(substanceButtonList.get(i));
                    
                    presentPositionIndex++;
                    
                }else if(phaseFilter==0){
                    
                    substanceButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedSubstanceButton);
                    
                    displayedSubstanceButtonList.add(substanceButtonList.get(i));
                    
                    presentPositionIndex++;
                    
                }
                
            }
        }
        */
        
        //System.out.println("AFTER: "+indexOfFirstDisplayedSubstanceButton);
        
    }
    
    public void updateDisplayedSubstancesWithoutFiltering(){
        
        indexOfLastDisplayedSubstanceButton=indexOfFirstDisplayedSubstanceButton+4;
        
        for(int i=indexOfFirstDisplayedSubstanceButton;i<filteredSubstanceButtonList.size();i++){
            
            filteredSubstanceButtonList.get(i).setPosition(i-indexOfFirstDisplayedSubstanceButton);
            
        }
        
        //if there is a button before the first one displayed, make it dissapear
        if(indexOfFirstDisplayedSubstanceButton!=0){
            
            filteredSubstanceButtonList.get(indexOfFirstDisplayedSubstanceButton-1).setPosition(-1);
            
            //System.out.println("substance button before first diaplyed: "+filteredSubstanceButtonList.get(indexOfFirstDisplayedSubstanceButton-1).getSubstance().getName()+"'s position set to -1");
            
        }
        
        //if there is a button after the last button displayed, make it dissapear
        if(indexOfFirstDisplayedSubstanceButton+5<filteredSubstanceButtonList.size()-1){
            
            filteredSubstanceButtonList.get(indexOfFirstDisplayedSubstanceButton+5).setPosition(-1);
            
            //System.out.println(filteredSubstanceButtonList.get(indexOfFirstDisplayedSubstanceButton+5).getSubstance().getName()+"'s position set to -1");
            
        }
        
    }
    
    public void setIndexOfFirstDisplayedSubstanceButton(int index){
        
        //System.out.println("BEFORE: "+indexOfFirstDisplayedSubstanceButton);
        
        indexOfFirstDisplayedSubstanceButton=index;
        
    }
    
    public int getIndexOfFirstDisplayedSubstanceButton(){
        
        return indexOfFirstDisplayedSubstanceButton;
        
    }
    
    public int getIndexOfLastDisplayedSubstanceButton(){
        
        return indexOfLastDisplayedSubstanceButton;
        
    }
    
    private void addMaterials(){
            
        materialButtonList.add(new MaterialButton(main,this,"Beaker",new Vector3f(-0.35f,0.5f,0.05f)));
        filteredMaterialButtonList.add(materialButtonList.get(materialButtonList.size()-1));
        materialButtonList.add(new MaterialButton(main,this,"Gas sac",new Vector3f(-0.35f,0.3f,0.05f)));
        filteredMaterialButtonList.add(materialButtonList.get(materialButtonList.size()-1));

        //materialButtonList.add(new MaterialButton(main,this,name,new Vector3f(-0.35f,-700,0.05f)));
        
    }
    
    public ArrayList<MaterialButton> getMaterialButtonList(){
        
        return materialButtonList;
        
    }
    
    public ArrayList<SubstanceButton> getSubstanceButtonList(){
        
        return substanceButtonList;
        
    }
    
    public SubstanceButton getSelectedSubstanceButton(){
        
        return selectedSubstanceButton;
        
    }
    
    public void setSelectedSubstanceButton(SubstanceButton substanceButton){
        
        selectedSubstanceButton=substanceButton;        
    }
    
    public MaterialButton getSelectedMaterialButton(){
        
        return selectedMaterialButton;
        
    }
    
    public void setSelectedMaterialButton(MaterialButton materialButton){
        
        selectedMaterialButton=materialButton;        
    }
    
    public void updateDisplayedMaterials(){
        
        //System.out.println(materialButtonList.size()-1+" and "+(indexOfFirstDisplayedMaterialButton+4));
        
        if(getMaterialFilterChanged()){
            
            indexOfFirstDisplayedMaterialButton=0;
            
            for(MaterialButton b: filteredMaterialButtonList){
                
                b.setPosition(-1);
                
                //System.out.println("Material button: "+b.getName()+" set to -1 because filter changed");
                
                setMaterialFilterChanged(false);
                
            }
            
        }
        
        //clear the current filtered button list is case of a change
        if(filteredMaterialButtonList.size()>0){
            
            filteredMaterialButtonList.clear();
            
        }
        
        if(initFilteredMaterialButtonList.size()>0){
            
            initFilteredMaterialButtonList.clear();
            
        }
        
        indexOfLastDisplayedMaterialButton=indexOfFirstDisplayedMaterialButton+4;
        
        for(int i=0;i<materialButtonList.size();i++){
            
            if(materialButtonList.get(i).getName().contains(getTextField().getText())){
                
                initFilteredMaterialButtonList.add(materialButtonList.get(i));
                
            }
            
        }
        
        for(int i=0;i<initFilteredMaterialButtonList.size();i++){
            
            if(classFilter==0){
                
                filteredMaterialButtonList.add(materialButtonList.get(i));
                
            }else if(classFilter!=0&&classFilter==materialButtonList.get(i).getClassInteger()){
                
                filteredMaterialButtonList.add(materialButtonList.get(i));
                
            }
            
        }
        
        for(int i=indexOfFirstDisplayedMaterialButton;i<filteredMaterialButtonList.size();i++){
            
            filteredMaterialButtonList.get(i).setPosition(i-indexOfFirstDisplayedMaterialButton);
            
        }
        
        //if there is a button before the first one displayed, make it dissapear
        if(indexOfFirstDisplayedMaterialButton!=0){
            
            filteredMaterialButtonList.get(indexOfFirstDisplayedMaterialButton-1).setPosition(-1);
            
            //System.out.println("Material button before first diaplyed: "+filteredMaterialButtonList.get(indexOfFirstDisplayedMaterialButton-1).getName()+"'s position set to -1");
            
        }
        
        //if there is a button after the last button displayed, make it dissapear
        if(indexOfFirstDisplayedMaterialButton+5<filteredMaterialButtonList.size()-1){
            
            filteredMaterialButtonList.get(indexOfFirstDisplayedMaterialButton+5).setPosition(-1);
            
            //System.out.println(filteredMaterialButtonList.get(indexOfFirstDisplayedMaterialButton+5).getName()+"'s position set to -1");
            
        }
        
        /*
        indexOfLastDisplayedMaterialButton=indexOfFirstDisplayedMaterialButton+4;
        
        presentPositionIndex=0;
        
        for(int i=indexOfFirstDisplayedMaterialButton;i<materialButtonList.size()&&i<=indexOfFirstDisplayedMaterialButton+4;i++){
            
            if(classFilter==0){
                
                displayedMaterialButtonList.add(materialButtonList.get(i));
                
                presentPositionIndex++;
                
            }else if(classFilter!=0&&(typeFilter-1)==materialButtonList.get(i).getClassInteger()){
                
                materialButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedMaterialButton);
                
                displayedMaterialButtonList.add(materialButtonList.get(i));
                
                presentPositionIndex++;
                
            }
            
        }
        */
        
        //System.out.println("AFTER: "+indexOfFirstDisplayedMaterialButton);
        
    }
    
    public void updateDisplayedMaterialWithoutFiltering(){
        
        indexOfLastDisplayedMaterialButton=indexOfFirstDisplayedMaterialButton+4;
        
        for(int i=indexOfFirstDisplayedMaterialButton;i<filteredMaterialButtonList.size();i++){
            
            filteredMaterialButtonList.get(i).setPosition(i-indexOfFirstDisplayedMaterialButton);
            
        }
        
        //if there is a button before the first one displayed, make it dissapear
        if(indexOfFirstDisplayedMaterialButton!=0){
            
            filteredMaterialButtonList.get(indexOfFirstDisplayedMaterialButton-1).setPosition(-1);
            
            //System.out.println("Material button before first diaplyed: "+filteredMaterialButtonList.get(indexOfFirstDisplayedMaterialButton-1).getName()+"'s position set to -1");
            
        }
        
        //if there is a button after the last button displayed, make it dissapear
        if(indexOfFirstDisplayedMaterialButton+5<filteredMaterialButtonList.size()-1){
            
            filteredMaterialButtonList.get(indexOfFirstDisplayedMaterialButton+5).setPosition(-1);
            
            //System.out.println(filteredMaterialButtonList.get(indexOfFirstDisplayedMaterialButton+5).getName()+"'s position set to -1");
            
        }
        
    }
    
    public void setIndexOfFirstDisplayedMaterialButton(int index){
        
        //System.out.println("BEFORE: "+indexOfFirstDisplayedMaterialButton);
        
        indexOfFirstDisplayedMaterialButton=index;
        
    }
    
    public int getIndexOfFirstDisplayedMaterialButton(){
        
        return indexOfFirstDisplayedMaterialButton;
        
    }
    
    public int getIndexOfLastDisplayedMaterialButton(){
        
        return indexOfLastDisplayedMaterialButton;
        
    }
    
    public BitmapText getTextField(){
        
        return texts.get(texts.size()-1);
        
    }
    
    public void setTextFieldText(String text){
        
        texts.get(texts.size()-1).setText(text);
        
    }
    
    public static void setTypeFilter(int typeFilter){
        
        Display.typeFilter=typeFilter;
        
        setSubstanceFilterChanged(true);
        
    }
    
    public static void setPhaseFilter(int phaseFilter){
        
        Display.phaseFilter=phaseFilter;
        
        setSubstanceFilterChanged(true);
        
    }
    
    public static void setClassFilter(int classFilter){
        
        Display.classFilter=classFilter;
        
        setMaterialFilterChanged(true);
        
    }
    
    public ArrayList<MaterialButton> getFilteredMaterialButtonList(){
        
        return filteredMaterialButtonList;
        
    }
    
    public ArrayList<SubstanceButton> getFilteredSubstanceButtonList(){
        
        return filteredSubstanceButtonList;
        
    }
    
    public static void setSubstanceFilterChanged(boolean substanceFilterChanged){
        
        Display.substanceFilterChanged=substanceFilterChanged;
        
    }
    
    public static boolean getSubstanceFilterChanged(){
        
        return Display.substanceFilterChanged;
        
    }
    
    public static void setMaterialFilterChanged(boolean materialFilterChanged){
        
        Display.materialFilterChanged=materialFilterChanged;
        
    }
    
    public static boolean getMaterialFilterChanged(){
        
        return Display.materialFilterChanged;
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
            
    
}
