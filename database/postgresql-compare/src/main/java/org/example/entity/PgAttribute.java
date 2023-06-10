package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PgAttribute {
    private String attrelid;
    private String attname;
    private String atttypid;
    private String attstattarget;
    private String attlen;
    private String attnum;
    private String attndims;
    private String attcacheoff;
    private String atttypmod;
    private String attbyval;
    private String attalign;
    private String attstorage;
    private String attcompression;
    private String attnotnull;
    private String atthasdef;
    private String atthasmissing;
    private String attidentity;
    private String attgenerated;
    private String attisdropped;
    private String attislocal;
    private String attinhcount;
    private String attcollation;
    private String attacl;
    private String attoptions;
    private String attfdwoptions;
    private String attmissingval;
}
