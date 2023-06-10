package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PgIndex {
    private String schemaname;
    private String tablename;
    private String indexname;
    private String tablespace;
    private String indexdef;
}
