package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.kounecorp.bettercallsam.R;

public class ConsoListAdapter extends ArrayAdapter<Consommer> {
    int mResource;
    private Context mContext;

    public ConsoListAdapter(Context context, int resource, List<Consommer> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }


    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        String nomAlcool = getItem(position).getNomAlcool();
        int nbV = getItem(position).getNbVerres();
        String dateh = getItem(position).getDateheure();
        dateh = dateh.substring(0,10);
        int degre = getItem(position).getDeg();

        Consommer C = new Consommer(nomAlcool,nbV,dateh,degre);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource,parent,false);

        TextView idA = (TextView) convertView.findViewById(R.id.NOMALCOOLC);
        TextView deg = (TextView) convertView.findViewById(R.id.degC);
        TextView nbVerre = (TextView) convertView.findViewById(R.id.NBVERREC);
        TextView dateHeure = (TextView) convertView.findViewById(R.id.heureC);

        idA.setText(nomAlcool);
        deg.setText(String.valueOf(degre));
        dateHeure.setText(dateh);
        nbVerre.setText(String.valueOf(nbV));

        return convertView;
    }


}
