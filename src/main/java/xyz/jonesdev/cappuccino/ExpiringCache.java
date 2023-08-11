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

package xyz.jonesdev.cappuccino;

import java.util.concurrent.ConcurrentHashMap;

public interface ExpiringCache<K> extends Cache<K> {

  /**
   * @return Amount of time that has to elapse before an entry is invalidated
   */
  long getDuration();

  /**
   * Invalidates all expired entries.
   */
  void cleanUp();

  /**
   * @see #cleanUp()
   * @param force Whether the minElapsedBeforeClean check should be enforced
   */
  void cleanUp(final boolean force);

  /**
   * @return The actual ConcurrentHashMap that holds the cache
   */
  ConcurrentHashMap<K, Long> asMap();
}
