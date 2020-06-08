package com.fan.mysql.async.codec

import com.fan.mysql.async.db.ResultSet
import com.fan.mysql.async.message.server._
import io.netty.channel.ChannelHandlerContext

trait MySQLHandlerDelegate {

  def onHandshake(message: HandshakeMessage)

  def connected(ctx: ChannelHandlerContext)

  def exceptionCaught(exception: Throwable)

  def onOk(message: OkMessage)

  def onError(message: ErrorMessage)

  def onEOF(message: EOFMessage)

  def onResultSet(resultSet: ResultSet, message: EOFMessage)

  def switchAuthentication(message: AuthenticationSwitchRequest)


}
