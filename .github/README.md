<div align="center">
  <!-- Introduction -->
  <p>
    <h1>☕ cappuccino</h1>
    Lightweight and simple dependency for creating expiring lists in your Java application.
  </p>

  <!-- Badges & icons -->
  [![](https://img.shields.io/github/v/release/jonesdevelopment/cappuccino)](https://github.com/jonesdevelopment/cappuccino/releases)
  [![](https://img.shields.io/github/issues/jonesdevelopment/cappuccino)](https://github.com/jonesdevelopment/cappuccino/issues)
  [![](https://img.shields.io/discord/923308209769426994.svg?logo=discord)](https://jonesdev.xyz/discord)
  [![](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
  <br>
  <br>
    <!-- Quick navigation -->
  [Releases](https://github.com/jonesdevelopment/cappuccino/releases)
  |
  [Issues](https://github.com/jonesdevelopment/cappuccino/issues)
  |
  [Pull Requests](https://github.com/jonesdevelopment/cappuccino/pulls)
  |
  [Discord](https://jonesdev.xyz/discord)
</div>

## Goal

Create lists that expire after a certain amount of time

## Example usage

First, you need to add the maven repository server to your project.

```
https://repo.jonesdev.xyz/releases/
```

Second, you need to add the dependency to your project.

```
<dependency>
  <groupId>xyz.jonesdev</groupId>
  <artifactId>cappuccino</artifactId>
  <version>0.1.5-SNAPSHOT</version>
</dependency>
```

Then, you can create a list that expires after 1 second (= 1000 milliseconds).

```java
import xyz.jonesdev.cappuccino.Cappuccino;
import xyz.jonesdev.cappuccino.ExpiringCache;

// ...
ExpiringCache<Account> yourExpiringCache = Cappuccino.buildExpiring(1000L);
```

`Cappuccino#buildExpiring(long)` creates a cache where the values decay after 1000 milliseconds.
<br>
If you want to edit the duration of the values, you can also select a
custom [TimeUnit](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/TimeUnit.html).

```java
import xyz.jonesdev.cappuccino.Cappuccino;
import xyz.jonesdev.cappuccino.ExpiringCache;

import java.util.concurrent.TimeUnit;

// ...
ExpiringCache<Account> yourExpiringCache = Cappuccino.buildExpiring(1L, TimeUnit.SECONDS);
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
