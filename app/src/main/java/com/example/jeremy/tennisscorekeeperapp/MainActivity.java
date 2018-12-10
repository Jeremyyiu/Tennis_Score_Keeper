package com.example.jeremy.tennisscorekeeperapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.jeremy.tennisscorekeeperapp.StartActivity.BEST_OF_3_SETS;
import static com.example.jeremy.tennisscorekeeperapp.StartActivity.BEST_OF_5_SETS;
import static com.example.jeremy.tennisscorekeeperapp.StartActivity.GAME_PREFERENCES;

/**
 * Created by Jeremy on 6/02/2018.
 */

public class MainActivity extends AppCompatActivity {
    public static final String FAULT = "Fault";
    public static final String DOUBLE_FAULT = "Double Fault";

    private Player player1;
    private Player player2;

    private int player1Set1Counter = 0;
    private int player2Set1Counter = 0;
    private int player1Set2Counter = 0;
    private int player2Set2Counter = 0;
    private int player1Set3Counter = 0;
    private int player2Set3Counter = 0;
    private int player1Set4Counter = 0;
    private int player2Set4Counter = 0;
    private int player1Set5Counter = 0;
    private int player2Set5Counter = 0;

    @BindView(R.id.player1_name)
    TextView player1Name;
    @BindView(R.id.player2_name)
    TextView player2Name;

    @BindView(R.id.p1Fault)
    TextView p1Fault;
    @BindView(R.id.p2Fault)
    TextView p2Fault;

    @BindView(R.id.p1)
    TextView p1;
    @BindView(R.id.p2)
    TextView p2;

    @BindView(R.id.set1header)
    TextView set1header;
    @BindView(R.id.p1Set1Points)
    TextView p1Set1Points;
    @BindView(R.id.p2Set1Points)
    TextView p2Set1Points;

    @BindView(R.id.set2header)
    TextView set2header;
    @BindView(R.id.p1Set2Points)
    TextView p1Set2Points;
    @BindView(R.id.p2Set2Points)
    TextView p2Set2Points;

    @BindView(R.id.set3header)
    TextView set3header;
    @BindView(R.id.p1Set3Points)
    TextView p1Set3Points;
    @BindView(R.id.p2Set3Points)
    TextView p2Set3Points;


    @BindView(R.id.set4header)
    TextView set4header;
    @BindView(R.id.p1Set4Points)
    TextView p1Set4Points;
    @BindView(R.id.p2Set4Points)
    TextView p2Set4Points;

    @BindView(R.id.set5header)
    TextView set5header;
    @BindView(R.id.p1Set5Points)
    TextView p1Set5Points;
    @BindView(R.id.p2Set5Points)
    TextView p2Set5Points;

    @BindView(R.id.player1_fault)
    Button player1_faultBtn;
    @BindView(R.id.player2_fault)
    Button player2_faultBtn;

    @BindView(R.id.player1_points)
    TextView player1Points_view;
    @BindView(R.id.player2_points)
    TextView player2Points_view;
    @BindView(R.id.p1Points)
    TextView p1Points_View;
    @BindView(R.id.p2Points)
    TextView p2Points_View;

    @BindView(R.id.player1_pointWin)
    Button player1_pointWinBtn;
    @BindView(R.id.player2_pointWin)
    Button player2_pointWinBtn;

