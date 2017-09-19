
# Confy

## Typesafe, container ready configurations for java

[![Build Status](https://travis-ci.org/GaruGaru/confy.svg?branch=master)](https://travis-ci.org/GaruGaru/confy2)
[![codecov](https://codecov.io/gh/GaruGaru/confy2/branch/master/graph/badge.svg)](https://codecov.io/gh/GaruGaru/confy2/branch/master)
[![](https://jitpack.io/v/GaruGaru/confy2.svg)](https://jitpack.io/#GaruGaru/confy2)


### Usage 

#### Define configuration interface

```java
    public interface MyConfiguration {
    
        @Param.String(key = "name", defaultValue = "localhost")
        String getHost();
    
        @Param.Integer(key = "port", defaultValue = 0)
        int getPort();
    
        @Param.Float(key = "threshold", defaultValue = 0.5F)
        float getThreshold();
    
    }
```

#### Make Confy implements methods (by default from env.)

```java
    MyConfiguration conf = Confy.implement(MyConfiguration.class);
    String host = conf.getHost();
    int port = conf.getPort();
```

#### Load from properties

```java
    MyConfiguration conf = Confy.implement(MyConfiguration.class, "myconf.properties");
    String host = conf.getHost();
    int port = conf.getPort();
``` 

#### Custom loading

```java
    boolean useEnv = true;
    MyConfiguration conf = Confy.implement(MyConfiguration.class, "myconf.properties", useEnv);
    String host = conf.getHost();
    int port = conf.getPort();  
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
	    <version>1.0</version>
	</dependency>
	
	