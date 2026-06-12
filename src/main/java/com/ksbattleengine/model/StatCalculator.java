package com.ksbattleengine.model;

import com.ksbattleengine.enums.Nature;
import com.ksbattleengine.enums.StatType;

public class StatCalculator {
        public static int calculateHP(
            int base,
            int iv,
            int ev,
            int level
        ) {
            return ((2 * base + iv + (ev / 4)) * level) / 100 + level + 10;
        }
    
        public static int calculateOtherStat(
            int base,
            int iv,
            int ev,
            int level,
            Nature nature,
            StatType stat
        ) {
        int calculatedStat =
            (((2 * base + iv + (ev / 4)) * level) / 100) + 5;

            return (int) (
                calculatedStat *
                nature.getMultiplier(stat)
            );
    }

}