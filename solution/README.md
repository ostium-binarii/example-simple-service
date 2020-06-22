# Solution: ostium-binarii (candidate Daniel Oh)

**Date:** 2020-06-22

## Summary

{Add a summary here describing the overall solution in your own words.}

http://sparkjava.com/documentation#getting-started

CALL OUT- if we wanted to use somethinng more scalable then Spark, the current code
architecture isn't a two way door. If we intended to replace Spark with something 
more scalable, then we should have decoupled Spark Request/Responses from the handlers,
for example SHOW CODE EXAMPLE 

Logging: lombok log4j, mdc (AWS Cloudwatch)
https://plugins.jetbrains.com/plugin/6317-lombok
https://stackoverflow.com/questions/42829246/lombok-log4j2-annotation-doesnt-work-in-intellij-idea


## Requirements

{Add breakdown of requirements necessary to run your solution. What steps would a brand new developer need to take to get the solution running locally? What is the command to run the unit tests?}

## Usage

{Add breakdown of usage, e.g. how do you run the HTTP service?}

## Answers

For each answer, copy/paste the execution of a simple `curl` command against your HTTP service and with its output and time execution.

---

#### Example

For example, the answer to Question #1 might look like so:

```bash
⇒  time curl http://localhost:5000/first_last_days
{
  "first_date": "YYYY-MM-DD",
  "last_date": "YYYY-MM-DD"
}
curl http://localhost:5000/first_last_days  0.01s user 0.01s system 14% cpu 0.120 total
```

---

1. What are the first and last dates represented in the relevant data?

```bash
curl http://localhost:4567/reportingapi/dataset-time-range
{"minDate":"1989-09-19","maxDate":"2019-11-15"}
```

2. What was the closing price of Facebook on January 1st, 2018?

```bash
curl "http://localhost:4567/reportingapi/closing-price/fb?date=2018-01-01"
{"companyCode":{"code":"FB"},"date":"2018-01-01","price":null}
```

3. What was the average closing price of Amazon in the month of July 2015?

```bash
curl "http://localhost:4567/reportingapi/avg-closing-price/amzn?startdate=2015-07-01&enddate=2015-08-01"
{"companyCode":{"code":"AMZN"},"startDate":"2015-07-01","endDate":"2015-08-01","numberOfDaysWithData":22,"avgPrice":478.709090909090909}
```

4. Assuming the fund holds an equal number of shares of each company, what were the top 10 biggest gaining days? The output should include the day and the % change from the previous day's close.

```bash
curl "http://localhost:4567/reportingapi/top-gaining"
{
 "topTenStockGains":
    [
    {"companyCode":{"code":"NFLX"},"date":"2013-01-24","closingPrice":20.98,"percentGain":0.422372881355932},
    {"companyCode":{"code":"NFLX"},"date":"2002-10-10","closingPrice":0.51,"percentGain":0.378378378378378},
    {"companyCode":{"code":"AMZN"},"date":"2001-11-26","closingPrice":12.21,"percentGain":0.344713656387665},
    {"companyCode":{"code":"AMZN"},"date":"2001-04-09","closingPrice":11.18,"percentGain":0.335722819593787},
    {"companyCode":{"code":"AAPL"},"date":"1997-08-06","closingPrice":0.94,"percentGain":0.323943661971831},
    {"companyCode":{"code":"AMZN"},"date":"2001-11-14","closingPrice":9.49,"percentGain":0.301783264746228},
    {"companyCode":{"code":"FB"},"date":"2013-07-25","closingPrice":34.36,"percentGain":0.296114673708035},
    {"companyCode":{"code":"AMZN"},"date":"2007-04-25","closingPrice":56.81,"percentGain":0.269497206703911},
    {"companyCode":{"code":"AMZN"},"date":"2009-10-23","closingPrice":118.49,"percentGain":0.267950775815944},
    {"companyCode":{"code":"AMZN"},"date":"2001-01-03","closingPrice":17.56,"percentGain":0.265129682997118}
    ]
}
```

## Ambiguity Notes

{Add notes here if you were unsure about any questions and what specific implementation choices were made.}
