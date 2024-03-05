package io.avaje.jsonb.msgpack;

import io.avaje.jsonb.spi.BufferedJsonWriter;
import io.avaje.jsonb.spi.BytesJsonWriter;
import org.msgpack.core.MessagePack;

import java.io.ByteArrayOutputStream;

/**
 * Implementation of {@link BufferedJsonWriter} for MessagePack.
 */
final class MsgpackBytesWriter extends MsgpackWriter implements BytesJsonWriter {

	private final ByteArrayOutputStream stream;

	MsgpackBytesWriter(boolean serializeNulls, boolean serializeEmpty) {
		super(serializeNulls, serializeEmpty);
		this.stream = new ByteArrayOutputStream();
		this.packer = MessagePack.newDefaultPacker(this.stream);
	}

	@Override
	public byte[] result() {
		return this.stream.toByteArray();
	}
}