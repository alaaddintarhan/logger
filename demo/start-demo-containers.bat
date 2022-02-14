@ECHO OFF
set KAFKA_SERVER=kafka:9092
set NETWORK=reactive-app

start /b docker run -d ^
  -e MP_MESSAGING_CONNECTOR_LIBERTY_KAFKA_BOOTSTRAP_SERVERS=%KAFKA_SERVER% ^
  -p 9090:9090 ^
  --network=%NETWORK% ^
  --name=demo ^
  --rm ^
  demo:1.0-SNAPSHOT