package com.yf.classes

import java.io.File

import org.apache.commons.io.FileUtils

import scala.collection.JavaConverters._
import scala.collection.mutable

object createHtml {

  def createHtml(head: Array[String], body: mutable.Buffer[Array[String]], file: String, dir: String, deep: Int,htmlname:String) = {
    var level = ""
    for (i <- 0 until deep) {
      level += "../"
    }
    var html =
      s"""
         |<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
         |<link rel="stylesheet" href="${level}js/bootstrap/css/bootstrap.min.css">
         |	<script src="${level}js/jquery-3.1.0.min.js"></script>
         |	<script src="${level}js/bootstrap/js/bootstrap.min.js"></script>
         | </head>
         | <style type="text/css">
         |	td{
         |		white-space:nowrap;
         |	}
         | </style>
         | <div id="wid" style="width: 1000px;">
         | <table width='1000px' class="table table-hover table-striped scrolltable">
         | <thead  style="display:block;">
         | <tr>
      """.stripMargin
    if (head.size < 8) {
      head.map { x =>
        val tableHead = "<th>" + x + "</th>"
        html += tableHead
      }
    } else {
      head.take(8).map { x =>
        val tableHead = "<th>" + x + "</th>"
        html += tableHead
      }
      html += "<th> ... </th>"
    }
    html += "</tr></thead><tbody style='display:block;max-height:380px;overflow-y: scroll;'>"
    if (head.size < 8) {
      body.map { x =>
        html += "<tr>"
        x.map { y =>
          val element = "<td>" + y + "</td>"
          html += element
        }
        html += "</tr>"
      }
    } else {
      body.map { x =>
        html += "<tr>"
        x.map { y =>
          val element = "<td>" + y + "</td>"
          html += element
        }
        html += "<td>...</td></tr>"
      }
    }
    html +=
      """
        |     </tr>
        |   </tbody>
        | </table>
        |</div>
        |<script type="text/javascript">
        |    $(document).ready(function(){
        |        var _width=$('#wid').width();
      """.stripMargin
    html += "$('#wid th').width(_width/" + head.size + ");"
    html += "$('#wid td').width(_width/" + head.size + ");"
    html += "})</script>  "

    val htmlpath = TableData.getFinalFilePath(file, dir) + htmlname
    FileUtils.writeStringToFile(new File(htmlpath), html, "UTF-8")
  }

  def taxTable(head: Array[String], body: mutable.Buffer[Array[String]], file: String, dir: String, deep: Int,htmlname:String) = {
    var level = ""
    for (i <- 0 until deep) {
      level += "../"
    }
    var html =
      s"""
         |<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
         |<link rel="stylesheet" href="${level}js/bootstrap/css/bootstrap.min.css">
         |	<script src="${level}js/jquery-3.1.0.min.js"></script>
         |	<script src="${level}js/bootstrap/js/bootstrap.min.js"></script>
         | </head>
         | <div id="wid" style="width: 100%;">
         | <table width='100%' class="table table-hover table-striped scrolltable">
         | <thead  style="display:block;">
         | <tr>
      """.stripMargin
    if (head.size < 8) {
      head.take(head.size - 1).map { x =>
        val tableHead = "<th width='" + 500 / (head.size - 1) + "px'>" + x + "</th>"
        html += tableHead
      }
      html += "<th width='500px'>taxonomy</th>"
    } else {
      head.take(6).map { x =>
        val tableHead = "<th width='71px'>" + x + "</th>"
        html += tableHead
      }
      html +=
        """
          |<th width="71px"> ... </th>
          |<th width="500px">taxonomy</th>
        """.stripMargin
    }
    html += "</tr></thead><tbody style='display:block;max-height:380px;overflow-y: scroll;'>"
    if (head.size < 8) {
      body.map { x =>
        html += "<tr>"
        x.take(head.size - 1).map { y =>
          val element = "<td width='" + 500 / (head.size - 1) + "px'>" + y + "</td>"
          html += element
        }
        html += "<td width='500px'>" + x.last + "</th></tr>"
      }
    } else {
      body.map { x =>
        html += "<tr>"
        x.take(6).map { y =>
          val element = "<td width='71px'>" + y + "</td>"
          html += element
        }
        html +=
          s"""
             |<td width="71px">...</td>
             |<td width="500px">${x.last}</td>
             |</tr>
          """.stripMargin
      }
    }
    html +=
      """
        |     </tr>
        |   </tbody>
        | </table>
        |</div>
      """.stripMargin

    val htmlpath = TableData.getFinalFilePath(file, dir) + htmlname
    FileUtils.writeStringToFile(new File(htmlpath), html, "UTF-8")
  }

