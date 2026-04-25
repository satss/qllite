package org.satadev.nio;

public enum StatementType {
    STATEMENT_SELECT("SELECT"),
    STATEMENT_INSERT("INSERT")
    ;
    private String statementName;

    StatementType(String statementName) {
        this.statementName = statementName;
    }
    public String getStatementName() {
       return this.statementName;
    }
}
