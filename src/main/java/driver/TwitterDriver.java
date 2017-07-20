package driver;

import twitter4j.conf.ConfigurationBuilder;

/**Initialises the Twitter connection
 *
 * Created by lightwan on 20-Jul-17.
 */
public class TwitterDriver {

    private final ConfigurationBuilder cb;

    public TwitterDriver(String ... args){
        String consumerKey = args[0];
        String consumerSecret = args[1];
        String accessToken =  args[2];
        String accessTokenSecret = args[3];

        this.cb = new ConfigurationBuilder();
        this.cb.setDebugEnabled(false).setOAuthConsumerKey(consumerKey)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);
    }

    public ConfigurationBuilder getConfig(){
        return this.cb;
    }
}
