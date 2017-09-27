@echo off
rem
rem copy new version and delete current version of image-manger from tomcat
rem 
cp target\image-manager.war c:\applications\apache-tomcat-9.0.0.M9\webapps\image-manager.war
rmdir /s /q c:\applications\apache-tomcat-9.0.0.M9\webapps\image-manager
rmdir /s /q C:\applications\apache-tomcat-9.0.0.M9\work\Catalina\localhost\image-manager