package com.kiguruming.android.joincursor;

import android.database.Cursor;

/**
 * JoinCursor for sorted Cursors.
 *
 * @author takahr@gmail.com
 */
public class SortedJoinCursor extends ReferenceCursor {
    private final ReferencedCursor mChildrenCursor;
    private final int mParentKeyColumn;
    private final int mChildKeyColumn;
    private final int mKeyType;

	static class ChildInfo {
		int position;
		int count;

		ChildInfo(int position, int count) {
			this.position = position;
			this.count = count;
		}

		ChildInfo() {
		}
	}
    private final ChildInfo[] mChildInfos;

    /**
     * Creates a joined cursor..
     *
     * @param parentCursor The wrapped parent Cursor..
     * @param childrenCursor The referencing children Cursor..
     */
    public SortedJoinCursor(Cursor parentCursor, int parentKeyColumn, Cursor childrenCursor, int childKeyColumn, int keyType) {
        super(parentCursor);

        if (childrenCursor instanceof ReferencedCursor) {
            mChildrenCursor = (ReferencedCursor) childrenCursor;
        } else {
            mChildrenCursor = new ReferencedCursor(childrenCursor);
        }

        mParentKeyColumn = parentKeyColumn;
        mChildKeyColumn = childKeyColumn;
        mKeyType = keyType;

        mChildInfos = new ChildInfo[getCount()];
        updateChildOffsets();
    }

    protected void updateChildOffsets() {
        switch (mKeyType) {
            case Cursor.FIELD_TYPE_INTEGER:
				updateChildOffsetsForIntKey();
                break;
			case Cursor.FIELD_TYPE_STRING:
				updateChildOffsetsForStringKey();
				break;
            default:
                throw new UnsupportedOperationException();
        }
    }

	protected void updateChildOffsetsForIntKey() {
		final int count = getCount();
		mChildrenCursor.moveToFirst();
		int childrenOffset = -1;
		for (int i = 0; i < count; i++) {
			moveToPosition(i);
			final int parentKey = getInt(mParentKeyColumn);
			int childrenCount = 0;
			if (!mChildrenCursor.isAfterLast() && !mChildrenCursor.isBeforeFirst()) {
				do {
					final int childKey = mChildrenCursor.getInt(mChildKeyColumn);
					if (parentKey != childKey) {
						break;
					}
					childrenOffset++;
					childrenCount++;
				} while (mChildrenCursor.moveToNext());
			}
			mChildInfos[i] = new ChildInfo(childrenOffset, childrenCount);
		}
	}

	protected void updateChildOffsetsForStringKey() {
		final int count = getCount();
		mChildrenCursor.moveToFirst();
		int childrenOffset = -1;
		for (int i = 0; i < count; i++) {
			moveToPosition(i);
			final String parentKey = getString(mParentKeyColumn);
			int childrenCount = 0;
			if (!mChildrenCursor.isAfterLast() && !mChildrenCursor.isBeforeFirst()) {
				do {
					final String childKey = mChildrenCursor.getString(mChildKeyColumn);
					if (!parentKey.equals(childKey)) {
						break;
					}
					childrenOffset++;
					childrenCount++;
				} while (mChildrenCursor.moveToNext());
			}
			mChildInfos[i] = new ChildInfo(childrenOffset, childrenCount);
		}
	}

    public Cursor getJoinedChildrenCursor() {
        final int parentPosition = getPosition();
        final int childPosition = mChildInfos[parentPosition].position;
        final int childCount = mChildInfos[parentPosition].count;

        return new NarrowedCursor(mChildrenCursor, childPosition, childCount);
    }
}
