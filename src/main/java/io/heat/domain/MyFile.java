package io.heat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MyFile implements Serializable {

    /**
     * Might more interesting to provide the filename with its extension
     * */
    private String shortName;

    private String longName;

    /**
     * should be encoded in Base64
     * */
    private String raw;

    private String ownerName;
}
