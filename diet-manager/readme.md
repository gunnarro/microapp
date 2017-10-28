
ref: http://docs.spring.io/autorepo/docs/spring-social/1.1.4.RELEASE/reference/htmlsingle/#section_signUpForm
ref: http://sunilkumarpblog.blogspot.no/2016/04/social-login-with-spring-security.html
ref: https://github.com/sunilpulugula/SpringSecuritySocialLoginExample/blob/master/src/main/webapp/WEB-INF/pages/login.jsp
# DietManager

## Introduction

GitHub: https://developer.github.com/v3/oauth/
Facebook Register and Configure an App https://developers.facebook.com/docs/apps/register

## Prerequisites

#### details :


## Build :

### If running docker daemon and kubernetes locally 
mvn clean install


### Openshift, use SourceToImage build (S2I)
For bulid docker image with openshift
- mvn clean install -Dfabric8.mode=openshift

Deploy to openshift
- mvn fabric8:deploy -Dfabric8.mode=openshift

Note! OpenShift tools (oc) must be installed (require 64 bit operating system)


### Add new app by oc -tools
 ref: https://blog.codecentric.de/en/2016/03/deploy-spring-boot-applications-openshift/
 
- oc new-app codecentric/springboot-maven3-centos~https://github.com/gunnarro/microapp.git
- oc expose service microapp --hostname=microapp.api.starter-us-west-2.openshift.com
 

## Deploy :




