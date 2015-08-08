package fr.jerome.climbinggymlog.view.custom;

import android.content.Context;
import android.util.AttributeSet;

import java.util.ArrayList;

import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.Cotation;


/**
 * Created by jerome on 08/08/15.
 */
public class CotationPicker extends PickerWithCustonFontSize {

    int initialPosition = 10;
    private String[] cotationValues;
    private String[] cotationValuesWithPlus;
    private boolean isPlus;

    public CotationPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {

        final ArrayList<Cotation> cotations = (ArrayList<Cotation>) AppManager.cotations;
        int size = cotations.size()/2;
        cotationValues = new String[size];
        cotationValuesWithPlus = new String[size];

        int indexWithoutPlus = 0;
        int indexWithPlus = 0;
        for (Cotation c : cotations) {
            int indexCotation = (int) (c.getId() - 1);
            if (indexCotation % 2 == 0) {
                cotationValues[indexWithoutPlus] = c.getNom();
                indexWithoutPlus++;
            }
            else {
                cotationValuesWithPlus[indexWithPlus] = c.getNom();
                indexWithPlus++;
            }
        }

        this.setMinValue(0);
        this.setValue(initialPosition);
        this.setWrapSelectorWheel(false);
        togglePlusCotationPicker();
    }

    /**
     * Raffraichi l'affichage du CotationPicker avec les cotation "+" ou non
     * exemple : si isPlusCoation = true alors "5c+" est affiché, sinon "5c" est affiché
     */
    private void togglePlusCotationPicker() {

        if (isPlus) {
            this.setMaxValue(cotationValuesWithPlus.length - 1);
            this.setDisplayedValues(cotationValuesWithPlus);
        }
        else {
            this.setMaxValue(cotationValues.length - 1);
            this.setDisplayedValues(cotationValues);
        }
    }

    public boolean isPlus() {
        return isPlus;
    }

    public void setPlus(boolean isPlus) {
        this.isPlus = isPlus;
        togglePlusCotationPicker();
    }
}
