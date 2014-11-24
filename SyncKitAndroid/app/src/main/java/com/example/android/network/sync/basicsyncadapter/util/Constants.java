package com.example.android.network.sync.basicsyncadapter.util;

/**
 * Created by Veni on 11/24/14.
 */
public class Constants {

    public enum SYNC_STATUS
    {
        SYNCED(0),
        INSERTED(1),
        DELETED(2),
        DIRTY(3),
        CONFLICTED(4);

        private int _value;

        SYNC_STATUS(int Value) {
            this._value = Value;
        }

        public int getValue() {
            return _value;
        }
    }
}
