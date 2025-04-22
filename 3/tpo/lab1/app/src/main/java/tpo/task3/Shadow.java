package tpo.task3;

// Описывает объект «тень»
public class Shadow {
    private ShadowState state;

    public Shadow() {
        this.state = ShadowState.MOVING;
    }

    public ShadowState getState() {
        return state;
    }

    public void setState(ShadowState state) {
        this.state = state;
    }
}
