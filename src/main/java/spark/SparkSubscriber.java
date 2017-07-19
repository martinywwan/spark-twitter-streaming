package spark;

import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import twitter4j.Status;

/**Subscribes to Twitter messages
 *
 *Created by Martin (Yew Wing) Wan
 */
public class SparkSubscriber {

    public SparkSubscriber(JavaReceiverInputDStream<Status> stream){
    }

}
