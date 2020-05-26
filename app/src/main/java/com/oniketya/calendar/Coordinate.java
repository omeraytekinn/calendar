package com.oniketya.calendar;

class Coordinate {

    private float startY;
    private float endY;

    /**
     * @return the startX
     */
    public float getStartY() {
        return startY;
    }
    /**
     * @param startY the startY to set
     */
    public void setStartY(float startY) {
        this.startY = startY;
    }
    /**
     * @return the endX
     */
    public float getEndY() {
        return endY;
    }
    /**
     * @param endY the endY to set
     */
    public void setEndY(float endY) {
        this.endY = endY;
    }

    public float getDiff(){
        return endY - startY;
    }

    public boolean isScrollUp(){
        if((startY - endY) > 300)
            return true;
        return false;

    }
    public boolean isScrollDown(){
        if((endY - startY) > 300)
            return true;
        return false;

    }
}