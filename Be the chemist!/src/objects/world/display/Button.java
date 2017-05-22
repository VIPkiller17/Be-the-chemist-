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
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Quad;
import interfaces.Grabbable;
import java.io.IOException;
import java.util.ArrayList;
import objects.PhysicalObject;
import objects.containers.Container;
import objects.containers.beaker.Beaker;
import objects.containers.gasSac.GasSac;
import objects.player.Hand;
import objects.solution.Solution;
import main.Main;
import objects.containers.erlenmeyer.Erlenmeyer;
import objects.containers.testTube.TestTube;

/**
 *
 * @author VIPkiller17
 */
public class Button implements Savable{
    
    private static final ColorRGBA GRAYED_OUT=ColorRGBA.Gray;
    
    private boolean grayedOut;
    
    private static Display substanceList,materialList;
    
    private static ArrayList<Button> stateFilterButtons=new ArrayList<>();
    
    private AssetManager assetManager;
    
    private Main main;
    
    private Display parentDisplay;
    
    private BitmapText text;
    
    private BitmapFont font;
    
    private Quad quad;
    private Geometry background;
    private Material backgroundMat;
    
    private Node node;
    
    private boolean pointed;
    
    private boolean toggle;
    private boolean toggleActivated;
    
    private static boolean capsActivated;
    
    private static Display keyBoardSelectedDisplay;
    
    private static final ColorRGBA POINTED_COLOR=new ColorRGBA(0,255,255,1f);
    private static final ColorRGBA NORMAL_COLOR=new ColorRGBA(0,0,255,1f);
    
    private ColorRGBA presentBackgroundColor;
    
    private int preset;
    
    /*
    //Main menu
    public static final int TOGGLE_MODE=0,CREDITS=1,EXIT_GAME=2;
        //Settings TODO
    //Substance list, material list
    public static final int GET_SUBSTANCE=3,GET_MATERIAL=53;
        //Filters
    public static final int GAS=5,LIQUID=6,SOLID=7,SUBSTANCE_PAGE_UP=45,SUBSTANCE_PAGE_DOWN=54,MATERIAL_PAGE_UP=55,MATERIAL_PAGE_DOWN=56;
    public static final int TYPE=57,CLASS=58;
            //Keyboard
    public static final int SWITCH_SELECTED_DISPLAY=4;
    public static final int KEY_Q=8,KEY_W=9,KEY_E=10,KEY_R=11,KEY_T=12,KEY_Y=13,KEY_U=14,KEY_I=15,KEY_O=16,KEY_P=17;
    public static final int KEY_A=18,KEY_S=19,KEY_D=20,KEY_F=21,KEY_G=22,KEY_H=23,KEY_J=24,KEY_K=25,KEY_L=26,KEY_Z=27;
    public static final int KEY_X=28,KEY_C=29,KEY_V=30,KEY_B=31,KEY_N=32,KEY_M=33,KEY_1=34,KEY_2=35,KEY_3=36,KEY_4=37;
    public static final int KEY_5=38,KEY_6=39,KEY_7=40,KEY_8=41,KEY_9=42,KEY_0=43,KEY_BACKSPACE=44,KEY_SPACE=46,KEY_DASH=47;
    public static final int KEY_OPEN_PARENTHESES=48,KEY_CLOSE_PARENTHESES=49,KEY_CAPS=50,KEY_COMMA=51,KEY_PERIOD=52;
    */
    
    public Button(Main main,Display parentDisplay,int preset){
        
        this.main=main;
        this.assetManager=main.getAssetManager();
        this.parentDisplay=parentDisplay;
        node=new Node();
        font=assetManager.loadFont("Interface/Fonts/Xolonium/Xolonium.fnt");
        text=new BitmapText(font);
        node.attachChild(text);
        
        this.preset=preset;
        
        setup(preset);
        
    }
    
    public void setup(int preset){
        
        switch(preset){
            
            case 0:
                
                text.setSize(0.05f);
                text.setText("   Toggle mode\n(Sandbox mode)");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2)+0.02f,0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,0.25f,0.05f);
                break;
                
                
            case 1:
                
                /*
                text.setSize(0.08f);
                text.setText("Credits");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.2f,0.05f);
                */
                break;
                
