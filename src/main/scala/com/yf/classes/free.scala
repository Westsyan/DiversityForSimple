package com.yf.classes

import java.io._
import java.util

import com.yf.classes.getAllValidFile.getPdfOrExcel
import com.yf.classes.pdfToPng._
import freemarker.template.Configuration
import org.apache.commons.io.FileUtils

import scala.collection.JavaConverters._


object free {

  def freeimg(imgpath:Array[String],dir:String,filename:String,ftlPath:String)={
    val head = imgpath.map(_.replaceAll("\\\\","/")).head.split("/").last.split(".png").head + ".pdf"
    val map = new util.HashMap[String, Object]()
    map.put("first",imgpath.head)
    map.put("name", head)
    map.put("sexMap", imgpath)
    try {
      val config = new Configuration
      config.setDefaultEncoding("UTF-8")
      config.setDirectoryForTemplateLoading(new File(ftlPath))
      val template = config.getTemplate("image.ftl")
      val writer = new BufferedWriter(
        new OutputStreamWriter(
          new FileOutputStream(dir + "/web/res/" + filename), "UTF-8"));
      // val writer = new FileWriter(dir + "/res/" + filename)
      template.process(map, writer)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def pngToHtml(dir:String,canpath:String,filename:String,ftlPath:String)={
    val file = new File(dir + "/web/res/" + canpath)
    val array = file.listFiles.map{x=>
      val name = x.getName
      val ar = name.split('.')
      if(ar.last == "png"){
        canpath + "/"+ ar.mkString(".")
      }else{
        "null"
      }
    }.distinct.diff(Array("null"))

    freeimg(array,dir,filename,ftlPath)
  }

  def html(dir:String,ftlPath:String,dataExist:String) ={
    val map = new util.HashMap[String, Object]()
    map.put("dataExist",dataExist)
    try {
      val config = new Configuration
      config.setDefaultEncoding("UTF-8")
      config.setDirectoryForTemplateLoading(new File(ftlPath))
      val template = config.getTemplate("report.ftl")
      val writer = new BufferedWriter(
        new OutputStreamWriter(
          new FileOutputStream(dir + "/report.html" ), "UTF-8"))
      template.process(map, writer)
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def pdf(dir:String,ftlPath:String,dataExist:String) ={
    val info = FileUtils.readLines(new File(dir + "/information.txt"),"UTF-8").asScala
    val infofile = new File(dir+"/information.txt")
    infofile.delete()
    val data = info(4).split("-")
    val map = new util.HashMap[String, Object]()
    map.put("client",info(0))
    map.put("man",info(1))
    map.put("mail",info(2))
    map.put("phone",info(3))
    map.put("year",data(0))
    map.put("month",data(1))
    map.put("day",data(2))
    map.put("dataExist",dataExist)
    try {
      val config = new Configuration
      config.setDefaultEncoding("UTF-8")
      config.setDirectoryForTemplateLoading(new File(ftlPath))
      val template = config.getTemplate("pdf.ftl")
      val writer = new BufferedWriter(
        new OutputStreamWriter(
          new FileOutputStream(dir + "/pdf.html" ), "UTF-8"));
      // val writer = new FileWriter(dir + "/res/" + filename)
      template.process(map, writer)
      writer.close()
    } catch {
      case e: Exception =>
        e.printStackTrace()
    }
  }

  def dealwithImg(dir:String,ftlPath:String) = {
    val pdfOrExcel =getPdfOrExcel(dir, 0)
    pdfOrExcel._1.foreach{x=>
      if(FileUtils.sizeOf(new File(x)) != 3612){
        println(x)
        pdf2Png(x, dir)
      }else{
        println("[warning] " + x + ". This image reads an exception!")
      }
    }
    pngToHtml(dir,"04.Beta/PCA","pca.html",ftlPath)
    pngToHtml(dir,"03.Community_composition/Community","community.html",ftlPath)
    println("图片处理成功！")
  }
}
