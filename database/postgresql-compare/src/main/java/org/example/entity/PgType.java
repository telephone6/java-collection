package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PgType {
    private String oid;
    private String typname;
    private String typnamespace;
    private String typowner;
    private String typlen;
    private String typbyval;
    private String typtype;
    private String typcategory;
    private String typispreferred;
    private String typisdefined;
    private String typdelim;
    private String typrelid;
    private String typsubscript;
    private String typelem;
    private String typarray;
    private String typinput;
    private String typoutput;
    private String typreceive;
    private String typsend;
    private String typmodin;
    private String typmodout;
    private String typanalyze;
    private String typalign;
    private String typstorage;
    private String typnotnull;
    private String typbasetype;
    private String typtypmod;
    private String typndims;
    private String typcollation;
    private String typdefaultbin;
    private String typdefault;
    private String typacl;
}
