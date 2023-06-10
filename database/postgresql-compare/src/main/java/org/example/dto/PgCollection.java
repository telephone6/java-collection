package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PgCollection {

    private List<PgClass> pgClassList;

    private List<PgAttribute> pgAttributeList;

    private List<PgDescription> pgDescriptionList;

    private List<PgIndex> pgIndexList;

    private List<PgType> pgTypeList;
}
