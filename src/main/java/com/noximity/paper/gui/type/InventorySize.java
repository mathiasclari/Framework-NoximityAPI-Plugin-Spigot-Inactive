package com.noximity.paper.gui.type;

public enum InventorySize {
    oneLine(9),
    twoLine(18),
    chest(27),
    fourLine(36),
    fiveLine(45),
    doubleChest(54);

    private int size;

    InventorySize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

}
