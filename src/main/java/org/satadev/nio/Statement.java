package org.satadev.nio;

public record Statement(StatementType statementType, Row rowToInsert) {
}
