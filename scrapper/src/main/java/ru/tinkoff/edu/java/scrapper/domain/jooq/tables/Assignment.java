/*
 * This file is generated by jOOQ.
 */
package ru.tinkoff.edu.java.scrapper.domain.jooq.tables;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import javax.annotation.processing.Generated;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Function2;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Records;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;
import ru.tinkoff.edu.java.scrapper.domain.jooq.DefaultSchema;
import ru.tinkoff.edu.java.scrapper.domain.jooq.Keys;
import ru.tinkoff.edu.java.scrapper.domain.jooq.tables.records.AssignmentRecord;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "https://www.jooq.org",
        "jOOQ version:3.18.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Assignment extends TableImpl<AssignmentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>ASSIGNMENT</code>
     */
    public static final Assignment ASSIGNMENT = new Assignment();

    /**
     * The class holding records for this type
     */
    @Override
    @NotNull
    public Class<AssignmentRecord> getRecordType() {
        return AssignmentRecord.class;
    }

    /**
     * The column <code>ASSIGNMENT.CHAT_ID</code>.
     */
    public final TableField<AssignmentRecord, Long> CHAT_ID = createField(DSL.name("CHAT_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>ASSIGNMENT.LINK_ID</code>.
     */
    public final TableField<AssignmentRecord, Long> LINK_ID = createField(DSL.name("LINK_ID"), SQLDataType.BIGINT.nullable(false), this, "");

    private Assignment(Name alias, Table<AssignmentRecord> aliased) {
        this(alias, aliased, null);
    }

    private Assignment(Name alias, Table<AssignmentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>ASSIGNMENT</code> table reference
     */
    public Assignment(String alias) {
        this(DSL.name(alias), ASSIGNMENT);
    }

    /**
     * Create an aliased <code>ASSIGNMENT</code> table reference
     */
    public Assignment(Name alias) {
        this(alias, ASSIGNMENT);
    }

    /**
     * Create a <code>ASSIGNMENT</code> table reference
     */
    public Assignment() {
        this(DSL.name("ASSIGNMENT"), null);
    }

    public <O extends Record> Assignment(Table<O> child, ForeignKey<O, AssignmentRecord> key) {
        super(child, key, ASSIGNMENT);
    }

    @Override
    @Nullable
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    @NotNull
    public UniqueKey<AssignmentRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_5A1;
    }

    @Override
    @NotNull
    public List<ForeignKey<AssignmentRecord, ?>> getReferences() {
        return Arrays.asList(Keys.CONSTRAINT_5, Keys.CONSTRAINT_5A);
    }

    private transient TelegramChat _telegramChat;
    private transient Link _link;

    /**
     * Get the implicit join path to the <code>PUBLIC.TELEGRAM_CHAT</code>
     * table.
     */
    public TelegramChat telegramChat() {
        if (_telegramChat == null)
            _telegramChat = new TelegramChat(this, Keys.CONSTRAINT_5);

        return _telegramChat;
    }

    /**
     * Get the implicit join path to the <code>PUBLIC.LINK</code> table.
     */
    public Link link() {
        if (_link == null)
            _link = new Link(this, Keys.CONSTRAINT_5A);

        return _link;
    }

    @Override
    @NotNull
    public Assignment as(String alias) {
        return new Assignment(DSL.name(alias), this);
    }

    @Override
    @NotNull
    public Assignment as(Name alias) {
        return new Assignment(alias, this);
    }

    @Override
    @NotNull
    public Assignment as(Table<?> alias) {
        return new Assignment(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Assignment rename(String name) {
        return new Assignment(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Assignment rename(Name name) {
        return new Assignment(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    @NotNull
    public Assignment rename(Table<?> name) {
        return new Assignment(name.getQualifiedName(), null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    @NotNull
    public Row2<Long, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Function)}.
     */
    public <U> SelectField<U> mapping(Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(Records.mapping(from));
    }

    /**
     * Convenience mapping calling {@link SelectField#convertFrom(Class,
     * Function)}.
     */
    public <U> SelectField<U> mapping(Class<U> toType, Function2<? super Long, ? super Long, ? extends U> from) {
        return convertFrom(toType, Records.mapping(from));
    }
}
