package com.example.anna.taekwondosparring;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {
    static final String COMPETITOR_1_Score = "score";
    static final String COMPETITOR_2_Score = "score";
    static final String COMPETITOR_1_Strike = "strike";
    static final String COMPETITOR_2_Strike = "strike";
    static final String TIMER = "seconds";
    int competitor1Score;
    int competitor2Score;

    //@BindView(R.id.competitor1_score)
    TextView competitor1ScoreView;

    //@BindView(R.id.competitor1_strike)
    TextView competitorOneStrikeView;
    // @BindView(R.id.competitor2_strike)
    TextView competitorTwoStrikeView;
    //@BindView(R.id.tv)
    TextView tView;
    int competitorOneStrike;
    int competitorTwoStrike;
    //@BindView(R.id.competitor2_score)
    TextView competitor2ScoreView;
    long timeLeft;
    //@BindView(R.id.competitor1_score)
    ImageView firstCompetitorWinner;
    //@BindView(R.id.competitor2_score)
    ImageView secondCompetitorWinner;
    Dialog competitorOneWon;
    Dialog competitorTwoWon;

    //Declare a variable to hold count down timer's paused status
    private boolean isPaused = false;
    //Declare a variable to hold count down timer's paused status
    private boolean isCanceled = false;
    //Declare a variable to hold CountDownTimer remaining time
    private long timeRemaining = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        competitor1ScoreView = findViewById(R.id.competitor1_score);
        competitorOneStrikeView = findViewById(R.id.competitor1_strike);
        competitorTwoStrikeView = findViewById(R.id.competitor2_strike);
        competitor2ScoreView = findViewById(R.id.competitor2_score);
        tView = findViewById(R.id.tv);

        //Get reference of the XML layout's widgets
        final TextView tView = (TextView) findViewById(R.id.tv);
        final Button btnStart = (Button) findViewById(R.id.btn_start);

        final Button btnCancel = (Button) findViewById(R.id.btn_cancel);


        btnStart.setOnClickListener(this);

        btnCancel.setOnClickListener(this);






        competitorOneWon = new Dialog(this);
        competitorOneWon.setTitle("The Winner of this Match");
        competitorTwoWon = new Dialog(this);
        competitorTwoWon.setTitle("The Winner of this Match");


        btnCancel.setEnabled(true);

    }

    @Override
    public void onClick(View v) {
        // Perform action on click
        switch (v.getId()) {
            case R.id.btn_start:
                isPaused = false;
                isCanceled = false;


                CountDownTimer timer;
                long millisInFuture = 300000; //30 seconds
                long countDownInterval = 1000; //1 second


                //Initialize a new CountDownTimer instance
                timer = new CountDownTimer(millisInFuture, countDownInterval) {
                    public void onTick(long millisUntilFinished) {
                        //do something in every tick
                        if (isPaused || isCanceled) {
                            //If the user request to cancel or paused the
                            //CountDownTimer we will cancel the current instance
                            cancel();
                        } else {
                            //Display the remaining seconds to app interface
                            //1 second = 1000 milliseconds
                            tView.setText("        " + millisUntilFinished / 1000 + "\nSECONDS LEFT ");
                            //Put count down timer remaining time in a variable
                            timeRemaining = millisUntilFinished;

                        }
                    }

                    public void onFinish() {
                        if (competitor2Score == competitor1Score) {
                            tView.setText("NEXT POINT\n WINS");
                        } else {
                            //Do something when count down finished
                            tView.setText("MATCH IS\n OVER");
                            if (competitor1Score > competitor2Score) {
                                showWinner1();
                            }
                            if (competitor1Score < competitor2Score) {
                                showWinner2();
                            }
                        }
                    }
                }.start();
                break;

            case R.id.btn_cancel:
                //When user request to cancel the CountDownTimer
                isCanceled = true;
                tView.setText("CountDown\n300 seconds");
                competitor2Score = 0;
                competitor1Score = 0;
                competitorOneStrike = 0;
                competitorTwoStrike = 0;
                competitor2ScoreView.setText(String.valueOf(competitor2Score));
                competitor1ScoreView.setText(String.valueOf(competitor1Score));
                competitorOneStrikeView.setText(String.valueOf(competitorOneStrike));
                competitorTwoStrikeView.setText(String.valueOf(competitorTwoStrike));

                break;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showWinner1() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup1);
        dialog.setTitle("The Winner is");
        dialog.show();
        Button  PopUpClose = (Button) dialog.findViewById(R.id.dismiss1);
        //competitorOneWon.setContentView(R.layout.popup1);
        // PopUpClose = (Button) competitorOneWon.findViewById(R.id.dismiss1);
        PopUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Please, Reset Score and Timer", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void showWinner2() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup2);
        dialog.setTitle("The Winner is");
        dialog.show();
        Button  PopUpClose = (Button) dialog.findViewById(R.id.dismiss2);
        //competitorOneWon.setContentView(R.layout.popup1);
        // PopUpClose = (Button) competitorOneWon.findViewById(R.id.dismiss1);
        PopUpClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Please, Reset Score and Timer", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void score1Competitor1(View view) {
        competitor1Score = competitor1Score + 1;
        competitor1ScoreView.setText(String.valueOf(competitor1Score));
        if (competitor1Score >= 5) {


                    showWinner1();
                }

        }

    public void score2Competitor1(View view) {
        competitor1Score = competitor1Score + 2;
        competitor1ScoreView.setText(String.valueOf(competitor1Score));
        if (competitor1Score >= 5) {
            showWinner1();
        }


    }


    public void score1Competitor2(View view) {
        competitor2Score = competitor2Score + 1;
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        if (competitor2Score >= 5) {
            showWinner2();
        }

    }

    public void score2Competitor2(View view) {
        competitor2Score = competitor2Score + 2;
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        if (competitor2Score >= 5) {
            showWinner2();
        }

    }


    public void reset(View view) {
        competitor2Score = 0;
        competitor1Score = 0;
        competitorOneStrike = 0;
        competitorTwoStrike = 0;
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        competitor1ScoreView.setText(String.valueOf(competitor1Score));

        competitorOneStrikeView.setText(String.valueOf(competitorOneStrike));
        competitorTwoStrikeView.setText(String.valueOf(competitorTwoStrike));

    }

    public void strikeCompetitor1(View view) {
        competitorOneStrike = competitorOneStrike + 1;
        competitorOneStrikeView.setText(String.valueOf(competitorOneStrike));
        if (competitorOneStrike == 2) {
            competitor2Score = competitor2Score + 1;
            if (competitor2Score >= 5) {
                showWinner2();
            }
        }
        competitor2ScoreView.setText(String.valueOf(competitor2Score));
        if (competitorOneStrike >= 3) {
            showWinner2();
        }


    }

    public void strikeCompetitor2(View view) {
        competitorTwoStrike = competitorTwoStrike + 1;
        competitorTwoStrikeView.setText(String.valueOf(competitorTwoStrike));
        if (competitorTwoStrike == 2) {
            competitor1Score = competitor1Score + 1;
            if (competitor1Score >= 5) {
                showWinner1();
            }
        }
        competitor1ScoreView.setText(String.valueOf(competitor1Score));
        if (competitorTwoStrike >= 3) {
            showWinner1();
        }


    }

}

