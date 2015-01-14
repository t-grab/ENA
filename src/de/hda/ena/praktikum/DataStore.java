package de.hda.ena.praktikum;

import java.util.ArrayList;

public final class DataStore {
	public static ArrayList<Category> cData;

    public static void initForFirstUse() {
        DataStore.cData = new ArrayList<Category>();

        Category studium = new Category("Studium");
        DataStore.cData.add(studium);
        Category lebensmittel = new Category("Lebensmittel");
        DataStore.cData.add(lebensmittel);
        Category diverses = new Category("Diverses");
        DataStore.cData.add(diverses);
    }
}
