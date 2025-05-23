package tpo.task3;

// Описывает выключатель с возможностью неисправности
public class SwitchDevice {
    private boolean isOn;
    private boolean isMalfunctioning;

    public SwitchDevice() {
        this.isOn = false;
        this.isMalfunctioning = false;
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean isMalfunctioning() {
        return isMalfunctioning;
    }

    public void setMalfunctioning(boolean malfunctioning) {
        isMalfunctioning = malfunctioning;
    }

    // Переключение выключателя происходит только если устройство не неисправно
    public void toggle() {
        if (!isMalfunctioning) {
            isOn = !isOn;
        }
    }
}
