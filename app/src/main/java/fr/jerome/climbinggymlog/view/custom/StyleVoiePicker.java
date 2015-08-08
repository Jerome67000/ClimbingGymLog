package fr.jerome.climbinggymlog.view.custom;

import android.content.Context;
import android.util.AttributeSet;

import fr.jerome.climbinggymlog.helpers.AppManager;
import fr.jerome.climbinggymlog.models.StyleVoie;
/**
 * Created by jerome on 14/07/15
 */
public class StyleVoiePicker extends PickerWithCustonFontSize {

    public StyleVoiePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {

        String[] styleVoieValues = new String[AppManager.styleVoies.size()];

        for (StyleVoie style : AppManager.styleVoies) {
            int index = (int) (style.getId() - 1);
            styleVoieValues[index] = style.getStyle();
        }

        this.setDisplayedValues(styleVoieValues);
        this.setMaxValue(styleVoieValues.length - 1);
        this.setMinValue(0);
        this.setWrapSelectorWheel(false);
        this.setValue(1);
   }

    public void setValue(int value) {

    }
}
