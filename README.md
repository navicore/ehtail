[![Build Status](https://travis-ci.org/navicore/ehtail.svg?branch=master)](https://travis-ci.org/navicore/ehtail)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

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

