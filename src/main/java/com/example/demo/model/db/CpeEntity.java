package com.example.demo.model.db;

import com.example.demo.model.json.JsonListConverter;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.*;
import org.hibernate.type.SqlTypes;

@Data
@Entity
@Table(name = "cpe")
public class CpeEntity implements Serializable {

    @Id
    @Column(name = "cpe_name_id", unique = true, nullable = false)
    private UUID cpeNameId;

    @Column(name = "cpe_name", nullable = false)
    private String cpeName;

    @Column(name = "deprecated", nullable = false)
    private boolean deprecated;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "last_modified", nullable = false)
    private LocalDateTime lastModified;

    @Convert(converter = TitlesJsonConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "title")
    private List<TitlesJson> titles;

    @Convert(converter = RefsJsonConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "reference")
    private List<RefsJson> refs;

    @Convert(converter = DeprecatedByJsonConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "deprecated_by")
    private List<DeprecatedByJson> deprecatedBy;

    @Convert(converter = DeprecatesJsonConverter.class)
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "deprecates")
    private List<DeprecatesJson> deprecates;

    @Data
    public static class TitlesJson implements Serializable {
        private String title;
        private String lang;
        public TitlesJson() {
        }
    }

    @Data
    public static class RefsJson implements Serializable {
        private String ref;
        private String type;
        public RefsJson() {
        }
    }

    @Data
    public static class DeprecatedByJson implements Serializable {
        private String cpeName;
        private String cpeNameId;
        public DeprecatedByJson() {
        }
    }

    @Data
    public static class DeprecatesJson implements Serializable {
        private String cpeName;
        private String cpeNameId;
        public DeprecatesJson() {
        }
    }

    @Converter
    public static class TitlesJsonConverter extends JsonListConverter<TitlesJson> {
    }

    @Converter
    public static class RefsJsonConverter extends JsonListConverter<RefsJson> {
    }

    @Converter
    public static class DeprecatedByJsonConverter extends JsonListConverter<DeprecatedByJson> {
    }

    @Converter
    public static class DeprecatesJsonConverter extends JsonListConverter<DeprecatesJson> {
    }

}