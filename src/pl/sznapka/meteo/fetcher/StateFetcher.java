package pl.sznapka.meteo.fetcher;

import java.util.ArrayList;

import pl.sznapka.meteo.valueobject.State;

public class StateFetcher implements IFetcher {

	@Override
	public ArrayList<State> fetch() {
		
		ArrayList<State> states = new ArrayList<State>();
		states.add(new State("dolnośląskie", "DS"));
		states.add(new State("kujawsko-pomorskie", "KP"));
		states.add(new State("lubelskie", "LL"));
		states.add(new State("lubuskie", "LS"));
		states.add(new State("łódzkie", "LD"));
		states.add(new State("małopolskie", "MP"));
		states.add(new State("mazowieckie", "MA"));
		states.add(new State("opolskie", "OP"));
		states.add(new State("podlaskie", "PD"));
		states.add(new State("podkarpackie", "PK"));
		states.add(new State("pomorskie", "PO"));
		states.add(new State("śląskie", "SL"));
		states.add(new State("świętokrzyskie", "SW"));
		states.add(new State("warmińsko-mazurskie", "WM"));
		states.add(new State("wielkopolskie", "WP"));
		states.add(new State("zachodniopomorskie", "ZP"));
		
		return states;
	}
}