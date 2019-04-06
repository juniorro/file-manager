package io.heat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author <a href="mailto:raphaeledwenson@gmail.com">eraphael - Edwenson Raphael</a>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferReport {

    private Integer storedFile;

    private Integer failedFile;
}
