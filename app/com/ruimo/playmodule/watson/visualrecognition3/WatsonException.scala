package com.ruimo.playmodule.watson.visualrecognition3

class WatsonException(message: String, cause: Throwable) extends Exception(message, cause) {
  def this(serviceName: String, statusCode: Int, statusText: String) = this(
    serviceName + "[statusCode: " + statusCode + ", statusText: '" + statusText + "']", null
  )
  def this(message: String) = this(message, null)
  def this(cause: Throwable) = this(null, cause)
  def this() = this(null, null)
}




 
