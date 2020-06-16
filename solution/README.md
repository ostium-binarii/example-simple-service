# Solution: {github-username}

**Date:** {date-completed}

## Summary

{Add a summary here describing the overall solution in your own words.}

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
{Add command output here}
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
