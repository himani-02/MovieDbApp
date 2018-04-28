package com.example.himan.moviedbapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link TVFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TVFragment extends Fragment {
    View rootView;
    RecyclerView popular_tv_recyclerView;
    TVRecyclerViewAdapter popular_tv_recyclerViewAdapter;
    ArrayList<TVElement> popular_tv_list=new ArrayList<>();

    RecyclerView airing_today_recyclerView;
    TVRecyclerViewAdapter airing_today_recyclerViewAdapter;
    ArrayList<TVElement>airing_today_list=new ArrayList<>();

    RecyclerView top_rated_recyclerView;
    TVRecyclerViewAdapter top_rated_recyclerViewAdapter;
    ArrayList<TVElement>top_rated_list=new ArrayList<>();

    RecyclerView on_the_air_recyclerView;
    TVRecyclerViewAdapter on_the_air_recyclerViewAdapter;
    ArrayList<TVElement>on_the_air_list=new ArrayList<>();

    ProgressBar progressBar;
    Context context=getActivity();
//    FavouriteDatabase favouriteDatabase= Room.databaseBuilder(context,FavouriteDatabase.class,"favourite_database")
//            .allowMainThreadQueries()
//            .build();
//    TVDao tvDao=favouriteDatabase.getTVDao();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public TVFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     //* @param param2 Parameter 2.
     * @return A new instance of fragment TVFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TVFragment newInstance(String param1,String param2) {
        TVFragment fragment = new TVFragment();
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
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_tv, container, false);
        popular_tv_recyclerView=rootView.findViewById(R.id.tv_popular);
        airing_today_recyclerView=rootView.findViewById(R.id.tv_airing_today);
        top_rated_recyclerView=rootView.findViewById(R.id.tv_top_rated);
        on_the_air_recyclerView=rootView.findViewById(R.id.tv_On_the_Air);

        progressBar=rootView.findViewById(R.id.progressBar_tv);
        fetchTVList(getActivity());

        popular_tv_recyclerViewAdapter=new TVRecyclerViewAdapter(popular_tv_list, getActivity(), new TVRecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                TVElement tvElement=popular_tv_list.get(position);
                Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.ID,tvElement.getId());
                intent.putExtra(Constants.NAME,tvElement.getName());
                intent.putExtra(Constants.IS_MOVIE,"false");
                startActivity(intent);

            }
        },true);
        popular_tv_recyclerView.setAdapter(popular_tv_recyclerViewAdapter);
        popular_tv_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        popular_tv_recyclerView.setItemAnimator(new DefaultItemAnimator());

        airing_today_recyclerViewAdapter=new TVRecyclerViewAdapter(airing_today_list, getActivity(), new TVRecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                TVElement tvElement=airing_today_list.get(position);
                Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.ID,tvElement.getId());
                intent.putExtra(Constants.NAME,tvElement.getName());
                intent.putExtra(Constants.IS_MOVIE,"false");
                startActivity(intent);
                //Toast.makeText(context,"TV is selected",Toast.LENGTH_SHORT).show();
            }
        },true);
        airing_today_recyclerView.setAdapter(airing_today_recyclerViewAdapter);
        airing_today_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        airing_today_recyclerView.setItemAnimator(new DefaultItemAnimator());

        top_rated_recyclerViewAdapter=new TVRecyclerViewAdapter(top_rated_list, getActivity(), new TVRecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(context,"TV is selected",Toast.LENGTH_SHORT).show();
                TVElement tvElement=top_rated_list.get(position);
                Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.ID,tvElement.getId());
                intent.putExtra(Constants.NAME,tvElement.getName());
                intent.putExtra(Constants.IS_MOVIE,"false");
                startActivity(intent);

            }
        },false);
        top_rated_recyclerView.setAdapter(top_rated_recyclerViewAdapter);
        top_rated_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        top_rated_recyclerView.setItemAnimator(new DefaultItemAnimator());

        on_the_air_recyclerViewAdapter=new TVRecyclerViewAdapter(on_the_air_list, getActivity(), new TVRecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(context,"TV is selected",Toast.LENGTH_SHORT).show();
                TVElement tvElement=on_the_air_list.get(position);
                Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.ID,tvElement.getId());
                intent.putExtra(Constants.NAME,tvElement.getName());
                intent.putExtra(Constants.IS_MOVIE,"false");
                startActivity(intent);

            }
        },false);
        on_the_air_recyclerView.setAdapter(on_the_air_recyclerViewAdapter);
        on_the_air_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        on_the_air_recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    private void fetchTVList(final Context context) {
        popular_tv_recyclerView.setVisibility(View.GONE);
        top_rated_recyclerView.setVisibility(View.GONE);
        on_the_air_recyclerView.setVisibility(View.GONE);
        airing_today_recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);


        Call<TVObject> call=ApiClient.getInstance().getContentApi().getTVPopular();
        call.enqueue(new Callback<TVObject>() {
            @Override
            public void onResponse(Call<TVObject> call, Response<TVObject> response) {
                TVObject tvObject=response.body();
                if(tvObject.getResults()!=null){
                    popular_tv_list.clear();
                    popular_tv_list.addAll(tvObject.getResults());
                    //tvDao.insert(popular_tv_list);
                    popular_tv_recyclerViewAdapter.notifyDataSetChanged();
                }
                popular_tv_recyclerView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<TVObject> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                popular_tv_recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        Call<TVObject> call2=ApiClient.getInstance().getContentApi().getTVAiringToday();
        call2.enqueue(new Callback<TVObject>() {
            @Override
            public void onResponse(Call<TVObject> call, Response<TVObject> response) {
                TVObject tvObject=response.body();
                if(tvObject.getResults()!=null){
                    airing_today_list.clear();
                    airing_today_list.addAll(tvObject.getResults());
                    //tvDao.insert(airing_today_list);
                    airing_today_recyclerViewAdapter.notifyDataSetChanged();
                }
                airing_today_recyclerView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<TVObject> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                airing_today_recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        Call<TVObject> call3=ApiClient.getInstance().getContentApi().getTVOnTheAir();
        call3.enqueue(new Callback<TVObject>() {
            @Override
            public void onResponse(Call<TVObject> call, Response<TVObject> response) {
                TVObject tvObject=response.body();
                if(tvObject.getResults()!=null){
                    on_the_air_list.clear();
                    on_the_air_list.addAll(tvObject.getResults());
                    //tvDao.insert(on_the_air_list);
                    on_the_air_recyclerViewAdapter.notifyDataSetChanged();
                }
                on_the_air_recyclerView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<TVObject> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                on_the_air_recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        Call<TVObject> call4=ApiClient.getInstance().getContentApi().getTVTopRated();
        call4.enqueue(new Callback<TVObject>() {
            @Override
            public void onResponse(Call<TVObject> call, Response<TVObject> response) {
                TVObject tvObject=response.body();
                if(tvObject.getResults()!=null){
                    top_rated_list.clear();
                    top_rated_list.addAll(tvObject.getResults());
                   // tvDao.insert(top_rated_list);
                    top_rated_recyclerViewAdapter.notifyDataSetChanged();
                }
                top_rated_recyclerView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<TVObject> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                top_rated_recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
        progressBar.setVisibility(View.GONE);
    }

    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
