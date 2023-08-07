/*
 * Copyright (C) 2023 jones
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

public interface Cache<K> {

  /**
   * Puts the given key into the cache
   *
   * @param key Key to cache
   */
  void put(final K key);

  /**
   * Removes the given key from the cache
   *
   * @param key Key to invalidate
   */
  void invalidate(final K key);

  /**
   * @param key Key to check
   * @return Whether the key is cached
   */
  boolean has(final K key);

  /**
   * @return Estimated (cached) size of the cache
   */
  int estimatedSize();

  /**
   * Invalidates every entry of the cache
   */
  void invalidateAll();
}
