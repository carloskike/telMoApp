package telmoapp.com.telmoapp;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class best_calification_fragment extends Fragment {

    private static final String  URL = "https://infinite-atoll-7499.herokuapp.com/api/v1/motelindex";
    private ProgressDialog dialog;
    private List<Motel> array = new ArrayList<Motel>();
    private ListView listView;
    private MotelAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_listar_moteles, container, false);

        listView = (ListView) v.findViewById(R.id.list_item);
        adapter=new MotelAdapter(getActivity(),array);
        listView.setAdapter(adapter);

        Cursor c=RefreshService.mLocationsDB.getAllLocations();
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                Motel item = new Motel();
                item.setId(c.getInt(0));
                item.setName(c.getString(4));
                item.setImage(c.getString(5));
                item.setDescription(c.getString(3));
                item.setType(c.getString(6));
                item.setAddres(c.getString(7));
                //add to array
                Log.v("prueba", item.getName());
                array.add(item);
            } while (c.moveToNext());
            adapter.notifyDataSetChanged();
        }
                //Crea el evento para ir al perfil del motel
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Object o = listView.getItemAtPosition(position);
                        Intent intent = new Intent("telmoapp.motelProfile");
                        Bundle b = new Bundle();
                        b.putInt("IdMotel", ((Motel) o).id);
                        intent.putExtras(b);
                        startActivity(intent);
                    }
                });




        return v;
    }

}
