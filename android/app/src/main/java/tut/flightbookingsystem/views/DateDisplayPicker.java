
package tut.flightbookingsystem.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

public class DateDisplayPicker extends AppCompatEditText
        implements DatePickerDialog.OnDateSetListener {
    private Context _context;
    private long minDate;

    public DateDisplayPicker(final Context context,
                             final AttributeSet attrs,
                             final int defStyle) {
        super(context, attrs, defStyle);
        _context = context;
        final Calendar c = Calendar.getInstance();
        setDate( c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
    }

    public DateDisplayPicker(final Context context,
                             final AttributeSet attrs) {
        super(context, attrs);
        _context = context;
        setAttributes();
        final Calendar c = Calendar.getInstance();
        setDate( c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
    }

    public DateDisplayPicker(final Context context) {
        super(context);
        _context = context;
        setAttributes();
        final Calendar c = Calendar.getInstance();
        setDate( c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
    }

    private void setAttributes() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    private void showDateDialog() {
        final Calendar c = Calendar.getInstance();
        final DatePickerDialog dp = new DatePickerDialog(_context,
                this,
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));
        dp.getDatePicker().setMinDate(c.getTimeInMillis());
        dp.show();
    }

    @Override
    public void onDateSet(final DatePicker view,
                          final int year,
                          final int monthOfYear,
                          final int dayOfMonth) {
        setDate(year, monthOfYear, dayOfMonth);
        view.setMinDate(getMinDate());
    }

    private void setDate(final int year,
                         final int monthOfYear,
                         final int dayOfMonth) {
        setText(String.format("%s-%02d-%02d", year, (monthOfYear + 1), dayOfMonth));
    }

    private void setMinDate(long minDate){
        this.minDate = minDate;
    }

    private long getMinDate(){
        return minDate;
    }
}