  def commonHtml(head: Array[String], body: mutable.Buffer[Array[String]], file: String, dir: String, deep: Int,htmlname:String) = {
    var level = ""
    for (i <- 0 until deep) {
      level += "../"
    }
    var html =
      s"""
         |<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
         |<link rel="stylesheet" href="${level}js/bootstrap/css/bootstrap.min.css">
         |	<script src="${level}js/jquery-3.1.0.min.js"></script>
         |	<script src="${level}js/bootstrap/js/bootstrap.min.js"></script>
         | </head>
         | <style type="text/css">
         |	td{
         |		white-space:nowrap;
         |	}
         | </style>
         | <div id="wid" style="width: 100%;">
         | <table width='100%' class="table table-hover table-striped">
         | <thead>
         | <tr>
      """.stripMargin
    if (head.size < 8) {
      head.map { x =>
        val tableHead = "<th>" + x + "</th>"
        html += tableHead
      }
    } else {
      head.take(8).map { x =>
        val tableHead = "<th>" + x + "</th>"
        html += tableHead
      }
      html += "<th> ... </th>"
    }
    html += "</tr></thead><tbody>"
    if (head.size < 8) {
      body.map { x =>
        html += "<tr>"
        x.map { y =>
          val element = "<td>" + y + "</td>"
          html += element
        }
        html += "</tr>"
      }
    } else {
      body.map { x =>
        html += "<tr>"
        x.map { y =>
          val element = "<td>" + y + "</td>"
          html += element
        }
        html += "<td>...</td></tr>"
      }
    }
    html +=
      """
        |     </tr>
        |   </tbody>
        | </table>
        |</div>
        |<script type="text/javascript">
        |    $(document).ready(function(){
        |        var _width=$('#wid').width();
      """.stripMargin
    html += "$('#wid th').width(_width/" + head.size + ");"
    html += "$('#wid td').width(_width/" + head.size + ");"
    html += "})</script>  "

    val htmlpath = TableData.getFinalFilePath(file, dir) + htmlname
    FileUtils.writeStringToFile(new File(htmlpath), html, "UTF-8")
  }

  def other(file: String, dir: String, deep: Int,htmlname:String) = {
    val data = TableData.getData(dir + file)
    val head = data.head
    val body = data.drop(1)
    createHtml(head, body, file, dir, deep,htmlname)
    commonHtml(head, body, file, dir, deep,"pdf_" + htmlname)
  }

  def top50(file: String, dir: String, deep: Int,htmlname:String) = {
    val d = dir.replaceAll("\\\\","/").split("/")
    val path = "/" + file.replaceAll("\\\\","/").split("/").diff(d).mkString("/")
    val data = TableData.getData(dir + path)
    val head = data.head
    val body = data.drop(1).take(50)
    createHtml(head, body, path, dir, deep,htmlname)
    commonHtml(head, body, path, dir, deep,"pdf_"+htmlname)
  }

  def toptax50(file: String, dir: String, deep: Int,htmlname:String) = {
    val d = dir.replaceAll("\\\\","/").split("/")
    val path = "/" + file.replaceAll("\\\\","/").split("/").diff(d).mkString("/")
    val data = TableData.getData(dir + path)
    val head = data.head
    val body = data.drop(1).take(50)
    taxTable(head, body, path, dir, deep,htmlname)
  }

  def estimators(dir: String) = {
    val data = FileUtils.readLines(new File(dir + "/results/02.Alpha/Estimators/estimators.html")).asScala
    val head =
      """
        |<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
        |<link rel="stylesheet" href="../../../js/bootstrap/css/bootstrap.min.css">
        |	<script src="../../../js/jquery-3.1.0.min.js"></script>
        |	<script src="../../../js/bootstrap/js/bootstrap.min.js"></script>
        | </head>
        | <style>
        |th{
        |	 text-align:center;
        |	}

        | </style>
        | <table width='100%' class="table table-hover table-striped table-bordered">
        | <thead>
        |      <tr align="center"><th rowspan="2">Sample ID</th><th rowspan="2">Reads</th><th colspan="5">0.97</th></tr>
        |      <tr><th>OTU</th><th>chao</th><th>coverage</th><th>shannon</th><th>simpson</th></tr>
        |      </thead>
        |      <tbody>
      """.stripMargin
    val body = data.drop(3).take(5)
    val html = head +  body.mkString("\n") + "</tbody></table>"
    FileUtils.writeStringToFile(new File(dir + "/web/res/02.Alpha/Estimators/estimators.html"), html )
  }

