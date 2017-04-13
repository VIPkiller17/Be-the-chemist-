/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.apparatus.chemichalWasteDisposalContainer;

import com.jme3.asset.AssetManager;
import com.jme3.export.Savable;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import main.Main;
import objects.apparatus.Apparatus;

/**
 *
 * @author VIPkiller17
 */
public class ChemicalWasteDisposalContainer extends Apparatus implements Savable{

    private Main main;
    private Spatial spatial;
    private Node rootNode;
    private Node node;
    
    
    public ChemicalWasteDisposalContainer(Main main,AssetManager assetManager,Node rootNode){
        
        super(main,Vector3f.ZERO);
        
        this.main=main;
        this.rootNode=rootNode;
        node=new Node();
        
        spatial=assetManager.loadModel("Models/Static/ChemicalWasteDisposalContainer/ChemicalWasteDisposalContainer.j3o");
        spatial.setName("Chemical waste disposal container");
        spatial.setUserData("correctCollision", true);
        spatial.setUserData("correspondingObject", this);
        node.attachChild(spatial);
        
        rootNode.attachChild(node);
        
    }
    
    @Override
    public String getDescription() {
        
        return "The chemical waste disposal container";
        
    }

    @Override
    public void setPos(Vector3f position) {
        
        spatial.setLocalTranslation(position);
        
    }
    
    @Override
    public Node getNode() {
        
        return node;
        
    }
    
    @Override
    public String getName() {
        
        return "Chemical waste disposal container";
        
    }
    
}
