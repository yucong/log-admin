FROM openjdk:8u181-jdk-alpine
ENV  WORKPATH /app
WORKDIR $WORKPATH
ADD log-admin-*.jar log-admin.jar
VOLUME $WORKPATH
EXPOSE 9003
ENTRYPOINT ["java","-Xmx256m","-Xms256m","-XX:+HeapDumpOnOutOfMemoryError","-XX:HeapDumpPath=/usr/local/","-Duser.timezone=GMT+8","-jar","log-admin.jar"]
