#!/usr/bin/env bash
mvn package
docker build . -t michaelbahr/horde-delivery-backend