package org.example.util;

import org.example.dto.PgCollection;
import org.example.entity.PgAttribute;
import org.example.entity.PgClass;
import org.example.entity.PgDescription;
import org.example.entity.PgType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PgClassComparator {

    public static void compare(PgCollection source, PgCollection target) {
        List<PgClass> sourcePgClassList = source.getPgClassList();
        List<PgClass> targetPgClassList = target.getPgClassList();

        List<PgAttribute> sourcePgAttributeList = source.getPgAttributeList();
        List<PgAttribute> targetPgAttributeList = target.getPgAttributeList();

        List<PgType> sourcePgTypeList = source.getPgTypeList();
        List<PgType> targetPgTypeList = target.getPgTypeList();

        List<PgDescription> sourcePgDescriptionList = source.getPgDescriptionList();
        List<PgDescription> targetPgDescriptionList = target.getPgDescriptionList();

        for (PgClass sourcePgClass : sourcePgClassList) {
            String sourceRelName = sourcePgClass.getRelname();

            Optional<PgClass> firstPgClass = targetPgClassList.stream().filter(s -> s.getRelname().equals(sourceRelName)).findFirst();
            if (!firstPgClass.isPresent()) {
                // target table less of table
                System.out.println("目标库缺少表[" + sourceRelName + "]");
                continue;
            }

            String sourceOid = sourcePgClass.getOid();
            String targetOid = firstPgClass.get().getOid();

            List<PgAttribute> sourceAttributes = sourcePgAttributeList.stream().filter(s -> s.getAttrelid().equals(sourceOid)
                    && sourcePgTypeList.stream().anyMatch(t -> t.getOid().equals(s.getAtttypid()))).collect(Collectors.toList());
            List<PgAttribute> targetAttributes = targetPgAttributeList.stream().filter(s -> s.getAttrelid().equals(targetOid)
                    && targetPgTypeList.stream().anyMatch(t -> t.getOid().equals(s.getAtttypid()))
            ).collect(Collectors.toList());
            compareAttribute(sourceRelName, sourceAttributes, sourcePgTypeList, sourcePgDescriptionList, targetAttributes, targetPgTypeList, targetPgDescriptionList);

            compareTableDescription(sourceRelName, sourcePgDescriptionList, sourceOid, targetPgDescriptionList, targetOid);
        }
    }

    private static void compareAttribute(
            String tableName,
            List<PgAttribute> sourceAttributes,
            List<PgType> sourcePgTypeList,
            List<PgDescription> sourcePgDescriptionList,
            List<PgAttribute> targetAttributes,
            List<PgType> targetPgTypeList,
            List<PgDescription> targetPgDescriptionList
    ) {
        for (PgAttribute sourcePgAttribute : sourceAttributes) {
            if (Integer.parseInt(sourcePgAttribute.getAttnum()) <= 0) {
                continue;
            }

            String sourceAttName = sourcePgAttribute.getAttname();

            Optional<PgAttribute> targetPgAttributeOption = targetAttributes.stream().filter(s -> s.getAttname().equals(sourceAttName)
                    && Integer.parseInt(s.getAttnum()) > 0
            ).findFirst();
            if (!targetPgAttributeOption.isPresent()) {
                // target table less of column
                System.out.println("目标库表[" + tableName + "]缺少字段[" + sourceAttName + "]");
                continue;
            }
            PgAttribute targetPgAttribute = targetPgAttributeOption.get();

            Optional<PgType> sourcePgTypeOption = sourcePgTypeList.stream().filter(s -> s.getOid().equals(sourcePgAttribute.getAtttypid())).findFirst();
            Optional<PgType> targetPgTypeOption = targetPgTypeList.stream().filter(s -> s.getOid().equals(targetPgAttribute.getAtttypid())).findFirst();
            if (sourcePgTypeOption.isPresent() && targetPgTypeOption.isPresent()) {
                PgType sourcePgType = sourcePgTypeOption.get();
                PgType targetPgType = targetPgTypeOption.get();

                if (!sourcePgType.getTypname().equals(targetPgType.getTypname())
                        && !sourcePgType.getTyplen().equals(targetPgType.getTyplen())) {
                    // target table has different column defined
                    System.out.println("目标库表[" + tableName + "]的字段[" + sourceAttName + "]类型不一致." +
                            "目标类型为[" + targetPgType.getTypname() + "]长度[" + targetPgType.getTyplen() + "], " +
                            "源类型为[" + sourcePgType.getTypname() + "]长度[" + sourcePgType.getTyplen() + "]");
                }
            }

            String sourceAttrelid = sourcePgAttribute.getAttrelid();
            String sourceAttnum = sourcePgAttribute.getAttnum();
            String targetAttrelid = targetPgAttribute.getAttrelid();
            String targetAttnum = targetPgAttribute.getAttnum();
            compareColumnDescription(tableName, sourceAttName, sourcePgDescriptionList, sourceAttrelid, sourceAttnum
                    , targetPgDescriptionList, targetAttrelid, targetAttnum
            );
        }
    }

    private static void compareTableDescription(String tableName, List<PgDescription> sourcePgDescriptionList, String sourceOid, List<PgDescription> targetPgDescriptionList, String targetOid) {

        Optional<PgDescription> sourcePgDescriptionOption = sourcePgDescriptionList.stream()
                .filter(s -> "0".equals(s.getObjsubid()) && s.getObjoid().equals(sourceOid)).findFirst();
        Optional<PgDescription> targetPgDescriptionOption = targetPgDescriptionList.stream()
                .filter(s -> "0".equals(s.getObjsubid()) && s.getObjoid().equals(targetOid)).findFirst();
        if(sourcePgDescriptionOption.isPresent() && targetPgDescriptionOption.isPresent()){
            PgDescription sourcePgDescription = sourcePgDescriptionOption.get();
            PgDescription targetPgDescription = targetPgDescriptionOption.get();
            if(!sourcePgDescription.getDescription().equals(targetPgDescription.getDescription())){
                // target table has different column description
                System.out.println("目标库表[" + tableName + "]的注释不一致." +
                        "目标注释为[" + targetPgDescription.getDescription() + "]," +
                        "源注释为[" + sourcePgDescription.getDescription() + "]"
                );
            }

        }
    }


    private static void compareColumnDescription(String tableName, String columnName, List<PgDescription> sourcePgDescriptionList, String sourceAttrelid, String sourceAttnum,
                                                 List<PgDescription> targetPgDescriptionList, String targetAttrelid, String targetAttnum) {

        Optional<PgDescription> sourcePgDescriptionOption = sourcePgDescriptionList.stream()
                .filter(s -> s.getObjoid().equals(sourceAttrelid) && s.getObjsubid().equals(sourceAttnum)).findFirst();
        Optional<PgDescription> targetPgDescriptionOption = targetPgDescriptionList.stream()
                .filter(s -> s.getObjoid().equals(targetAttrelid) && s.getObjsubid().equals(targetAttnum)).findFirst();
        if(sourcePgDescriptionOption.isPresent() && targetPgDescriptionOption.isPresent()){
            PgDescription sourcePgDescription = sourcePgDescriptionOption.get();
            PgDescription targetPgDescription = targetPgDescriptionOption.get();
            if(!sourcePgDescription.getDescription().equals(targetPgDescription.getDescription())){
                System.out.println("目标库表[" + tableName + "]字段[" + columnName + "]的注释不一致." +
                        "目标注释为[" + targetPgDescription.getDescription() + "]," +
                        "源注释为[" + sourcePgDescription.getDescription() + "]"
                );
            }

        }
    }
}
