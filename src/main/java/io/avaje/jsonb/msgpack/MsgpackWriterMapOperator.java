package io.avaje.jsonb.msgpack;

import io.avaje.jsonb.JsonIoException;
import org.msgpack.core.MessagePacker;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Implementation of {@link MsgpackWriterOperator} that writes a map.
 */
final class MsgpackWriterMapOperator implements MsgpackWriterOperator {

	private final MsgpackWriterOperator parent;
	private final Set<Consumer<MessagePacker>> operators;

	MsgpackWriterMapOperator() {
		this(null);
	}

	MsgpackWriterMapOperator(MsgpackWriterOperator parent) {
		this.parent = parent;
		this.operators = new HashSet<>();
	}

	@Override
	public Optional<MsgpackWriterOperator> parent() {
		return Optional.ofNullable(this.parent);
	}

	@Override
	public void operation(MessagePackerConsumer operator) {
		this.operators.add(operator);
	}

	@Override
	public void operate(MessagePacker packer) {
		try {
			packer.packMapHeader(this.operators.size() / 2);
			for (Consumer<MessagePacker> operator : this.operators) operator.accept(packer);
		} catch (Exception exception) {
			throw new JsonIoException(new IOException(exception));
		}
	}
}