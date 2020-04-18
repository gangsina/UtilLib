package com.bentengwu.utillib.ai;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * @Author thender email: bentengwu@163.com
 * @Date 2020/4/18 1:07.
 */
public abstract class ScreenCut {

    public static final BufferedImage screenCut(int x, int y, int width, int height) throws Exception {
            Robot robot = new Robot();
            BufferedImage bi = robot.createScreenCapture(new Rectangle(x, y, width, height));
            return bi;
    }

    public static void main(String[] args) throws Exception{
        BufferedImage bi = screenCut(18 ,883 ,404 ,109);
        ImageIO.write(bi, "png", new File("f:\\cache\\1.png"));
    }
}
