package org.cjc.mydives.divetracker.db;

/**
 * Constants for the User model class
 * @author JuanCarlos
 *
 */
public class UserConstants {
	public static final String DB_TABLE = "user";
	public static final String FIELD_ROWID = "_id";
	public static final String FIELD_NAME = "name";
	public static final String FIELD_SURNAME = "surname";
	public static final String FIELD_PROFILEPIC = "profilepic";

	public static String[] fields(){
		return new String[]{FIELD_ROWID, FIELD_NAME, FIELD_SURNAME, FIELD_PROFILEPIC};
	}
}
