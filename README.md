java-spark-twitter-streaming <br />
=============
<br />
Synopsis <br />
---------------
<br />
Simple Spark application that connects to Twitter and prints twitter messages based on a filter (if any). <br />
The Spark application can be run as a Standalone Application or on Hadoop. <br />
<br />
Motivation <br />
---------------
<br />
The motivation behind this project was to provide support to developers and researchers in connecting to Twitter using Apache Spark. <br />
<br />
Execution <br />
---------------
<br />
Prerequisites: <br />
1)If you are running on Hadoop, ensure ${HADOOP_CONF_DIR} and ${HADOOP_HOME} are set
<br />
<br />
Instructions to run the application using an IDE: <br />
1) Edit the run configuration to include the following arguments: [args0 - consumerKey] [args1 - consumerSecret] [args2 - accessToken] [args3 - accessTokenSecret] <br />
2) Run the SparkApplication class  (Optional: edit the FILTERS array to filter out the tweets received) <br />
<br />
Instructions to run the application on the command line: <br />
1) Ensure maven is installed and enter "mvn clean package" <br />
2) In the target folder, you should see a jar file with dependencies. Run "java -jar [generated_jar].jar [args0 - consumerKey] [args1 - consumerSecret] [args2 - accessToken] [args3 - accessTokenSecret] <br />
<br />
<br />