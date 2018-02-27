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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupGame(); //sets player names and current set.
        firstServer(); //user selects first user
    }

    /**
     * Setups up the game by setting the players name by the input given in the previous activity. All sets besides the first set are
     * set as invisible.
     */
    private void setupGame() {
        SharedPreferences settings = getApplicationContext().getSharedPreferences(GAME_PREFERENCES, MODE_PRIVATE);
        TextView player1Name = (TextView) findViewById(R.id.player1_name);
        TextView player2Name = (TextView) findViewById(R.id.player2_name);
        TextView p1Fault = (TextView) findViewById(R.id.p1Fault);
        TextView p2Fault = (TextView) findViewById(R.id.p2Fault);
        TextView p1 = (TextView) findViewById(R.id.p1);
        TextView p2 = (TextView) findViewById(R.id.p2);
        player1Set1Counter = player2Set1Counter = player1Set2Counter = player2Set2Counter = player1Set3Counter = player2Set3Counter = player1Set4Counter = player2Set4Counter = player1Set5Counter = player2Set5Counter = 0;

        p1Fault.setVisibility(View.INVISIBLE);
        p2Fault.setVisibility(View.INVISIBLE);

        TextView set2h = (TextView) findViewById(R.id.set2header);
        set2h.setVisibility(View.GONE);
        TextView p1Set2 = (TextView) findViewById(R.id.p1Set2Points);
        p1Set2.setVisibility(View.GONE);
        TextView p2Set2 = (TextView) findViewById(R.id.p2Set2Points);
        p2Set2.setVisibility(View.GONE);

        TextView set3h = (TextView) findViewById(R.id.set3header);
        set3h.setVisibility(View.GONE);
        TextView p1Set3 = (TextView) findViewById(R.id.p1Set3Points);
        p1Set3.setVisibility(View.GONE);
        TextView p2Set3 = (TextView) findViewById(R.id.p2Set3Points);
        p2Set3.setVisibility(View.GONE);

        TextView set4h = (TextView) findViewById(R.id.set4header);
        set4h.setVisibility(View.GONE);
        TextView p1Set4 = (TextView) findViewById(R.id.p1Set4Points);
        p1Set4.setVisibility(View.GONE);
        TextView p2Set4 = (TextView) findViewById(R.id.p2Set4Points);
        p2Set4.setVisibility(View.GONE);

        TextView set5h = (TextView) findViewById(R.id.set5header);
        set5h.setVisibility(View.GONE);
        TextView p1Set5 = (TextView) findViewById(R.id.p1Set5Points);
        p1Set5.setVisibility(View.GONE);
        TextView p2Set5 = (TextView) findViewById(R.id.p2Set5Points);
        p2Set5.setVisibility(View.GONE);

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
                        Button player1_faultBtn = (Button) findViewById(R.id.player1_fault);
                        player1_faultBtn.setEnabled(false);
                        player1_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
                        player2.setIsServing(true);
                        dialog.cancel();
                    }
                })
                .setNegativeButton(player1.getName(), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Button player2_faultBtn = (Button) findViewById(R.id.player2_fault);
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
        Button player1Faultbtn = (Button) findViewById(R.id.player1_fault);
        Button player2Faultbtn = (Button) findViewById(R.id.player2_fault);
        if (player1.isServing()) {
            player2.setIsServing(true);
            player2Faultbtn.setEnabled(true);
            player2Faultbtn.setBackgroundColor(getResources().getColor(R.color.color_orange));
            player1.setIsServing(false);
            player1Faultbtn.setEnabled(false);
            player1Faultbtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
        } else {
            player1.setIsServing(true);
            player1Faultbtn.setEnabled(true);
            player1Faultbtn.setBackgroundColor(getResources().getColor(R.color.color_orange));
            player2.setIsServing(false);
            player2Faultbtn.setEnabled(false);
            player2Faultbtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
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

    public void player1PointWin(View view) {
        playerPointWin(player1, view);
    }

    public void player2PointWin(View view) {
        playerPointWin(player2, view);
    }

    /**
     * Updates the point scores of the players.
     */
    public void updatePlayerScores() {
        TextView player1Points_view = (TextView) findViewById(R.id.player1_points);
        TextView player2Points_view = (TextView) findViewById(R.id.player2_points);
        TextView p1Points_View = (TextView) findViewById(R.id.p1Points);
        TextView p2Points_View = (TextView) findViewById(R.id.p2Points);

        if (player1.getPoints().getPointValue() == "tb") {
            player1Points_view.setText("" + player1.getTieBreakerPoints());
            player2Points_view.setText("" + player2.getTieBreakerPoints());

            p1Points_View.setText("" + player1.getTieBreakerPoints());
            p2Points_View.setText("" + player2.getTieBreakerPoints());
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
    public int checkWhatSet() {
        TextView set2 = (TextView) findViewById(R.id.set2header);
        TextView set3 = (TextView) findViewById(R.id.set3header);
        TextView set4 = (TextView) findViewById(R.id.set4header);
        TextView set5 = (TextView) findViewById(R.id.set5header);
        if (set5.getVisibility() == View.VISIBLE) {
            return 5;
        } else if (set4.getVisibility() == View.VISIBLE) {
            return 4;
        } else if (set3.getVisibility() == View.VISIBLE) {
            return 3;
        } else if (set2.getVisibility() == View.VISIBLE) {
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
        TextView player1Points_view = (TextView) findViewById(R.id.player1_points);
        player1Points_view.setText(player1.getPoints().getPointValue());
        TextView player2Points_view = (TextView) findViewById(R.id.player2_points);
        player1Points_view.setText(player2.getPoints().getPointValue());

        player1.setPoints(Player.Point.point_0); //resets points of players
        player2.setPoints(Player.Point.point_0);

        TextView p1Set1 = (TextView) findViewById(R.id.p1Set1Points);
        TextView p1Set2 = (TextView) findViewById(R.id.p1Set2Points);
        TextView p1Set3 = (TextView) findViewById(R.id.p1Set3Points);
        TextView p1Set4 = (TextView) findViewById(R.id.p1Set4Points);
        TextView p1Set5 = (TextView) findViewById(R.id.p1Set5Points);

        TextView p2Set1 = (TextView) findViewById(R.id.p2Set1Points);
        TextView p2Set2 = (TextView) findViewById(R.id.p2Set2Points);
        TextView p2Set3 = (TextView) findViewById(R.id.p2Set3Points);
        TextView p2Set4 = (TextView) findViewById(R.id.p2Set4Points);
        TextView p2Set5 = (TextView) findViewById(R.id.p2Set5Points);

        switch (checkWhatSet()) {  //checks what set is currently being played
            case 1:
                if (player == player1) { //checks which player
                    p1Set1.setText("" + ++player1Set1Counter); //increments selected player's counter of the current set
                } else {
                    p2Set1.setText("" + ++player2Set1Counter);
                }
                break;
            case 2:
                if (player == player1) {
                    p1Set2.setText("" + ++player1Set2Counter);
                } else {
                    p2Set2.setText("" + ++player2Set2Counter);
                }
                break;
            case 3:
                if (player == player1) {
                    p1Set3.setText("" + ++player1Set3Counter);
                } else {
                    p2Set3.setText("" + ++player2Set3Counter);
                }
                break;
            case 4:
                if (player == player1) {
                    p1Set4.setText("" + ++player1Set4Counter);
                } else {
                    p2Set4.setText("" + ++player2Set4Counter);
                }
                break;
            case 5:
                if (player == player1) {
                    p1Set5.setText("" + ++player1Set5Counter);
                } else {
                    p2Set5.setText("" + ++player2Set5Counter);
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

        switch (checkWhatSet()) { //identifies current set and makes the textviews of the next set visible
            case 1:
                TextView set2h = (TextView) findViewById(R.id.set2header);
                set2h.setVisibility(View.VISIBLE);
                TextView p1Set2 = (TextView) findViewById(R.id.p1Set2Points);
                p1Set2.setVisibility(View.VISIBLE);
                TextView p2Set2 = (TextView) findViewById(R.id.p2Set2Points);
                p2Set2.setVisibility(View.VISIBLE);
                break;

            case 2:
                TextView set3h = (TextView) findViewById(R.id.set3header);
                set3h.setVisibility(View.VISIBLE);
                TextView p1Set3 = (TextView) findViewById(R.id.p1Set3Points);
                p1Set3.setVisibility(View.VISIBLE);
                TextView p2Set3 = (TextView) findViewById(R.id.p2Set3Points);
                p2Set3.setVisibility(View.VISIBLE);
                break;

            case 3:
                TextView set4h = (TextView) findViewById(R.id.set4header);
                set4h.setVisibility(View.VISIBLE);
                TextView p1Set4 = (TextView) findViewById(R.id.p1Set4Points);
                p1Set4.setVisibility(View.VISIBLE);
                TextView p2Set4 = (TextView) findViewById(R.id.p2Set4Points);
                p2Set4.setVisibility(View.VISIBLE);
                break;

            case 4:
                TextView set5h = (TextView) findViewById(R.id.set5header);
                set5h.setVisibility(View.VISIBLE);
                TextView p1Set5 = (TextView) findViewById(R.id.p1Set5Points);
                p1Set5.setVisibility(View.VISIBLE);
                TextView p2Set5 = (TextView) findViewById(R.id.p2Set5Points);
                p2Set5.setVisibility(View.VISIBLE);
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
    public void playerOneForfeit(View view) {
        playerForfeit(player1, view);
    }

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
        builder.setMessage(player.getName() + ", is forfeiting the match")
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
    public void player1Fault(View view) {
        playerFault(view, player1);
    }

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

        final TextView p1Fault = (TextView) findViewById(R.id.p1Fault);
        final TextView p2Fault = (TextView) findViewById(R.id.p2Fault);

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
        final TextView p1Fault = (TextView) findViewById(R.id.p1Fault);
        final TextView p2Fault = (TextView) findViewById(R.id.p2Fault);

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

        //Disable all gameplay buttons.
        Button player1_pointWinBtn = (Button) findViewById(R.id.player1_pointWin);
        player1_pointWinBtn.setEnabled(false);
        player1_pointWinBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
        Button player2_pointWinBtn = (Button) findViewById(R.id.player2_pointWin);
        player2_pointWinBtn.setEnabled(false);
        player2_pointWinBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));

        Button player1_faultBtn = (Button) findViewById(R.id.player1_fault);
        player1_faultBtn.setEnabled(false);
        player1_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
        Button player2_faultBtn = (Button) findViewById(R.id.player2_fault);
        player2_faultBtn.setEnabled(false);
        player2_faultBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));

        Button player1_forfeitBtn = (Button) findViewById(R.id.player1_forfeit);
        player1_forfeitBtn.setEnabled(false);
        player1_forfeitBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
        Button player2_forfeitBtn = (Button) findViewById(R.id.player2_forfeit);
        player2_forfeitBtn.setEnabled(false);
        player2_forfeitBtn.setBackgroundColor(getResources().getColor(R.color.color_grey));
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
