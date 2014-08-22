/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.talend.se.demo;

public class PropertyChecker {
    
    private String prompt;
    
    public PropertyChecker(String prompt) {
        System.out.println("PropertyChecker: constructor: " + prompt);
        this.prompt = prompt;
    }
    
    public String check() {
        System.out.println(prompt);
        return prompt;
    }
    
}
