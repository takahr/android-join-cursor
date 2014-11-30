package com.kiguruming.android.joincursor;

import android.database.Cursor;
import android.database.CursorWrapper;

/**
 * Cursor windowed from position and count.
 *
 * takahr@gmail.com
 */
public class NarrowedCursor extends ReferenceCursor {
    private final int mFrom;
    private final int mCount;

    /**
     * Creates a windowed cursor, windowed from position and count.
     *
     * @param cursor The underlying cursor to wrap.
     * @param from window position from.
     * @param count cursor count.
     */
    public NarrowedCursor(Cursor cursor, int from, int count) {
        super(cursor);
        mFrom = from;
        mCount = count;
        moveToPosition(0);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public boolean moveToPosition(int position) {
        return super.moveToPosition(position + mFrom);
    }

    @Override
    public boolean moveToFirst() {
        return moveToPosition(mFrom);
    }

    @Override
    public boolean moveToLast() {
        return moveToPosition(mFrom + mCount - 1);
    }

}
