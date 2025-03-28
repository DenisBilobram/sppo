package lab5.labwork.fields;

public class IdField extends Field{

    private Long value;

    @Override
    public boolean validate() {
        if (this.value == null || this.value > 0) {
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
