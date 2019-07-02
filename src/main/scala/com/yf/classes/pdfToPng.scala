package com.yf.classes

import java.io.File
import javax.imageio.ImageIO

import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.rendering.PDFRenderer


object pdfToPng {
  /*
      * pdf 转 图片
      */
  def pdf2Png(pdfPath: String,file:String): Unit = {
    //转换路径格式，便于识别
    val filePath = pdfPath.replaceAll("\\\\", "/")
    val filePath2 = file.replaceAll("\\\\","/")
    //得到文件名
    val pdfFile = filePath.split("/").last
    //得到原文件存储路径，如果不想存储在原文件，也可以自己传路径
    val x = filePath2.split("/")
    val x1 = filePath.split("/").diff(Array(pdfFile)).diff(x)
    val path = filePath2 + "/web/res/" + x1.drop(1).mkString("/") + "/"
    //获得文件名，如果有后缀去掉pdf后缀
    val filename = pdfFile.split('.').diff(Array("pdf")).mkString(".")
    val pdfFiles = new File(pdfPath)
    val outFile = new File(path + filename + ".png")
    val document = PDDocument.load(pdfFiles)
    val renderer = new PDFRenderer(document)
    ImageIO.write(renderer.renderImage(0, 3), "png", outFile)
    document.close()
  }

  def getPath(filePath: String,totalpath:String) ={
    val file = filePath.replaceAll("\\\\", "/")
    val path = totalpath.replaceAll("\\\\","/")
    //得到文件名
    val filename = file.split("/").last
    //得到原文件存储路径，如果不想存储在原文件，也可以自己传路径
    val x = path.split("/")
    val x1 = file.split("/").diff(Array(filename)).diff(x)
    val lastpath = path + "/res/" + x1.mkString("/") + "/"
    //获得文件名，如果有后缀去掉pdf后缀
    (lastpath,filename)
  }

}
