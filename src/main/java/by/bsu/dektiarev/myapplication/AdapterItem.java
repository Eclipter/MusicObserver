package by.bsu.dektiarev.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by USER on 07.12.2015.
 */
public class AdapterItem extends ArrayAdapter<String> {

    Context context;
    private final String[] values;

    public AdapterItem(Context context, String[] values) {
        super(context, R.layout.list_view_artist, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_view_artist, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.textViewItem);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageViewItem);
        textView.setText(values[position]);
        //imageView.setBackgroundResource(R.drawable.abyssal_blade_lg);

        return rowView;
    }
}
