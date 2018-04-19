package edu.washington.ieand.lifecounter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Space
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val playerIdTracker: IntArray =
            intArrayOf(-1, R.id.player1, R.id.player2, R.id.player3,
                    R.id.player4, R.id.player5, R.id.player6, R.id.player7,
                    R.id.player8)
    private val deadPlayers = mutableListOf<String>()
    private var numPlayers = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpAddBtn()

        setUpListeners(playerIdTracker[1])
        adjustPlayerName(1)
        setUpListeners(playerIdTracker[2])
        adjustPlayerName(2)
    }

    private fun adjustPlayerName(playerNumber: Int) {
        val currPlayer = findViewById<View>(playerIdTracker[playerNumber])
        val currPlayerNameView: EditText = currPlayer.findViewById(R.id.edittext_player_name)
        val currPlayerName: String = currPlayerNameView.text.toString() + " #$playerNumber"
        currPlayerNameView.setText(currPlayerName)
    }

    private fun setUpAddBtn() {
        try {
            val addPlayerBtn: Button = findViewById(R.id.btn_add_player)
            addPlayerBtn.setOnClickListener {
                numPlayers++
                val nextPlayer: View = findViewById(playerIdTracker[numPlayers])
                nextPlayer.visibility = View.VISIBLE
                setUpListeners(playerIdTracker[numPlayers])
                adjustPlayerName(numPlayers)
                if (numPlayers == 8) {
                    addPlayerBtn.isEnabled = false
                }
            }
        }
        catch (e: Exception) {
            Log.e(TAG, "Oops! Error in setUpAddBtn. Exception: " + e.toString())
        }

    }

    private fun setUpListeners(playerId: Int) {
        try {
            val currPlayer = findViewById<View>(playerId)

            setUpButtonListener(currPlayer, R.id.btn_minus1, -1)
            setUpButtonListener(currPlayer, R.id.btn_minus5, -5)
            setUpButtonListener(currPlayer, R.id.btn_plus1, 1)
            setUpButtonListener(currPlayer, R.id.btn_plus5, 5)
        }
        catch (e: Exception) {
            Log.e(TAG, "Oops! Error in setUpListeners. Exception: " + e.toString())
        }

    }

    private fun setUpButtonListener(currPlayer: View, btnId: Int, difference: Int) {
        try {
            val currLifeCounter = currPlayer.findViewById<TextView>(R.id.text_player_life_amount)
            val currBtn = currPlayer.findViewById<Button>(btnId)
            currBtn.setOnClickListener {
                var currLifeAmt = currLifeCounter.text.toString().toInt()
                if (currLifeAmt > 0) {
                    currLifeAmt += difference
                    currLifeCounter.text = currLifeAmt.toString()
                }
                if (currLifeAmt <= 0) {
                    val currPlayerName = currPlayer.findViewById<EditText>(R.id.edittext_player_name).text.toString()
                    deadPlayers.add(currPlayerName)
                    disableAllButtons(currPlayer)
                    val deadPlayersDisplay: TextView = findViewById(R.id.text_death_display)
                    var deathString = ""
                    if (deadPlayers.count() > 1) {
                        for (item in deadPlayers) deathString += "$item, "
                        deathString = deathString.substring(0, deathString.length - 2)
                        deathString += " have all DIED!"
                    } else {
                        deathString = deadPlayers.first() + " has died!"
                    }
                    deadPlayersDisplay.text = deathString
                    findViewById<Space>(R.id.space).visibility = View.VISIBLE
                    deadPlayersDisplay.visibility = View.VISIBLE
                }
            }
        }
        catch (e: Exception) {
            Log.e(TAG, "Oops! Error in setUpButtonListener. Exception: " + e.toString())
        }

    }

    private fun disableAllButtons(currPlayer: View) {
        try {
            currPlayer.findViewById<Button>(R.id.btn_minus1).isEnabled = false
            currPlayer.findViewById<Button>(R.id.btn_minus5).isEnabled = false
            currPlayer.findViewById<Button>(R.id.btn_plus1).isEnabled = false
            currPlayer.findViewById<Button>(R.id.btn_plus5).isEnabled = false
        }
        catch (e: Exception) {
            Log.e(TAG, "Oops! Error in disableAllButtons. Exception: " + e.toString())
        }

    }

    companion object {
        private val TAG = "MainActivity"
    }
}

/* Not working quite right. Saving for research's sake. */
//val currLayout: ConstraintLayout = findViewById(R.id.main_layout)
//var newLayout = LayoutInflater.from(applicationContext).inflate(R.layout.player, currLayout, false)
//val params: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT)
//params.setMargins(8, 0, 8, 0)
//newLayout.layoutParams = params
//val currId = newLayout.id
//val player2Id = R.id.player2
//val newConstraints = ConstraintSet()
//currLayout.addView(newLayout)
//newConstraints.clone(currLayout)
//newConstraints.connect(currId, ConstraintSet.TOP, player2Id, ConstraintSet.BOTTOM)
//newConstraints.connect(currId, ConstraintSet.START, R.id.main_layout, ConstraintSet.START)
//newConstraints.connect(currId, ConstraintSet.END, R.id.main_layout, ConstraintSet.END)
//newConstraints.applyTo(currLayout)