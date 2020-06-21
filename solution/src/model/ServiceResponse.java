package model;

/**
 * Representation of API responses at within the service. Categorizes responses of all APIs under one type which
 * allows certain top-level classes to treat all APIs' responses with a common rule (e.g. global exception handling).
 * @see handler.Handler
 */
public interface ServiceResponse {}
