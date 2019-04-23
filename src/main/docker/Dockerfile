FROM java:8
VOLUME /tmp
ADD target/b-orders.war b-orders.war
EXPOSE 8085
ENTRYPOINT ["java","b-orders.war"]