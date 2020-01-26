###################################################################################################
# Dockerfile to build meeting_room_reservation container images
# BUild: docker image build -t mokhovav/meeting_room_reservation .
###################################################################################################

###################################################################################################
# defines the basic image for starting the container building process
# openjdk:11.0.4-jdk-slim - base image
# maven - assembly stage name
FROM openjdk:11.0.4-jdk-slim as maven

# author
MAINTAINER mohovav_zhukovsd

# set the directory from which the CMD command will be executed. This creates the directory if nonexistent.
WORKDIR /build

# COPY command takes two arguments, the path from where to copy the file and the path to copy files to the container’s own file system
# COPY only supports the basic copying of local files into the container
# ADD has some features (like local-only tar extraction and remote URL support)
# "." - current directory
COPY mvnw .
COPY .mvn ./.mvn

# copy pom.xml and fetch all dependencies by switching to offline mode
COPY pom.xml .
RUN chmod -R 777 .
# RUN takes the command as an argument and launches it from the image. Unlike CMD, this command is used to build an image.
RUN ./mvnw dependency:go-offline

# copy all sources and build the project
COPY ./ .
# copy hibernate.xml from production
COPY ./src/main/resources/production/ ./src/main/resources/
# clean package - delete of all artifacts created during the assembly process
# spring-boot:repackage - Repackages existing JAR and WAR archives so that they can be executed from the command line using java -jar
RUN chmod -R 777 .
RUN ./mvnw clean package spring-boot:repackage
###################################################################################################

###################################################################################################
FROM openjdk:11.0.4-jre-slim
# ENV command is used to set environment variables (one or many).
# These variables look as follows “key = value” and they are available inside the container for scripts and various applications
ENV name=meeting_room_reservation-0.0.1-SNAPSHOT

WORKDIR /srv/${name}

# --from=maven - defines the image from which to copy
COPY --from=maven /build/target/${name}.jar .
# EXPOSE is used to bind a specific port to implement network connectivity between the process inside the container and the outside world - the host
EXPOSE 8080
# It is used to execute certain programs, but, unlike RUN, this command is usually used to launch / initiate applications
# or commands after they are installed using RUN at the time of building the container.
# JAVA_OPTS is the standard environment variable. used to configure java options (not used here)
CMD java ${JAVA_OPTS} -jar ${name}.jar
###################################################################################################

###################################################################################################
# Other:
# ENTRYPOINT sets a specific default application, which is used every time a container is built using an image.
# after creating the container from the image, the application will perceive the CMD command
# command syntax: ENTRYPOINT% application% "argument"
# Example:
# CMD «Hello from Merionet!»
#  #ENTRYPOINT echo

# USER command - used to set the UID or username that will be used in the container.
# Command syntax: USER% user_id%

# VOLUME - this command is used to organize access of your container to the directory on the host (the same as mounting the directory)
# Command syntax: VOLUME ["/ dir_1", "/ dir2" ...]
###################################################################################################