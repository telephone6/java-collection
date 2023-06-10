package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PgClass {
    private String oid;
    private String relname;
    private String relnamespace;
    private String reltype;
    private String reloftype;
    private String relowner;
    private String relam;
    private String relfilenode;
    private String reltablespace;
    private String relpages;
    private String reltuples;
    private String relallvisible;
    private String reltoastrelid;
    private String relhasindex;
    private String relisshared;
    private String relpersistence;
    private String relkind;
    private String relnatts;
    private String relchecks;
    private String relhasrules;
    private String relhastriggers;
    private String relhassubclass;
    private String relrowsecurity;
    private String relforcerowsecurity;
    private String relispopulated;
    private String relreplident;
    private String relispartition;
    private String relrewrite;
    private String relfrozenxid;
    private String relminmxid;
    private String relacl;
    private String reloptions;
    private String relpartbound;
}
