/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.conceptarea.fpproto;

import com.conceptarea.biometrics.CFingerPrint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 *
 * @author slavik
 */
public class main {

  Map<Integer, String> database;
  private CFingerPrint m_finger = new CFingerPrint();

  private double finger[] = new double[m_finger.FP_TEMPLATE_MAX_SIZE];
  private BufferedImage m_bimage = new BufferedImage(m_finger.FP_IMAGE_WIDTH ,m_finger.FP_IMAGE_HEIGHT,BufferedImage.TYPE_INT_RGB );
  
  public void writeTPL() throws IOException{
    FileWriter writer = new FileWriter("db.txt", false);
    for(int i = 1; i<=1961;i++){
      m_bimage=ImageIO.read(new File("fingerData\\"+i+".bmp")) ;
      m_finger.setFingerPrintImage(m_bimage) ;
      finger= m_finger.getFingerPrintTemplate();
      writer.write("\n"+i);
      writer.write("\n"+m_finger.ConvertFingerPrintTemplateDoubleToString(finger));
      } 
    
    writer.flush();
    writer.close();
  }
    
  public void loadTPL() throws FileNotFoundException, IOException{
    Scanner s = new Scanner(new File("db.txt"));
    database = new HashMap<Integer, String>();
    double []b = null;
    while (s.hasNextLine()){
      database.put(s.nextInt(), s.next());
    }
  }
  
  public static void main(String[] args) throws IOException, InterruptedException{
    // TODO code application logic here
     main m = new main();
    // m.writeTPL();
     m.loadTPL();
     m.m_bimage=ImageIO.read(new File("fingerData\\1961.bmp")) ;
     m.m_finger.setFingerPrintImage(m.m_bimage);
     m.finger= m.m_finger.getFingerPrintTemplate();
     
     int end = m.database.size()/Runtime.getRuntime().availableProcessors();
     int start = 1;
     int step = end+1;
     
     for(int i =0; i<Runtime.getRuntime().availableProcessors(); i++ ){
        Thread thread = new Thread(new FingerPrintMatcher(start,end,m.finger,m.database));
        thread.start();
        start += step;
        end += step;
     }

  }
}