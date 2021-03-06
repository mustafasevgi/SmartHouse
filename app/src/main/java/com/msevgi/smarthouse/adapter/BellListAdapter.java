package com.msevgi.smarthouse.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.msevgi.smarthouse.R;
import com.msevgi.smarthouse.content.SmartHouseContentProvider;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public final class BellListAdapter extends CursorAdapter {

    private LayoutInflater mInflater;
    private PrettyTime mPrettyTime;

    private int mTimestampIndex;
    private int mBitmapIndex;

    public BellListAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        mInflater = LayoutInflater.from(context);
        mPrettyTime = new PrettyTime();

        mTimestampIndex = cursor.getColumnIndex(SmartHouseContentProvider.Bell.KEY_TIME_STAMP);
        mBitmapIndex = cursor.getColumnIndex(SmartHouseContentProvider.Bell.KEY_BITMAP);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = mInflater.inflate(R.layout.cell_bell, parent, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        long mTimestamp = cursor.getLong(mTimestampIndex);
        Date date = new Date(mTimestamp);
        String time = mPrettyTime.format(date);
        viewHolder.mDateTextView.setText(time);

        byte[] byteArray = cursor.getBlob(mBitmapIndex);
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        viewHolder.mImageView.setImageBitmap(bitmap);
    }

    protected static class ViewHolder {

        @InjectView(R.id.cell_bell_photo_imageview)
        ImageView mImageView;

        @InjectView(R.id.cell_bell_time_textview)
        TextView mDateTextView;

        protected ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

}
