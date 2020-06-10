

package com.fan.mysql.async.util

import java.nio.charset.Charset

import com.fan.mysql.async.exceptions.UnknownLengthException
import io.netty.buffer.ByteBuf

import scala.language.implicitConversions

object ChannelWrapper {
  implicit def bufferToWrapper(buffer: ByteBuf): ChannelWrapper = new ChannelWrapper(buffer)

  final val MySQL_NULL = 0xfb
  final val log = Log.get[ChannelWrapper]

}

class ChannelWrapper(val buffer: ByteBuf) extends AnyVal {

  import ChannelWrapper._

  def readFixedString(length: Int, charset: Charset): String = {
    val bytes = new Array[Byte](length)
    buffer.readBytes(bytes)
    new String(bytes, charset)
  }

  def readCString(charset: Charset): String = ByteBufferUtils.readCString(buffer, charset)

  def readUntilEOF(charset: Charset): String = ByteBufferUtils.readUntilEOF(buffer, charset)

  def readLengthEncodedString(charset: Charset): String = {
    val length = readBinaryLength
    readFixedString(length.asInstanceOf[Int], charset)
  }

  def readBinaryLength: Long = {
    val firstByte = buffer.readUnsignedByte()

    if (firstByte <= 250) {
      firstByte
    } else {
      firstByte match {
        case MySQL_NULL => -1
        case 252 => buffer.readUnsignedShort()
        case 253 => readLongInt
        case 254 => buffer.readLong()
        case _ => throw new UnknownLengthException(firstByte)
      }
    }

  }

  def readLongInt: Int = {
    val first = buffer.readByte()
    val second = buffer.readByte()
    val third = buffer.readByte()

    (first & 0xff) | ((second & 0xff) << 8) | ((third & 0xff) << 16)
  }

  def writeLength(length: Long): Unit = {
    if (length < 251) {
      buffer.writeByte(length.asInstanceOf[Byte])
    } else if (length < 65536L) {
      buffer.writeByte(252)
      buffer.writeShort(length.asInstanceOf[Int])
    } else if (length < 16777216L) {
      buffer.writeByte(253)
      writeLongInt(length.asInstanceOf[Int])
    } else {
      buffer.writeByte(254)
      buffer.writeLong(length)
    }
  }

  def writeLongInt(i: Int): Unit = {
    buffer.writeByte(i & 0xff)
    buffer.writeByte(i >>> 8)
    buffer.writeByte(i >>> 16)
  }

  def writeLengthEncodedString(value: String, charset: Charset): Unit = {
    val bytes = value.getBytes(charset)
    writeLength(bytes.length)
    buffer.writeBytes(bytes)
  }

  def writePacketLength(sequence: Int = 0): Unit = {
    ByteBufferUtils.writePacketLength(buffer, sequence)
  }

  def mysqlReadInt(): Int = {
    val first = buffer.readByte()
    val last = buffer.readByte()

    (first & 0xff) | ((last & 0xff) << 8)
  }


}
