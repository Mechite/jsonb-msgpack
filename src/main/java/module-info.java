import io.avaje.jsonb.msgpack.MsgpackAdapterFactory;

module io.avaje.jsonb.msgpack {

	requires jdk.unsupported;
	requires transitive io.avaje.jsonb;

	exports org.msgpack.core;
	exports org.msgpack.core.annotations;
	exports org.msgpack.core.buffer;

	exports org.msgpack.value;
	exports org.msgpack.value.impl;

	exports io.avaje.jsonb.msgpack;

	provides io.avaje.jsonb.spi.AdapterFactory with MsgpackAdapterFactory;
}