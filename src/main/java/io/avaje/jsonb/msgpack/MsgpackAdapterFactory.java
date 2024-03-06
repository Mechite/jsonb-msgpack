package io.avaje.jsonb.msgpack;

import io.avaje.jsonb.spi.AdapterFactory;
import io.avaje.jsonb.spi.JsonStreamAdapter;

/**
 * Implementation of {@link AdapterFactory} for the {@link MsgpackAdapter} instances
 * to be automatically created with default settings if no configuration is desired
 * and the {@code io.avaje.jsonb.msgpack} module is found on the module path (or on
 * the classpath via {@code META-INF/services/io.avaje.jsonb.spi.AdapterFactory}).
 *
 * @see MsgpackAdapter
 * @since 1.11
 * @author Mechite
 */
public final class MsgpackAdapterFactory implements AdapterFactory {

	@Override
	public JsonStreamAdapter create(boolean serializeNulls, boolean serializeEmpty, boolean failOnUnknown) {
		return new MsgpackAdapter(serializeNulls, serializeEmpty, failOnUnknown);
	}
}