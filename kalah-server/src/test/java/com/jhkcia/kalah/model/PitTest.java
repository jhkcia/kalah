package com.jhkcia.kalah.model;

import com.jhkcia.kalah.excaption.InvalidSowException;
import org.junit.Assert;
import org.junit.Test;

public class PitTest {
    @Test
    public void testInitialStoneCount() {
        for (int i = 0; i < 14; i++) {
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
        for (int i = 0; i < 14; i++) {
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

    @Test
    public void testIsEmpty() {
        for (int i = 0; i < 14; i++) {
            Pit p = new Pit(i);
            if (i == 6 || i == 13) {
                Assert.assertTrue(p.isEmpty());
            } else {
                Assert.assertFalse(p.isEmpty());
            }
        }
    }

    @Test
    public void testBelongsTo() {
        for (int i = 0; i < 14; i++) {
            Pit p = new Pit(i);
            if (i <= 6) {
                Assert.assertTrue(p.belongsToPlayer(0));
                Assert.assertFalse(p.belongsToPlayer(1));
            } else {
                Assert.assertFalse(p.belongsToPlayer(0));
                Assert.assertTrue(p.belongsToPlayer(1));
            }
        }
        Assert.assertFalse(new Pit(0).belongsToPlayer(2));
        Assert.assertFalse(new Pit(0).belongsToPlayer(-1));
    }

    @Test
    public void testGetOppositePit() {
        Assert.assertEquals(12, new Pit(0).getOppositePitIndex());
        Assert.assertEquals(11, new Pit(1).getOppositePitIndex());
        Assert.assertEquals(10, new Pit(2).getOppositePitIndex());
        Assert.assertEquals(9, new Pit(3).getOppositePitIndex());
        Assert.assertEquals(8, new Pit(4).getOppositePitIndex());
        Assert.assertEquals(7, new Pit(5).getOppositePitIndex());
        Assert.assertEquals(5, new Pit(7).getOppositePitIndex());
        Assert.assertEquals(4, new Pit(8).getOppositePitIndex());
        Assert.assertEquals(3, new Pit(9).getOppositePitIndex());
        Assert.assertEquals(2, new Pit(10).getOppositePitIndex());
        Assert.assertEquals(1, new Pit(11).getOppositePitIndex());
        Assert.assertEquals(0, new Pit(12).getOppositePitIndex());
    }

    @Test
    public void testGetStoreIndex() {
        Assert.assertEquals(6, Pit.getStoreIndex(0));
        Assert.assertEquals(13, Pit.getStoreIndex(1));
        Assert.assertEquals(-1, Pit.getStoreIndex(2));
    }

    @Test
    public void testRemoveAll() {
        Pit pit = new Pit(5);
        pit.removeAllStones();
        Assert.assertEquals(0, pit.getStones());
    }

    @Test
    public void testAddStone() {
        Pit pit = new Pit(0);
        pit.addStone();
        Assert.assertEquals(5, pit.getStones());
    }

    @Test
    public void testAddStones() {
        Pit pit = new Pit(0);
        pit.addStones(6);
        Assert.assertEquals(10, pit.getStones());
    }

    @Test
    public void testRemoveStone() {
        Pit pit = new Pit(0);
        pit.removeStone();
        Assert.assertEquals(3, pit.getStones());
    }

    @Test
    public void testRemoveStones() {
        Pit pit = new Pit(0);
        pit.removeStones(2);
        Assert.assertEquals(2, pit.getStones());
    }

    @Test
    public void testRemoveStonesMoreThanCurrentStones() {
        Pit pit = new Pit(1);

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> pit.removeStones(5));
        Assert.assertEquals("Can not remove 5 stone from pit 1.", exception.getMessage());
    }
}
