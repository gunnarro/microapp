@echo off
rem
rem copy new version and delete current version of calendar-manger from tomcat
rem 
cp target\calendar-manager.war c:\applications\apache-tomcat-9.0.0.M9\webapps\calendar-manager.war
rmdir /s /q c:\applications\apache-tomcat-9.0.0.M9\webapps\calendar-manager
rmdir /s /q C:\applications\apache-tomcat-9.0.0.M9\work\Catalina\localhost\calendar-manager