package com.example.abhimanyu.cafeteria;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
//Bleh bleh

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link feed.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link feed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class feed extends Fragment {
    EditText ph,nm,em,feed;
    View mView;
    FirebaseFirestore db=FirebaseFirestore.getInstance();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public feed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment feed.
     */
    // TODO: Rename and change types and number of parameters
    public static feed newInstance(String param1, String param2) {
        feed fragment = new feed();
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
       mView= inflater.inflate(R.layout.fragment_feed, container, false);
       nm=(EditText)mView.findViewById(R.id.name);
        ph=(EditText)mView.findViewById(R.id.phone);
        feed=(EditText)mView.findViewById(R.id.feedback);
        Button butoon=(Button)mView.findViewById(R.id.push);
        butoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                put();
            }
        });
        return mView;
    }
    public void put(){
        if(nm==null||ph==null||ph==null){
            Toast.makeText(getContext(),"Fill all details",Toast.LENGTH_LONG);
        }
        else{
            String name=nm.getText().toString();
            String phone=ph.getText().toString();
            String fee=feed.getText().toString();
            Map<String,Object> send=new HashMap<>();
            send.put("name",name);
            send.put("feedback",fee);

            db.collection("feedbacks").document(phone).set(send).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG,"worked!!");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.e(TAG,"Didnt work niggga!!");
                }
            });;

            nm.setText("");
            ph.setText("");
            feed.setText("");
            TextView tv=(TextView)mView.findViewById(R.id.finish);
            tv.setText("Done!!");

        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
