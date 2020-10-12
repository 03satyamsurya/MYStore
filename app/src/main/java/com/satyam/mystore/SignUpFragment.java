package com.satyam.mystore;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.HashMap;
import java.util.Map;


public class SignUpFragment extends Fragment {

    public SignUpFragment() {
        // Required empty public constructor
    }
    private TextView gotologin;
    private FrameLayout parentframelayout;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private EditText email;
    private EditText password;
    private EditText fullname;
    private EditText cnfrmpassword;
    private ProgressBar progressbar;
    private ImageButton closesignup;
    private String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";
    private Button signupbtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sign_up, container, false);
        closesignup = view.findViewById(R.id.closesignup);//close signup fragment
        progressbar = view.findViewById(R.id.progressbar);
        gotologin = view.findViewById(R.id.gotologin);
        signupbtn = view.findViewById(R.id.signupbtn);

        email = view.findViewById(R.id.email);
        fullname = view.findViewById(R.id.fullname);
        cnfrmpassword = view.findViewById(R.id.cnfrmpassword);
        password = view.findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();// Initialize Firebase Auth
        firebaseFirestore = FirebaseFirestore.getInstance();//firebase firestore
        parentframelayout = getActivity().findViewById(R.id.registerframelayout);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SigninFragment());
            }
        });
        closesignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();

            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
          //method
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password.addTextChangedListener(new TextWatcher() {
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
        fullname.addTextChangedListener(new TextWatcher() {
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
        cnfrmpassword.addTextChangedListener(new TextWatcher() {
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

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();

            }
        });
    }
    private void setFragment(Fragment fragment) //method
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentframelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs() //methods for check inputs
    {

         if(!TextUtils.isEmpty(email.getText()))
         {
             if(!TextUtils.isEmpty(fullname.getText()) )
             {
                 if(!TextUtils.isEmpty(password.getText()) && password.length()>= 6)
                 {
                     if(!TextUtils.isEmpty(cnfrmpassword.getText()))
                     {
                        signupbtn.setEnabled(true);
                        signupbtn.setTextColor(Color.rgb(255,255,255));
                     }else{
                         signupbtn.setEnabled(false);
                         signupbtn.setTextColor(Color.argb(50,255,255,255));

                     }

                 }else{  signupbtn.setEnabled(false);
                     signupbtn.setTextColor(Color.argb(50,255,255,255));

                 }

             }else{   signupbtn.setEnabled(false);
                 signupbtn.setTextColor(Color.argb(50,255,255,255));

             }

         }else{   signupbtn.setEnabled(false);
             signupbtn.setTextColor(Color.argb(50,255,255,255));

         }

    }
    private void checkEmailAndPassword()
    {
        Drawable customErrorIcon = getResources().getDrawable(R.mipmap.alertt);
        customErrorIcon.setBounds(0,0,customErrorIcon.getIntrinsicWidth(),customErrorIcon.getIntrinsicHeight());
        if(email.getText().toString().matches(emailpattern))
        {
            if(password.getText().toString().equals(cnfrmpassword.getText().toString()))
            {
                progressbar.setVisibility(View.VISIBLE);
                signupbtn.setEnabled(false);
                signupbtn.setTextColor(Color.argb(50,255,255,255));
                mAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Map<Object,String> userdata = new HashMap<>();
                            userdata.put("fullname",fullname.getText().toString());

                            firebaseFirestore.collection("USERS")
                                    .add(userdata)
                                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentReference> task) {
                                            if(task.isSuccessful())
                                            {
                                            mainIntent();
                                            }
                                            else
                                            {
                                                progressbar.setVisibility(View.INVISIBLE);
                                                signupbtn.setEnabled(true);
                                                signupbtn.setTextColor(Color.rgb(255,255,255));
                                                String error = task.getException().getMessage();
                                                Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();

                                            }

                                        }
                                    });

                        }else
                        {
                            progressbar.setVisibility(View.INVISIBLE);
                            signupbtn.setEnabled(true);
                            signupbtn.setTextColor(Color.rgb(255,255,255));
                            String error = task.getException().getMessage();
                            Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                        }
                    }
                }) ;
            }
            else {
                    cnfrmpassword.setError("Password doesn't matched!",customErrorIcon);
            }
        }else
        {
          email.setError("Invalid Email",customErrorIcon);
        }

    }
    private void mainIntent()
    {
        Intent mainintent = new Intent(getActivity(),MainActivity.class);
        startActivity(mainintent);
        getActivity().finish();
    }
}
