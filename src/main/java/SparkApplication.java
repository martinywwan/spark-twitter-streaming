import driver.TwitterDriver;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.twitter.TwitterUtils;
import driver.SparkDriver;
import spark.SparkSubscriber;
import twitter4j.Status;
import twitter4j.auth.OAuthAuthorization;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;


/**Subscribes to Twitter using Spark Streaming
 *
 * Created by Martin (Yew Wing) Wan on 19-Jul-17.
 */
public class SparkApplication {

    public static final String[] FILTERS = new String[] {"chesterbennington", "rip", "youareamazing"}; //filter tweets
    private final SparkDriver sparkFactory;
    private final TwitterDriver twitterDriver;
    private final JavaReceiverInputDStream<Status> stream;
    private final JavaStreamingContext javaStreamingContext;
    private final SparkSubscriber sparkSubscriber;


    static {
        Logger.getLogger("org").setLevel(Level.OFF); //remove debug logs
        Logger.getLogger("akka").setLevel(Level.OFF); //remove debug logs
    }

    //Initialises Spark and Twitter connection
    public SparkApplication(String ... args){
        this.sparkFactory = new SparkDriver("TwitterApplication");
        this.twitterDriver = new TwitterDriver(args);
        OAuthAuthorization auth = new OAuthAuthorization(this.twitterDriver.getConfig().build());
        this.javaStreamingContext = new JavaStreamingContext(this.sparkFactory.getConf(), new Duration(5000));
        //TODO - filters defined in a properties file
        this.stream = FILTERS.length!=0? TwitterUtils.createStream(javaStreamingContext, auth, FILTERS) : TwitterUtils.createStream(javaStreamingContext, auth); //Twitter stream
        this.sparkSubscriber = new SparkSubscriber(javaStreamingContext, stream);
    }

    /**Gets tweets
     *
     * @throws InterruptedException
     */
    public void run() throws InterruptedException {
        this.sparkSubscriber.getTweets();
    }

    public static void main(String[] args) throws Exception {
        if(args.length!=4) {
            throw new Exception("Expecting 4 arguments \n" +
                    "args0: consumerKey\n" +
                    "args1: consumerSecret\n" +
                    "args2: accessToken\n"+
                    "args3: accessTokenSecret");
        }
        SparkApplication sparkApplication = new SparkApplication(args);
        sparkApplication.run();
    }
}
