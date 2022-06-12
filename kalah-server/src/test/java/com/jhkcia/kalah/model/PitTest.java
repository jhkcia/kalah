package com.jhkcia.kalah.model;

import com.jhkcia.kalah.exception.InvalidSowException;
import org.junit.Assert;
import org.junit.Test;

public class PitTest {

    @Test
    public void should_not_empty() {
        Pit pit = new Pit(0);
        pit.setStones(4);

        Assert.assertFalse(pit.isEmpty());
    }

    @Test
    public void should_be_empty() {
        Pit pit = new Pit(0);
        pit.setStones(0);
        
        Assert.assertTrue(pit.isEmpty());
    }

    @Test
    public void should_remove_all() {
        Pit pit = new Pit(5);
        pit.setStones(4);

        pit.removeAllStones();

        Assert.assertEquals(0, pit.getStones());
    }

    @Test
    public void should_add_single_stone() {
        Pit pit = new Pit(0);
        pit.setStones(4);

        pit.addStone();

        Assert.assertEquals(5, pit.getStones());
    }

    @Test
    public void should_add_multiple_stones() {
        Pit pit = new Pit(0);
        pit.setStones(4);

        pit.addStones(6);

        Assert.assertEquals(10, pit.getStones());
    }

    @Test
    public void should_remove_single_stone() {
        Pit pit = new Pit(0);
        pit.setStones(4);

        pit.removeStone();

        Assert.assertEquals(3, pit.getStones());
    }

    @Test
    public void should_remove_multiple_stones() {
        Pit pit = new Pit(0);
        pit.setStones(4);

        pit.removeStones(2);

        Assert.assertEquals(2, pit.getStones());
    }

    @Test
    public void should_throw_exception_on_removing_more_stones() {
        Pit pit = new Pit(1);
        pit.setStones(4);

        Exception exception = Assert.assertThrows(InvalidSowException.class, () -> pit.removeStones(5));
        Assert.assertEquals("Can not remove 5 stone from pit 1.", exception.getMessage());
    }
}
