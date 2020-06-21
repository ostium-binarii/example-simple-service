# Solution: {github-username}

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
curl http://localhost:4567/dataset-time-range
{"maxDate":"2019-11-15","minDate":"1989-09-19"}
```

2. What was the closing price of Facebook on January 1st, 2018?

```bash
{Add command output here}
```

3. What was the average closing price of Amazon in the month of July 2015?

```bash
{Add command output here}
```

4. Assuming the fund holds an equal number of shares of each company, what were the top 10 biggest gaining days? The output should include the day and the % change from the previous day's close.

```bash
{Add command output here}
```

## Ambiguity Notes

{Add notes here if you were unsure about any questions and what specific implementation choices were made.}
