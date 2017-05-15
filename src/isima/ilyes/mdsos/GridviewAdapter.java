package isima.ilyes.mdsos;

import info.androidhive.slidingmenu.R;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GridviewAdapter extends BaseAdapter {

	private ArrayList<String> listNumero;
    private ArrayList<String> listNames;
    private Context mContext;
    
	public GridviewAdapter(Context context, ArrayList<String> listNumero,
			ArrayList<String> listNames) {
		super();
		this.listNumero = listNumero;
		this.listNames = listNames;
		mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listNumero.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listNumero.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder view;
       
        if(convertView==null)
        {
        	LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.gridview_row, parent, false);
        	
            view = new ViewHolder();
           
            view.txtNums = (TextView) convertView.findViewById(R.id.txtNums);
            view.txtNames = (TextView) convertView.findViewById(R.id.txtNames);
 
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }
 
        view.txtNums.setText(listNumero.get(position));
        view.txtNames.setText(listNames.get(position));
        
		return convertView;
	}
    
    
	 public static class ViewHolder
	    {
	        public TextView txtNums;
	        public TextView txtNames;
	    }
}

