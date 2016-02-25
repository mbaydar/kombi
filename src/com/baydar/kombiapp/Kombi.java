package com.baydar.kombiapp;

import java.util.ArrayList;

public class Kombi {

	String name;
	ArrayList<Model> models = new ArrayList<Model>();

	public Kombi() {

	}

	public String toString() {
		String ret = "";
		ret += "Kombi adi" + name;
		for (int i = 0; i < models.size(); i++) {
			ret += "Model adi" + models.get(i).name;
			for (int j = 0; j < models.get(i).errorCodes.size(); j++) {
				ret += "Hata Kodu" + models.get(i).errorCodes.get(j);
				ret += "\n";
				ret += "Hata Aciklama" + models.get(i).errorExps.get(j);
			}
		}
		return ret;
	}

}
