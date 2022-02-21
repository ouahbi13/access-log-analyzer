# access-log-analyzer

In this project, we analyze Apache Server Access Logs using Spark SQL Structured Streaming, and ELK stack (Elasticsearch, Logstash, Kibana). The access logs are generated using a Python script.
<br>
<div align="center">
  <img width="500px" src="images/elk.png">
</div>
<br>
Using Kibana UI for visualization we get the following results:
<div align="center">
  <h3>Visited websites counts :</h3>
  <img width="500px" src="images/visited-counts.png">
  <h3>Status code counts :</h3>
  <img width="500px" src="images/status-counts.png">
</div>
<br>
Side Note:
  
  - The proportions in the pie charts are equal since the python random function, used in generating the access logs, uses an uniform distribution to generate random results. So we except the results to have same occurrences when the number of access logs is high.