            case 2:
                
                text.setSize(0.08f);
                text.setText("Exit");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.65f,0.05f);
                break;
                
            case 3:
                
                text.setSize(0.08f);
                text.setText("Get selected item");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.85f,0.05f);
                
                substanceList=parentDisplay;
                
                keyBoardSelectedDisplay=parentDisplay;
                
                break;
                
            case 4:
                
                toggle=true;
                text.setSize(0.08f);
                text.setText("\u2192");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,0.4f,0.05f);
                stateFilterButtons.add(this);
                break;
                
            case 5:
                
                toggle=true;
                text.setSize(0.08f);
                text.setText("Gases");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.06f,0.05f);
                stateFilterButtons.add(this);
                
                //System.out.println("gases filter button added to stateFilterButtons, its size id now: "+stateFilterButtons.size());
                
                break;
                
            case 6:
                
                toggle=true;
                text.setSize(0.08f);
                text.setText("Liquid");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.19f,0.05f);
                stateFilterButtons.add(this);
                
                //System.out.println("liquid filter button added to stateFilterButtons, its size id now: "+stateFilterButtons.size());
                
                break;
                
            case 7:
                
                toggle=true;
                text.setSize(0.08f);
                text.setText("Solid");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.32f,0.05f);
                stateFilterButtons.add(this);
                
                //System.out.println("solid filter button added to stateFilterButtons, its size id now: "+stateFilterButtons.size());
                
                break;
                
            case 8:
                
                text.setSize(0.08f);
                text.setText("Q");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.495f,0.03f,0.01f);
                
                //keyBoardSelectedDisplay=parentDisplay;
                
                break;
                
            case 9:
                
                text.setSize(0.08f);
                text.setText("W");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.385f,0.03f,0.01f);
                break;
                
            case 10:
                
                text.setSize(0.08f);
                text.setText("E");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.275f,0.03f,0.01f);
                break;
                
            case 11:
                
                text.setSize(0.08f);
                text.setText("R");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.165f,0.03f,0.01f);
                break;
                
            case 12:
                
                text.setSize(0.08f);
                text.setText("T");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.055f,0.03f,0.01f);
                break;
                
            case 13:
                
                text.setSize(0.08f);
                text.setText("Y");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.055f,0.03f,0.01f);
                break;
                
            case 14:
                
                text.setSize(0.08f);
                text.setText("U");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.165f,0.03f,0.01f);
                break;
                
            case 15:
                
                text.setSize(0.08f);
                text.setText("I");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.275f,0.03f,0.01f);
                break;
                
            case 16:
                
                text.setSize(0.08f);
                text.setText("O");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.385f,0.03f,0.01f);
                break;
                
            case 17:
                
                text.setSize(0.08f);
                text.setText("P");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.495f,0.03f,0.01f);
                break;
                
            case 18:
                
                text.setSize(0.08f);
                text.setText("A");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.44f,-0.08f,0.01f);
                break;
                
            case 19:
                
                text.setSize(0.08f);
                text.setText("S");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.33f,-0.08f,0.01f);
                break;
                
            case 20:
                
                text.setSize(0.08f);
                text.setText("D");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.22f,-0.08f,0.01f);
                break;
                
            case 21:
                
                text.setSize(0.08f);
                text.setText("F");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.11f,-0.08f,0.01f);
                break;
                
            case 22:
                
                text.setSize(0.08f);
                text.setText("G");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0f,-0.08f,0.01f);
                break;
                
            case 23:
                
                text.setSize(0.08f);
                text.setText("H");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.11f,-0.08f,0.01f);
                break;
                
            case 24:
                
                text.setSize(0.08f);
                text.setText("J");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.22f,-0.08f,0.01f);
                break;
                
            case 25:
                
                text.setSize(0.08f);
                text.setText("K");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.33f,-0.08f,0.01f);
                break;
                
            case 26:
                
                text.setSize(0.08f);
                text.setText("L");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.44f,-0.08f,0.01f);
                break;
                
            case 27:
                
                text.setSize(0.08f);
                text.setText("Z");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.385f,-0.19f,0.01f);
                break;
                
            case 28:
                
                text.setSize(0.08f);
                text.setText("X");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.275f,-0.19f,0.01f);
                break;
                
            case 29:
                
                text.setSize(0.08f);
                text.setText("C");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.165f,-0.19f,0.01f);
                break;
                
            case 30:
                
                text.setSize(0.08f);
                text.setText("V");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.055f,-0.19f,0.01f);
                break;
                
            case 31:
                
                text.setSize(0.08f);
                text.setText("B");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.055f,-0.19f,0.01f);
                break;
                
            case 32:
                
                text.setSize(0.08f);
                text.setText("N");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.165f,-0.19f,0.01f);
                break;
                
            case 33:
                
                text.setSize(0.08f);
                text.setText("M");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.275f,-0.19f,0.01f);
                break;
                
            case 34:
                
                text.setSize(0.08f);
                text.setText("1");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.495f,0.14f,0.01f);
                break;
                
            case 35:
                
                text.setSize(0.08f);
                text.setText("2");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.385f,0.14f,0.01f);
                break;
                
            case 36:
                
                text.setSize(0.08f);
                text.setText("3");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.275f,0.14f,0.01f);
                break;
                
            case 37:
                
                text.setSize(0.08f);
                text.setText("4");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.165f,0.14f,0.01f);
                break;
                
            case 38:
                
                text.setSize(0.08f);
                text.setText("5");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.055f,0.14f,0.01f);
                break;
                
            case 39:
                
                text.setSize(0.08f);
                text.setText("6");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.055f,0.14f,0.01f);
                break;
                
            case 40:
                
                text.setSize(0.08f);
                text.setText("7");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.165f,0.14f,0.01f);
                break;
                
            case 41:
                
                text.setSize(0.08f);
                text.setText("8");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.275f,0.14f,0.01f);
                break;
                
            case 42:
                
                text.setSize(0.08f);
                text.setText("9");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.385f,0.14f,0.01f);
                break;
                
            case 43:
                
                text.setSize(0.08f);
                text.setText("0");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.495f,0.14f,0.01f);
                break;
                
            case 44:
                
                text.setSize(0.08f);
                text.setText(" < ");
                createBackground(0.14f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.405f,-0.19f,0.01f);
                break;
                
            case 45:
                
                text.setSize(0.08f);
                text.setText("\u2191");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,-((parentDisplay.getHeightDimension()/2)+quad.getHeight())+0.33f,0.05f);
                break;
                
            case 46:
                
                text.setSize(0.08f);
                text.setText(" ");
                createBackground(0.30f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0f,-0.30f,0.01f);
                break;
                
            case 47:
                
                text.setSize(0.08f);
                text.setText("-");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.21f,-0.30f,0.01f);
                break;
                
            case 48:
                
                text.setSize(0.08f);
                text.setText("(");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.43f,-0.30f,0.01f);
                break;
                
            case 49:
                
                text.setSize(0.08f);
                text.setText(")");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.32f,-0.30f,0.01f);
                break;
                
            case 50:
                
                toggle=true;
                text.setSize(0.08f);
                text.setText("\u2191");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-0.495f,-0.19f,0.01f);
                
                capsActivated=false;
                
                break;
                
            case 51:
                
                text.setSize(0.08f);
                text.setText(",");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.21f,-0.30f,0.01f);
                break;
                
            case 52:
                
                text.setSize(0.08f);
                text.setText(".");
                createBackground(0.1f,0.1f);
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.001f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0.32f,-0.30f,0.01f);
                break;
                
            case 53:
                
                text.setSize(0.08f);
                text.setText("Get selected item");
                createBackground(0.2f+text.getLineWidth(),0.2f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(0,-0.85f,0.05f);
                
                materialList=parentDisplay;
                
                break;
                
            case 54:
                
                text.setSize(0.08f);
                text.setText("\u2193");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,-((parentDisplay.getHeightDimension()/2)+quad.getHeight())+0.20f,0.05f);
                break;
                
            case 55:
                
                text.setSize(0.08f);
                text.setText("\u2191");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-(parentDisplay.getWidthDimension()/2)+(quad.getWidth()/2)+0.05f,-((parentDisplay.getHeightDimension()/2)+quad.getHeight())+0.33f,0.05f);
                break;
                
            case 56:
                
                text.setSize(0.08f);
                text.setText("\u2193");
                createBackground(0.3f,0.02f+text.getLineHeight());
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-(parentDisplay.getWidthDimension()/2)+(quad.getWidth()/2)+0.05f,-((parentDisplay.getHeightDimension()/2)+quad.getHeight())+0.20f,0.05f);
                break;
                
            case 57:
                
                text.setSize(0.05f);
                text.setText("Type (None)");
                createBackground(0.53f,0.02f+text.getLineHeight());
                
                //System.out.println("Type quad width: "+quad.getWidth());
                
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation((parentDisplay.getWidthDimension()/2)-(quad.getWidth()/2)-0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.45f,0.05f);
                break;
                
            case 58:
                
                text.setSize(0.05f);
                text.setText("Class (None)");
                createBackground(0.49f,0.02f+text.getLineHeight());
                
                //System.out.println("Class quad width: "+quad.getWidth());
                
                text.setLocalTranslation(-text.getLineWidth()/2,(quad.getHeight()/2)+(text.getLineHeight()/2),0.01f);
                text.setQueueBucket(RenderQueue.Bucket.Translucent);
                node.setLocalTranslation(-(parentDisplay.getWidthDimension()/2)+(quad.getWidth()/2)+0.05f,((parentDisplay.getHeightDimension()/2)-quad.getHeight())-0.06f,0.05f);
                break;
            
        }
        
        if(background!=null&&text!=null){
        
            node.attachChild(background);
            node.attachChild(text);
            
        }
        
        parentDisplay.getNode().attachChild(node);
        
    }
    
    public void createBackground(float width,float height){
        
        quad=new Quad(width,height);
        background=new Geometry("Button background",quad);
        background.setUserData("correctCollision",true);
        background.setUserData("correspondingObject", this);
        background.setLocalTranslation(-width/2,0,0);
        backgroundMat=new Material(assetManager,"Common/MatDefs/Misc/Unshaded.j3md");
        backgroundMat.setColor("Color",new ColorRGBA(0,0,255,0.7f));
        presentBackgroundColor=NORMAL_COLOR;
        background.setMaterial(backgroundMat);
        
    }
    
    public boolean isPointed(){
        
        return pointed;
        
    }
    
    public void setPointed(boolean pointed){
        
        this.pointed=pointed;
        
        //System.out.println("Button with preset: "+preset+" is pointed: "+pointed);
        
        if(!grayedOut){
        
            if(!toggle){

                if(pointed){

                    backgroundMat.setColor("Color", POINTED_COLOR);
                    presentBackgroundColor=POINTED_COLOR;

                }else{

                    backgroundMat.setColor("Color", NORMAL_COLOR);
                    presentBackgroundColor=NORMAL_COLOR;

                }
                
            }
            
        }
        
    }
    
    public void setToggleActivated(boolean toggleActivated){
        
        this.toggleActivated=toggleActivated;
        
    }
    
    public boolean getToggleActivated(){
        
        return toggleActivated;
        
    }
    
    public boolean isToggle(){
        
        return toggle;
        
    }
    
    public void activate(Hand hand){
        
        //System.out.println("Button activate() called on preset: "+preset);
        
        if(!grayedOut){
        
            switch(preset){

                case 0:

                    /*
                    if(text.getText().equals("Toggle mode\n(Sandbox mode)"))

                        text.setText("Toggle mode\n(Career mode)");

                    else if(text.getText().equals("Toggle mode\n(Career mode)"))

                        text.setText("Toggle mode\n(Sandbox mode)");

                    //ALSO MAKE IT SO THE MODE ACTUALLY GETS CHANGED
                    */

                    break;


                case 1:

                    //PROBABLY JUST DISPLAY THE CREDITS IN THE GUINODE

                    break;

                case 2:

                    System.exit(0);
                    break;

                case 3:

                    //WILL NEED TO ADD LOGIC BEHIND SUBSTANCE LIST FIRST

                    if(parentDisplay.getSelectedSubstanceButton()!=null){

                        if(hand.isHoldingObject()&&hand.getHeldObject() instanceof Container){

                            if(parentDisplay.getSelectedSubstanceButton().getSubstance().getStateInteger(298)==0&&((Container)hand.getHeldObject()).canContain(0)){

                                ((Container)hand.getHeldObject()).mergeSolution(new Solution(main,((Container)hand.getHeldObject()),parentDisplay.getSelectedSubstanceButton().getSubstance(),1,298));

                            }else if(parentDisplay.getSelectedSubstanceButton().getSubstance().getStateInteger(298)==1&&((Container)hand.getHeldObject()).canContain(1)){

                                ((Container)hand.getHeldObject()).mergeSolution(new Solution(main,((Container)hand.getHeldObject()),parentDisplay.getSelectedSubstanceButton().getSubstance(),1,298));

                            }else if(parentDisplay.getSelectedSubstanceButton().getSubstance().getStateInteger(298)==2&&((Container)hand.getHeldObject()).canContain(2)){

                                ((Container)hand.getHeldObject()).mergeSolution(new Solution(main,((Container)hand.getHeldObject()),parentDisplay.getSelectedSubstanceButton().getSubstance(),1,298));

                            }

                        }else if(!hand.isHoldingObject()){

                            System.out.println("Get selected substance button pressed, and hand is empty, spawning "+parentDisplay.getSelectedSubstanceButton().getSubstance());
                            
                            switch (parentDisplay.getSelectedSubstanceButton().getSubstance().getStateInteger(298)) {
                                case 0:
                                    hand.setHeldObject(new GasSac(main,hand.getWorldTranslation(),new Solution(main,((Container)hand.getHeldObject()),parentDisplay.getSelectedSubstanceButton().getSubstance(),1,298)));
                                    break;
                                case 1:
                                    hand.setHeldObject(new Beaker(main,hand.getWorldTranslation(),new Solution(main,((Container)hand.getHeldObject()),parentDisplay.getSelectedSubstanceButton().getSubstance(),1,298)));
                                    break;
                                case 2:
                                    System.out.println("    Substance is solid");
                                    hand.setHeldObject(new Beaker(main,hand.getWorldTranslation(),new Solution(main,((Container)hand.getHeldObject()),parentDisplay.getSelectedSubstanceButton().getSubstance(),100,298)));
                                    break;
                                default:
                                    System.out.println("ERROR: Invalid getGetStateInteger() return value in activate() of get substance item button with name: "+parentDisplay.getSelectedSubstanceButton().getSubstance().getName());
                                    break;
                            }

                            ((Grabbable)hand.getHeldObject()).highlightVisible(false);

                        }

                    }

                    break;

                case 4:

                    //System.out.println("Selected dislay switch button pressed, text is: "+text.getText()+", checking is equal to \u2192");

                    if(text.getText().equals("\u2192")){

                        //System.out.println("Text is equal");

                        text.setText("\u2190");

                        keyBoardSelectedDisplay=materialList;

                    }else{

                        //System.out.println("Text is not equal");

                        text.setText("\u2192");

                        keyBoardSelectedDisplay=substanceList;

                    }

                    break;

                case 5:

                    if(presentBackgroundColor==NORMAL_COLOR){

                        backgroundMat.setColor("Color", POINTED_COLOR);
                        presentBackgroundColor=POINTED_COLOR;

                        //System.out.println("stateFilterButtons list size: "+stateFilterButtons.size());

                        for(Button b: stateFilterButtons){

                            //System.out.println("Checking if present button b:"+b.getPreset()+" is a different button than this one: "+getPreset());

                            if(!b.equals(this)){

                                //System.out.println("It is, setting its color to normal");

                                b.setColor(NORMAL_COLOR);

                            }

                        }

                        Display.setPhaseFilter(1);

                    }else{

                        backgroundMat.setColor("Color", NORMAL_COLOR);
                        presentBackgroundColor=NORMAL_COLOR;

                        Display.setPhaseFilter(0);

                    }

                    substanceList.updateDisplayedSubstances();

                    break;

                case 6:

                    if(presentBackgroundColor==NORMAL_COLOR){

                        backgroundMat.setColor("Color", POINTED_COLOR);
                        presentBackgroundColor=POINTED_COLOR;

                        for(Button b: stateFilterButtons){

                            if(b!=this){

                                b.setColor(NORMAL_COLOR);

                            }

                        }

                        Display.setPhaseFilter(2);

                    }else{

                        backgroundMat.setColor("Color", NORMAL_COLOR);
                        presentBackgroundColor=NORMAL_COLOR;

                        Display.setPhaseFilter(0);

                    }

                    substanceList.updateDisplayedSubstances();

                    break;

                case 7:

                    if(presentBackgroundColor==NORMAL_COLOR){

                        backgroundMat.setColor("Color", POINTED_COLOR);
                        presentBackgroundColor=POINTED_COLOR;

                        for(Button b: stateFilterButtons){

                            if(b!=this){

                                b.setColor(NORMAL_COLOR);

                            }

                        }

                        Display.setPhaseFilter(3);

                    }else{

                        backgroundMat.setColor("Color", NORMAL_COLOR);
                        presentBackgroundColor=NORMAL_COLOR;

                        Display.setPhaseFilter(2);

                    }

                    substanceList.updateDisplayedSubstances();

                    break;

                case 8:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("Q");

                    }else{

                        keyBoardSelectedDisplay.addLetter("q");

                    }

                    break;

                case 9:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("W");

                    }else{

                        keyBoardSelectedDisplay.addLetter("w");

                    }

                    break;

                case 10:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("E");

                    }else{

                        keyBoardSelectedDisplay.addLetter("e");

                    }

                    break;

                case 11:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("R");

                    }else{

                        keyBoardSelectedDisplay.addLetter("r");

                    }

                    break;

                case 12:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("T");

                    }else{

                        keyBoardSelectedDisplay.addLetter("t");

                    }

                    break;

                case 13:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("Y");

                    }else{

                        keyBoardSelectedDisplay.addLetter("y");

                    }

                    break;

                case 14:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("U");

                    }else{

                        keyBoardSelectedDisplay.addLetter("u");

                    }

                    break;

                case 15:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("I");

                    }else{

                        keyBoardSelectedDisplay.addLetter("i");

                    }

                    break;

                case 16:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("O");

                    }else{

                        keyBoardSelectedDisplay.addLetter("o");

                    }

                    break;

                case 17:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("P");

                    }else{

                        keyBoardSelectedDisplay.addLetter("p");

                    }

                    break;

                case 18:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("A");

                    }else{

                        keyBoardSelectedDisplay.addLetter("a");

                    }

                    break;

                case 19:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("S");

                    }else{

                        keyBoardSelectedDisplay.addLetter("s");

                    }

                    break;

                case 20:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("D");

                    }else{

                        keyBoardSelectedDisplay.addLetter("d");

                    }

                    break;

                case 21:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("F");

                    }else{

                        keyBoardSelectedDisplay.addLetter("f");

                    }

                    break;

                case 22:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("G");

                    }else{

                        keyBoardSelectedDisplay.addLetter("g");

                    }

                    break;

                case 23:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("H");

                    }else{

                        keyBoardSelectedDisplay.addLetter("h");

                    }

                    break;

                case 24:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("J");

                    }else{

                        keyBoardSelectedDisplay.addLetter("j");

                    }

                    break;

                case 25:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("K");

                    }else{

                        keyBoardSelectedDisplay.addLetter("k");

                    }

                    break;

                case 26:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("L");

                    }else{

                        keyBoardSelectedDisplay.addLetter("l");

                    }

                    break;

                case 27:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("Z");

                    }else{

                        keyBoardSelectedDisplay.addLetter("z");

                    }

                    break;

                case 28:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("X");

                    }else{

                        keyBoardSelectedDisplay.addLetter("x");

                    }

                    break;

                case 29:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("C");

                    }else{

                        keyBoardSelectedDisplay.addLetter("c");

                    }

                    break;

                case 30:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("V");

                    }else{

                        keyBoardSelectedDisplay.addLetter("v");

                    }

                    break;

                case 31:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("B");

                    }else{

                        keyBoardSelectedDisplay.addLetter("b");

                    }

                    break;

                case 32:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("N");

                    }else{

                        keyBoardSelectedDisplay.addLetter("n");

                    }

                    break;

                case 33:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("M");

                    }else{

                        keyBoardSelectedDisplay.addLetter("m");

                    }

                    break;

                case 34:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2081");

                    }else{

                        keyBoardSelectedDisplay.addLetter("1");

                    }

                    break;

                case 35:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2082");

                    }else{

                        keyBoardSelectedDisplay.addLetter("2");

                    }

                    break;

                case 36:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2083");

                    }else{

                        keyBoardSelectedDisplay.addLetter("3");

                    }

                    break;

                case 37:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2084");

                    }else{

                        keyBoardSelectedDisplay.addLetter("4");

                    }

                    break;

                case 38:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2085");

                    }else{

                        keyBoardSelectedDisplay.addLetter("5");

                    }

                    break;

                case 39:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2086");

                    }else{

                        keyBoardSelectedDisplay.addLetter("6");

                    }

                    break;

                case 40:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2087");

                    }else{

                        keyBoardSelectedDisplay.addLetter("7");

                    }

                    break;

                case 41:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2088");

                    }else{

                        keyBoardSelectedDisplay.addLetter("8");

                    }

                    break;

                case 42:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2089");

                    }else{

                        keyBoardSelectedDisplay.addLetter("9");

                    }

                    break;

                case 43:

                    if(capsActivated){

                        keyBoardSelectedDisplay.addLetter("\u2080");

                    }else{

                        keyBoardSelectedDisplay.addLetter("0");

                    }

                    break;

                case 44:

                    if(keyBoardSelectedDisplay.getTextField().getText().length()>0)

                        keyBoardSelectedDisplay.removeLastLetter();

                    break;

                case 45:

                    if(parentDisplay.getIndexOfFirstDisplayedSubstanceButton()>0){

                        parentDisplay.setIndexOfFirstDisplayedSubstanceButton(parentDisplay.getIndexOfFirstDisplayedSubstanceButton()-1);
                        parentDisplay.updateDisplayedSubstancesWithoutFiltering();

                    }

                    break;

                case 46:

                    keyBoardSelectedDisplay.addLetter(" ");

                    break;

                case 47:

                    keyBoardSelectedDisplay.addLetter("-");

                    break;

                case 48:

                    keyBoardSelectedDisplay.addLetter("(");

                    break;

                case 49:

                    keyBoardSelectedDisplay.addLetter(")");

                    break;

                case 50:

                    if(presentBackgroundColor==NORMAL_COLOR){

                        backgroundMat.setColor("Color", POINTED_COLOR);
                        presentBackgroundColor=POINTED_COLOR;

                    }else{

                        backgroundMat.setColor("Color", NORMAL_COLOR);
                        presentBackgroundColor=NORMAL_COLOR;

                    }

                    capsActivated = !capsActivated;

                    break;

                case 51:

                    keyBoardSelectedDisplay.addLetter(",");

                    break;

                case 52:

                    keyBoardSelectedDisplay.addLetter(".");

                    break;

                case 53:

                    //System.out.println("Get selected item material button pressed");

                    if(parentDisplay.getSelectedMaterialButton()!=null){

                        if(!hand.isHoldingObject()){

                            switch (parentDisplay.getSelectedMaterialButton().getName()) {
                                case "Beaker":
                                    hand.setHeldObject(new Beaker(main,hand.getWorldTranslation()));
                                    break;
                                case "Gas sac":
                                    hand.setHeldObject(new GasSac(main,hand.getWorldTranslation()));
                                    break;
                                case "Erlenmeyer":
                                    hand.setHeldObject(new Erlenmeyer(main,hand.getWorldTranslation()));
                                    break;
                                case "Test tube":
                                    hand.setHeldObject(new TestTube(main,hand.getWorldTranslation()));
                                    break;
                                default:
                                    System.out.println("ERROR: Invalid material button name");
                                    break;
                            }

                            ((Grabbable)hand.getHeldObject()).highlightVisible(false);

                        }       

                    }

                    break;

                case 54:

                    if(parentDisplay.getFilteredSubstanceButtonList().size()>5&&parentDisplay.getIndexOfLastDisplayedSubstanceButton()<parentDisplay.getFilteredSubstanceButtonList().size()-1){

                        //System.out.println("filtered substance button list size: "+parentDisplay.getFilteredSubstanceButtonList().size()+" is higher than 5 and index os last displayed substance button: "+parentDisplay.getIndexOfLastDisplayedSubstanceButton()+" is lower than the last valid index in filteredSusbtanceButtonList: "+(parentDisplay.getFilteredSubstanceButtonList().size()-1));

                        parentDisplay.setIndexOfFirstDisplayedSubstanceButton(parentDisplay.getIndexOfFirstDisplayedSubstanceButton()+1);
                        parentDisplay.updateDisplayedSubstancesWithoutFiltering();

                    }

                    break;

                case 55:

                    if(parentDisplay.getIndexOfFirstDisplayedMaterialButton()>0){

                        parentDisplay.setIndexOfFirstDisplayedMaterialButton(parentDisplay.getIndexOfFirstDisplayedMaterialButton()-1);
                        parentDisplay.updateDisplayedSubstancesWithoutFiltering();

                    }

                    break;

                case 56:

                    if(parentDisplay.getFilteredMaterialButtonList().size()>5&&parentDisplay.getIndexOfLastDisplayedMaterialButton()<parentDisplay.getMaterialButtonList().size()-1){

                        parentDisplay.setIndexOfFirstDisplayedMaterialButton(parentDisplay.getIndexOfFirstDisplayedMaterialButton()+1);
                        parentDisplay.updateDisplayedSubstancesWithoutFiltering();

                    }

                    break;

                case 57:

                    if(text.getText().contains("None")){

                        text.setText("Type (Acid)");

                        Display.setTypeFilter(1);

                    }else if(text.getText().contains("Acid")){

                        text.setText("Type (Base)");

                        Display.setTypeFilter(2);

                    }else if(text.getText().contains("Base")){

                        text.setText("Type (Halogen)");

                        Display.setTypeFilter(3);

                    }else if(text.getText().contains("Halogen")){

                        text.setText("Type (Salt)");

                        Display.setTypeFilter(4);

                    }else if(text.getText().contains("Salt")){

                        text.setText("Type (Metal)");

                        Display.setTypeFilter(5);

                    }else if(text.getText().contains("Metal")){

                        text.setText("Type (Other)");

                        Display.setTypeFilter(6);

                    }else if(text.getText().contains("Other")){

                        text.setText("Type (None)");

                        Display.setTypeFilter(0);

                    }

                    substanceList.updateDisplayedSubstances();

                    break;

                case 58:

                    if(text.getText().contains("None")){

                        text.setText("Class (Container)");

                        Display.setClassFilter(1);

                    }else if(text.getText().contains("Container")){

                        text.setText("Class (Tool)");

                        Display.setClassFilter(2);

                    }else if(text.getText().contains("Tool")){

                        text.setText("Class (None)");

                        Display.setClassFilter(0);

                    }

                    materialList.updateDisplayedMaterials();

                    break;

            }

            if(background!=null&&text!=null){

                node.attachChild(background);
                node.attachChild(text);

            }

            parentDisplay.getNode().attachChild(node);
        
        }
        
    }
    
    public void setColor(ColorRGBA color){
        
        //System.out.println("Setting "+getPreset()+"'s color to normal");
        
        backgroundMat.setColor("Color", color);
        presentBackgroundColor=color;
        
    }
    
    public int getPreset(){
        
        return preset;
        
    }
    
    public void setGrayedOut(boolean grayedOut){
        
        this.grayedOut=grayedOut;
        
        backgroundMat.setColor("Color", GRAYED_OUT);
        
    }
    
    public boolean isGrayedOut(){
        
        return grayedOut;
        
    }

    @Override
    public void write(JmeExporter je) throws IOException {
    }

    @Override
    public void read(JmeImporter ji) throws IOException {
    }
    
    @Override
    public boolean equals(Object otherButton){
        
        if(otherButton instanceof Button)
            
            return ((Button) otherButton).getPreset()==preset;
        
        else
            
            return false;
        
    }
    
    @Override
    public String toString(){
        
        return "Button with preset: "+preset;
        
    }
    
}
