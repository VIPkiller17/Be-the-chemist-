/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.jme3.math.Vector3f;

/**
 *
 * @author VIPkiller17
 */
public interface Heatable {
    
    void setTemperature(double temperature);
    
    double getTemperature();
    
    void addKelvin(double kelvin);
    
    void removeKelvin(double kelvin);
    
    Vector3f getPosition();
    
}
