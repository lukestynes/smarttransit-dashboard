package seng202.team5.exceptions;

import java.sql.SQLDataException;
import java.sql.SQLSyntaxErrorException;

/**
 * @author Dominic Gorny
 * Indicates that an SQLQueryObjects sql string has been requested before it was built. this exception exist to ensure
 * No empty sql strings are returned as these might go unoticed
 */
public class QueryNotBuiltException extends IllegalArgumentException
{
    /**
     * creates a QueryNotBuiltException with the Message:
     * <i>Can't call Query String, Query not built. Call buildSelectQuery on SQLQueryObject for default query</i>
     */
    public QueryNotBuiltException()
    {
        super("Can't call Query String, Query not built. Call buildSelectQuery on SQLQueryObject for default query");
    }
}
