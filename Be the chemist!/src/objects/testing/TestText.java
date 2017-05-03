/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.testing;

import com.jme3.font.BitmapText;
import main.Main;

/**
 *
 * @author VIPkiller17
 */
public class TestText {
    
    public TestText(Main main,String text){
        
        BitmapText t=new BitmapText(main.getAssetManager().loadFont("Interface/Fonts/Xolonium/Xolonium.fnt"));
        t.setText(text);
        main.getRootNode().attachChild(t);
        t.setLocalTranslation(-4,0.5f,-5);
        
    }
    
}
