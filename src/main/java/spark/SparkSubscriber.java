package spark;

import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import twitter4j.Status;

import java.util.Arrays;

/**Subscribes to Twitter messages
 *
 *Created by Martin (Yew Wing) Wan
 */
public class SparkSubscriber {

    private final JavaStreamingContext javaStreamingContext;
    private final JavaReceiverInputDStream<Status> stream;

    /**Constructor - Java Streaming context using Spark and Twitter stream
     *
     * @param javaStreamingContext - Spark stream
     * @param stream - Twitter stream
     */
    public SparkSubscriber(JavaStreamingContext javaStreamingContext, JavaReceiverInputDStream<Status> stream) {
        this.javaStreamingContext = javaStreamingContext;
        this.stream = stream;
    }

    //Gets the twitter message and prints the Tweet and user details
    //TODO - Write to HDFS for analytics
    public void getTweets() throws InterruptedException {
        this.stream.map(msg -> msg).foreachRDD(rdd -> {
            rdd.foreachPartition(partition -> {
                while (partition.hasNext()) {
                    Status status = partition.next();
                    String tweet = status.getText();
                    Arrays.stream(status.getHashtagEntities()).forEach(System.out::print);
                    String details = "User: " + status.getUser() + "\n*************************************";

                    if(tweet.startsWith("RT")) {
                        System.out.println("ReTweet: " + tweet + "\n" + details);
                    } else {
                        System.out.println("Tweet: " + tweet + "\n" + details);
                    }
//                    Arrays.stream(status.getHashtagEntities()).forEach(System.out::print);
                }
            });
        });

        System.out.println("-Starting Spark Streaming-");
        this.javaStreamingContext.start(); //start streaming
        this.javaStreamingContext.awaitTermination(); //continue until killed
    }
}
