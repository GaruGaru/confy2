
# Confy

## Typesafe, container ready configurations for java

[![Build Status](https://travis-ci.org/GaruGaru/confy.svg?branch=master)](https://travis-ci.org/GaruGaru/confy2)
[![codecov](https://codecov.io/gh/GaruGaru/confy2/branch/master/graph/badge.svg)](https://codecov.io/gh/GaruGaru/confy2/branch/master)
[![](https://jitpack.io/v/GaruGaru/confy2.svg)](https://jitpack.io/#GaruGaru/confy2)


### Usage 

#### Define configuration interface

```java
    public interface MyConfiguration {
    
        @Param.String() // Key from method name
        String getHost();
    
        @Param.Integer(key = "port", defaultValue = 0)
        int getPort();
    
        @Param.Float(key = "threshold", defaultValue = 0.5F)
        float getThreshold();
    
    }
```

#### Make Confy implements methods (by default from env. and configuration.properties if present)

```java
    MyConfiguration conf = Confy.implement(MyConfiguration.class);
    String host = conf.getHost();
    int port = conf.getPort();
```

#### Custom loading 

```java
    MyConfiguration conf = Confy.fromArgs(args)
                .withProperty("myconf.properties")
                .withEnv()
                .to(MyConfiguration.class)
    String host = conf.getHost();
    int port = conf.getPort();
``` 

### Keys resolution using method names

```java
    public interface DatabaseConfiguration {
    
        @Param.String() 
        String getDatabaseHost(); // Env: DATABASE_HOST | Property: database.host

        @Param.Integer(defaultValue = 3306)
        int getDatabasePort(); // Env: DATABASE_PORT| Property: database.port
        
    }
```
   
### Use the same keys between env and properties!

test.properties
    
    my.env=confy

environment 

    MY_ENV=confy-env

Get using same key

    confy.get("my.env"): "confy-env"

### Installation

##### Add jitpack repository

```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

##### Add dependency 

```xml
	<dependency>
	    <groupId>com.github.GaruGaru</groupId>
	    <artifactId>confy2</artifactId>
	    <version>1.3</version>
	</dependency>
	
	