package tv.skimo.connector.lib;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = -8088323444134719018L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}
}
