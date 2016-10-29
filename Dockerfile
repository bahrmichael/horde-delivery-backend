FROM michaelbahr/java8-maven3

WORKDIR /home/

ADD cert.cer .
RUN echo changeit | keytool -import -alias example -keystore  /opt/java-oracle/jdk1.8.0_11/jre/lib/security/cacerts -file cert.cer -noprompt

RUN mkdir key

RUN mkdir src
ADD pom.xml .

ADD src src
RUN mvn package

EXPOSE 8444

ENTRYPOINT java -Djava.security.egd=file:/dev/./urandom -jar target/horde-delivery*.jar