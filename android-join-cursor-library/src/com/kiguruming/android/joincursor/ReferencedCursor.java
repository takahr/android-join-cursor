package com.kiguruming.android.joincursor;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Cursor with reference count.
 *
 * @author takahr@gmail.com
 */
public class ReferencedCursor extends CursorWrapper {
    protected final Cursor mCursor;
    private int mReferencedCount;

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ReferencedCursor(Cursor cursor) {
        super(cursor);
        mCursor = cursor;
        mReferencedCount = 0;
    }

    synchronized public Cursor retain() {
        mReferencedCount++;
        return mCursor;
    }

    @Override
    synchronized public void close() {
        mReferencedCount--;
        if (mReferencedCount <= 0) {
            super.close();
        }
    }
}
