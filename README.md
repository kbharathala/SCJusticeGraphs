# SCJusticeGraphs

Our goal is to use supreme court voting history to determine alignment between judges and centrality.

We used a public data set from Wikipedia to get voting history.

supremecourt.py puts the data into CasesTrial.csv in a more easily read form. If a judge was in the majority opinion they were assigned 1, if they were in the dissent assigned 0, and if there was a concurrence then 0.5.

Based on this data we hope to run various graph tests like Centrality and PageRank. 