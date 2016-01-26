mkdir ./bin
mvn clean package
mv ./target/wechat-1.0.0.0-SNAPSHOT.jar ./bin
cd bin/
nohup java -jar wechat-1.0.0.0-SNAPSHOT.jar > /dev/null 2>&1 &