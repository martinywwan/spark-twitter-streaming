package spark;

import org.apache.spark.SparkConf;

/**Spark setup to submit jobs
 *
 * Created by Martin (Yew Wing) Wan
 */
public class SparkDriver {

    private final SparkConf conf;

    public SparkDriver(String applicationName){
        //Standalone application
         conf = new SparkConf()
                 .setMaster("local[*]") //comment to run in hadoop cluster
                 .setAppName(applicationName).set("spark.driver.allowMultipleContexts", "true");
    }

    public SparkConf getConf() {
        return conf;
    }
}
