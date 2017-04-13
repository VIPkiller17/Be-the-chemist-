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
        
        texts=new ArrayList<BitmapText>();
        buttons=new ArrayList<Button>();
        
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
                texts.get(texts.size()-1).setLocalTranslation(-(texts.get(texts.size()-1).getLineWidth()/2),0.9f+(texts.get(texts.size()-1).getLineHeight()/2),0.01f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                buttons.add(new Button(main,this,0));
                buttons.add(new Button(main,this,1));
                buttons.add(new Button(main,this,2));
                node.setLocalTranslation(new Vector3f(-1.1f,1.2f,0.5f));
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
                node.setLocalTranslation(new Vector3f(-1.1f,1.2f,2f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                
                texts.add(new BitmapText(font));
                node.attachChild(texts.get(texts.size()-1));
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("");
                texts.get(texts.size()-1).setLocalTranslation(-0.40f,0.60f,0.055f);
                texts.get(texts.size()-1).setQueueBucket(RenderQueue.Bucket.Translucent);
                
                addSubstances();
                
                break;
                
            case 2://Keyboard
                
                createBackground(1.15f,0.58f);
                for(int i=8;i<53;i++){
                    
                    if(i!=45)
                    
                        buttons.add(new Button(main,this,i));
                    
                }
                node.setLocalTranslation(new Vector3f(-1.1f,0.49f,3.09f));
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
                node.setLocalTranslation(new Vector3f(-1.1f,1.2f,4.18f));
                node.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.PI/2,Vector3f.UNIT_Y));
                
                texts.add(new BitmapText(font));
                node.attachChild(texts.get(texts.size()-1));
                texts.get(texts.size()-1).setSize(0.08f);
                texts.get(texts.size()-1).setText("");
                texts.get(texts.size()-1).setLocalTranslation(-0.40f,0.60f,0.055f);
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
                node.setLocalTranslation(new Vector3f(-1.1f,1.5f,3.09f));
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
        backgroundMat.setColor("Color",new ColorRGBA(0,255,0,0.5f));
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
                
                updateDisplayedSubstances();
                
            }else if(preset==3){
                
                updateDisplayedMaterials();
                
            }
            
        }
        
    }
    
    public void removeLetter(){
        
        if(preset==1||preset==3){
        
            texts.get(texts.size()-1).setText(texts.get(texts.size()-1).getText()+"\b");
            
            if(preset==1){
                
                updateDisplayedSubstances();
                
            }else if(preset==3){
                
                updateDisplayedMaterials();
                
            }
            
        }
        
    }
    
    private void addSubstances(){
        
        for(int i=0;i<main.getSubstances().size();i++){
            
            if(i<5){
            
                substanceButtonList.add(new SubstanceButton(main,this,main.getSubstances().get(i),new Vector3f(-0.35f,0.5f-i*0.2f,0.05f)));
            
            }else{
                
                substanceButtonList.add(new SubstanceButton(main,this,main.getSubstances().get(i),new Vector3f(-0.35f,-700,0.05f)));
                
            }
            
        }
        
        System.out.println(indexOfFirstDisplayedSubstanceButton);
        
    }
    
    public void updateDisplayedSubstances(){
        
        System.out.println(substanceButtonList.size()-1+" and "+(indexOfFirstDisplayedSubstanceButton+4));
        
        //if there is a button before the first one displayed, make it dissapear
        if(indexOfFirstDisplayedSubstanceButton!=0){
            
            substanceButtonList.get(indexOfFirstDisplayedSubstanceButton-1).setPosition(-1);
            
        }
        
        //if there is a button after the last button displayed, make it dissapear
        if(indexOfFirstDisplayedSubstanceButton+5<substanceButtonList.size()-1){
            
            substanceButtonList.get(indexOfFirstDisplayedSubstanceButton+5).setPosition(-1);
            
            System.out.println(substanceButtonList.get(indexOfFirstDisplayedSubstanceButton+5).getSubstance().getName()+"'s position set to -1");
            
        }
        
        indexOfLastDisplayedSubstanceButton=indexOfFirstDisplayedSubstanceButton+4;
        
        presentPositionIndex=0;
        
        for(int i=indexOfFirstDisplayedSubstanceButton;i<substanceButtonList.size()&&i<=indexOfFirstDisplayedSubstanceButton+4;i++){
            
            if(typeFilter==0&&phaseFilter==0){
                
                substanceButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedSubstanceButton);
                
                presentPositionIndex++;
                
            }else if(typeFilter!=0&&(typeFilter-1)==substanceButtonList.get(i).getSubstance().getTypeInteger()){
                
                if(phaseFilter!=0&&(phaseFilter-1)==substanceButtonList.get(i).getSubstance().getStateInteger(298)){
                
                    substanceButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedSubstanceButton);
                    
                    presentPositionIndex++;
                    
                }else if(phaseFilter==0){
                    
                    substanceButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedSubstanceButton);
                    
                    presentPositionIndex++;
                    
                }
                
            }
            
        }
        
        System.out.println("AFTER: "+indexOfFirstDisplayedSubstanceButton);
        
    }
    
    public void setIndexOfFirstDisplayedSubstanceButton(int index){
        
        System.out.println("BEFORE: "+indexOfFirstDisplayedSubstanceButton);
        
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
        materialButtonList.add(new MaterialButton(main,this,"Gas sac",new Vector3f(-0.35f,0.3f,0.05f)));

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
        
        System.out.println(materialButtonList.size()-1+" and "+(indexOfFirstDisplayedMaterialButton+4));
        
        //if there is a button before the first one displayed, make it dissapear
        if(indexOfFirstDisplayedMaterialButton!=0){
            
            materialButtonList.get(indexOfFirstDisplayedMaterialButton-1).setPosition(-1);
            
        }
        
        //if there is a button after the last button displayed, make it dissapear
        if(indexOfFirstDisplayedMaterialButton+5<materialButtonList.size()-1){
            
            materialButtonList.get(indexOfFirstDisplayedMaterialButton+5).setPosition(-1);
            
            System.out.println(materialButtonList.get(indexOfFirstDisplayedMaterialButton+5).getName()+"'s position set to -1");
            
        }
        
        indexOfLastDisplayedMaterialButton=indexOfFirstDisplayedMaterialButton+4;
        
        presentPositionIndex=0;
        
        for(int i=indexOfFirstDisplayedMaterialButton;i<materialButtonList.size()&&i<=indexOfFirstDisplayedMaterialButton+4;i++){
            
            if(classFilter==0){
                
                materialButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedMaterialButton);
                
                presentPositionIndex++;
                
            }else if(classFilter!=0&&(typeFilter-1)==materialButtonList.get(i).getClassInteger()){
                
                materialButtonList.get(i).setPosition(presentPositionIndex-indexOfFirstDisplayedMaterialButton);
                
                presentPositionIndex++;
                
            }
            
        }
        
        System.out.println("AFTER: "+indexOfFirstDisplayedMaterialButton);
        
    }
    
    public void setIndexOfFirstDisplayedMaterialButton(int index){
        
        System.out.println("BEFORE: "+indexOfFirstDisplayedMaterialButton);
        
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
        
    }
    
    public static void setPhaseFilter(int phaseFilter){
        
        Display.phaseFilter=phaseFilter;
        
    }
    
    public static void setClassFilter(int classFilter){
        
        Display.classFilter=classFilter;
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
            
    
}
