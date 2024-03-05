package io.avaje.jsonb.msgpack;

import io.avaje.jsonb.JsonReader;
import io.avaje.jsonb.JsonWriter;
import io.avaje.jsonb.spi.BufferedJsonWriter;
import io.avaje.jsonb.spi.BytesJsonWriter;
import io.avaje.jsonb.spi.JsonStreamAdapter;
import io.avaje.jsonb.spi.PropertyNames;
import io.avaje.jsonb.stream.JsonOutput;
import org.msgpack.core.MessagePack;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * Implementation of {@link JsonStreamAdapter} for MessagePack.
 * A usage exemplar for explicit usage is as follows:
 * <pre>{@code
 *     Jsonb jsonb = Jsonb.builder()
 *             .adapter(new MsgpackAdapter())
 *             .build();
 * }</pre>
 *
 * <h3>Service loading initialise</h3>
 * Including the {@code io.avaje:avaje-jsonb-msgpack} dependency in the classpath will,
 * by default, cause this adapter to be automatically selected via service loading.
 * <p>
 * Should this be undesirable, e.g., if the default constructor options for the
 * serialization of null values, empty arrays, etc. is undesirable, explicitly using
 * the adapter as demonstrated above would allow this to be configured as desired.
 * <h3>Unimplemented Methods</h3>
 * <ul>
 *     <li>{@link MsgpackAdapter#bufferedWriter()}</li>
 *     <li>{@link MsgpackAdapter#bufferedWriterAsBytes()}</li>
 *     <li>{@link MsgpackAdapter#properties(String...)}</li>
 * </ul>
 * These will be implemented in a future release. {@link UnsupportedOperationException}
 * will be thrown should they be called, detail message reading <i>Not implemented yet</i>.
 *
 * @author Mechite
 * @since 1u1
 */
public class MsgpackAdapter implements JsonStreamAdapter {

	private final boolean serializeNulls;
	private final boolean serializeEmpty;
	private final boolean failOnUnknown;

	/**
	 * No-argument constructor that creates an adapter with default settings
	 * as outlined below:
	 * <ul>
	 *     <li>serializeNulls true</li>
	 *     <li>serializeEmpty true</li>
	 *     <li>failOnUnknown false</li>
	 * </ul>
	 *
	 * @see MsgpackAdapter#MsgpackAdapter(boolean, boolean, boolean)
	 * @since 1u1
	 */
	public MsgpackAdapter() {
		this(true, true, false);
	}

	/**
	 * Constructor that creates an adapter with the provided settings.
	 *
	 * @param serializeNulls Whether to enable the serialization of {@code null} values.
	 * @param serializeEmpty Whether to enable the serialization of empty <i>arrays</i>.
	 * @param failOnUnknown Whether to fail when deserializing unknown properties.
	 *
	 * @see MsgpackAdapter#MsgpackAdapter()
	 * @since 1u1
	 */
	public MsgpackAdapter(boolean serializeNulls, boolean serializeEmpty, boolean failOnUnknown) {
		this.serializeNulls = serializeNulls;
		this.serializeEmpty = serializeEmpty;
		this.failOnUnknown = failOnUnknown;
	}

	@Override
	public JsonReader reader(String string) {
		return this.reader(string.getBytes());
	}

	@Override
	public JsonReader reader(byte[] bytes) {
		return new MsgpackReader(MessagePack.newDefaultUnpacker(bytes), this.failOnUnknown);
	}

	@Override
	public JsonReader reader(Reader reader) {
		return this.reader(new ReaderInputStream(reader));
	}

	@Override
	public JsonReader reader(InputStream stream) {
		return new MsgpackReader(MessagePack.newDefaultUnpacker(stream), this.failOnUnknown);
	}

	@Override
	public JsonWriter writer(Writer writer) {
		return this.writer(new WriterOutputStream(writer));
	}

	@Override
	public JsonWriter writer(JsonOutput output) {
		return this.writer(output.unwrapOutputStream());
	}

	@Override
	public JsonWriter writer(OutputStream stream) {
		return new MsgpackWriter(MessagePack.newDefaultPacker(stream), this.serializeNulls, this.serializeEmpty);
	}

	@Override
	public BufferedJsonWriter bufferedWriter() {
		return new MsgpackBufferedWriter(this.serializeNulls, this.serializeEmpty);
	}

	@Override
	public BytesJsonWriter bufferedWriterAsBytes() {
		return new MsgpackBytesWriter(this.serializeNulls, this.serializeEmpty);
	}

	@Override
	public PropertyNames properties(String... names) {
		return new MsgspackPropertyNames(names);
	}
}