  def richAndDiv(path: String, dir: String) = {
    val data = FileUtils.readLines(new File(path)).asScala
    val rich = data.map(_.split("\t").take(4))
    val richHead = rich.head
    val richBody = rich.last
    val div = data.map(_.split("\t").drop(4))
    val divHead = div.head
    val divBody = div.last
    richAndDivHtml(richHead,richBody,dir,"richness.html")
    richAndDivHtml(divHead,divBody,dir,"diversity.html")
  }

  def richAndDivHtml(head: Array[String], body: Array[String],dir:String,htmlname:String) = {
    var html =
      """
        |<head><meta http-equiv='Content-Type' content='text/html; charset=utf-8' />
        |<link rel="stylesheet" href="../../../js/bootstrap/css/bootstrap.min.css">
        |	<script src="../../../js/jquery-3.1.0.min.js"></script>
        |	<script src="../../../js/bootstrap/js/bootstrap.min.js"></script>
        | </head>
        | <div id="wid" style="width: 100%;">
        | <table width='100%' class="table table-hover table-striped">
        | <thead>
        | <tr>
      """.stripMargin
    head.map { x =>
      val tableHead = "<th>" + x + "</th>"
      html += tableHead
    }
    html += "</tr></thead><tbody><tr>"
    body.map { y =>
      val element = "<td>" + y + "</td>"
      html += element
    }
    html +=
      """
        |     </tr>
        |   </tbody>
        | </table>
        |</div>
        |<script type="text/javascript">
        |    $(document).ready(function(){
        |        var _width=$('#wid').width();
      """.stripMargin
    html += "$('#wid th').width(_width/" + head.size + ");"
    html += "$('#wid td').width(_width/" + head.size + ");"
    html += "})</script>  "

    val htmlpath = dir + "/web/res/02.Alpha/Estimators/" + htmlname
    FileUtils.writeStringToFile(new File(htmlpath), html, "UTF-8")
  }

  def head(file:String,dir:String,deep:Int,htmlname:String) ={
    val data = TableData.getData(dir + file)
    val head  = Array("Length(bp)","Sequences","Percent")
    val body = data
    createHtml(head, body, file, dir, deep,htmlname)
    commonHtml(head, body, file, dir, deep,"pdf_"+htmlname)
  }

  def dealwithTable(dir:String,dataExist:String) = {
    estimators(dir)
    if(dataExist == "true"){
      val ard = new File(dir + "/data/allRawData.stat.txt")
      val trcd = new File(dir + "/allRawData.stat.txt")
      FileUtils.copyFile(ard, trcd)
      other("/allRawData.stat.txt", dir, 1,"allRawData.stat.html")
      trcd.delete()
    }

    val acd = new File(dir + "/data/allCleanData.stat.txt")
    val tacd = new File(dir + "/allCleanData.stat.txt")
    FileUtils.copyFile(acd, tacd)
    other("/allCleanData.stat.txt", dir, 1,"allCleanData.stat.html")
    tacd.delete()

    head("/results/00.Data/clean.data.len.dist.txt", dir, 2,"clean.data.len.dist.html")
    other("/results/00.Data/cleanData.stat.xls", dir, 2,"cleanData.stat.html")
    top50("/results/01.OTU_Taxa/normalize/otu_table.xls", dir, 3,"otu_table.html")
    toptax50("/results/01.OTU_Taxa/normalize/otu_taxa_table.xls", dir, 3,"otu_taxa_table.html")
    val esti = getAllValidFile.getFirstXls(dir, "/results/02.Alpha/Estimators")
    richAndDiv(esti,dir)
    val rare = getAllValidFile.getFirstXls(dir,"/results/02.Alpha/Rarefaction")
    top50(rare,dir,3,"rarefaction.html")
    val shan = getAllValidFile.getFirstXls(dir,"/results/02.Alpha/Shannon_rarefac")
    top50(shan,dir,3,"r_shannon.html")


    println("表格处理成功")
  }
}
