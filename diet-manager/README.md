# diet-manager

## release
git tag -a v1.7.0 -m "release 1.7.0"
git push origin v1.7.0

## Build and Run
mvn clean install -DskipTests
mvn spring-boot:run -Dspring-boot.run.profiles=dev

i## Cloud sql
mysql -uroot -p -h 35.228.155.125 --ssl-ca=~/.ssh/cloud-sql-server-ca.pem --ssl-cert=~/.ssh/cloud-sql-client-cert.pem --ssl-key=~/.ssh/cloud-sql-client-key.pem


 mysql -uroot -p -h 35.228.155.125 --ssl-ca=~/.ssh/cloud-sql-server-ca.pem --ssl-cert=~/.ssh/cloud-sql-client-cert.pem --ssl-key=~/.ssh/cloud-sql-client-key.pem < /home/mentos/code/github/diet-manager/database/dietmanager.sql


