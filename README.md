### Order Service ###
An assignment that implements fail-over mechanism using Apache Zookeeper.

#### Requirements ####
* Java 1.7
* Maven with Tomcat plugin
* Zookeeper
* Redis
* Jar package of https://github.com/hunghoang/masterslave

#### Config ####
Redis connection, key name and the number of items accounted for in the statistics services are configurable
at `/orderservice/src/main/resources/config.properties`

#### Build ####
* Build https://github.com/hunghoang/masterslave in order to have the required jar available in the local maven repository
* Without tests: `mvn clean install -DskipTests`
* With unit tests: `mvn clean install` (a Redis server with proper [config](#config) is required)

#### Deployment ####
* Start zookeeper on the local machine that listens on port `2181`
* Start redis as [configured](#config)
* Run `mvn tomcat:run -Dmaven.tomcat.port=<port>`

Without the `-Dmaven.tomcat.port` parameter, the web app will be binded to port 8080 by default

The web app is then available at `http://localhost:<port>/orderservice/`

* Run several server instances at different ports simultaneously in order to test fail-over mechanism
* `/orderservice/test.html` could be used to test `POST` order services

#### Services ####
##### Place Order #####
```POST http://localhost:<port>/orderservice/api/order/placeOrder```

`POST` parameters:

* account: string
* symbol: string
* price: float
* volume: integer
* type: string

##### Replace Order #####
```POST http://localhost:<port>/orderservice/api/order/replaceOrder```

`POST` parameters:

* id: string
* account: string
* symbol: string
* price: float
* volume: integer
* type: string

##### Cancel Order #####
```POST http://localhost:<port>/orderservice/api/order/cancelOrder```

`POST` parameters:

* id: string
* account: string
* symbol: string
* price: float
* volume: integer
* type: string

##### Top-value Orders #####
```GET http://localhost:<port>/orderservice/api/stats/topOrders```

##### Accounts with top-value orders #####
```GET http://localhost:<port>/orderservice/api/stats/topAccounts```
