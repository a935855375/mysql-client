package com.fan.mysql.async.codec

import com.fan.mysql.async.message.client.{ClientMessage, SendLongDataMessage}
import com.fan.mysql.async.util.{ByteBufferUtils, Log}
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageEncoder
import org.slf4j.Logger

object SendLongDataEncoder {
  val log: Logger = Log.get[SendLongDataEncoder]

  val LONG_THRESHOLD = 1023
}

class SendLongDataEncoder
  extends MessageToMessageEncoder[SendLongDataMessage](classOf[SendLongDataMessage]) {

  import SendLongDataEncoder.log

  def encode(ctx: ChannelHandlerContext, message: SendLongDataMessage, out: java.util.List[Object]): Unit = {
    if (log.isTraceEnabled) {
      log.trace(s"Writing message ${message.toString}")
    }

    val sequence = 0

    val headerBuffer = ByteBufferUtils.mysqlBuffer(3 + 1 + 1 + 4 + 2)
    ByteBufferUtils.write3BytesInt(headerBuffer, 1 + 4 + 2 + message.value.readableBytes())
    headerBuffer.writeByte(sequence)

    headerBuffer.writeByte(ClientMessage.PreparedStatementSendLongData)
    headerBuffer.writeBytes(message.statementId)
    headerBuffer.writeShort(message.paramId)

    val result = Unpooled.wrappedBuffer(headerBuffer, message.value)

    out.add(result)
  }

}