    @BindView(R.id.player1_forfeit)
    Button player1_forfeitBtn;
    @BindView(R.id.player2_forfeit)
    Button player2_forfeitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupGame(); //sets player names and current set.
        firstServer(); //user selects first user
    }

    /**
     * Setups up the game by setting the players name by the input given in the previous activity. All sets besides the first set are
     * set as invisible.
     */
    private void setupGame() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(GAME_PREFERENCES, MODE_PRIVATE);
        player1Set1Counter = player2Set1Counter = player1Set2Counter = player2Set2Counter = player1Set3Counter = player2Set3Counter = player1Set4Counter = player2Set4Counter = player1Set5Counter = player2Set5Counter = 0;

        p1Fault.setVisibility(View.INVISIBLE);
        p2Fault.setVisibility(View.INVISIBLE);

        //Do not show Set 2 boxes for now
        set2header.setVisibility(View.GONE);
        p1Set2Points.setVisibility(View.GONE);
        p2Set2Points.setVisibility(View.GONE);

        //Do not show Set 3 boxes for now
        set3header.setVisibility(View.GONE);
        p1Set3Points.setVisibility(View.GONE);
        p2Set3Points.setVisibility(View.GONE);

        //Do not show Set 4 boxes for now
        set4header.setVisibility(View.GONE);
        p1Set4Points.setVisibility(View.GONE);
        p2Set4Points.setVisibility(View.GONE);

        //Do not show Set 5 boxes for now
        set5header.setVisibility(View.GONE);
        p1Set5Points.setVisibility(View.GONE);
        p2Set5Points.setVisibility(View.GONE);

        //get player names from shared preferences
        String getPlayer1Name = settings.getString("player1name", "Player 1");
        String getPlayer2Name = settings.getString("player2name", "Player 2");

        player1 = new Player();
        player2 = new Player();

        player1.setName(getPlayer1Name);
        player2.setName(getPlayer2Name);

        //set textViews with the player names inputted previously by the user.
        player1Name.setText(player1.getName());
        player2Name.setText(player2.getName());
        p1.setText(player1.getName());
        p2.setText(player2.getName());
    }

    /**
     * Identifies the first server of the match
     */
    private void firstServer() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Who is serving first?")
                .setCancelable(false)
                .setPositiveButton(player2.getName(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        player1_faultBtn.setEnabled(false);
                        player1_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
                        player2.setIsServing(true);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(player1.getName(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        player2_faultBtn.setEnabled(false);
                        player2_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
                        player1.setIsServing(true);
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Identifies which player was currently serving and changes the other player to be the server
     */
    public void serverSwitch() {
        if (player1.isServing()) {
            player2.setIsServing(true);
            player2_faultBtn.setEnabled(true);
            player2_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_orange));
            player1.setIsServing(false);
            player1_faultBtn.setEnabled(false);
            player1_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
        } else {
            player1.setIsServing(true);
            player1_faultBtn.setEnabled(true);
            player1_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_orange));
            player2.setIsServing(false);
            player2_faultBtn.setEnabled(false);
            player2_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
        }
    }

    /**
     * Updates the scores from the selected player winning a point.
     *
     * @param player - selected player that won the point
     * @param view   - to update all changes to the screen.
     */
    public void playerPointWin(Player player, View view) {
        serverSwitch();
        switch (player.getPoints()) {
            case point_0:
                player.setPoints(Player.Point.point_15);
                break;
            case point_15:
                player.setPoints(Player.Point.point_30);
                break;
            case point_30:
                player.setPoints(Player.Point.point_40);
                if ((player == player1 && player2.getPoints() == Player.Point.point_40) || (player == player2 && player1.getPoints() == Player.Point.point_40)) {
                    player1.setPoints(Player.Point.point_deuce);
                    player2.setPoints(Player.Point.point_deuce);
                }
                break;
            case point_40:
                if ((player == player1 && player2.getPoints() == Player.Point.point_adv) || player == player2 && player1.getPoints() == Player.Point.point_adv) {
                    player1.setPoints(Player.Point.point_deuce);
                    player2.setPoints(Player.Point.point_deuce);
                    break;
                }
                playerGameWon(player, view);
                break;
            case point_deuce:
                player.setPoints(Player.Point.point_adv);
                if (player == player1) {
                    player2.setPoints(Player.Point.point_40);
                } else {
                    player1.setPoints(Player.Point.point_40);
                }
                break;
            case point_adv:
                playerGameWon(player, view);
                break;
            case point_tiebreaker: //tiebreaker conditions for a point win.
                player.tiebreakerPointWin();
                if (((player == player1 && player.getTieBreakerPoints() >= 6 && (player.getTieBreakerPoints() >= (player2.getTieBreakerPoints() + 2)))) || (player == player2 && player.getTieBreakerPoints() >= 6 && (player.getTieBreakerPoints() >= (player1.getTieBreakerPoints() + 2)))) {
                    playerGameWon(player, view);
                }
                break;
            default:
                break;
        }
        resetFaultCounters();
        updatePlayerScores();
    }

    @OnClick(R.id.player1_pointWin)
    public void player1PointWin(View view) {
        playerPointWin(player1, view);
    }

    @OnClick(R.id.player2_pointWin)
    public void player2PointWin(View view) {
        playerPointWin(player2, view);
    }

    /**
     * Updates the point scores of the players.
     */
    public void updatePlayerScores() {
        if (player1.getPoints().getPointValue().equals("tb")) {
            player1Points_view.setText(String.valueOf(player1.getTieBreakerPoints()));
            player2Points_view.setText(String.valueOf(player2.getTieBreakerPoints()));

            p1Points_View.setText(String.valueOf(player1.getTieBreakerPoints()));
            p2Points_View.setText(String.valueOf(player2.getTieBreakerPoints()));
        } else {
            player1Points_view.setText(player1.getPoints().getPointValue());
            player2Points_view.setText(player2.getPoints().getPointValue());

            p1Points_View.setText(player1.getPoints().getPointValue());
            p2Points_View.setText(player2.getPoints().getPointValue());
        }
    }

    public void resetFaultCounters() {
        player1.resetFaultCounter();
        player2.resetFaultCounter();
    }

    /**
     * Checks which set textviews are visible to identify the current set the players are playing
     *
     * @return current set number
     */
    public int checkSetNumber() {
        if (set5header.getVisibility() == View.VISIBLE) {
            return 5;
        } else if (set4header.getVisibility() == View.VISIBLE) {
            return 4;
        } else if (set3header.getVisibility() == View.VISIBLE) {
            return 3;
        } else if (set2header.getVisibility() == View.VISIBLE) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * Resets the player points, identifies the current set and increments set counter of the player passed in the parameter
     *
     * @param player - selected player that has won a game
     * @param view   - to make edits to the current screen
     */
    public void playerGameWon(Player player, View view) {
        player1Points_view.setText(player1.getPoints().getPointValue());
        player1Points_view.setText(player2.getPoints().getPointValue());

        player1.setPoints(Player.Point.point_0); //resets points of players
        player2.setPoints(Player.Point.point_0);

        switch (checkSetNumber()) {  //checks what set is currently being played
            case 1:
                if (player == player1) { //checks which player
                    p1Set1Points.setText(String.valueOf(++player1Set1Counter)); //increments selected player's counter of the current set
                } else {
                    p2Set1Points.setText(String.valueOf(++player2Set1Counter));
                }
                break;
            case 2:
                if (player == player1) {
                    p1Set2Points.setText(String.valueOf(++player1Set2Counter));
                } else {
                    p2Set2Points.setText(String.valueOf(++player2Set2Counter));
                }
                break;
            case 3:
                if (player == player1) {
                    p1Set3Points.setText(String.valueOf(++player1Set3Counter));
                } else {
                    p2Set3Points.setText(String.valueOf(++player2Set3Counter));
                }
                break;
            case 4:
                if (player == player1) {
                    p1Set4Points.setText(String.valueOf(++player1Set4Counter));
                } else {
                    p2Set4Points.setText(String.valueOf(++player2Set4Counter));
                }
                break;
            case 5:
                if (player == player1) {
                    p1Set5Points.setText(String.valueOf(++player1Set5Counter));
                } else {
                    p2Set5Points.setText(String.valueOf(++player2Set5Counter));
                }
                break;
            default:
                break;
        }
        player.winGame();

        /* win set or tiebreaker conditions */
        if ((player == player1 && player1.getGames() == 6 && player2.getGames() < 5) || (player == player2 && player.getGames() == 6 && player1.getGames() < 5)) {
            playerSetWin(player, view);
        } else if (player1.getGames() == 6 && player2.getGames() == 6) {
            player1.setPoints(Player.Point.point_tiebreaker);
            player2.setPoints(Player.Point.point_tiebreaker);
        } else if ((player == player1 && player.getGames() == 7 && player2.getGames() == 6) || (player == player2 && player.getGames() == 7 && player1.getGames() == 6)) {
            playerSetWin(player, view);
        }
        updatePlayerScores();
    }

    /**
     * Updates the scores to show that the selected player has won a set - may trigger function to show that the match is won if conditions are met.
     *
     * @param player - selected player that has won a set
     * @param view   - to make edits to the current screen.
     */
    public void playerSetWin(Player player, View view) {
        player.winSet();
        resetAllTieBreakerPoints();
        SharedPreferences settings = getApplicationContext().getSharedPreferences(GAME_PREFERENCES, MODE_PRIVATE);
        int getMatchLength = settings.getInt("MatchLength", BEST_OF_3_SETS); //retrieves the match length user inputted previously.
        if (player.getSets() == 2 && getMatchLength == BEST_OF_3_SETS) {
            matchComplete(player); //If the player wins the most matches first then s/he wins the match.
            return;
        } else if (player.getSets() == 3 && getMatchLength == BEST_OF_5_SETS) {
            matchComplete(player);
            return;
        }

        switch (checkSetNumber()) { //identifies current set and makes the textviews of the next set visible
            case 1:
                set2header.setVisibility(View.VISIBLE);
                p1Set2Points.setVisibility(View.VISIBLE);
                p2Set2Points.setVisibility(View.VISIBLE);
                break;

            case 2:
                set3header.setVisibility(View.VISIBLE);
                p1Set3Points.setVisibility(View.VISIBLE);
                p2Set3Points.setVisibility(View.VISIBLE);
                break;

            case 3:
                set4header.setVisibility(View.VISIBLE);
                p1Set4Points.setVisibility(View.VISIBLE);
                p2Set4Points.setVisibility(View.VISIBLE);
                break;

            case 4:
                set5header.setVisibility(View.VISIBLE);
                p1Set5Points.setVisibility(View.VISIBLE);
                p2Set5Points.setVisibility(View.VISIBLE);
                break;

            case 5:
                matchComplete(player); //maximum amount of sets played therefore, there must be a winner.
                break;
            default:
                break;
        }
        player1.resetGamesCounter(); //resets games counter so that the new set starts with empty games counters.
        player2.resetGamesCounter();
    }

    public void resetAllTieBreakerPoints() {
        player1.resetTieBreakerPoints();
        player2.resetTieBreakerPoints();
    }

    /**
     * Player forfeits
     **/
    @OnClick(R.id.player1_forfeit)
    public void playerOneForfeit(View view) {
        playerForfeit(player1, view);
    }

    @OnClick(R.id.player2_forfeit)
    public void playerTwoForfeit(View view) {
        playerForfeit(player2, view);
    }

    /**
     * Asks the user if the selected player really wants to forfeit, if confirmed then the other player wins the match.
     *
     * @param player - Selected player that is forfeiting
     * @param view   - to make edits to the current screen
     */
    public void playerForfeit(final Player player, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(player.getName() + " is forfeiting the match")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast toast = Toast.makeText(getApplicationContext(), player.getName() + " forfeits the match", Toast.LENGTH_SHORT);
                        toast.show();
                        if (player == player1) {
                            matchComplete(player2);
                        } else {
                            matchComplete(player1);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Creates a new game and returns to the first activity allowing the user to reselect player names and match length
     *
     * @param view - to make edits to the current screen
     */
    @OnClick(R.id.remake_game)
    public void remakeGame(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to create a new game?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Undo the previous action
     *
     * @param view - to make edits to the current screen.
     */
    @OnClick(R.id.undo_action)
    public void undoAction(View view) {
        //TODO: undo functionality
        Toast toast = Toast.makeText(getApplicationContext(), "Undo functionality currently in progress", Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Resets the game, all visual and functional aspects e.g. textviews/set counters/points are reset
     * The app is returned to a state where the StartActivity just opens the Mainactivity
     *
     * @param view - to make edits to the current screen
     */
    @OnClick(R.id.reset_game)
    public void resetGame(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to reset the game?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        restartActivity();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Restart the activity
     */
    public void restartActivity() {
        Intent mIntent = getIntent(); //Retrieves the intent that started the activity
        finish(); //closes the activity
        startActivity(mIntent); //starts the activity given by the intent.
    }

    /**
     * Player Faults
     */
    @OnClick(R.id.player1_fault)
    public void player1Fault(View view) {
        playerFault(view, player1);
    }

    @OnClick(R.id.player2_fault)
    public void player2Fault(View view) {
        playerFault(view, player2);
    }

    /**
     * Selected player has made a fault, a fault animation will be played and points is awarded to the other player if double fault
     *
     * @param view   - required to make changes to the screen
     * @param player - player that made a fault
     */
    public void playerFault(View view, Player player) {
        player.addToFaultCounter();

        if (player.getFaults() >= 2) {
            if (player == player1) {
                setFaultAnimation(player1, DOUBLE_FAULT);
            } else {
                setFaultAnimation(player2, DOUBLE_FAULT);
            }
            player.resetFaultCounter();
            if (player == player1) {
                player2PointWin(view);
            } else {
                player1PointWin(view);
            }
        } else {
            if (player == player1) {
                setFaultAnimation(player1, FAULT);
            } else {
                setFaultAnimation(player2, FAULT);
            }
        }
    }

    /**
     * Creates an animation that reveals a textview declaring that a fault was made.
     *
     * @param player - player that made a fault!
     * @param text   - player fault text to be displayed
     */
    public void setFaultAnimation(final Player player, final String text) {
        //fade out animation
        AlphaAnimation alphaAnim = new AlphaAnimation(1, 0);
        alphaAnim.setStartOffset(500);
        alphaAnim.setDuration(1000);
        alphaAnim.setFillAfter(true);
        alphaAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (player == player1) {
                    p1Fault.setVisibility(View.INVISIBLE);
                } else {
                    p2Fault.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        if (player == player1) {
            p1Fault.setText(text);
            p1Fault.setVisibility(View.VISIBLE);
            p1Fault.setAnimation(alphaAnim);
        } else {
            p2Fault.setText(text);
            p2Fault.setVisibility(View.VISIBLE);
            p2Fault.setAnimation(alphaAnim);
        }
    }

    /**
     * Player passed in the parameters is announced the winner, all game play buttons are disabled
     *
     * @param winner The player that won the match!
     */
    public void matchComplete(Player winner) {
        Toast toast = Toast.makeText(getApplicationContext(), winner.getName() + " has won!!", Toast.LENGTH_SHORT);
        toast.show();
        disableGameplayButtons();
    }

    /**
     * Disables all gameplay buttons once the match ends
     */
    private void disableGameplayButtons() {
        buttonDisable(player1_pointWinBtn);
        buttonDisable(player2_pointWinBtn);

        buttonDisable(player1_faultBtn);
        buttonDisable(player2_faultBtn);

        buttonDisable(player1_forfeitBtn);
        buttonDisable(player2_forfeitBtn);
    }

    /**
     * Disables selected button to be unclickable.
     *
     * @param button Button that is to be disabled.
     */
    private void buttonDisable(Button button) {
        button.setEnabled(false);
        button.setBackgroundColor(getResources().getColor(R.color.color_grey));
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
