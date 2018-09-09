[![Build Status](https://travis-ci.org/navicore/ehtail.svg?branch=master)](https://travis-ci.org/navicore/ehtail)

ehtail - a cli to monitor Azure EventHubs content
-----

## INSTALL

Presumes 
  * you have `$HOME/bin` in your `PATH`
  * `sbt` is installed
  * `java 1.8+` is installed

```console
sbt clone git@github.com:navicore/ehtail.git
cd ehtail
./install.sh 
<open a new shell instance>
```

## USAGE

#### for help
```console
ehtail -h
```

#### for reading all messages from the beginning of an eh with 8 partitions
```console
ehtail -c myConsumerGroup -o EARLIEST -p 8 "Endpoint=sb://CHANGEME.servicebus.windows.net/;SharedAccessKeyName=CHANGEME;SharedAccessKey=CHANGEME=;EntityPath=CHANGEME"
```

#### for reading all new messages with pretty print
```console
ehtail --pretty -c myConsumerGroup -p 8 "Endpoint=sb://CHANGEME.servicebus.windows.net/;SharedAccessKeyName=CHANGEME;SharedAccessKey=CHANGEME=;EntityPath=CHANGEME"
```

