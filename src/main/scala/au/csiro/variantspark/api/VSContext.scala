package au.csiro.variantspark.api

import au.csiro.variantspark.input.FeatureSource
import au.csiro.variantspark.input.VCFSource
import org.apache.spark.SparkContext
import au.csiro.variantspark.input.VCFFeatureSource
import org.apache.spark.SparkConf
import au.csiro.variantspark.input.CsvLabelSource
import org.apache.hadoop.fs.FileSystem
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.SparkSession


class VSContext(val spark:SparkSession, val sparkPar:Int=0) {

  val sc = spark.sparkContext
  val sqlContext = spark.sqlContext
  
  implicit val fs = FileSystem.get(sc.hadoopConfiguration)
  implicit val hadoopConf = sc.hadoopConfiguration
  
  def featureSource(inputFile:String, inputType:String = null): FeatureSource = {
    val vcfSource = VCFSource(sc.textFile(inputFile, if (sparkPar > 0) sparkPar else sc.defaultParallelism))
    VCFFeatureSource(vcfSource) 
  }
  
  def labelSource(featuresFile:String, featureColumn:String)  = {
    new CsvLabelSource(featuresFile, featureColumn)
  }
}

object VSContext {
  def apply(spark:SparkSession) = new VSContext(spark)
}