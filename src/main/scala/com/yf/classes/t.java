package com.yf.classes;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.io.File;

public class t {

    public static void main(String[] args){

        File pdfFile;
        File outFile ;
        PDDocument document;
        PDFRenderer renderer;
        try {
            pdfFile = new File("D:/venn.pdf");
            outFile = new File("D:/test.png");
            document = PDDocument.load(pdfFile);
            renderer = new PDFRenderer(document);
            ImageIO.write(renderer.renderImage(0,3),"png",outFile);
            document.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}
