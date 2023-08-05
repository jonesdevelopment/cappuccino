## ☕ cappuchino
![](https://img.shields.io/github/v/release/jonesdevelopment/cappuccino)
![](https://img.shields.io/github/issues/jonesdevelopment/cappuccino)
<p>
  cappuchino is a small Java caching library used in <a href="https://github.com/jonesdevelopment/sonar">Sonar</a>.
  <br>
  It is designed to be a basic & lightweight dependency for your Java application.
</p>

### Main purpose
Create lists that expire after a certain amount of time

### Example usage
First, you need to add the maven repository server to your project.

```
https://repo.jonesdev.xyz/releases/
```

Second, you need to add the dependency to your project.

```
<dependency>
  <groupId>xyz.jonesdev</groupId>
  <artifactId>cappuchino</artifactId>
  <version>0.1.4-SNAPSHOT</version>
</dependency>
```

Then, you can create a list that expires after 1 second (= 1000 milliseconds).
```java
import xyz.jonesdev.cappuchino.Cappuchino;
import xyz.jonesdev.cappuchino.ExpiringCache;

// ...
ExpiringCache<Account> yourExpiringCache = Cappuchino.buildExpiring(1000L);
```

`Cappuchino#buildExpiring(long)` creates a cache where the values decay after 1000 milliseconds.
<br>
If you want to edit the duration of the values, you can also select a custom [TimeUnit](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html).

```java
import xyz.jonesdev.cappuchino.Cappuchino;
import xyz.jonesdev.cappuchino.ExpiringCache;

import java.util.concurrent.TimeUnit;

// ...
ExpiringCache<Account> yourExpiringCache = Cappuchino.buildExpiring(1L, TimeUnit.SECONDS);
```

If you don't clean the cache, no values will be removed after the given duration.
<br>
To prevent this from happening, you must call the cleanUp() method before using the cache.

```java
int getRegisteredAmount() {
  yourExpiringCache.cleanUp();
  return yourExpiringCache.size();
}
```
