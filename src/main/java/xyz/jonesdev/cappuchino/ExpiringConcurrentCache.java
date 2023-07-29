/*
 * Copyright (C) 2023 Sonar Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.jonesdev.cappuchino;

import lombok.Getter;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

final class ExpiringConcurrentCache<K> extends ConcurrentHashMap<K, Long> implements ExpiringCache<K> {
  @Getter
  private final long duration;
  private volatile long lastCleanUpTimestamp;
  // Add a cool-down of 2.5 seconds between each cleanUp() task.
  // This is done to prevent any sort of performance impact.
  private final long minElapsedBeforeClean;

  // Return map instance of this class
  @Override
  public ConcurrentHashMap<K, Long> asMap() {
    return this;
  }

  public ExpiringConcurrentCache(final long duration,
                                 final TimeUnit timeUnit,
                                 final long minElapsedBeforeClean) {
    this.duration = TimeUnit.MILLISECONDS.convert(duration, timeUnit);
    this.minElapsedBeforeClean = minElapsedBeforeClean;
  }

  /**
   * Puts a key into the map with the current timestamp
   */
  @Override
  public void put(final K key) {
    put(key, System.currentTimeMillis());
  }

  /**
   * Removes a key from the map
   */
  @Override
  public void invalidate(final K key) {
    remove(key);
  }

  /**
   * Cleans all entries and then returns the size of the map
   *
   * @see #cleanUp()
   */
  @Override
  public int estimatedSize() {
    cleanUp();
    return size();
  }

  /**
   * Removes all entries from the map
   */
  @Override
  public void invalidateAll() {
    clear();
  }

  /**
   * Checks if the map contains a key
   */
  @Override
  public boolean has(final K key) {
    return containsKey(key);
  }

  /**
   * Removes all expired entries of the map
   *
   * @see #estimatedSize()
   */
  @Override
  public void cleanUp() {
    cleanUp(false);
  }

  /**
   * Removes all expired entries of the map
   *
   * @param force Bypass min elapsed time check
   * @see #estimatedSize()
   */
  @Override
  public void cleanUp(final boolean force) {
    final long timestamp = System.currentTimeMillis();
    if (!force) {
      final long elapsed = timestamp - lastCleanUpTimestamp;
      // Check if this task is being executed too frequently
      if (elapsed < minElapsedBeforeClean) {
        // Just don't do anything
        return;
      }
      lastCleanUpTimestamp = timestamp;
    }

    final Iterator<Entry<K, Long>> iterator = entrySet().iterator();
    while (iterator.hasNext()) {
      final Entry<K, Long> entry = iterator.next();
      final long entryTimestamp = entry.getValue();
      final long delay = timestamp - entryTimestamp;

      // Invalidate entry if duration is over
      if (delay > duration) {
        iterator.remove();
      }
    }
  }
}
