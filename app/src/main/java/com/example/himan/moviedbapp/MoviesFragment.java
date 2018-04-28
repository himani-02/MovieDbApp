package com.example.himan.moviedbapp;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {

    View rootView;
    RecyclerView popular_movies_recyclerView;
    MovieRecyclerViewAdapter popular_movies_recyclerViewAdapter;
    ArrayList<MovieElement> popular_movies_list=new ArrayList<>();

    RecyclerView upcoming_movies_recyclerView;
    MovieRecyclerViewAdapter upcoming_movies_recyclerViewAdapter;
    ArrayList<MovieElement>upcoming_movies_list=new ArrayList<>();

    RecyclerView top_rated_recyclerView;
    MovieRecyclerViewAdapter top_rated_recyclerViewAdapter;
    ArrayList<MovieElement>top_rated_list=new ArrayList<>();

    RecyclerView now_showing_recyclerView;
    MovieRecyclerViewAdapter now_showing_recyclerViewAdapter;
    ArrayList<MovieElement>now_showing_list=new ArrayList<>();

    TextView popular,upcoming,top_rated,now_showing;
    ProgressBar progressBar;
    Context context=getActivity();
//    FavouriteDatabase favouriteDatabase= Room.databaseBuilder(context,FavouriteDatabase.class,"favourite_database")
//            .allowMainThreadQueries()
//            .build();
//    MovieDao movieDao=favouriteDatabase.getMovieDao();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   // private OnFragmentInteractionListener mListener;

    public MoviesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoviesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoviesFragment newInstance(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_movies, container, false);
        popular=rootView.findViewById(R.id.tvairingtoday);
        upcoming=rootView.findViewById(R.id.tvPopular);
        now_showing=rootView.findViewById(R.id.tvOntheAir);
        top_rated=rootView.findViewById(R.id.tvToprated);

        popular_movies_recyclerView=rootView.findViewById(R.id.tv_airing_today);
        upcoming_movies_recyclerView=rootView.findViewById(R.id.tv_popular);
        top_rated_recyclerView=rootView.findViewById(R.id.top_rated);
        now_showing_recyclerView=rootView.findViewById(R.id.tv_On_the_Air);

        progressBar=rootView.findViewById(R.id.progressBar);
        fetchPopularMovies(getActivity());

        popular_movies_recyclerViewAdapter=new MovieRecyclerViewAdapter(popular_movies_list, getActivity(), new MovieRecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(context,"Movie is selected",Toast.LENGTH_SHORT).show();
                MovieElement movieElement=popular_movies_list.get(position);
                Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.ID,movieElement.getId());
                intent.putExtra(Constants.NAME,movieElement.getTitle());
                intent.putExtra(Constants.IS_MOVIE,"true");
                startActivity(intent);
            }
        },true);
        popular_movies_recyclerView.setAdapter(popular_movies_recyclerViewAdapter);
        popular_movies_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        popular_movies_recyclerView.setItemAnimator(new DefaultItemAnimator());

        upcoming_movies_recyclerViewAdapter=new MovieRecyclerViewAdapter(upcoming_movies_list, getActivity(), new MovieRecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(context,"Movie is selected",Toast.LENGTH_SHORT).show();
                MovieElement movieElement=upcoming_movies_list.get(position);
                Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.ID,movieElement.getId());
                intent.putExtra(Constants.NAME,movieElement.getTitle());
                intent.putExtra(Constants.IS_MOVIE,"true");
                startActivity(intent);
            }
        },false);
        upcoming_movies_recyclerView.setAdapter(upcoming_movies_recyclerViewAdapter);
        upcoming_movies_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        upcoming_movies_recyclerView.setItemAnimator(new DefaultItemAnimator());

        top_rated_recyclerViewAdapter=new MovieRecyclerViewAdapter(top_rated_list, getActivity(), new MovieRecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(context,"Movie is selected",Toast.LENGTH_SHORT).show();
                MovieElement movieElement=top_rated_list.get(position);
                Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.ID,movieElement.getId());
                intent.putExtra(Constants.NAME,movieElement.getTitle());
                intent.putExtra(Constants.IS_MOVIE,"true");
                startActivity(intent);
            }
        },false);
        top_rated_recyclerView.setAdapter(top_rated_recyclerViewAdapter);
        top_rated_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        top_rated_recyclerView.setItemAnimator(new DefaultItemAnimator());

        now_showing_recyclerViewAdapter=new MovieRecyclerViewAdapter(now_showing_list, getActivity(), new MovieRecyclerViewAdapter.onItemClickListner() {
            @Override
            public void onItemClick(int position) {
                //Toast.makeText(context,"Movie is selected",Toast.LENGTH_SHORT).show();
                MovieElement movieElement=now_showing_list.get(position);
                Intent intent=new Intent(getActivity(),MovieDetailActivity.class);
                intent.putExtra(Constants.ID,movieElement.getId());
                intent.putExtra(Constants.NAME,movieElement.getTitle());
                intent.putExtra(Constants.IS_MOVIE,"true");
                startActivity(intent);
            }
        },false);
        now_showing_recyclerView.setAdapter(now_showing_recyclerViewAdapter);
        now_showing_recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        now_showing_recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    private void fetchPopularMovies(final Context context) {
        popular_movies_recyclerView.setVisibility(View.GONE);
        top_rated_recyclerView.setVisibility(View.GONE);
        upcoming_movies_recyclerView.setVisibility(View.GONE);
        now_showing_recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        popular.setVisibility(View.GONE);
        top_rated.setVisibility(View.GONE);
        upcoming.setVisibility(View.GONE);
        now_showing.setVisibility(View.GONE);

        Call<MovieObject> call=ApiClient.getInstance().getContentApi().getPopularMovie();
        call.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                MovieObject movieObject=response.body();
                if(movieObject.getResults()!=null){
                    popular_movies_list.clear();
                    popular_movies_list.addAll(movieObject.getResults());
                   // movieDao.insert(popular_movies_list);
                    popular_movies_recyclerViewAdapter.notifyDataSetChanged();
                }
                popular.setVisibility(View.VISIBLE);
                popular_movies_recyclerView.setVisibility(View.VISIBLE);
                //progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                popular_movies_recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        Call<MovieObject> call_now_showing=ApiClient.getInstance().getContentApi().getNowShowingMovie();
        call_now_showing.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                MovieObject movieObject=response.body();
                if(movieObject!=null){
                    now_showing_list.clear();
                    now_showing_list.addAll(movieObject.getResults());
                    //movieDao.insert(now_showing_list);
                    now_showing_recyclerViewAdapter.notifyDataSetChanged();
                }
                now_showing.setVisibility(View.VISIBLE);
                now_showing_recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                now_showing_recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        Call<MovieObject> call_upcoming=ApiClient.getInstance().getContentApi().getUpcomingMovie();
        call_upcoming.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                MovieObject movieObject=response.body();
                if(movieObject!=null){
                    upcoming_movies_list.clear();
                    upcoming_movies_list.addAll(movieObject.getResults());
                   // movieDao.insert(upcoming_movies_list);
                    upcoming_movies_recyclerViewAdapter.notifyDataSetChanged();
                }
                upcoming.setVisibility(View.VISIBLE);
                upcoming_movies_recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                upcoming_movies_recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });

        Call<MovieObject> call_top_rated=ApiClient.getInstance().getContentApi().getTopRatedMovie();
        call_top_rated.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                MovieObject movieObject=response.body();
                if(movieObject!=null){
                    top_rated_list.clear();
                    top_rated_list.addAll(movieObject.getResults());
                   // movieDao.insert(top_rated_list);
                    top_rated_recyclerViewAdapter.notifyDataSetChanged();
                }
                top_rated.setVisibility(View.VISIBLE);
                top_rated_recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_SHORT).show();
                top_rated_recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        });
        progressBar.setVisibility(View.GONE);
    }

//    // TODO: Rename method, update argument and hook method into UI event
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
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
