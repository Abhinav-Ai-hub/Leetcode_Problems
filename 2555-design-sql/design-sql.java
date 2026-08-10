class SQL {

    // We can simplify things if we store table-specific data in a class.
    private class Table {
        int autoIncId;
        final int columnSize;
        final Map<Integer, List<String>> rows; // Key row id, value List<String>

        Table(int columnSize) {
            this.autoIncId = 1; // 1-indexed ids
            this.columnSize = columnSize;
            this.rows = new HashMap<>();
        }
    }

    // A HashMap to store all tables (key name, value Table)
    final Map<String, Table> tables;

    public SQL(List<String> names, List<Integer> columns) {
        // We can tell HashMap the capacity we need and the load factor. An
        // optimization.
        // Since we do not add any tables, we can have a load factor of 1, where the
        // HashMap will not increase its capacity unless we insert more than capacity
        // (default load factor of 0.75, which would cause an allocation)
        tables = new HashMap<>(names.size(), 1f);
        for (int i = 0; i < names.size(); i++) {
            tables.put(names.get(i), new Table(columns.get(i)));
        }
    }

    public boolean ins(String name, List<String> row) {
        var table = tables.get(name);
        if (table == null || table.columnSize != row.size())
            return false;
        table.rows.put(table.autoIncId++, row);
        return true;
    }

    public void rmv(String name, int rowId) {
        var table = tables.get(name);
        if (table == null)
            return;
        table.rows.remove(rowId); // Won't do anything if does not exist
    }

    public String sel(String name, int rowId, int columnId) {
        var table = tables.get(name);
        if (table == null)
            return "<null>";
        var row = table.rows.get(rowId);
        // They don't tell you this but columnId is 1-indexed
        if (row == null || columnId > row.size())
            return "<null>";
        return row.get(columnId - 1);
    }

    public List<String> exp(String name) {
        var table = tables.get(name);
        if (table == null)
            return List.of();
        // EntrySet converts a HashMap into a list of K/V pairs
        var entrySet = table.rows.entrySet();
        // We choose a LinkedList since we will only ever append strings
        List<String> ret = new LinkedList<>();
        for (var row : entrySet) {
            ret.add(row.getKey().toString() + "," + String.join(",", row.getValue()));
        }
        return ret;
    }
}

/**
 * Your SQL object will be instantiated and called as such:
 * SQL obj = new SQL(names, columns);
 * boolean param_1 = obj.ins(name,row);
 * obj.rmv(name,rowId);
 * String param_3 = obj.sel(name,rowId,columnId);
 * List<String> param_4 = obj.exp(name);
 */