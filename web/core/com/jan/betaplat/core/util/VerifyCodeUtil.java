/**
 * <p>Copyright: Copyright (c) 2012</p>
 * <p>Company: 联动优势科技有限公司</p>
 * <p>2013-5-30下午5:42:04</p>
 * @author Zhang Wensheng
 * @version 1.0
 */
package com.jan.betaplat.core.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** 
 * desc:
 * <p>创建人：Zhang Wensheng 创建日期：2013-5-30 </p>
 * @version V1.0  
 */
public class VerifyCodeUtil{
	private static final int VERIFY_CODE_TYPE_NUMBER = 1;  
    private static final int VERIFY_CODE_TYPE_LETTER_DEFAULT = 2;  
    
    private static final int VERIFY_CODE_CHARS = 4;  
    
    private static final int VERIFY_CODE_WIDTH_DEFAULT = 80; 
    
    private static final int VERIFY_CODE_HEIGHT_DEFAULT = 20;  
      
    public static void create(int type,String sessionVerifyCodeName, HttpServletRequest request, HttpServletResponse response) throws IOException{  
        create(VERIFY_CODE_WIDTH_DEFAULT,VERIFY_CODE_HEIGHT_DEFAULT,type,sessionVerifyCodeName,request,response);  
    }  
      
    public static void create(String sessionVerifyCodeName,HttpServletRequest request, HttpServletResponse response) throws IOException{  
        create(VERIFY_CODE_WIDTH_DEFAULT,VERIFY_CODE_HEIGHT_DEFAULT,VERIFY_CODE_TYPE_LETTER_DEFAULT,sessionVerifyCodeName,request,response);  
    }  
      
    public static void create(int width, int height,int type,String sessionVerifyCodeName, HttpServletRequest request, HttpServletResponse response) throws IOException{  
        response.setContentType("image/jpeg");  
        response.setHeader("Pragma","No-cache");  
        response.setHeader("Cache-Control","no-cache");  
        response.setDateHeader("Expires", 0);        
//        HttpSession session=request.getSession(false);  
          
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics g = image.getGraphics();  
        Random random = new Random();  
          
        Font mFont = new Font("Arial", Font.PLAIN, 18);
		g.setColor(getRandColor(140, 220));
		g.fillRect(1, 1, width, height);
		g.setFont(mFont);
		g.setColor(new Color(173, 197, 222));
		g.drawRect(0, 0, width - 1, height - 1);
		for (int i = 0; i < 20; i++) {
			g.setColor(getRandColor(170, 222));
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl, y + yl);
		}

		for (int i = 0; i < 20; i++) {
			g.setColor(getRandColor(150, 222));
			int x = random.nextInt(width - 1);
			int y = random.nextInt(height - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			g.drawLine(x, y, x - xl, y - yl);
		}

		String sRand = "";
		for (int i = 0; i < VERIFY_CODE_CHARS; i++) {
			String randTemp = "";
			if (VERIFY_CODE_TYPE_NUMBER == type) {
				randTemp = String.valueOf(random.nextInt(10));
				sRand += randTemp;
			} else {
				randTemp = getRandomChar();
				sRand += randTemp;
			}
			g.setColor(new Color(20 + random.nextInt(160), 20 + random
					.nextInt(120), 20 + random.nextInt(150)));
			g.drawString(randTemp, 15 * i + 10, 15);
		}
		request.getSession().setAttribute(sessionVerifyCodeName, sRand);
        g.dispose();  
        ServletOutputStream responseOutputStream =response.getOutputStream();  
        ImageIO.write(image, "JPEG", responseOutputStream);          
        responseOutputStream.flush();  
        responseOutputStream.close();  
    }  
      
    public static Color getRandColor(int fc,int bc){  
        Random random = new Random();  
        if(fc>255) fc=255;  
        if(bc>255) bc=255;  
        int r=fc+random.nextInt(bc-fc);  
        int g=fc+random.nextInt(bc-fc);  
        int b=fc+random.nextInt(bc-fc);  
        return new Color(r,g,b);  
    }  
      
    private static String getRandomChar(){  
        int rand = (int)Math.round(Math.random() * 2);  
        long itmp = 0;  
        char ctmp = '\\';  
        switch (rand) {  
            case 1:  
                itmp = Math.round(Math.random() * 25 + 65);  
                ctmp = (char)itmp;  
                return String.valueOf(ctmp);  
            case 2:  
                itmp = Math.round(Math.random() * 25 + 97);  
                ctmp = (char)itmp;  
                return String.valueOf(ctmp);  
            default :  
                itmp = Math.round(Math.random() * 9);  
                return String.valueOf(itmp);  
        }  
    } 


}
