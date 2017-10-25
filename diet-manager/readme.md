
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

If running docker daemon and kubernetes locally 
mvn clean install


Openshift, use SourceToImage build (S2I)
For bulid docker image with on openshift
mvn clean install -Dfabric8.mode=openshift

Deploy to openshift
mvn fabric8:deploy -Dfabric8.mode=openshift

Note! OpenShift tools (oc) must be installed (require 64 bit operating system)


## Deploy :

