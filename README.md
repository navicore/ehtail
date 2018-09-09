[![Build Status](https://travis-ci.org/navicore/ehtail.svg?branch=master)](https://travis-ci.org/navicore/ehtail)

ehtail - a cli to monitor Azure EventHubs content
-----

## INSTALL

```console
sbt clone git@github.com:navicore/ehtail.git
cd ehtail
sbt assembly
```

## USAGE

#### for help
```console
java -jar target/scala-2.12/EhTail.jar -h
```

#### for reading all messages from the beginning of an eh with 8 partitions
```console
java -jar target/scala-2.12/EhTail.jar -c myConsumerGroup -o EARLIEST -p 8 "Endpoint=sb://CHANGEME.servicebus.windows.net/;SharedAccessKeyName=CHANGEME;SharedAccessKey=CHANGEME=;EntityPath=CHANGEME"
```

#### for reading all new messages with pretty print
```console
java -jar target/scala-2.12/EhTail.jar --pretty -c myConsumerGroup -p 8 "Endpoint=sb://CHANGEME.servicebus.windows.net/;SharedAccessKeyName=CHANGEME;SharedAccessKey=CHANGEME=;EntityPath=CHANGEME"
```

