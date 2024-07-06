package database;

public class UniqueRecordException extends CreatingException{
    public UniqueRecordException(String message) {
        super(message);
    }
}
