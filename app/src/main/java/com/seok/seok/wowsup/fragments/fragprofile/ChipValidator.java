package com.seok.seok.wowsup.fragments.fragprofile;

import com.tylersuehr.chips.Chip;
import com.tylersuehr.chips.ChipsInputLayout;

public class ChipValidator implements ChipsInputLayout.ChipValidator {
    @Override
    public boolean validate(Chip chip) {
        return chip.getTitle().toLowerCase().contains("t");
    }
}
