package me.nort721.simpleflyplugin;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProtocolVersion {
    V1_7(4, "v1_7_R3"),
    V1_7_10(5, "v1_7_R4"),
    V1_8(45, "v1_8_R1"),
    V1_8_5(46, "v1_8_R2"),
    V1_8_9(47, "v1_8_R3"),
    V1_9(107, "v1_9_R1"),
    V1_9_1(108, null),
    V1_9_2(109, "v1_9_R2"),
    V1_9_4(110, "v1_9_R2"),
    V1_10(210, "v1_10_R1"),
    V1_10_1(210, "v1_10_R2"),
    V1_10_2(210, "v1_10_R3"),
    V1_11(315, "v1_11_R1"),
    V1_11_1(316, "v1_11_R2"),
    V1_11_2(316, "v1_11_R3"),
    V1_12(338, "v1_12_R1"),
    V1_12_1(338, "v1_12_R2"),
    V1_12_2(340, "v1_12_R3"),
    V1_13(393, "v1_13_R1"),
    V1_13_1(401, "v1_13_R2"),
    V1_13_2(404, "v1_13_R3"),
    V1_14(477, "v1_14_R1"),
    V1_14_1(480, "v1_14_R2"),
    V1_14_2(485, "v1_14_R3"),
    V1_14_3(490, "v1_14_R4"),
    V1_14_4(498, "v1_14_R5"),
    V1_15(573, "v1_15_R1"),
    V1_15_1(575, "v1_15_R2"),
    V1_15_2(578, "v1_15_R3"),
    V1_16(735, "v1_16_R1"),
    V1_16_1(736, "v1_16_R2"),
    V1_16_2(737, "v1_16_R3"),
    V1_16_3(738, "v1_16_R4"),
    V1_16_4(739, "v1_16_R5"),
    V1_16_5(740, "v1_16_R6"),
    V1_17(755, "v1_17_R1"),
    V1_17_1(756, "v1_17_R2"),
    V1_18(757, "v1_18_R1"),
    V1_18_1(758, "v1_18_R2"),
    V1_18_2(759, "v1_18_R3"),
    V1_19(760, "v1_19_R1"),
    V1_19_3(761, "v1_19_R2"),
    UNKNOWN(999, "UNKNOWN");

    @Getter private static final ProtocolVersion gameVersion = fetchGameVersion();
    private int version;
    private String serverVersion;

    private static ProtocolVersion fetchGameVersion() {
        for (ProtocolVersion version : values()) {
            if (version.getServerVersion() != null && version.getServerVersion().equals(SimpleFlyPlugin.VERSION))
                return version;
        }
        return UNKNOWN;
    }

    public static ProtocolVersion getVersion(int versionId) {
        for (ProtocolVersion version : values()) {
            if (version.getVersion() == versionId) return version;
        }
        return UNKNOWN;
    }

    public boolean isBelow(ProtocolVersion version) {
        return this.getVersion() < version.getVersion();
    }

    public boolean isAbove(ProtocolVersion version) {
        return this.getVersion() > version.getVersion();
    }

    public boolean isOrAbove(ProtocolVersion version) {
        return this.getVersion() >= version.getVersion();
    }
}

