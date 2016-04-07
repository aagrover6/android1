package edu.calpoly.android.lab2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.GridLayout.LayoutParams;
import android.view.ViewGroup;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;

public class SimpleJokeList extends Activity {

	/** Contains the list Jokes the Activity will present to the user. */
	protected ArrayList<Joke> m_arrJokeList;

	/** LinearLayout used for maintaining a list of Views that each display Jokes. */
	protected LinearLayout m_vwJokeLayout;

	/** EditText used for entering text for a new Joke to be added to m_arrJokeList. */
	protected EditText m_vwJokeEditText;

	/** Button used for creating and adding a new Joke to m_arrJokeList using the
	 * text entered in m_vwJokeEditText. */
	protected Button m_vwJokeButton;
	
	/** Background Color values used for alternating between light and dark rows
	 * of Jokes. */
	protected int m_nDarkColor;
	protected int m_nLightColor;

	/** Used to determine alternating colors. */
	public int alternate = 0;

	/** Text color. */
	protected int m_nTextColor;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();

		this.m_arrJokeList = new ArrayList<Joke>();
		String[] jokes = this.getResources().getStringArray(R.array.jokeList);

		for (int i = 0; i < jokes.length; i++) {
			Log.d("lab2<userid>", "Adding new joke: " + jokes[i]);
			addJoke(jokes[i]);
		}

		initAddJokeListeners();
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Layout
	 * for this Activity. 
	 */
	protected void initLayout() {
		m_vwJokeLayout = new LinearLayout(this);
		m_vwJokeLayout.setOrientation(LinearLayout.VERTICAL);

		ScrollView sv = new ScrollView(this);
		sv.addView(m_vwJokeLayout);

		// root ViewGroup.
		LinearLayout vertLayout = new LinearLayout(this);
		vertLayout.setOrientation(LinearLayout.VERTICAL);

		// ViewGroup containing the "Add Joke" button and text field.
		LinearLayout horzLayout = new LinearLayout(this);
		horzLayout.setOrientation(LinearLayout.HORIZONTAL);

		m_vwJokeButton = new Button(this);
		m_vwJokeButton.setText("Add Joke");
		horzLayout.addView(m_vwJokeButton);

		m_vwJokeEditText = new EditText(this);

		m_vwJokeEditText.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
		m_vwJokeEditText.setInputType(InputType.TYPE_CLASS_TEXT);

		horzLayout.addView(m_vwJokeEditText);
		vertLayout.addView(horzLayout);
		vertLayout.addView(sv);
		setContentView(vertLayout);
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Joke to the 
	 * list. 
	 */
	protected void initAddJokeListeners() {

		m_vwJokeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String jokeToAdd = m_vwJokeEditText.getText().toString();
				m_vwJokeEditText.setText("");

				if (!jokeToAdd.isEmpty()) {
					addJoke(jokeToAdd);
				}

				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);
			}
		});

		m_vwJokeEditText.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_DOWN &&
						(keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER)) {
					String jokeToAdd = m_vwJokeEditText.getText().toString();

					if (!jokeToAdd.isEmpty()) {
						addJoke(jokeToAdd);
					}

					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);

					return true;
				}
				return false;
			}
		});
	}

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to m_arrJokeList, and display it on screen.
	 * 
	 * @param strJoke
	 *            A string containing the text of the Joke to add.
	 */
	protected void addJoke(String strJoke) {

		Joke joke = new Joke(strJoke);
		this.m_arrJokeList.add(joke);

		m_nDarkColor = this.getResources().getColor(R.color.dark);
		m_nLightColor = this.getResources().getColor(R.color.light);
		m_nTextColor = this.getResources().getColor(R.color.text);

		TextView textView = new TextView(this);
		textView.setText(strJoke);
		textView.setTextSize(16);
		textView.setTextColor(m_nTextColor);

		if (alternate++ % 2 == 0) {
			textView.setBackgroundColor(m_nDarkColor);
		}
		else {
			textView.setBackgroundColor(m_nLightColor);
		}

		this.m_vwJokeLayout.addView(textView);
	}
}