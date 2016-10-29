#!/usr/bin/env bash
ps -ef | grep "java.*servicev2.jar" | grep -v grep | awk '{print $2}' | xargs kill -9
nohup java -Djava.security.egd=file:/dev/./urandom -jar servicev2.jar > webv2.out 2> webv2.err < /dev/null &
exit