package de.hda.ena.praktikum;

import java.util.ArrayList;

import android.os.Environment;

public final class DataStore {
	public static ArrayList<Category> cData;
	public static double dMaxExpense = -1;

    public static void initForFirstUse() {
        DataStore.cData = new ArrayList<Category>();

        Category studium = new Category("Studium");
        DataStore.cData.add(studium);
        Category lebensmittel = new Category("Lebensmittel");
        DataStore.cData.add(lebensmittel);
        Category kleidung = new Category("Kleidung");
        DataStore.cData.add(kleidung);
        Category spiele = new Category("Spiele");
        DataStore.cData.add(spiele);
        Category elektronik = new Category("Elektronik");
        DataStore.cData.add(elektronik);
        Category kino = new Category("Kino");
        DataStore.cData.add(kino);
        Category kraftstoff = new Category("Kraftstoff");
        DataStore.cData.add(kraftstoff);
        Category diverses = new Category("Diverses");
        DataStore.cData.add(diverses);
    }
    
    public static double sumExpense(Interval i) {
    	double dSum = 0.0;
    	for(Category c : DataStore.cData) {
    		dSum += c.sum(i);
    	}
    	return dSum;
    }
    
    public static String sPath = Environment
			.getExternalStorageDirectory().getPath() + "/expenses.json";
    
    public static String sSettingsPath = Environment
			.getExternalStorageDirectory().getPath() + "/settings.json";
    
}
