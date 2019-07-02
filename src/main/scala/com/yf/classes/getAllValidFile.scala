package com.yf.classes

import java.io.File

object getAllValidFile {


  def getPdfOrExcel(path:String,deep:Int) = {
    var pdf  = Array[String]()
    var excel = Array[(String,Int)]()
    getFile(path,deep)
    def getFile(path: String, deep: Int):Unit = {
      val file = new File(path)
      val array = file.listFiles
      for (i <- 0 until array.length) {
        if (array(i).isFile) {
          val filename = array(i).getName
          val fileSuffix = filename.split('.').last
          if (fileSuffix == "xls" || fileSuffix == "xlsx") {
            excel = excel :+ (array(i).getPath , deep)
          } else if (fileSuffix == "pdf") {
            pdf = pdf :+ array(i).getPath
          }
          array(i).getPath
        } else if (array(i).isDirectory) {
          getFile(array(i).getPath, deep + 1)
        }
      }
    }
    (pdf,excel)
  }


  def getFirstXls(dir:String,path:String) = {
    val file = new File(dir + path)
    val files = file.listFiles()
    val xls = files.map { x =>
      val f = x.getPath
      if (f.split('.').last == "xls" || f.split('.').last == "xlsx") {
        x.getPath
      } else {
        "null"
      }
    }.distinct.diff(Array("null"))
      xls.head
    }

}
