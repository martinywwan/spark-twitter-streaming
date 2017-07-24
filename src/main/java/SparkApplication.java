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


/**Simple Spark streaming application used to subscribe to Twitter messages
 *
 * Created by Martin (Yew Wing) Wan on 19-Jul-17.
 */
public class SparkApplication {

    public static final String[] FILTERS = new String[] {"chesterbennington", "rip", "youareamazing"}; //filter tweets
    private final SparkSubscriber sparkSubscriber;

    static {
        Logger.getLogger("org").setLevel(Level.OFF); //remove debug logs
        Logger.getLogger("akka").setLevel(Level.OFF); //remove debug logs
    }

    //Initialises Spark and Twitter connection
    public SparkApplication(String ... args){
        SparkDriver sparkDriver = new SparkDriver("TwitterApplication"); //setup Spark
        TwitterDriver twitterDriver = new TwitterDriver(args); //define Twitter configuration
        OAuthAuthorization auth = new OAuthAuthorization(twitterDriver.getConfig().build()); //setup Twitter connection
        JavaStreamingContext javaStreamingContext = new JavaStreamingContext(sparkDriver.getConf(), new Duration(5000)); //set streamingContext using Spark
        //TODO - filters defined in a properties file
        JavaReceiverInputDStream<Status> stream = FILTERS.length!=0? TwitterUtils.createStream(javaStreamingContext, auth, FILTERS) : TwitterUtils.createStream(javaStreamingContext, auth); //Twitter stream
        this.sparkSubscriber = new SparkSubscriber(javaStreamingContext, stream);
    }

    /**Gets Twitter messages (Tweets)
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
