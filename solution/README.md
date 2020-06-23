# Solution: ostium-binarii (candidate Daniel Oh)

**Date:** 2020-06-22

## Summary
Lightweight reporting web service.

###CallOuts
Forgive the lack of a class diagram or call flow diagrams, this took me a little longer than I expected. Also note, that in the interest of time, certain key practices in some areas, such as thread-safety, defensive copying, and logging best practices were less focused on. Also note that the code is brittle to change in certain places, such as if we need to switch over to a database (I point this and the other callout throughout the code in comments). It would have been easier to make a more durable solution if spec, or some hints were given about what a future "production" version of the project would need to look like: (e.g. we intend to migrate over to a database, note that the data may grow to large amounts, note that new companies may be added, note that data may need to be hourly, this is a reporting service and will not need to POST data for example, etc. etc. etc.)

## Stack
```* indicates I used this for the first time and learned about as I went along.```
* Language: Java 8
* Unit Testing: Junit5 & Mockito
* Dependency Injection: Guice
* Logging: Slf4j
* API: *Spark Framework
* Build: *Apache Maven

## Requirements and Usage
The project can be built using Apache Maven. After downloading the git repository, open the terminal do the following. Assumes at least the Java 8 JDK is set up and the user is on a Macintosh.
```bash
# navigate to the solution folder of the project.
cd {base_path}/take-home-exercise-ostium-binarii/solution
```
```bash
# unzip the provided apache-maven bin
unzip apache-maven-3.6.3-bin.zip
```
```bash
# temporarily set environment variables for the session to enable Maven commands.
JAVA_HOME={Java JDK Home on your machine, for me it looks like "/Library/Java/JavaVirtualMachines/jdk-12.0.2.jdk/Contents/Home"} 
PATH=$PATH:./apache-maven-3.6.3/bin
```
```bash
# check maven is working.
mvn -v
# expected example output:
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /Users/danieoh/sandbox/take-home-exercise-ostium-binarii/solution/apache-maven-3.6.3
Java version: 12.0.2, vendor: Oracle Corporation, runtime: /Library/Java/JavaVirtualMachines/jdk-12.0.2.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.15.5", arch: "x86_64", family: "mac"
```
```bash
# Compile and run unit tests
mvn clean package
# expected example output
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.843 s
[INFO] Finished at: 2020-06-22T18:27:50-07:00
[INFO] ------------------------------------------------------------------------
```
```bash
# start the server. After it is started, keep it running in the session and the API's will be reachable.
java -cp target/take-home-exercise-ostium-binarii-1.0.jar reporting.Application
# expected example output
1038 [Thread-0] INFO org.eclipse.jetty.server.session - node0 Scavenging every 600000ms
1069 [Thread-0] INFO org.eclipse.jetty.server.AbstractConnector - Started ServerConnector@22fa3549{HTTP/1.1,[http/1.1]}{0.0.0.0:4567}
1069 [Thread-0] INFO org.eclipse.jetty.server.Server - Started @1148ms
```

## API Usage

###GetDataSetTimeRange
Provides the first and last stock price closing dates in the entire available dataset. Simply requires hitting a static URL.
```bash
curl "http://localhost:4567/reportingapi/dataset-time-range"
```

###GetClosingPrice
Provides the stock closing price for a given company and stock closing date. Requires a company stock ticker symbol (e.g. "goog") and a stock price closing date. The provided string for ticker symbol is case insensitive. Note that if no data is available for a date (such as when the stock market is closed) the price retunred from this will be "null".
```bash 
curl "http://localhost:4567/reportingapi/closing-price/{TICKER_SYMBOL e.g. "amzn"}?date={YYYY_MM_DD}}"
```

###GetAvgClosingPrice
Provides the average closing prices across a range of dates for a given company. Requires a case-insensitive stock ticker symbol and a start date (inclusive) and end date (exclusive).
```bash
curl "http://localhost:4567/reportingapi/avg-closing-price/{TICKER_SYMBOL}?startdate={YYYY_MM_DD}&enddate={YYYY_MM_DD}"
```

