/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conceptarea.fpproto;

import com.conceptarea.biometrics.CFingerPrint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author slavik
 */
public class FingerPrintMatcher  implements Runnable{
  private CFingerPrint m_finger = new CFingerPrint();
   
  private int start;
  private int end;
  private double finger[];
  private Map<Integer, String> database;
   FingerPrintMatcher (int start,int end,double finger[],Map<Integer, String> database){
    this.start = start;
    this.end = end;
    this.finger = finger;
    this.database = database;  
   }
    public int checkTPL(){
    for (Integer i = start;i<=end;i++)
     {	
       double [] k = m_finger.ConvertFingerPrintTemplateStringToDouble(database.get(i));
       if(m_finger.Match(finger , k ,100,true) > 90){
           return m_finger.Match(finger , k,100,true); 
       }
     }
      return 0;
    }
    
  @Override
  public void run() {
    System.out.println(checkTPL());
  }
   
}
