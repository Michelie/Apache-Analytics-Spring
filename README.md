# Apache-analytics-spring

This is the solution to the home test. 
It is a Java project using SpringBoot. 

## How to run
You can run manually in the IDE after pulling the project,<br> 
and go to http://localhost:8080/swagger-ui.html here you'll see the swagger ui with the requests.<br>
through the POST request you can upload .log file. <br>
the GET request: /print will print to the console all the stats.<br>
the GET request: /stats would return a JSON with all stas.<br>
the GET request /stats/{type} - you can enter Country, Os or Browser and it will return JSON with the stats according to the type.
if you go to http://localhost:8080/ you'll have a nice html page where you can upload a log file and get the stats in a pie chart form. 

You can also pull a docker image I've made and run it in the command prompt:

```bash
pull michellei/apache-analytics:0.0.1
docker run -p 8080:8080 michellei/apache-analytics:0.0.1
```
once it starts you can visit the same links: http://localhost:8080/swagger-ui.html and http://localhost:8080<br>
and it will work the same.
