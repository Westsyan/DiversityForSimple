package com.yf.App

import java.io.File

import com.yf.classes.{createHtml, free, htmlToPdf}
import org.apache.commons.io.FileUtils

object run {

  def dealwithFile(inputFile:String,outputFile:String)={
    val output = new File(outputFile+"/Diversity")
    if(output.exists()){
      FileUtils.deleteDirectory(output)
    }
    val file = new File(inputFile)
    val dir = output.getPath

    val path = this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    val p = path.split("/").map(_.trim).dropRight(1).mkString("/")

  //val p = "D:\\凌恩报告系统资料\\DiverForWindows\\DiversityForSimple"

    val ftlPath =  p + "/resources/ftl"
    val htmlfile = new File(p+"/resources/web")
    FileUtils.copyDirectory(file,output)
    FileUtils.copyDirectory(htmlfile,new File(dir + "/web"))

    val ard = new File(dir + "/data/allRawData.stat.txt")
    val dataexist = if(ard.exists()){"true"}else{"false"}
    free.dealwithImg(dir,ftlPath)


    createHtml.dealwithTable(dir,dataexist)

    //生成网页，PDF网页
    free.html(dir,ftlPath,dataexist)
    free.pdf(dir,ftlPath,dataexist)

    //生成pdf
    htmlToPdf.htoP(dir,p)
    FileUtils.deleteDirectory(new File(dir+"/data"))
    val pdf = new File(dir + "/pdf.html")
    pdf.delete()
    println("生成报告成功！")

  }


  def main(args: Array[String]): Unit = {
    val usage =
      """
        |用法：  java -jar  jarfiles  -i xx -o xx
        |注意：
        |   请按顺序输入，搞错顺序程序将不能正确运行
        |
        |服务器下运行方法：
        |        java -Djava.awt.headless=true -jar jarfiles -i xx -o xx
        |
        |其中选项包括：
        |
        |   -i      inputPath:输入源文件路径
        |   -o      outputPath:输出报告路径,需先创建一个文件夹
        |   -c      查看多样性目录结构
      """.stripMargin
    val log =
      """
        | 多样性分析项目目录结构：
        |
        |   -      information.txt                       项目负责人概况
        |
        |   -      data                                   原始序列数据 （后续发送）
        |   ----        allRawData.stat.xls               未拼接的原始数据(可以缺失)
        |   ----        cleanData.stat.xls                优化序列统计
        |
        |   -      results                                分析结果
        |   --       00.Data                              优化序列数据统计
        |   ----        cleanData.stat.xls                优化序列数据统计表
        |   ----        clean.data.len.pdf                优化序列长度分布图
        |   ----        clean.data.len.dist.txt           优化序列长度分布
        |
        |   --       01.OTU_Taxa                          各分类学水平统计表
        |   ----        normalize                         抽平的结果
        |   ----        original                          原始的非抽平结果
        |   ------         otu_table.xls                  每个OTU中包含的序列编号列表
        |   ------         otu_taxa_table.xls             各分类学水平样本序列数统计表
        |
        |   --       02.Alpha                             Alpha多样性分析
        |   ----        Estimators                        多样性分析指数
        |   ------          Estimate.html                 每个样本的指数值
        |   ------          otu.*.summary.xls             所有指数分析结果列表
        |
        |   ----        Rarefaction                       取样深度曲线
        |   ------          otu.*.rarefaction.xls         每个样本在不同取样值的OTU数目表
        |   ------          rarefaction.All.pdf           多样本稀释曲线图
        |
        |   ----        Shannon_rarefac                   Shannon-wiener曲线
        |   ------          otu.*r.shannon.xls            每个样本在不同取样值的shannon指数表
        |   ------          r_shannon.All.pdf             shannon曲线图
        |
        |   ----        Rank_Abundance                    Rank Abundance分析
        |   ------          rankabundance.pdf             多样本曲线图
        |
        |   ----        Specaccum                         Specaccum分析
        |   ------          specaccum.all.pdf             物种累积曲线图
        |
        |   --      03.Community_composition              物种组成分析
        |   ----        Community                         群落结构组分图
        |   ------          bar.All.*.xls.pdf             多样本柱状图
        |
        |   --      04.Beta                               Beta多样性比较分析
        |   ----        PCA                               PCA分析
        |   ------            pca_pc*.pdf                 PCA图
        |
        |   注意：若目录名不一致或缺失目录中的文件，程序将不能正常运行！
      """.stripMargin
    val symbol = Array[String]("-i","-o","-c")
    if(args.length ==1 && args(0) == "-c"){
      println(log)
    }else if(args.length != 4){
      println("输入参数缺失，请查看相关帮助：" + usage)
    }else{
      val (inPath,outPath) = (new File(args(1)),new File(args(3)))
      val a = Array(args(0),args(2))
      if(symbol.diff(a).length != 1){
        println("输入选项有误，请查看相关帮助：" + usage)
      }else if(!inPath.exists()){
        println("错误：输入路径不存在！")
      }else if(!inPath.isDirectory){
        println("错误：输入路径不是文件夹！")
      }else if(!outPath.isDirectory) {
        println("错误：输出路径不是文件夹！")
      }else{
        dealwithFile(inPath.getPath,outPath.getPath)
      }
    }
  }


}
