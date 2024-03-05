package io.avaje.jsonb.msgpack;

import io.avaje.jsonb.spi.BufferedJsonWriter;
import org.msgpack.core.MessagePack;

import java.io.ByteArrayOutputStream;

/**
 * Implementation of {@link BufferedJsonWriter} for MessagePack.
 */
final class MsgpackBufferedWriter extends MsgpackWriter implements BufferedJsonWriter {

	private final ByteArrayOutputStream stream;

	MsgpackBufferedWriter(boolean serializeNulls, boolean serializeEmpty) {
		super(serializeNulls, serializeEmpty);
		this.stream = new ByteArrayOutputStream();
		this.packer = MessagePack.newDefaultPacker(this.stream);
	}

	@Override
	public String result() {
		return this.stream.toString();
	}
}