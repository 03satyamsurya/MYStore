package com.satyam.mystore;

import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class SigninFragment extends Fragment {

    public SigninFragment() {// constructor

    }
    private TextView gotoregister;
    private FrameLayout parentframelayout;
    private ProgressBar progressbar1;
    private TextView frgotpassword;


    private EditText email0;
    private EditText password0;
    private ImageButton cross1;
    private Button signinbtn;
    private String emailpatternn = "[a-zA-Z0-9._-]+@[a-z]+.[a-z]+";

    private FirebaseAuth fireauth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_signin, container, false);
         gotoregister = view.findViewById(R.id.gotoregister);
         email0 = view.findViewById(R.id.email0);
         password0 = view.findViewById(R.id.password0);
         cross1 = view.findViewById(R.id.cross1);
         frgotpassword = view.findViewById(R.id.frgotpassword);
         progressbar1 = view.findViewById(R.id.progressbar1);
         signinbtn = view.findViewById(R.id.signinbtn);
         fireauth = FirebaseAuth.getInstance();
        parentframelayout = getActivity().findViewById(R.id.registerframelayout);
         return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new SignUpFragment());
            }
        });
        frgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new ForgotpassFragment());
            }
        });
        cross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainIntent();

            }
        });
        email0.addTextChangedListener(new TextWatcher() {
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
        password0.addTextChangedListener(new TextWatcher() {
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
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndPassword();
            }
        });
    }
    private void setFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentframelayout.getId(),fragment);
        fragmentTransaction.commit();
    }
    private void checkInputs()
    {
        if(!TextUtils.isEmpty(email0.getText()))
        {if(!TextUtils.isEmpty(password0.getText()))
        {
            signinbtn.setEnabled(true);
            signinbtn.setTextColor(Color.rgb(255,255,255));
        }
        else
            {
                signinbtn.setEnabled(false);
                signinbtn.setTextColor(Color.argb(50,255,255,255));
            }
        }
        else
        {
            signinbtn.setEnabled(false);
            signinbtn.setTextColor(Color.argb(50,255,255,255));
        }
    }
    private void checkEmailAndPassword()
    {if(email0.getText().toString().matches(emailpatternn))
    {
        if(password0.length() >= 6)
        {
            progressbar1.setVisibility(View.VISIBLE);
            signinbtn.setEnabled(false);
            signinbtn.setTextColor(Color.argb(50,255,255,255));

            fireauth.signInWithEmailAndPassword(email0.getText().toString(),password0.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                mainIntent();
                            }
                            else{
                                progressbar1.setVisibility(View.INVISIBLE);
                                signinbtn.setEnabled(true);
                                signinbtn.setTextColor(Color.rgb(255,255,255));
                                String error = task.getException().getMessage();
                                Toast.makeText(getActivity(),error,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }else{
            Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_SHORT).show();

        }

    }
    else{
        Toast.makeText(getActivity(),"Incorrect email or password",Toast.LENGTH_SHORT).show();

    }

    }
    private void mainIntent()
    {
        Intent mainintent = new Intent(getActivity(),MainActivity.class);
        startActivity(mainintent);
        getActivity().finish();
    }
}
