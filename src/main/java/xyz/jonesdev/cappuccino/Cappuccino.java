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

package xyz.jonesdev.cappuccino;

import java.util.concurrent.TimeUnit;

public interface Cappuccino {

  /**
   * Returns an expiring cache
   *
   * @param duration Duration in milliseconds
   * @return ExpiringCache
   * @param <K> Type of the key
   * @see #buildExpiring(long, TimeUnit)
   */
  static <K> ExpiringCache<K> buildExpiring(final long duration) {
    return buildExpiring(duration, TimeUnit.MILLISECONDS);
  }

  /**
   * Returns an expiring cache
   *
   * @param duration Duration in the given unit
   * @return ExpiringCache
   * @param <K> Type of the key
   * @param timeUnit Time unit of the duration
   * @see #buildExpiring(long, TimeUnit, long)
   */
  static <K> ExpiringCache<K> buildExpiring(final long duration,
                                            final TimeUnit timeUnit) {
    return buildExpiring(duration, timeUnit, 0L);
  }

  /**
   * Returns an expiring cache
   *
   * @param duration Duration in the given unit
   * @return ExpiringCache
   * @param <K> Type of the key
   * @param timeUnit Time unit of the duration
   * @param minElapsedBeforeClean Time between each clean (in milliseconds)
   */
  static <K> ExpiringCache<K> buildExpiring(final long duration,
                                            final TimeUnit timeUnit,
                                            final long minElapsedBeforeClean) {
    return new ExpiringConcurrentCache<>(duration, timeUnit, minElapsedBeforeClean);
  }
}
