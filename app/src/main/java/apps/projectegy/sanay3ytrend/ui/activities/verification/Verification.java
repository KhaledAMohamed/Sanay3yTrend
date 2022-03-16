package apps.projectegy.sanay3ytrend.ui.activities.verification;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import apps.projectegy.sanay3ytrend.R;
import apps.projectegy.sanay3ytrend.utils.BoldEditText;
import apps.projectegy.sanay3ytrend.utils.RegularTextView;

public class Verification extends AppCompatActivity {

    private BoldEditText mTxtOne;
    private BoldEditText mTxtTwo;
    private BoldEditText mTxtThree;
    private BoldEditText mTxtFour;
    private RegularTextView mBtnContinue;


    private String numberOne, numberTwo, numberThree, numberFour;
    private VerificationPresenter verificationPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        verificationPresenter = new VerificationPresenter(this);


        mTxtOne = findViewById(R.id.txt_one);
        mTxtTwo = findViewById(R.id.txt_two);
        mTxtThree = findViewById(R.id.txt_three);
        mTxtFour = findViewById(R.id.txt_four);
        mBtnContinue = findViewById(R.id.btn_verify);

        mTxtOne.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTxtOne.length() == 1) {

                    mTxtOne.clearFocus();
                    mTxtTwo.requestFocus();
                    mTxtTwo.setCursorVisible(true);

                    numberOne = s.toString();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });
        mTxtTwo.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTxtTwo.length() == 1) {

                    mTxtTwo.clearFocus();
                    mTxtThree.requestFocus();
                    mTxtThree.setCursorVisible(true);

                    numberTwo = s.toString();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });
        mTxtThree.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTxtThree.length() == 1) {

                    mTxtThree.clearFocus();
                    mTxtFour.requestFocus();
                    mTxtFour.setCursorVisible(true);

                    numberThree = s.toString();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {


            }

            public void afterTextChanged(Editable s) {

            }
        });
        mTxtFour.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mTxtFour.length() == 1) {
                    mTxtFour.clearFocus();
                    numberFour = s.toString();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        mBtnContinue.setOnClickListener(view -> {
            String code = (numberOne + numberTwo + numberThree + numberFour).trim();
            verificationPresenter.verifyAccount(code);
        });

    }
}