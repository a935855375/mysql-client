package com.fan.mysql.async.exceptions

class DateEncoderNotAvailableException(value: Any)
    extends DatabaseException(
      "There is no encoder for value [%s] of type %s".format(value,
                                                             value.getClass.getCanonicalName))
