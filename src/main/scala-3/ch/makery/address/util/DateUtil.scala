package ch.makery.address.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

object DateUtil :
  val DATE_PATTERN = "dd.MM.yyyy" //day day. month month. year year
  val DATE_FORMATTER =  DateTimeFormatter.ofPattern(DATE_PATTERN) //static method, not instance (did not create an instance of DateTimeFormatter)

  //extension is used when u want to add methods to an existing class without modifying the original class
  //only scala can do this, not Java
  //use we cant modify the original class (source code)
  extension (date: LocalDate)
    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link DateUtil# DATE_PATTERN} is used.
     *
     * @param date the date to be returned as a string
     * @return formatted string
     */
    def asString: String =
      if (date == null)
        return null;
      return DATE_FORMATTER.format(date); //will return a String in the format of the defined DATE_PATTERN

  extension (data : String)
    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN}
     * to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString the date as String
     * @return the date object or null if it could not be converted
     */
    
    //parse the date user entered (String) to LocalDate object, cannot store as String in the model
    
    // bad practice: use of "null" in Scala
//    def parseLocalDate : LocalDate =
//      try
//        LocalDate.parse(data, DATE_FORMATTER)
//      catch
//        case  e: DateTimeParseException => null (bad practice, using null in Scala is discouraged)

    // good practice: use of Option in Scala
    def parseLocalDate : Option[LocalDate] =
      try
        Option(LocalDate.parse(data, DATE_FORMATTER)) //option always give us 2 values, either Some(value) or None
        // Some is when there is value, None is when there is no value (hence, no null when no value)
      catch
        case  e: DateTimeParseException => null
  
    def isValid : Boolean =
      data.parseLocalDate != null
