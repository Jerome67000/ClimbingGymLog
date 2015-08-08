package fr.jerome.climbinggymlog.view.custom;

import android.content.Context;
import android.util.AttributeSet;


import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.TypeEsc;

/**
 * Created by jerome on 14/07/15.
 */
public class TypeEscaladePicker extends PickerWithCustonFontSize {

    public TypeEscaladePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {

        String[] typeEscArray = new String[AppManager.typesEsc.size()];
        for (TypeEsc type : AppManager.typesEsc) {
            int index = (int) (type.getId() - 1);
            typeEscArray[index] = type.getType();
        }


        this.setDisplayedValues(typeEscArray);

        this.setMinValue(0);
        this.setMaxValue(typeEscArray.length - 1);
        this.setWrapSelectorWheel(false);
        this.setValue(1);
    }
}
