package spark;

import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import twitter4j.Status;

/**Subscribes to Twitter messages
 *
 *Created by Martin (Yew Wing) Wan
 */
public class SparkSubscriber {

    public SparkSubscriber(JavaStreamingContext javaStreamingContext, JavaReceiverInputDStream<Status> stream) throws InterruptedException {
        stream.map(status -> {
                System.out.println("Tweet: " + status.getText());
                return status.getText();
            }).foreachRDD(rdd -> {
                    rdd.foreachPartition(itr -> {
                        while(itr.hasNext()){
                            System.out.println("Sub-Tweet: " + itr.next());
                        }
                    });
               });

        System.out.println("-Starting Spark Streaming-");
        javaStreamingContext.start(); //start streaming
        javaStreamingContext.awaitTermination(); //continue until killed
    }
}
