import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming._
object streamingApp {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Testing Streaming").setMaster("yarn-client")
    val ssc = new StreamingContext(conf, Seconds(10))
    val lines = ssc.socketTextStream("gw01.itversity.com", 19999)
    val linesFlatMap = lines.flatMap(rec => rec.split(" "))
    val linesMap = linesFlatMap.map((_, 1))
    val linesRBK = linesMap.reduceByKey(_ + _)
    linesRBK.print()
    ssc.start()
    ssc.awaitTermination()

  }
}
test//comitndddk