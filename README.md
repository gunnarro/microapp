"# microapp"

oc new-app codecentric/springboot-maven3-centos~https://github.com/gunnarro/microapp.git
oc expose service microapp --hostname=microapp.api.starter-us-west-2.openshift.com
