package Upasthiti.vistor_vision.in.eAttendance.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import Upasthiti.vistor_vision.in.eAttendance.entity.LeaveHistoryData;
import Upasthiti.vistor_vision.in.eAttndance.R;


public class LeaveHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private ArrayList<LeaveHistoryData> mEntries = new ArrayList<LeaveHistoryData>();

    public LeaveHistoryAdapter(Context context, ArrayList<LeaveHistoryData> mEntries) {
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
        TextView Apply_Date = (TextView) vi.findViewById(R.id.text_date);
        TextView From_Date = (TextView) vi.findViewById(R.id.text_status);
        TextView To_Date = (TextView) vi.findViewById(R.id.text_in_time);
        TextView Leave_Type = (TextView) vi.findViewById(R.id.text_holiday);
        TextView moreinfo = (TextView) vi.findViewById(R.id.text_out_time);

        // Setting all values in listview
        Apply_Date.setText(mEntries.get(position).getApplyDate());
        From_Date.setText(mEntries.get(position).getDateFrom());
        To_Date.setText(mEntries.get(position).getDateTo());
        Leave_Type.setText(mEntries.get(position).getLeaveType());

      if(!Leave_Type.equals(""))
      {
          Leave_Type.setVisibility(View.VISIBLE);

      }
      else {
          Leave_Type.setVisibility(View.GONE);

      }

        moreinfo.setBackground(vi.getResources().getDrawable(R.drawable.ic_view));
        return vi;

    }

    public void upDateEntries(ArrayList<LeaveHistoryData> entries) {
        mEntries = entries;
        notifyDataSetChanged();
    }

}
