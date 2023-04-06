package client.labwork.fields;

public class MinimalPointField extends Field {

    private Long value;

    @Override
    public boolean validate() {
        if (this.value == null) {
            return true;
        }
        if (this.value <= 0 || this.value < Long.MIN_VALUE || this.value > Long.MAX_VALUE) {
            return false;
        } else {
            return true;
        }
    }

    public Long toType(String input) {
        if (input == null) {
            return null;
        }
        return Long.parseLong(input);
    }

    public void putValue(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return this.value;
    }
    
}
