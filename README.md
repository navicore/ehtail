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

```console
export EVENTHUBS_1_CONNSTR="CHANGE ME"
export EVENTHUBS_1_CONSUMER_GROUP="CHANGE ME"
export EVENTHUBS_1_PARTITION_COUNT="?"
#export EVENTHUBS_1_DEFAULT_OFFSET="earliest"  # 'latest' is default
#export PRETTY="true" # default
export EVENTHUBS_1_RECEIVER_TIMEOUT="8h"  # long timeouts decrease noise in restarts - restarts are also work-in-progress and are buggy

java 
```

## KNOWN BUGS

* may not reliably reconnect after default timeout of 2 minutes (workaround is increase `EVENTHUBS_1_RECEIVER_TIMEOUT`)

