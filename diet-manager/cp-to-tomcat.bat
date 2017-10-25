@echo off
rem
rem copy new version and delete current version of diet-manger from tomcat
rem 
cp target\dietmanager.war c:\applications\apache-tomcat-9.0.0.M9\webapps\dietmanager.war
rmdir /s /q c:\applications\apache-tomcat-9.0.0.M9\webapps\dietmanager
rmdir /s /q C:\applications\apache-tomcat-9.0.0.M9\work\Catalina\localhost\dietmanager