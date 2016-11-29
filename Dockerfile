
FROM tomcat:2.0
MAINTAINER <zhangliwen_0920@126.com>
ENV REFRESHED_AT 2016/3/15

ADD 10.war /usr1/tomcat/apache-tomcat-7.0.68/webapps/10.war
EXPOSE 8088
RUN chmod u+x -R /usr1/tomcat/apache-tomcat-7.0.68/bin
CMD /usr1/tomcat/apache-tomcat-7.0.68/bin/catalina.sh run
 
