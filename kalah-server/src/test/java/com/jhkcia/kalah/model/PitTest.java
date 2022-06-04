package com.jhkcia.kalah.model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class PitTest {
    @Test
    public void testInitialStoneCount() {
        for (int i = 0; i < 13; i++) {
            Pit p = new Pit(i);
            Assert.assertEquals(i, p.getId());
            if (i == 6 || i == 13) {
                Assert.assertEquals(0, p.getStones());
            } else {
                Assert.assertEquals(4, p.getStones());
            }
        }
    }

    @Test
    public void testPitType() {
        for (int i = 0; i < 13; i++) {
            Pit p = new Pit(i);
            if (i == 6 || i == 13) {
                Assert.assertTrue(p.isStore());
                Assert.assertFalse(p.isHouse());
            } else {
                Assert.assertFalse(p.isStore());
                Assert.assertTrue(p.isHouse());
            }
        }
    }
}
