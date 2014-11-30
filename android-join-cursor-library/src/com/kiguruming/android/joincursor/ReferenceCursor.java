package com.kiguruming.android.joincursor;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;

/**
 * Cursor referencing other cursor.
 *
 * @author takahr@gmail.com
 */
public class ReferenceCursor implements Cursor {
    private final ReferencedCursor mReferencedCursor;
    private int mPosition;

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ReferenceCursor(Cursor cursor) {
        if (cursor instanceof ReferencedCursor) {
            mReferencedCursor = (ReferencedCursor) cursor;
        } else {
            mReferencedCursor = new ReferencedCursor(cursor);
        }
        mReferencedCursor.retain();
        mPosition = mReferencedCursor.getPosition();
    }

    public ReferencedCursor getReferencedCursor() {
        return mReferencedCursor;
    }

    @Override
    public int getCount() {
        return mReferencedCursor.getCount();
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    @Override
    public boolean move(int offset) {
        boolean result = mReferencedCursor.move(offset);
        mPosition = mReferencedCursor.getPosition();
        return result;
    }

    @Override
    public boolean moveToPosition(int position) {
        boolean result = mReferencedCursor.moveToPosition(position);
        mPosition = mReferencedCursor.getPosition();
        return result;
    }

    @Override
    public boolean moveToFirst() {
        boolean result = mReferencedCursor.moveToFirst();
        mPosition = mReferencedCursor.getPosition();
        return result;
    }

    @Override
    public boolean moveToLast() {
        boolean result = mReferencedCursor.moveToLast();
        mPosition = mReferencedCursor.getPosition();
        return result;
    }

    @Override
    public boolean moveToNext() {
        boolean result = mReferencedCursor.moveToNext();
        mPosition = mReferencedCursor.getPosition();
        return result;
    }

    @Override
    public boolean moveToPrevious() {
        boolean result = mReferencedCursor.moveToPrevious();
        mPosition = mReferencedCursor.getPosition();
        return result;
    }

    @Override
    public boolean isFirst() {
        final boolean result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.isFirst();
        }
        return result;
    }

    @Override
    public boolean isLast() {
        final boolean result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.isLast();
        }
        return result;
    }

    @Override
    public boolean isBeforeFirst() {
        final boolean result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.isBeforeFirst();
        }
        return result;
    }

    @Override
    public boolean isAfterLast() {
        final boolean result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.isAfterLast();
        }
        return result;
    }

    @Override
    public int getColumnIndex(String columnName) {
        return mReferencedCursor.getColumnIndex(columnName);
    }

    @Override
    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
        return mReferencedCursor.getColumnIndexOrThrow(columnName);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return mReferencedCursor.getColumnName(columnIndex);
    }

    @Override
    public String[] getColumnNames() {
        return mReferencedCursor.getColumnNames();
    }

    @Override
    public int getColumnCount() {
        return mReferencedCursor.getColumnCount();
    }

    @Override
    public byte[] getBlob(int columnIndex) {
        final byte[] result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.getBlob(columnIndex);
        }
        return result;
    }

    @Override
    public String getString(int columnIndex) {
        final String result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.getString(columnIndex);
        }
        return result;
    }

    @Override
    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            mReferencedCursor.copyStringToBuffer(columnIndex, buffer);
        }
    }

    @Override
    public short getShort(int columnIndex) {
        final short result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.getShort(columnIndex);
        }
        return result;
    }

    @Override
    public int getInt(int columnIndex) {
        final int result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.getInt(columnIndex);
        }
        return result;
    }

    @Override
    public long getLong(int columnIndex) {
        final Long result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.getLong(columnIndex);
        }
        return result;
    }

    @Override
    public float getFloat(int columnIndex) {
        final float result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.getFloat(columnIndex);
        }
        return result;
    }

    @Override
    public double getDouble(int columnIndex) {
        final double result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.getDouble(columnIndex);
        }
        return result;
    }

    @Override
    public int getType(int columnIndex) {
        return mReferencedCursor.getType(columnIndex);
    }

    @Override
    public boolean isNull(int columnIndex) {
        final boolean result;
        synchronized (mReferencedCursor) {
            mReferencedCursor.moveToPosition(mPosition);
            result = mReferencedCursor.isNull(columnIndex);
        }
        return result;
    }

    @Override
    public void deactivate() {
        mReferencedCursor.deactivate();
    }

    @Override
    public boolean requery() {
        return false;
    }

    @Override
    public void close() {
        mReferencedCursor.close();
    }

    @Override
    public boolean isClosed() {
        return mReferencedCursor.isClosed();
    }

    @Override
    public void registerContentObserver(ContentObserver observer) {
        mReferencedCursor.registerContentObserver(observer);
    }

    @Override
    public void unregisterContentObserver(ContentObserver observer) {
        mReferencedCursor.unregisterContentObserver(observer);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mReferencedCursor.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        mReferencedCursor.unregisterDataSetObserver(observer);
    }

    @Override
    public void setNotificationUri(ContentResolver cr, Uri uri) {
        mReferencedCursor.setNotificationUri(cr, uri);
    }

    @Override
    public Uri getNotificationUri() {
        return mReferencedCursor.getNotificationUri();
    }

    @Override
    public boolean getWantsAllOnMoveCalls() {
        return getWantsAllOnMoveCalls();
    }

    @Override
    public Bundle getExtras() {
        return getExtras();
    }

    @Override
    public Bundle respond(Bundle extras) {
        return respond(extras);
    }
}
