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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Bookings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Bookings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bookings extends Fragment {
    String em,ph,nm,tn,otot,oava;
    int oCount;
    EditText emm,eph,enm;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    public DocumentReference mDocRef= FirebaseFirestore.getInstance().document("total/tables");
    public DocumentReference mCount= FirebaseFirestore.getInstance().document("counts/count");
    View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Bookings() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bookings.
     */
    // TODO: Rename and change types and number of parameters
    public static Bookings newInstance(String param1, String param2) {
        Bookings fragment = new Bookings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void avail(){
        mDocRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                                            if(documentSnapshot.exists()){

                                                TextView tot=(TextView)mView.findViewById(R.id.total);
                                                TextView av=(TextView)mView.findViewById(R.id.present);
                                                 otot=documentSnapshot.getString("total");
                                                 oava=documentSnapshot.getString("available");
                                                tot.setText("Total Tables="+otot);
                                                av.setText("Availaible Tables="+oava);


                                            }
                                        }
                                    }
        );
        mCount.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    oCount=Integer.parseInt(documentSnapshot.getString("number"));
                }
            }
        });


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
        mView=inflater.inflate(R.layout.fragment_bookings, container, false);
        avail();
        emm=(EditText)mView.findViewById(R.id.email);
        eph=(EditText)mView.findViewById(R.id.phone);
        enm=(EditText)mView.findViewById(R.id.name);
        Button button=(Button)mView.findViewById(R.id.book);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               if(Integer.parseInt(oava)>0){
                   book();
               }
               else{
                   TextView tv=(TextView)mView.findViewById(R.id.bkno);
                   tv.setText("All tables are booked");

               }
            }});
        return mView;
    }
    public void book()
    {
        em=emm.getText().toString();
        nm=enm.getText().toString();
        ph=eph.getText().toString();

        Map<String, Object> mBook=new HashMap<>();
        mBook.put("email",em);
        mBook.put("name",nm);
        mBook.put("number",oava);
        mBook.put("phone",ph);

        db.collection("bookings").document("a"+oCount).set(mBook).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG, "DocumentSnapshot successfully written!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error writing document", e);
            }
        });
        Map<String,Object> pCount= new HashMap<>();
        pCount.put("number",""+(oCount+1));
        db.collection("counts").document("count").set(pCount).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"count entered");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"error writing count",e);
            }
        });
        TextView tv=(TextView)mView.findViewById(R.id.bkno);
        tv.setText("Booking number ="+"a"+oCount);
        Map<String,Object> pAva=new HashMap<>();
        pAva.put("available",""+(Integer.parseInt(oava)-1));
        pAva.put("total",""+50);
        db.collection("total").document("tables").set(pAva).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d(TAG,"done!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG,"failed ;_;",e);
            }
        });
        enm.setText("");
        eph.setText("");
        emm.setText("");



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
