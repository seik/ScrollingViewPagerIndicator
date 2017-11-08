package me.ivmg.scrollingviewpagerindicatorsample;

public enum ModelObject {

    ONE(R.string.red, R.layout.view_red),
    TWO(R.string.blue, R.layout.view_blue),
    THREE(R.string.green, R.layout.view_green),
    FOUR(R.string.red, R.layout.view_red),
    FIVE(R.string.blue, R.layout.view_blue),
    SIX(R.string.green, R.layout.view_green);

    private int titleResId;
    private int layoutResId;

    ModelObject(int titleResId, int layoutResId) {
        this.titleResId = titleResId;
        this.layoutResId = layoutResId;
    }

    public int getTitleResId() {
        return titleResId;
    }

    public int getLayoutResId() {
        return layoutResId;
    }

}