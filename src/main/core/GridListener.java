package main.core;

/**
 * Interface which make the connections between the model {@link Grid} and observers of this model, like {@link main.gui.VueGrid}.
 * @author David Matthias
 */
public interface GridListener {
    /**
     * Occured when <b>Grid</b> have changed
     * @see Grid#setBoard(Cell[][])
     * @see Grid#nextGen()
     */
    public void changeOccured();

    /**
     * Occured when <b>Cell</b> at coordinates x y had changed
     * @param x Horizontal axis of <b>Cell</b> that changed
     * @param y Vertcal axis of <b>Cell</b> that changed
     * @see Grid#setCell(int, int, Cell)
     */
    public void changeCell(int x, int y);
}
