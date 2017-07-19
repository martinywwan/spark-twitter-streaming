import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import spark.SparkDriver;
import spark.SparkSubscriber;
import twitter4j.Status;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.conf.ConfigurationBuilder;

/**Subscribes to Twitter using Spark Streaming
 *
 * Created by Martin (Yew Wing) Wan on 19-Jul-17.
 */
public class SparkApplication {

    public static final String[] FILTERS = new String[] {"spark","apache", "hadoop"}; //filter tweets

    public static void main(String[] args) throws Exception {
        if(args.length!=4) {
            throw new Exception("Expecting 4 arguments \n" +
                    "args0: consumerKey\n" +
                    "args1: consumerSecret\n" +
                    "args2: accessToken\n"+
                    "args3: accessTokenSecret");
        }

        SparkDriver sparkFactory = new SparkDriver("TwitterApplication");
        String consumerKey = args[0];
        String consumerSecret = args[1];
        String accessToken =  args[2];
        String accessTokenSecret = args[3];

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(false).setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        OAuthAuthorization auth = new OAuthAuthorization(cb.build());
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkFactory.getConf(), new Duration(5000));
        JavaReceiverInputDStream<Status> stream = TwitterUtils.createStream(javaStreamingContext, auth, FILTERS); //Twitter stream
        new SparkSubscriber(javaStreamingContext, stream);
    }
}
