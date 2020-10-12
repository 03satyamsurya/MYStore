package com.satyam.mystore;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotpassFragment extends Fragment {

    public ForgotpassFragment() {
        // Required empty public constructor
    }
    private EditText  enteremail;
    private Button resetpassword;
    private TextView goback;
    private FrameLayout parentframelayout;
    private FirebaseAuth firebaseauthh;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_forgotpass, container, false);
        enteremail = view.findViewById(R.id.enteremail);
        resetpassword = view.findViewById(R.id.resetpassword);
        parentframelayout = getActivity().findViewById(R.id.registerframelayout);
        goback = view.findViewById(R.id.goback);
        firebaseauthh = FirebaseAuth.getInstance();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        enteremail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());

            }
        });
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetpassword.setEnabled(false);
                resetpassword.setTextColor(Color.argb(50,255,255,255));

                firebaseauthh.sendPasswordResetEmail(enteremail.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(getActivity(),"Email Sent Successfully! ",Toast.LENGTH_LONG).show();
                                }
                                else
                                {
                                    String error = task.getException().getMessage();
                                    resetpassword.setEnabled(true);
                                    resetpassword.setTextColor(Color.rgb(255,255,255));
                                    Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                                }


                            }
                        });

            }
        });

    }
    private void checkInputs()
    {
        if(TextUtils.isEmpty(enteremail.getText()))
        {
            resetpassword.setEnabled(false);
            resetpassword.setTextColor(Color.argb(50,255,255,255));

        }else
        {
            resetpassword.setEnabled(true);
            resetpassword.setTextColor(Color.rgb(255,255,255));
        }


    }
    private void setFragment(Fragment fragment) //method
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentframelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
