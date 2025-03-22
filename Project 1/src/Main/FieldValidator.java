package Main;
/**
 * The {@code FieldValidator} interface provides a generic validation mechanism.
 * It uses type parameters to support validation of different value types against various thresholds.
 * Implementations of this interface define specific validation logic for different field types.
 */
public interface FieldValidator<T, E> {
    public String validate(T value, E threshold);

}
