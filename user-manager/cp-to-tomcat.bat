@echo off
rem
rem copy new version and delete current version of user-manger from tomcat
rem 
cp target\user-manager.war c:\applications\apache-tomcat-9.0.0.M9\webapps\user-manager.war
rmdir /s /q c:\applications\apache-tomcat-9.0.0.M9\webapps\b39
rmdir /s /q C:\applications\apache-tomcat-9.0.0.M9\work\Catalina\localhost\user-manager
