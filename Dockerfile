FROM openjdk:17
COPY target/apache-analytics*.jar /usr/src/apache-analytics.jar
COPY src/main/resources/application-docker.properties /opt/conf/application-docker.properties
COPY src/main/resources/geolite/GeoLite2-Country.mmdb /opt/conf/GeoLite2-Country.mmdb
CMD ["java", "-jar", "/usr/src/apache-analytics.jar", "--spring.config.location=file:/opt/conf/application-docker.properties"]