###GetTopGaining API
Provides the top 10 stock closing prices by % increase from the prior date (for all time). Simply requires hitting a static URL.
```bash
curl "http://localhost:4567/reportingapi/top-gaining"
```

## Answers
1. What are the first and last dates represented in the relevant data?

```bash
curl "http://localhost:4567/reportingapi/dataset-time-range" | json_pp

  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    47    0    47    0     0   3615      0 --:--:-- --:--:-- --:--:--  3615
{
   "minDate" : "1989-09-19",
   "maxDate" : "2019-11-15"
}
```

2. What was the closing price of Facebook on January 1st, 2018?

```bash
curl "http://localhost:4567/reportingapi/closing-price/fb?date=2018-01-01" | json_pp

  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100    62    0    62    0     0   5636      0 --:--:-- --:--:-- --:--:--  5636
{
   "price" : null,
   "date" : "2018-01-01",
   "companyCode" : {
      "code" : "FB"
   }
}
```

3. What was the average closing price of Amazon in the month of July 2015?

```bash
curl "http://localhost:4567/reportingapi/avg-closing-price/amzn?startdate=2015-07-01&enddate=2015-08-01" | json_pp

  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100   136    0   136    0     0   4250      0 --:--:-- --:--:-- --:--:--  4250
{
   "numberOfDaysWithData" : 22,
   "companyCode" : {
      "code" : "AMZN"
   },
   "endDate" : "2015-08-01",
   "avgPrice" : 478.709090909091,
   "startDate" : "2015-07-01"
}
```

4. Assuming the fund holds an equal number of shares of each company, what were the top 10 biggest gaining days? The output should include the day and the % change from the previous day's close.

```bash
curl "http://localhost:4567/reportingapi/top-gaining" | json_pp

  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  1068    0  1068    0     0  35600      0 --:--:-- --:--:-- --:--:-- 35600
{
   "topTenStockGains" : [
      {
         "date" : "2013-01-24",
         "percentGain" : 0.422372881355932,
         "companyCode" : {
            "code" : "NFLX"
         },
         "closingPrice" : 20.98
      },
      {
         "closingPrice" : 0.51,
         "percentGain" : 0.378378378378378,
         "companyCode" : {
            "code" : "NFLX"
         },
         "date" : "2002-10-10"
      },
      {
         "date" : "2001-11-26",
         "companyCode" : {
            "code" : "AMZN"
         },
         "percentGain" : 0.344713656387665,
         "closingPrice" : 12.21
      },
      {
         "date" : "2001-04-09",
         "closingPrice" : 11.18,
         "percentGain" : 0.335722819593787,
         "companyCode" : {
            "code" : "AMZN"
         }
      },
      {
         "date" : "1997-08-06",
         "percentGain" : 0.323943661971831,
         "companyCode" : {
            "code" : "AAPL"
         },
         "closingPrice" : 0.94
      },
      {
         "companyCode" : {
            "code" : "AMZN"
         },
         "percentGain" : 0.301783264746228,
         "closingPrice" : 9.49,
         "date" : "2001-11-14"
      },
      {
         "date" : "2013-07-25",
         "closingPrice" : 34.36,
         "percentGain" : 0.296114673708035,
         "companyCode" : {
            "code" : "FB"
         }
      },
      {
         "date" : "2007-04-25",
         "closingPrice" : 56.81,
         "percentGain" : 0.269497206703911,
         "companyCode" : {
            "code" : "AMZN"
         }
      },
      {
         "companyCode" : {
            "code" : "AMZN"
         },
         "percentGain" : 0.267950775815944,
         "closingPrice" : 118.49,
         "date" : "2009-10-23"
      },
      {
         "closingPrice" : 17.56,
         "percentGain" : 0.265129682997118,
         "companyCode" : {
            "code" : "AMZN"
         },
         "date" : "2001-01-03"
      }
   ]
}
```