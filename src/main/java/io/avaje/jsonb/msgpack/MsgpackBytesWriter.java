package io.avaje.jsonb.msgpack;

import io.avaje.jsonb.spi.BufferedJsonWriter;
import io.avaje.jsonb.spi.BytesJsonWriter;
import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.ByteArrayOutputStream;

/**
 * Implementation of {@link BufferedJsonWriter} for MessagePack.
 */
final class MsgpackBytesWriter extends MsgpackWriter implements BytesJsonWriter {

	MsgpackBytesWriter(boolean serializeNulls, boolean serializeEmpty) {
		super(serializeNulls, serializeEmpty);
		this.packer = MessagePack.newDefaultBufferPacker();
	}

	@Override
	public byte[] result() {
		MessageBufferPacker packer = (MessageBufferPacker) this.packer;
		return packer.toByteArray();
	}
}