package in.shabhushan.catalog.constants

/**
 * @author Shashi Bhushan
 */
enum ToolTipPlacement {
    LEFT("left"), RIGHT("right"), TOP("top"), BOTTOM("bottom")

    private String toolTipPlacement

    private ToolTipPlacement(String toolTipPlacement) {
        this.toolTipPlacement = toolTipPlacement
    }
}
