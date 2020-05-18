FROM docker.io/relateiq/oracle-java8
VOLUME /tmp
ADD target/acgcloud.jar acgcloud.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/acgcloud.jar"]
EXPOSE 8088