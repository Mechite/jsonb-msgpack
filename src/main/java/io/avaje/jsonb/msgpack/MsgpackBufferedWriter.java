package io.avaje.jsonb.msgpack;

import io.avaje.jsonb.spi.BufferedJsonWriter;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.ByteArrayOutputStream;

/**
 * Implementation of {@link BufferedJsonWriter} for MessagePack.
 */
final class MsgpackBufferedWriter extends MsgpackWriter implements BufferedJsonWriter {

	MsgpackBufferedWriter(boolean serializeNulls, boolean serializeEmpty) {
		super(serializeNulls, serializeEmpty);
		this.packer = MessagePack.newDefaultBufferPacker();
	}

	@Override
	public String result() {
		MessageBufferPacker packer = (MessageBufferPacker) this.packer;
		return new String(packer.toByteArray());
	}
}