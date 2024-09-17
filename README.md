# Apache-analytics-spring

This is the solution to the home test. 
It is a Java project using SpringBoot. 

## How to run
you can run manually in the IDE after pulling the project,<br> 
go to http://localhost:8080/swagger-ui.html here you'll see the swagger ui with the requests.
if you go to http://localhost:8080/ you'll have a nice html page where you can upload a log file and get the stats in a pie chart form. 

You can also pull a docker image I've made and run it in the command prompt:


```bash
pull michellei/apache-analytics:0.0.1
docker run -p 8080:8080 michellei/apache-analytics:0.0.1
```
once it starts you can visit the same links: http://localhost:8080/swagger-ui.html and http://localhost:8080.
