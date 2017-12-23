/*Assignment : Inclass08
Yash Ghia
Prabhakar Teja Seeda*/


package com.example.teja.inclass08;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchFragment extends Fragment implements RecycleViewAppListAdapter.IAppListner,GetRecipeAsyncTask.IrecipeResponse, GetRecipeAsyncTask.InoResults {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView recycleViewList ;
    static RecycleViewAppListAdapter recycleAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String> ingredients = new ArrayList<>();
    String baseURL = "http://www.recipepuppy.com/api/?i=";
    static String URL = "url";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_search,container,false);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recycleViewList = (RecyclerView)getView().findViewById(R.id.recyclerview);
        //MainActivity.trackResultsList.add(1);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recycleViewList.setLayoutManager(mLayoutManager);
        //recycleViewList.setHasFixedSize(false);
        recycleAdapter = new RecycleViewAppListAdapter(SearchFragment.this,(ArrayList<Integer>)MainActivity.trackResultsList);
        recycleViewList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
        getView().findViewById(R.id.imageView3).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                if(MainActivity.trackResultsList.size()<=3) {
                    MainActivity.trackResultsList.add(1);
                    addNewRow((ArrayList<Integer>) MainActivity.trackResultsList);
                }
                else {
                    Toast.makeText(getContext(), "Maximum ingredients can be 5", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getView().findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                ingredients.add(((EditText)getView().findViewById(R.id.ingName)).getText().toString());
                for (int i =0;i<recycleAdapter.getItemCount();i++) {
                    View view = recycleViewList.getChildAt(i);
                    String text = ((EditText)view.findViewById(R.id.name)).getText().toString();
                    if(text!=null || !text.isEmpty()) {
                        ingredients.add(text);
                    }
                }
                try {
                    if(ingredients.size()>0) {
                        URL = getEncodedUrl(ingredients);
                        new GetRecipeAsyncTask(getActivity(), SearchFragment.this,SearchFragment.this).execute(URL);
                    }
                    else{
                        Toast.makeText(getContext(), "add at least 1 ingredient for recipe", Toast.LENGTH_SHORT).show();
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public void addNewRow(ArrayList<Integer> trackResults) {
        recycleViewList = (RecyclerView) getView().findViewById(R.id.recyclerview);
        recycleAdapter = new RecycleViewAppListAdapter(SearchFragment.this,trackResults);
        recycleViewList.setAdapter(recycleAdapter);
        recycleAdapter.notifyDataSetChanged();
        recycleViewList.setHasFixedSize(false);
        recycleViewList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true));
    }

    public String getEncodedParams(ArrayList<String> mylist) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : mylist){
            String val = URLEncoder.encode(key,"UTF-8");
            if(stringBuilder.length()>0){
                stringBuilder.append(",");
            }
            stringBuilder.append(val);
        }
        return stringBuilder.toString();
    }

    public String getEncodedUrl(ArrayList<String> mylist) throws UnsupportedEncodingException {
        EditText getRecipe=(EditText)getView().findViewById(R.id.editText);
        String s=getRecipe.getText().toString().trim();
        return baseURL+getEncodedParams(mylist)+"&q="+s;
    }

    @Override
    public void recipes(ArrayList<RecipeResults> recipeResults) {
        getFragmentManager().beginTransaction()
                .replace(R.id.container,new RecipeFragment(recipeResults),"recipes")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void noResults() {
        Toast.makeText(getActivity(),"No recipes found",Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction()
                .replace(R.id.container,new SearchFragment(),"recipes")
                .commit();

    }
}
