package com.spiffymap.datalog.ui.mvp.stations;

/**
 *
 * @author steve
 */
@FunctionalInterface
public interface StationSetter<T> {
    void set(T value);
}
