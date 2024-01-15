package Upasthiti.vistor_vision.in.eAttendance.Adaptor;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.MonthlyReportData;
import Upasthiti.vistor_vision.in.eAttndance.R;


public class MonthlyReportAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<MonthlyReportData> mEntries = new ArrayList<MonthlyReportData>();

    public MonthlyReportAdapter(Context context, ArrayList<MonthlyReportData> mEntries) {
        mContext = context;
        this.mEntries = mEntries;

        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mEntries.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mEntries.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = (LinearLayout) mLayoutInflater.inflate(R.layout.report_attendance_summery_item,null);
        TextView Month = (TextView) vi.findViewById(R.id.text_date);
        TextView Present = (TextView) vi.findViewById(R.id.text_status);
        TextView Absent = (TextView) vi.findViewById(R.id.text_in_time);
        TextView Holidays = (TextView) vi.findViewById(R.id.text_holiday);
        TextView moreinfo = (TextView) vi.findViewById(R.id.text_out_time);


        // Setting all values in listview
        Month.setText(mEntries.get(position).getMonth());
        Present.setText(mEntries.get(position).getPresent());
        Absent.setText(mEntries.get(position).getAbsent());
        Holidays.setText(mEntries.get(position).getHolidays());

      if(!Holidays.equals(""))
      {
          Holidays.setVisibility(View.VISIBLE);
      }
      else {Holidays.setVisibility(View.GONE);}

        moreinfo.setBackground(vi.getResources().getDrawable(R.drawable.ic_view));
        return vi;

    }

    public void upDateEntries(ArrayList<MonthlyReportData> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

}
